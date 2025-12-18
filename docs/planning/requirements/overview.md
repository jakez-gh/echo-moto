Project Name: Echo - Personal On-Device AI Companion
Version: 1.0 (December 18, 2025)
Target Platform: Single Motorola Moto G device (Android 15/16, Snapdragon chipset) - Sideloading only, no multi-device or distribution.
Budget: $0 - All tools free tier (GitHub private repo, Firebase Spark plan for optional distribution, open-source libs).

Core Vision:
Echo is an always-present, privacy-first personal AI assistant embodied in the user's phone. It prioritizes silence and inaction as default behaviors, intervening only when relevance/confidence thresholds are met. Driven by curiosity, it measures prediction surprises (inspired by SuRe replay prioritization) to refine its world model through on-device continual learning. All data remains local; growth emerges from user's life (audio, events, interactions).

Key Principles:
- Silence/Inaction Default: Responses (voice/text/overlay) only if confidence >80%; otherwise thoughtful pause or nothing.
- Ethical Sensing: Record/transcribe all in/out audio locally (opt-in); manual call recording (GA one-party consent; auto unreliable on Android 2025).
- Progressive Growth: Start from micro random-init transformer (~5-10M params); full-param bursts on charger; no base pre-trained model.
- Curiosity Engine: SuRe-inspired NLL/RPE surprise scoring; prioritize high-intensity replays; proactive polite probes for weak domains.
- Early & Frequent Value: TDD-driven; visible milestones every cycle (e.g., permission prompt, beep record, overlay hint).

Constraints & Realism:
- On-device only: No cloud inference/training.
- Battery/Heat: Throttle learns; expect drain during offline bursts.
- Feasibility: Early versions primitive/incoherent; human-like convo months/years away.
- Legal: One-party consent for user-participated calls; manual triggers; interstate warnings.
