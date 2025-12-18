#!/bin/bash

# Echo Companion - Deployment Script for Moto G
# This script builds and deploys the Echo Companion app to your Moto G device
# Requirements: 
#   - Android SDK installed on Ubuntu
#   - ADB (Android Debug Bridge) installed
#   - USB debugging enabled on Moto G
#   - Wireless debugging (optional)

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
APP_NAME="Echo Companion"
PACKAGE_NAME="com.echo.companion"
APK_PATH="app/build/outputs/apk/debug/app-debug.apk"

# Function to print colored messages
print_message() {
    local color=$1
    local message=$2
    echo -e "${color}${message}${NC}"
}

# Function to check if command exists
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Function to check ADB connection
check_adb_connection() {
    if adb devices | grep -q "device$"; then
        return 0
    else
        return 1
    fi
}

# Function to setup wireless ADB
setup_wireless_adb() {
    print_message $YELLOW "Setting up wireless ADB connection..."
    echo "Please ensure your Moto G and Ubuntu laptop are on the same Wi-Fi network."
    echo ""
    echo "On your Moto G:"
    echo "1. Go to Settings > System > Developer options"
    echo "2. Enable 'Wireless debugging'"
    echo "3. Tap 'Pair device with pairing code'"
    echo ""
    read -p "Enter the IP address and port shown on your phone (e.g., 192.168.1.100:37853): " ip_port
    read -p "Enter the pairing code: " pairing_code
    
    adb pair $ip_port $pairing_code
    
    echo ""
    echo "Now tap 'Wireless debugging' again to see the device IP and port (without pairing)"
    read -p "Enter the IP address and port for connection (e.g., 192.168.1.100:43517): " device_ip_port
    
    adb connect $device_ip_port
    
    if check_adb_connection; then
        print_message $GREEN "✓ Wireless ADB connected successfully!"
    else
        print_message $RED "✗ Failed to connect via wireless ADB"
        return 1
    fi
}

# Main deployment script
main() {
    print_message $BLUE "========================================="
    print_message $BLUE "    $APP_NAME Deployment Script"
    print_message $BLUE "========================================="
    echo ""

    # Check for required tools
    print_message $YELLOW "Checking requirements..."
    
    if ! command_exists adb; then
        print_message $RED "✗ ADB not found. Installing..."
        sudo apt-get update
        sudo apt-get install -y android-tools-adb android-tools-fastboot
    else
        print_message $GREEN "✓ ADB found"
    fi
    
    if ! command_exists java; then
        print_message $RED "✗ Java not found. Please install Java 17 or later:"
        echo "  sudo apt-get install openjdk-17-jdk"
        exit 1
    else
        print_message $GREEN "✓ Java found"
    fi
    
    # Check for device connection
    print_message $YELLOW "Checking for connected devices..."
    if ! check_adb_connection; then
        print_message $YELLOW "No device connected via USB."
        echo ""
        echo "Choose connection method:"
        echo "1) Connect via USB cable"
        echo "2) Connect via wireless ADB"
        echo "3) Exit"
        read -p "Enter choice (1-3): " choice
        
        case $choice in
            1)
                print_message $YELLOW "Please connect your Moto G via USB and ensure USB debugging is enabled."
                echo "Press Enter when ready..."
                read
                if ! check_adb_connection; then
                    print_message $RED "✗ No device detected. Please check:"
                    echo "  1. USB debugging is enabled"
                    echo "  2. You've authorized this computer on your phone"
                    echo "  3. USB cable is properly connected"
                    exit 1
                fi
                ;;
            2)
                if ! setup_wireless_adb; then
                    exit 1
                fi
                ;;
            3)
                exit 0
                ;;
            *)
                print_message $RED "Invalid choice"
                exit 1
                ;;
        esac
    fi
    
    # Display connected device
    print_message $GREEN "✓ Device connected:"
    adb devices
    echo ""
    
    # Build the APK
    print_message $YELLOW "Building $APP_NAME..."
    if [ -f "gradlew" ]; then
        ./gradlew assembleDebug
    else
        print_message $RED "✗ gradlew not found. Please ensure you're in the project root directory."
        exit 1
    fi
    
    # Check if APK was built
    if [ ! -f "$APK_PATH" ]; then
        print_message $RED "✗ APK not found at $APK_PATH"
        print_message $RED "Build may have failed. Check the output above."
        exit 1
    fi
    
    print_message $GREEN "✓ Build successful!"
    
    # Get APK size
    APK_SIZE=$(du -h "$APK_PATH" | cut -f1)
    print_message $BLUE "APK size: $APK_SIZE"
    
    # Uninstall previous version (if exists)
    print_message $YELLOW "Checking for existing installation..."
    if adb shell pm list packages | grep -q "$PACKAGE_NAME"; then
        print_message $YELLOW "Found existing installation. Uninstalling..."
        adb uninstall $PACKAGE_NAME
        print_message $GREEN "✓ Previous version uninstalled"
    fi
    
    # Install the APK
    print_message $YELLOW "Installing $APP_NAME on your Moto G..."
    adb install -r "$APK_PATH"
    
    if [ $? -eq 0 ]; then
        print_message $GREEN "✓ Installation successful!"
        
        # Launch the app
        print_message $YELLOW "Launching $APP_NAME..."
        adb shell monkey -p $PACKAGE_NAME -c android.intent.category.LAUNCHER 1
        
        print_message $GREEN "✓ App launched successfully!"
    else
        print_message $RED "✗ Installation failed"
        exit 1
    fi
    
    echo ""
    print_message $BLUE "========================================="
    print_message $GREEN "      Deployment Complete! 🚀"
    print_message $BLUE "========================================="
    
    # Show helpful commands
    echo ""
    print_message $BLUE "Useful ADB commands:"
    echo "  View logs:     adb logcat | grep $PACKAGE_NAME"
    echo "  Take screenshot: adb shell screencap -p /sdcard/screenshot.png && adb pull /sdcard/screenshot.png"
    echo "  Clear app data: adb shell pm clear $PACKAGE_NAME"
    echo "  Uninstall:     adb uninstall $PACKAGE_NAME"
    echo ""
    
    # Keep wireless connection info
    if adb get-state 2>&1 | grep -q "device"; then
        DEVICE_IP=$(adb shell ip route | awk '/wlan0/ {print $9}' | head -n 1)
        if [ ! -z "$DEVICE_IP" ]; then
            print_message $YELLOW "Device IP for future wireless connections: $DEVICE_IP"
            echo "To reconnect wirelessly later, use: adb connect $DEVICE_IP:5555"
        fi
    fi
}

# Run the main function
main "$@"