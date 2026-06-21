# S-Games

A small collection of self-contained, fully-offline games. Each game is a single
HTML file at the repo root, wrapped in a minimal Android `WebView` so it can ship
as a tiny APK built by GitHub Actions.

| Game | File | App project | Build workflow | Docs |
|------|------|-------------|----------------|------|
| 🦄 Unicorn Magic | `unicorn-magic.html` | `android/` | `android.yml` | below |
| ⛳ Clutch Golf | `clutch-golf.html` | `android-golf/` | `android-golf.yml` | [CLUTCH_GOLF.md](CLUTCH_GOLF.md) |

---

# 🦄 Unicorn Magic

A gentle, colourful learning game for a 3-year-old who loves unicorns — **no
reading required**. Everything is spoken aloud and shown with pictures. It runs
**100% offline**: no internet, no accounts, no ads, and the Android app asks for
**zero permissions**.

There are two ways to play, from the *same* game:

1. **The app (recommended for her):** install `unicorn-magic.apk` — a real
   home-screen icon that opens full-screen.
2. **A single web page:** open `unicorn-magic.html` in a browser. Handy for a
   quick try on any phone, tablet or computer.

---

## The games

On the home screen, tap a coloured bubble to pick a game. Every game is
**endless and impossible to lose** — just sparkles, happy chimes, and a friendly
voice that names things and praises her. The little **house button (top-left)**
always goes back home. Turn the volume up!

| Bubble | Game | What she learns |
|--------|------|-----------------|
| ⭐ | **Counting Stars** | Numbers 1–10 — tap each star, the voice counts; the count grows each round. |
| 🌈 | **Colour the Unicorn** | Colour names — tap paint blobs to colour the unicorn's mane. |
| 🔵 | **Shape Bubbles** | Shapes — pop floating bubbles (circle, square, triangle, star, heart). |
| 🎵 | **Rainbow Music** | Cause & effect + a happy musical scale (every note sounds nice together). |
| 🐱 | **Animals & Sounds** | Animals — tap one to hear its name and the sound it makes. |
| 🔤 | **Letters (ABC)** | Letters — pop a letter to hear it and a word ("U is for Unicorn!"). |
| 🧩 | **Simple Jigsaw** | Matching & spatial skills — drag the big shapes into their homes. |
| 🔍 | **Find the Colour** | Listening + colours — the voice asks her to find a colour; she taps it. |

Designed for the car: big tap targets, no tiny buttons, no menus to read, calm
pacing, and it works in any orientation.

### The challenge grows with her

Each game now sets a little task rather than just letting her explore, and it
gets harder as she succeeds (and eases back a touch if she's struggling). Her
level in each game is remembered between sessions, so she picks up where she
left off:

- **Counting Stars** – the count to reach climbs from 3 up to 10, and the stars
  start gently drifting so they're harder to catch.
- **Colour the Unicorn** – a voice names a colour and she has to find the right
  paint (the correct blob glows to help after a few tries); more of the mane to
  colour as she improves.
- **Shape Bubbles** – pop only the shape shown in the badge / named aloud; more
  bubbles, faster, at higher levels.
- **Rainbow Music** – now "copy the tune": the unicorns play a melody and she
  plays it back from memory; the tune gains a note each time she's right.
- **Animals** – "can you find the cow?"; any tap still teaches the animal, but
  finding the right one is the goal. More animals on screen as she levels up.
- **Letters** – "find the letter B" (shown in a badge to match); more letters,
  faster.
- **Jigsaw** – grows from 3 to 5 pieces, and at higher levels every piece is the
  same colour, so she has to match by **shape** alone.
- **Find the Colour** – more balloons, and trickier colours to tell apart.

---

## Getting the app onto the (offline) Pixel 6 Pro

You only need **one file: `unicorn-magic.apk`**.

### 1. Get the APK
The APK is built automatically by GitHub Actions (the phone never needs to build
anything). To download it:

- Go to the repository's **Releases** page and download **`unicorn-magic.apk`**
  from the *"Unicorn Magic (latest APK)"* release, **or**
- Open the **Actions** tab → the latest *"Build Unicorn Magic APK"* run →
  download the **`unicorn-magic-apk`** artifact (it's a zip containing the apk).

> The build runs on a push to the game branch, or you can start it manually:
> **Actions → Build Unicorn Magic APK → Run workflow**.

### 2. Put it on the phone (no internet needed once you have the file)
- **USB cable:** plug the Pixel into a computer, allow "File transfer", and copy
  `unicorn-magic.apk` into the phone's `Download` folder, **or**
- Email it to yourself / drop it in Drive while briefly online, then download it
  on the Pixel once.

### 3. Install it
1. On the Pixel, open the **Files** app and tap `unicorn-magic.apk`.
2. The first time, Android will ask to allow **"Install unknown apps"** for
   Files/Chrome — turn it on, then tap **Install**. (This is normal for apps
   that don't come from the Play Store.)
3. Open **Unicorn Magic** from the home screen. Tap the unicorn to start.

The app keeps the screen awake, hides the status/navigation bars, and works
entirely offline forever.

### A note on the voice
The spoken words use the Pixel's built-in **Text-to-Speech**, which works
offline. If you ever hear chimes but no words, open Android **Settings →
Accessibility → Text-to-speech** once while online so the English voice data is
downloaded; after that it's offline too. (The sparkle/chime sounds always work
regardless.)

---

## For developers

### Web version
`unicorn-magic.html` is the entire game in one file — all graphics drawn with the
Canvas API (no image assets), sounds synthesised with the Web Audio API, and
words spoken via the Web Speech API. No build step, no dependencies. Just open it.

### Android app
The `android/` folder is a minimal native wrapper: a single full-screen
`WebView` that loads the game from assets, plus a tiny `TextToSpeech` bridge
(`window.AndroidTTS`) because a bare WebView has no working Web Speech API. There
is **no INTERNET permission** — the app cannot touch the network.

`unicorn-magic.html` is the single source of truth; a Gradle `copyGame` task
copies it into the app's assets at build time, so you only ever edit the one HTML
file.

Build locally (needs the Android SDK + JDK 17):

```bash
cd android
./gradlew assembleDebug
# -> app/build/outputs/apk/debug/app-debug.apk
```

Or just push to the game branch and let `.github/workflows/android.yml` build and
publish the APK for you.
