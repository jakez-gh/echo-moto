```
Repository & Visibility Foundation
1.1. GitHub Private Repo Setup
1.1.1. Navigate browser to github.com/new
1.1.2. Name repo EchoBody-Private
1.1.3. Select Private
1.1.4. Initialize with README
1.1.5. Create repo
1.1.6. Clone locally via terminal git clone [URL]
1.2. GitHub Actions Basic APK Build
1.2.1. In repo create .github/workflows/android.yml
1.2.2. Paste YAML for Gradle assembleDebug on push
1.2.3. Commit and push—verify Actions artifact
Device & Wireless Deploy Prep
2.1. Moto G Wireless ADB
2.1.1. Settings > System > Developer options > Wireless debugging ON
2.1.2. Pair with code
2.1.3. adb connect [IP:port]
Project Creation & First Milestone
3.1. Android Studio Project
3.1.1. New Project > Empty Activity
3.1.2. Name EchoBody
3.1.3. Enable Compose
3.2. TDD Cycle 1: Visible "Echo Lives!"
3.2.1. Write red UI test assert text not exists
3.2.2. Add Text composable "Echo Lives!"
3.2.3. Green test
3.2.4. Commit + push
3.2.5. Build APK via Actions
3.2.6. adb install -r artifact.apk
3.2.7. Launch on Moto G—see text (first value!)
