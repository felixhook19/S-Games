# 🦄 Unicorn Magic

A gentle, colourful learning game for a 3-year-old who loves unicorns — no
reading required. It runs **100% offline** in a phone's web browser. There is
nothing to install, no accounts, and it never touches the internet.

## What's in it

Tap a coloured bubble on the home screen to pick a game. Every game is endless
and you can't lose — just lots of sparkles, happy chimes, and a spoken voice
that praises her. A little house button (top-left) always goes back home.

| Game | What she learns |
|------|-----------------|
| ⭐ **Counting Stars** | Numbers 1–10. Tap each star; the voice counts them aloud. The number of stars grows each round. |
| 🌈 **Colour the Unicorn** | Colour names. Tap a paint blob to paint the unicorn's mane; the voice says the colour. |
| 🔵 **Shape Bubbles** | Shapes — circle, square, triangle, star, heart. Pop the floating bubbles; the voice names each shape. |
| 🎵 **Rainbow Music** | Cause & effect + a happy musical scale. Tap the rainbow bars (every note sounds nice together). |

Designed for the car: big tap targets, no tiny buttons, no menus to read, and
calm pacing.

## How to get it onto the (offline) Pixel 6 Pro

You only need to copy **one file** — `unicorn-magic.html` — onto the phone.
Easiest options:

1. **USB cable (no internet needed):** Plug the Pixel into a computer, allow
   "File transfer" on the phone, and copy `unicorn-magic.html` into the
   `Download` folder.
2. **Email / cloud:** While the phone *does* briefly have a connection, email
   the file to yourself or drop it in Google Drive, then open/download it on
   the Pixel. After that it works fully offline forever.

### Open it and make it feel like an app

1. On the Pixel, open **Chrome** and go to `file:///sdcard/Download/unicorn-magic.html`
   — or just open the file from the **Files** app and choose Chrome.
2. Tap the **⋮ menu → "Add to Home screen"**. Now there's a Unicorn Magic icon
   on the home screen that opens full-screen, just like a real app.
3. Tap the unicorn to start (the first tap is what lets the sound play).

### Tips
- Turn the volume up — the counting/colour/shape words are spoken aloud.
- If you'd like it to never dim, set the screen timeout longer, or keep it
  charging in the car.
- The spoken voice uses Android's built-in text-to-speech. If you ever hear no
  voice, open Android **Settings → Accessibility → Text-to-speech** once while
  online so the English voice data is downloaded; after that it's offline too.
  (The chimes and sparkle sounds always work, voice or not.)

## For developers

Everything lives in the single file `unicorn-magic.html`: all graphics are
drawn with the Canvas API (no image files), sounds are synthesised with the Web
Audio API, and words use the Web Speech API. No build step, no dependencies.
Open the file in any modern browser to run it.
