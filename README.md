# Echo Companion - Personal On-Device AI Assistant

<img src="https://img.shields.io/badge/Platform-Android-green.svg" alt="Platform">
<img src="https://img.shields.io/badge/Min%20SDK-29-blue.svg" alt="Min SDK">
<img src="https://img.shields.io/badge/Target-Moto%20G-orange.svg" alt="Target Device">
<img src="https://img.shields.io/badge/Privacy-On--Device-red.svg" alt="Privacy">

Echo is your personal, privacy-first AI companion that lives on your Moto G phone. It learns from your life while keeping all data completely on-device.

## 🌟 Key Features

- **🔒 Privacy-First**: All processing happens on-device. Your data never leaves your phone.
- **🤫 Silence by Default**: Only responds when confidence is >80%, otherwise stays quiet.
- **🧠 Continuous Learning**: Grows and adapts from your interactions using on-device learning.
- **🔋 Battery-Aware**: Learns during charging, throttles when battery is low.
- **📱 Moto G Optimized**: Specifically designed for your Motorola Moto G device.

## 🚀 Quick Start

### Prerequisites
- Moto G phone (Android 10+)
- Ubuntu laptop for development
- USB cable or Wi-Fi for deployment

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/YOUR_USERNAME/echo-moto.git
   cd echo-moto
   ```

2. **Deploy to your Moto G**:
   ```bash
   ./deploy_to_moto_g.sh
   ```

That's it! The script handles everything else.

## 📱 Architecture

Echo uses a modular architecture designed for on-device AI:

- **UI Layer**: Jetpack Compose for modern, reactive UI
- **Service Layer**: Foreground services for continuous audio monitoring
- **AI Engine**: On-device inference with MediaPipe/NexaSDK
- **Learning Module**: Curiosity-driven continual learning
- **Privacy Module**: Local encryption and data management

## 🛠️ Development

### Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Build**: Gradle
- **CI/CD**: GitHub Actions
- **Testing**: JUnit, Espresso (TDD-driven)

### Building from Source

```bash
# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew test

# Deploy to device
./deploy_to_moto_g.sh
```

### Project Structure

```
echo-moto/
├── app/
│   ├── src/main/java/com/echo/companion/
│   │   ├── MainActivity.kt          # Main UI
│   │   ├── MainViewModel.kt        # State management
│   │   ├── service/                # Background services
│   │   │   ├── AudioRecordingService.kt
│   │   │   └── OverlayService.kt
│   │   └── ui/theme/               # UI theming
│   └── src/test/                   # Unit tests
├── docs/                            # Documentation
│   └── planning/                    # Design docs
├── deploy_to_moto_g.sh             # Deployment script
└── .github/workflows/              # CI/CD
```

## 🔧 Configuration

### Permissions Required
- 🎤 **RECORD_AUDIO**: For listening and transcription
- 🔔 **POST_NOTIFICATIONS**: For status updates
- 📍 **ACCESS_LOCATION** (optional): For context awareness
- 🪟 **SYSTEM_ALERT_WINDOW**: For floating suggestions

### Development Setup

See [DEPLOYMENT.md](DEPLOYMENT.md) for detailed deployment instructions.

## 🧪 Testing

Echo follows Test-Driven Development (TDD):

```bash
# Run unit tests
./gradlew test

# Run instrumentation tests (device required)
./gradlew connectedAndroidTest

# View test coverage
./gradlew jacocoTestReport
```

## 📊 Milestones

- [x] Project setup and structure
- [x] GitHub Actions CI/CD
- [x] Basic UI with Jetpack Compose
- [x] Audio recording service
- [x] Permission handling
- [x] Deployment scripts
- [ ] On-device STT integration
- [ ] Curiosity engine implementation
- [ ] Surprise scoring system
- [ ] Battery-aware learning
- [ ] Overlay UI for suggestions
- [ ] Call recording (manual trigger)

## 🤝 Contributing

This is a personal project for a single Moto G device, but feel free to fork and adapt for your needs!

## 📄 License

This project is for personal use. See [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- Inspired by SuRe replay prioritization research
- Built with privacy and user control as core principles
- Designed for genuine helpfulness, not engagement

---

**Note**: This is an experimental personal AI assistant. Early versions will be primitive. Human-like conversation capabilities will develop over time through continual learning.