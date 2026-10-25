# NothingLauncher — Play Store Listing

Ready-to-paste copy for the Google Play Console listing.

## Contents

```
play-store/listing/
├── README.md
├── categorization.md        # category, tags, audience, content type
├── content-rating.md        # IARC/Play content-rating questionnaire answers
├── privacy-policy.md        # privacy policy text (EN + ES)
├── en-US/
│   ├── title.txt
│   ├── short-description.txt   # <= 80 chars
│   ├── full-description.txt    # <= 4000 chars
│   └── changelog.txt
└── es-419/
    ├── title.txt
    ├── short-description.txt
    ├── full-description.txt
    └── changelog.txt
```

## Quick reference

| Field | Value |
|-------|-------|
| Title | Nothing Launcher |
| Short description (EN) | Minimal home screen. One word: Nothing. Tap to launch your apps. |
| Category | Personalization |
| Price | Free |
| Ads | None |
| Content rating | Everyone |
| Data collected | None |

## Character limits (verified)

Run `python3 check_limits.py` to verify the short/full descriptions stay
within Play limits (80 / 4000 chars).
