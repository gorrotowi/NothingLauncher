# NothingLauncher — Play Store Assets

Generated assets for publishing **NothingLauncher** on Google Play.

## What's here

```
play-store/assets/
├── generate_assets.py   # Python/Pillow generator (re-run to regenerate)
├── DESIGN.md            # Brand spec + asset rationale
├── README.md
└── output/
    ├── icon-512.png
    ├── feature-graphic-1024x500.png
    ├── promo-graphic-180x120.png
    ├── tv-banner-1280x720.png
    └── screenshots/
        ├── phone-1-home-light-1080x1920.png
        ├── phone-2-apps-light-1080x1920.png
        ├── phone-3-home-dark-1080x1920.png
        └── phone-4-apps-dark-1080x1920.png
```

## How to use in Play Console

1. **App icon** → upload `output/icon-512.png` (512×512, ≤ 1 MB).
2. **Feature graphic** → upload `output/feature-graphic-1024x500.png`
   (1024×500, JPG/PNG 24-bit).
3. **Screenshots** → upload all four files in `output/screenshots/`
   (phone, 1080×1920). Order: home-light, apps-light, home-dark, apps-dark.
4. **Promo graphic** (optional) → `output/promo-graphic-180x120.png`.
5. **TV banner** (only if you also ship an Android TV listing) →
   `output/tv-banner-1280x720.png`.

## Requirements checklist

- [x] Icon 512×512
- [x] Feature graphic 1024×500
- [x] ≥2 phone screenshots (1080×1920)
- [x] Light + dark variants
- [ ] (optional) replace generated screenshots with real device captures
- [ ] (optional) promo video 30s

## Regenerate

```bash
pip install Pillow
python3 play-store/assets/generate_assets.py
```

The generator reads the Questrial font from the main app repo and writes to
`play-store/assets/output/`.
