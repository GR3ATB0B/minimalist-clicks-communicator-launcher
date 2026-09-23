# Minimalist Clicks Communicator Launcher

An open-source Android launcher that turns the [Clicks Communicator](https://www.clicks.tech/) into a dumbphone-like tool. It cuts the device down to four apps (**Phone, Messages, Camera, Claude**) and is meant to use the Communicator's hardware LED ring for ambient notifications. No Play Store, no browser, no app drawer, nothing to scroll.

If you own a Clicks Communicator and want it to be a tool instead of a distraction, this is for you. MIT licensed, so fork it, strip it down further, or make it yours.

## Status

Phase 1 complete (May 2026): 2x2 launcher grid, swipe-down Control Center with brightness/volume sliders, silent-mode toggle, and Wi-Fi/Bluetooth/Settings deep-links. Tested on Android emulator (API 35). Phase 2 (LED + notifications) and Phase 3 (Wispr Flow + Clicks key) gated on Clicks SDK access.

## The Four Apps

| App    | Source                  | Purpose                  |
|--------|-------------------------|--------------------------|
| Phone  | Fossify Simple Dialer   | Calls                    |
| SMS    | Fossify Simple SMS      | Text messaging           |
| Camera | Open Camera             | Photos + built-in viewer |
| Claude | Anthropic Claude        | AI assistant             |

Plus **Wispr Flow** as a background voice-input service, triggered by the Clicks hardware key.

## Roadmap

- **Phase 1 — Core launcher (current).** 2x2 home grid, control center, default-home behavior, emulator-tested.
- **Phase 2 — LED + notifications.** Clicks LED ring control, SMS/call monitoring, priority contacts. Requires Clicks SDK.
- **Phase 3 — Wispr Flow + Clicks key.** Hardware key mapping for push-to-talk transcription. Requires Clicks SDK.

## Build

Phase 1 plan: [`docs/superpowers/plans/2026-05-04-minimal-core-launcher.md`](docs/superpowers/plans/2026-05-04-minimal-core-launcher.md)

### Requirements

- macOS or Linux with JDK 17 (Temurin recommended)
- Android command-line SDK with API 35 platform, build-tools 35.0.0, emulator, and an `arm64-v8a` system image (or `x86_64` on Intel hosts)
- An emulator AVD or a physical Android 14+ device

### Commands

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
adb shell am start -n com.minimal.launcher/.MainActivity
adb shell cmd package set-home-activity com.minimal.launcher/com.minimal.launcher.MainActivity
```

## License

MIT — see [LICENSE](LICENSE).
