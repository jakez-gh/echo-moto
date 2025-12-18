```mermaid
sequenceDiagram
    participant Monitor as BatteryMonitor
    participant Gate as Charger Gate
    participant Trainer as Growth Trainer
    participant Model as Micro-Model Layers

    Monitor->>Monitor: BroadcastReceiver POWER_CONNECTED / LEVEL_CHANGED
    Monitor->>Gate: Current Level + Charging Status
    alt >60% + Charging
        Gate->>Trainer: Start Deep Batch (High-Surprise Replay)
        Trainer->>Model: Full-Param Burst + Prune/Grow
        Trainer-->>Monitor: Schedule Pause Check
    else <50% or Unplugged
        Gate->>Trainer: Pause / Resume Micro-Online Only
    end
    Trainer-->>Model: Updated Weights (Local Save)
