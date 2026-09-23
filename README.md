# Project Peel

Phones aren't the enemy. They pull double duty as a utility and an entertainment device, and the second half is what erodes time and attention without anyone choosing it. Underneath that, smartphones collapsed *reachability* into one unbounded default: be available instantly, to everyone, always. That's a design choice, and it can be designed differently.

Project Peel puts a shape back around reachability, the way "be home when the streetlights come on" used to. Going full dumbphone doesn't work (dorm keys live in Apple Wallet, classes run through Canvas) and dumbphones keep the same always-reachable assumption anyway. So Peel builds a narrow, well-defined channel instead: a short allowlist of people who can always get through, a receive path that isn't instant, and a reply path with enough friction that "reachable" doesn't mean "hijackable." The model is pagers and payphones, rebuilt with modern parts.

When a default has to choose between convenience and boundedness, Peel picks boundedness.

Full philosophy and scope: [`docs/PROJECT_PEEL_BRIEF.md`](docs/PROJECT_PEEL_BRIEF.md)

## The pieces

1. **Peel launcher** (this app). Reduces an Android phone to four tools. Details below.
2. **Peel/Banana mesh pager** (designed, not built yet). An Android bridge phone catches real SMS and calls, filters and summarizes them, and relays the ones that matter over the free MeshCore LoRa mesh to a pocket pager (Heltec V3 + M5Stack keyboard). Replies go back the same way as real SMS. No cell towers on the pager side, best-effort by design, with a web dashboard as the full record.
   Architecture, both message-flow diagrams, the message-code scheme, and open questions: [`docs/PEEL-mesh-pager-design.md`](docs/PEEL-mesh-pager-design.md)

## Peel launcher

A minimalist Android launcher for the [Clicks Communicator](https://www.clicks.tech/) that turns the phone back into a tool, not a distraction.

Peel restricts the device to exactly four apps — **Phone, SMS, Camera, Claude** — and uses the Clicks Communicator's hardware LED ring for ambient notifications. No Play Store, no browser, no app drawer.

### Status

Phase 1 complete (May 2026): 2x2 launcher grid, swipe-down Control Center with brightness/volume sliders, silent-mode toggle, and Wi-Fi/Bluetooth/Settings deep-links. Tested on Android emulator (API 35). Phase 2 (LED + notifications) and Phase 3 (Wispr Flow + Clicks key) gated on Clicks SDK access.

### The Four Apps

| App    | Source                  | Purpose                  |
|--------|-------------------------|--------------------------|
| Phone  | Fossify Simple Dialer   | Calls                    |
| SMS    | Fossify Simple SMS      | Text messaging           |
| Camera | Open Camera             | Photos + built-in viewer |
| Claude | Anthropic Claude        | AI assistant             |

Plus **Wispr Flow** as a background voice-input service, triggered by the Clicks hardware key.

### Roadmap

- **Phase 1 — Core launcher (current).** 2x2 home grid, control center, default-home behavior, emulator-tested.
- **Phase 2 — LED + notifications.** Clicks LED ring control, SMS/call monitoring, priority contacts. Requires Clicks SDK.
- **Phase 3 — Wispr Flow + Clicks key.** Hardware key mapping for push-to-talk transcription. Requires Clicks SDK.

### Build

Phase 1 plan: [`docs/superpowers/plans/2026-05-04-peel-core-launcher.md`](docs/superpowers/plans/2026-05-04-peel-core-launcher.md)

#### Requirements

- macOS or Linux with JDK 17 (Temurin recommended)
- Android command-line SDK with API 35 platform, build-tools 35.0.0, emulator, and an `arm64-v8a` system image (or `x86_64` on Intel hosts)
- An emulator AVD or a physical Android 14+ device

#### Commands

```bash
# Build the debug APK
./gradlew assembleDebug

# Install on a connected emulator/device
./gradlew installDebug

# Run JVM unit tests (Robolectric + JUnit)
./gradlew test

# Run instrumented tests on a running emulator/device
./gradlew connectedCheck

# Launch on the emulator and set as default home
adb shell am start -n com.peel.launcher/.MainActivity
adb shell cmd package set-home-activity com.peel.launcher/com.peel.launcher.MainActivity
```

## License

MIT — see [LICENSE](LICENSE).
