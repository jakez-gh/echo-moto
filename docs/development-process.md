Development Process

Methodology: Test-Driven Development (TDD) - Red-Green-Refactor cycles mandatory.
- Write failing test first.
- Minimal implementation to pass.
- Refactor clean.
- Commit + deploy milestone.

Version Control & Visibility:
- Private GitHub repo: All code/issues tracked.
- GitHub Actions: Auto-build debug APK on push; artifact downloadable.
- Optional Firebase App Distribution (Spark free): One-click tester installs for faster feedback.
- Wireless ADB: Primary for instant sideloading—engineers see changes on Moto G immediately after build.

Early & Frequent Progress:
- Milestones every 5-15 tasks: Visible on-device (e.g., UI appears, permission flows, beep records, overlay hints).
- Goal: Engineer experiences tangible value multiple times daily/weekly—no blind coding.
- Deploy Flow: Push → Actions build → Download/install → Test on device → Feedback loop.
