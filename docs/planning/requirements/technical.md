Non-Functional Requirements

- Performance: Partial module activation; <500ms latency target.
- Battery: Passive <5%/hour; throttle learns.
- Privacy/Security: Local encrypted storage; runtime permissions; differential privacy noise.
- Testing: TDD mandatory; red-green-refactor every feature.
- Development Visibility: Engineers see progress frequently via milestone deploys (GitHub Actions artifact + wireless ADB/Firebase free distro).
- Tools: GitHub private repo; optional Firebase Spark for one-click installs (generous free limits).


- Platform: Android (Moto G specific); Kotlin + Jetpack Compose.
- Inference: MediaPipe/NexaSDK; scratch micro-transformer init.
- Dependencies: Free/open-source (MediaPipe, Whisper-tiny quantized, Piper TTS).
- Version Control: Private GitHub repo; Actions auto-build APK artifacts.
- Distribution: Optional Firebase App Distribution (Spark free tier); fallback wireless ADB sideload.
- Testing: TDD mandatory; 80%+ coverage goal.
- Security: Runtime permissions; scoped storage; no telemetry.
- Performance: <500ms response latency target; throttle heat >60°C.
- Battery: Learns only on charger thresholds; <10% drain/hour passive.
- Scalability: Single-device forever; storage pruning for limited space.

Technical Stack:
- UI: Jetpack Compose
- Audio: MediaRecorder foreground service
- TTS: Piper/Orca
- Permissions: ActivityResultLauncher
- Git: Local + GitHub private
- Build: Gradle; optional Firebase plugin for distro
