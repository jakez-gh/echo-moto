Functional Requirements

1. Passive Observation
   - Low-power audio buffer; on-device transcription (Whisper-tiny).
   - Tag: Speaker/mood/inflection/pauses/timestamps.
   - Manual call recording trigger (visible during OFFHOOK).

2. Triggers & Events
   - Time/location/notification/Bluetooth heart rate.
   - No automatic call recording (legal/technical risks).

3. Response Engine
   - Silence default; threshold-gated overlay bubbles/TTS.
   - Subtle conversation suggestions via floating UI.

4. Curiosity & Learning
   - SuRe-style surprise scoring (NLL/RPE intensity).
   - Prioritized replay buffer.
   - Polite probes for high-surprise domains.
   - Charger-gated bursts (>60% charge start, <50% pause).

5. Early Value Milestones
   - Frequent visible progress: TDD cycles → deploy → see on-device (e.g., overlay appears, beep records).
