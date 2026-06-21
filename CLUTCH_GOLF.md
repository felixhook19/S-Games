# ⛳ Clutch Golf

A **Golf Clash / PGA-style** golf game that runs **100% offline** as a tiny
Android APK (or as a single web page). Aim, time the 3-stop swing meter, fight
the wind and hazards, and beat an AI opponent over a short match for a coin
stake. Grind coins, gems, cards and chests to unlock and upgrade better clubs
and balls.

The whole game is one self-contained file — **`clutch-golf.html`** — with all
graphics drawn on an HTML5 Canvas (no image assets), so there's nothing to
download and the APK stays small. Progress is saved in `localStorage`.

---

## How to play

1. **Pick a tour** from *Play Match*. Each tour is a 3–4 hole match versus an AI
   of a set skill, with a coin **stake** to enter and a bigger coin + gem +
   chest payout if you win (lowest total strokes across the holes wins).
2. **Aim:** drag anywhere on the course to swing your aim left/right. The dotted
   line and reticle show your target. Tap a **club** at the bottom to change it.
3. **Swing (3 stops):** tap **SWING** to start the meter.
   - **Tap 1 — Power:** stops the rising bar to set how far you hit.
   - The bar marks a white **perfect line** where you set power, with a green
     forgiveness band (wider for higher-accuracy gear).
   - **Tap 2 — Accuracy:** the marker sweeps back down; tap it right on the
     perfect line for a dead-straight shot. Off the line curves the ball.
4. **Wind, hazards & lies** all matter — water and out-of-bounds cost a penalty
   stroke; sand and rough rob distance.
5. **On the green** you auto-switch to the **putter** — same meter, gentler
   distances. Sink it to finish the hole.

## Economy & grind

- **Coins** buy gear and pay match stakes; **gems** buy premium gear and chests.
- **Cards** (from chests) unlock commons automatically and, with coins, upgrade
  any club/ball. Rarities cap at level 5 / 7 / 9 / 11 (common → legendary).
- A **free chest** is available every 30 minutes from the clubhouse, plus you win
  a chest for every match you win.

---

## Getting the app

The APK is built automatically by GitHub Actions — the phone never builds
anything.

- **Releases page:** download **`clutch-golf.apk`** from the
  *"Clutch Golf (latest APK)"* release, **or**
- **Actions tab → "Build Clutch Golf APK"** → latest run → download the
  **`clutch-golf-apk`** artifact (a zip containing the apk).

> The build runs on a push that touches the game, or start it by hand:
> **Actions → Build Clutch Golf APK → Run workflow**.

Copy `clutch-golf.apk` to the phone, tap it in the **Files** app, allow
*"Install unknown apps"* once if prompted, and install. The app is full-screen,
keeps the screen awake, requests **no permissions**, and works entirely offline.

---

## For developers

### Web version
`clutch-golf.html` is the entire game in one file. Open it in any browser to
play — no build step, no dependencies. The code is organised into namespaces on
a single global `GAME` object:

| Namespace | Responsibility |
|-----------|----------------|
| `GAME.DATA`   | Rarities, clubs, balls, tours/holes, chests, stat maths |
| `GAME.save`   | Default state, migrate, load/commit/reset via `localStorage` |
| `GAME.eco`    | Currency, XP/levels, buy/unlock/upgrade/equip, chests, settlement |
| `GAME.phys`   | Ball-flight simulation (carry, wind, curve, roll, lie, putting) |
| `GAME.render` | Canvas drawing: course, hazards, ball + shadow + trail, flag, FX |
| `GAME.round`  | In-round controller, swing meter, AI opponent, rAF loop |
| `GAME.ui`     | Menu screens + routing |

### Android app
`android-golf/` is a minimal native wrapper: one full-screen `WebView` that loads
the game from assets, plus a tiny `AndroidApp` bridge so the hardware Back button
returns to the clubhouse before exiting. There is **no INTERNET permission**.

`clutch-golf.html` is the single source of truth; a Gradle `copyGame` task copies
it into the app's assets as `index.html` at build time, so you only edit the one
HTML file.

Build locally (needs the Android SDK + JDK 17):

```bash
cd android-golf
./gradlew assembleDebug
# -> app/build/outputs/apk/debug/app-debug.apk
```

Or push to the development branch and let `.github/workflows/android-golf.yml`
build and publish the APK for you.
