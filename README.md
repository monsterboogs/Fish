# Stardew Auto Fish — Fabric Port

Fabric port of `autofish-addon-1.0.3`, targeting **stardewfishing-fabric 1.3.1** by kltyton.

## What changed (Forge → Fabric)

| Forge | Fabric |
|---|---|
| `@Mod("autofish")` | `implements ClientModInitializer` |
| FML event bus + `RegisterKeyMappingsEvent` | `KeyBindingHelper` + `ClientTickEvents` |
| Target: `com.bonker.stardewfishing.*` | Target: `com.kltyton.stardewfishingFabric.*` |
| Chest/treasure system | Not present in Fabric base mod — removed |
| `setInputDown(boolean)` method | Direct `mouseDown` field shadow |
| `getBarSize()` from minigame | Hardcoded `HIT_HEIGHT = 29` constant |
| `MinigameModifiersReloadListener` crash fix | `FishBehaviorReloadListener` null guard |

## Mixin summary

- **FishingAutoMixin** — injects into `FishingScreen.tick()`, reads `bobberPos` + `fishPos`, and sets the `mouseDown` field to track the fish automatically.
- **FishingMinigameMixin** — injects into `FishingMinigame.tick()` to smoothly lerp the bobber bar toward the fish, removing jitter.
- **MinigameCrashFixMixin** — guards `FishBehaviorReloadListener.getBehavior()` against a null `INSTANCE` crash.

## Building

1. Requires **JDK 17**.
2. `libs/stardewfishing-fabric-1.3.1.jar` is already included — no extra setup.
3. Run:
   ```
   ./gradlew build
   ```
4. Output is in `build/libs/` — use the file **without** `-sources`.

## Installing

Drop the built `.jar` and `stardewfishing-fabric-1.3.1.jar` into `.minecraft/mods/` alongside Fabric API.
