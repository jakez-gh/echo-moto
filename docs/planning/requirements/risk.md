Risk Assessment & Mitigation

High Risks:
1. Call Recording Feasibility/Legality
   - Risk: Android 2025 blocks third-party auto (no API; accessibility flaky); interstate calls may trigger all-party states.
   - Mitigation: Manual trigger only; explicit opt-in dialog; auto-detect/warn potential conflicts; no auto-start.

2. On-Device Learning Speed
   - Risk: Scratch-micro growth extremely slow; early incoherence; forgetting/heat.
   - Mitigation: Temper expectations; micro-bursts only; SuRe prioritization; user-aware milestones.

3. Battery/Heat/Storage
   - Risk: Drains during sensing/learning; limited Moto G space.
   - Mitigation: Charger thresholds; prune cold data; partial execution.

4. Privacy/Ethical
   - Risk: Sensitive audio capture.
   - Mitigation: Local-only; opt-ins; auto-prune; silence default.

Medium Risks:
- TTS Quality: Lightweight cloning spotty.
- Mitigation: Start Piper basic; layer samples progressively.

Overall Strategy: Progressive unlocks; TDD catches issues early; frequent deploys for real-device feedback.
