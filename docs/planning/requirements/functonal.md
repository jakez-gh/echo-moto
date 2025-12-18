Functional Requirements

1. Sensing & Triggers
   - Always-low-power audio buffering; transcribe on-device (Whisper-tiny).
   - Multi-event triggers: Time (AlarmManager), location (geofences), notifications (NotificationListener), Bluetooth heart rate.
   - Manual telephony recording: Button during OFFHOOK state; route to main pipeline.
   - Tag transcripts: Speaker ID, mood/inflection/pauses.

2. Decision & Response
   - Silence Gate: Default inaction; threshold-based voice/text/overlay.
   - Subtle Suggestions: SYSTEM_ALERT_WINDOW floating bubbles mid-convo.
   - Voice Output: Piper/Orca TTS; attribute choice (pitch/rate); lightweight cloning from samples.

3. Curiosity & Growth
   - Surprise Meter: Per-token NLL/RPE intensity scoring.
   - Replay Buffer: Prioritized queue; high-surprise (esp. calls) first.
   - Proactive Probes: Polite requests for surprise-rich data.
   - Continual Learning: Micro-online updates (>30% battery); deep offline batches (>60% charging, pause <50%).
   - Adaptive Arch: Modular gating; prune/grow on failure patterns.

4. Data Management
   - Local encrypted DB: Transcripts, embeddings, logs.
   - Offline Archiving: Migrate cold data to SD/external when storage low.

5. Progressive Milestones (Early Value)
   - M1: App launches with "Echo Lives!" text.
   - M2: Permission requests + basic overlay.
   - M3: Audio beep record + notification.
   - M4: Manual call record button.
   - Later: Transcribe stub, surprise scoring, first growth burst.

Non-Functional:
- Privacy: All data local; differential privacy noise optional.
- Performance: Partial module activation for speed/battery.
- TDD: Every feature red-green-refactor; unit/UI tests.
