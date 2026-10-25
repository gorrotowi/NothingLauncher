# NothingLauncher — Store Asset Design Spec

## Brand essence

NothingLauncher is a **minimalist** launcher. The brand is literal "nothing":
empty space, monochrome, one word. Assets must never feel busy.

| Token | Value | Use |
|-------|-------|-----|
| Black | `#000000` | backgrounds, primary text on white |
| White | `#FFFFFF` | text on black, light screens |
| Green | `#3DDC84` | single accent (period, underline, dot) |
| Gray | `#787878` | secondary text / taglines |
| Typeface | Questrial (OTF) | all wordmarks and UI text |

Rules:
1. One accent color only. Green is a punctuation mark, never a fill for large areas.
2. Black/white dominate. Screens must look "empty" on purpose.
3. Questrial only. No bold, no italic, no letter-spacing tricks.
4. Everything centered or left-aligned on a strict grid.

## Assets produced

| File | Size | Play Console field |
|------|------|--------------------|
| `output/icon-512.png` | 512×512 | App icon |
| `output/feature-graphic-1024x500.png` | 1024×500 | Feature graphic |
| `output/promo-graphic-180x120.png` | 180×120 | Promo graphic (optional) |
| `output/tv-banner-1280x720.png` | 1280×720 | TV banner (Android TV, optional) |
| `output/screenshots/phone-1-home-light-1080x1920.png` | 1080×1920 | Phone screenshot |
| `output/screenshots/phone-2-apps-light-1080x1920.png` | 1080×1920 | Phone screenshot |
| `output/screenshots/phone-3-home-dark-1080x1920.png` | 1080×1920 | Phone screenshot |
| `output/screenshots/phone-4-apps-dark-1080x1920.png` | 1080×1920 | Phone screenshot |

## Icon rationale

A black square with a white Questrial **N** and a green period dot.
- Reads at 36dp, unique at a glance.
- The green dot is the only color and ties back to the app's original
  (default) icon green `#3DDC84`.
- Keeps the "Nothing." wordmark idea: the period is the accent.

## Screenshot rationale

Four screens tell the whole story in two axes:
- **Home vs App list** — the only two screens the launcher has.
- **Light vs Dark** — the launcher follows system Day/Night; showing both
  proves it.

Each screenshot is a faithful recreation of the real layouts
(`activity_main.xml`, `activity_app_list.xml`): a centered "Nothing" label
on Home, and a single-column app list with circular app icons.

## Regenerating

```bash
python3 generate_assets.py   # requires: pip install Pillow
```

Edit the brand tokens at the top of `generate_assets.py` and re-run. The
font is read from the app's `app/src/main/res/font/questrialregular.otf`.

## Notes for a "real" polish pass

- These are **programmatic** assets. For final submission consider exporting
  the same compositions from Figma/Illustrator for anti-aliasing parity.
- Keep screenshots free of OEM status-bar glyphs if you want a clean look;
  the generated status bar is a placeholder you can drop.
