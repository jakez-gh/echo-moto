```mermaid
sequenceDiagram
    participant Telephony as TelephonyManager
    participant Activity as Echo Activity
    participant Service as Recording Service
    participant User

    Telephony->>Activity: Call State OFFHOOK Callback
    Activity->>User: Show Floating Manual Record Button + Opt-In Reminder
    User->>Activity: Tap Start (After Confirmation)
    Activity->>Service: Route Call Audio to Buffer
    Service->>Service: Record + Tag "Call"
    Telephony->>Activity: Call State IDLE
    Activity->>Service: Auto-Stop + Save
