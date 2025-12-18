#!/bin/bash

# Echo Companion - Deploy from GitHub Actions
# This script downloads the APK from GitHub Actions and deploys it to your Moto G

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
GITHUB_REPO="YOUR_GITHUB_USERNAME/EchoBody-Private"  # Update this with your GitHub username
PACKAGE_NAME="com.echo.companion"
ARTIFACT_NAME="echo-companion-debug-apk"
TEMP_DIR="/tmp/echo-deployment"

print_message() {
    local color=$1
    local message=$2
    echo -e "${color}${message}${NC}"
}

main() {
    print_message $BLUE "========================================="
    print_message $BLUE "  Echo Companion - GitHub Deployment"
    print_message $BLUE "========================================="
    echo ""
    
    # Check if gh CLI is installed
    if ! command -v gh >/dev/null 2>&1; then
        print_message $YELLOW "GitHub CLI not found. Installing..."
        # Install GitHub CLI
        type -p curl >/dev/null || sudo apt install curl -y
        curl -fsSL https://cli.github.com/packages/githubcli-archive-keyring.gpg | sudo dd of=/usr/share/keyrings/githubcli-archive-keyring.gpg
        sudo chmod go+r /usr/share/keyrings/githubcli-archive-keyring.gpg
        echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/githubcli-archive-keyring.gpg] https://cli.github.com/packages stable main" | sudo tee /etc/apt/sources.list.d/github-cli.list > /dev/null
        sudo apt update
        sudo apt install gh -y
    fi
    
    # Check if logged into GitHub
    if ! gh auth status >/dev/null 2>&1; then
        print_message $YELLOW "Please login to GitHub:"
        gh auth login
    fi
    
    # Create temp directory
    rm -rf $TEMP_DIR
    mkdir -p $TEMP_DIR
    cd $TEMP_DIR
    
    # Get latest workflow run
    print_message $YELLOW "Fetching latest build from GitHub Actions..."
    
    # Download artifact
    gh run download -n $ARTIFACT_NAME -R $GITHUB_REPO --dir $TEMP_DIR
    
    # Find the APK
    APK_FILE=$(find $TEMP_DIR -name "*.apk" | head -n 1)
    
    if [ -z "$APK_FILE" ]; then
        print_message $RED "✗ APK not found in artifact"
        exit 1
    fi
    
    print_message $GREEN "✓ APK downloaded: $(basename $APK_FILE)"
    
    # Check ADB connection
    print_message $YELLOW "Checking device connection..."
    if ! adb devices | grep -q "device$"; then
        print_message $RED "✗ No device connected. Please connect your Moto G and enable USB debugging"
        exit 1
    fi
    
    print_message $GREEN "✓ Device connected"
    
    # Install APK
    print_message $YELLOW "Installing Echo Companion..."
    adb install -r "$APK_FILE"
    
    if [ $? -eq 0 ]; then
        print_message $GREEN "✓ Installation successful!"
        
        # Launch the app
        print_message $YELLOW "Launching Echo Companion..."
        adb shell monkey -p $PACKAGE_NAME -c android.intent.category.LAUNCHER 1
        
        print_message $GREEN "✓ App launched!"
    else
        print_message $RED "✗ Installation failed"
        exit 1
    fi
    
    # Cleanup
    rm -rf $TEMP_DIR
    
    echo ""
    print_message $GREEN "Deployment complete! 🚀"
}

main "$@"