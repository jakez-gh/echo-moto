# Echo Companion - Deployment Guide

## 📱 Deployment Options

Echo Companion can be deployed to your Moto G device using multiple methods:

### Option 1: Direct Deployment via USB/Wireless ADB (Recommended)

Use the provided deployment script for the easiest experience.

#### Prerequisites
- Ubuntu laptop with Android SDK installed
- Moto G with Developer Options and USB Debugging enabled
- USB cable or Wi-Fi connection (for wireless ADB)

#### Steps

1. **Enable Developer Options on Moto G**:
   - Go to Settings → About phone
   - Tap "Build number" 7 times
   - Go back to Settings → System → Developer options
   - Enable "USB debugging"
   - (Optional) Enable "Wireless debugging" for wireless deployment

2. **Run the deployment script**:
   ```bash
   ./deploy_to_moto_g.sh
   ```
   
   The script will:
   - Check for required tools (ADB, Java)
   - Build the APK
   - Connect to your device (USB or wireless)
   - Install and launch the app

### Option 2: GitHub Actions Automatic Build

GitHub automatically builds the APK when you push code.

#### Setup

1. **Push code to GitHub**:
   ```bash
   git add .
   git commit -m "Initial Echo Companion implementation"
   git push origin main
   ```

2. **Download APK from GitHub Actions**:
   - Go to your GitHub repository
   - Click on "Actions" tab
   - Click on the latest successful workflow run
   - Download the `echo-companion-debug-apk` artifact

3. **Install manually**:
   ```bash
   adb install app-debug.apk
   ```

### Option 3: Deploy from GitHub Artifacts

Use the GitHub deployment script to automatically download and install the latest build:

1. **Configure the script**:
   - Edit `deploy_from_github.sh`
   - Update `GITHUB_REPO` with your GitHub username

2. **Run the script**:
   ```bash
   ./deploy_from_github.sh
   ```

### Option 4: Firebase App Distribution (Optional)

For easier distribution without cables:

1. **Setup Firebase** (if desired):
   - Create a Firebase project
   - Add Android app with package name `com.echo.companion`
   - Download `google-services.json` to `app/` directory

2. **Add Firebase plugin** to `app/build.gradle.kts`:
   ```kotlin
   plugins {
       id("com.google.gms.google-services")
       id("com.google.firebase.appdistribution")
   }
   ```

3. **Configure distribution** in GitHub Actions

## 🔧 Wireless ADB Setup (One-time)

For cable-free deployment:

1. **On Moto G**:
   - Settings → System → Developer options
   - Enable "Wireless debugging"
   - Tap "Pair device with pairing code"

2. **On Ubuntu**:
   ```bash
   # Pair with the code shown on phone
   adb pair 192.168.1.100:37853 123456
   
   # Connect to the device
   adb connect 192.168.1.100:43517
   ```

3. **Future connections**:
   ```bash
   # Just connect without pairing
   adb connect 192.168.1.100:5555
   ```

## 🚀 Quick Deploy Commands

```bash
# Build and install via USB
./deploy_to_moto_g.sh

# Download and install from GitHub
./deploy_from_github.sh

# Manual ADB commands
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.echo.companion/.MainActivity

# View logs
adb logcat | grep "com.echo.companion"

# Take screenshot
adb shell screencap -p /sdcard/echo_screenshot.png
adb pull /sdcard/echo_screenshot.png
```

## 📝 Development Workflow

1. **Make changes** to the code
2. **Run tests**: `./gradlew test`
3. **Build locally**: `./gradlew assembleDebug`
4. **Deploy**: `./deploy_to_moto_g.sh`
5. **Test on device**
6. **Commit and push** for automatic GitHub build

## ⚙️ Troubleshooting

### Device not detected
- Ensure USB debugging is enabled
- Try different USB cable/port
- Revoke and re-authorize USB debugging permissions
- Use `adb kill-server && adb start-server`

### Build failures
- Check Java version: `java -version` (needs Java 17+)
- Clear build: `./gradlew clean`
- Check Android SDK: `echo $ANDROID_HOME`

### Wireless ADB issues
- Ensure both devices are on same Wi-Fi network
- Check firewall settings on Ubuntu
- Try using IP address instead of hostname
- Restart wireless debugging on phone

## 📱 Device Requirements

- **Moto G** running Android 10+ (API 29+)
- Developer options enabled
- USB debugging enabled
- Storage space for app (~20MB)
- Permissions: Microphone, Notifications, Location (optional)

## 🔒 Security Notes

- The debug APK is signed with debug keys
- For production, create a release signing configuration
- Never commit signing keys to version control
- Use Firebase App Distribution for secure beta testing

## 📊 Monitoring

Once deployed, monitor the app:

```bash
# View real-time logs
adb logcat | grep -E "Echo|companion"

# Check app info
adb shell dumpsys package com.echo.companion

# Monitor performance
adb shell dumpsys cpuinfo | grep com.echo.companion

# Check permissions
adb shell dumpsys package com.echo.companion | grep permission
```