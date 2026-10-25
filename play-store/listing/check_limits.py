#!/usr/bin/env python3
"""Verify Play Store character limits for the listing copy."""
import os

BASE = os.path.dirname(os.path.abspath(__file__))
SHORT_LIMIT = 80
FULL_LIMIT = 4000

fail = False
for lang in ("en-US", "es-419"):
    short = open(os.path.join(BASE, lang, "short-description.txt")).read().strip()
    full = open(os.path.join(BASE, lang, "full-description.txt")).read().strip()
    print(f"[{lang}] short: {len(short)}/{SHORT_LIMIT}  full: {len(full)}/{FULL_LIMIT}")
    if len(short) > SHORT_LIMIT:
        print(f"  ERROR: short description too long by {len(short)-SHORT_LIMIT} chars")
        fail = True
    if len(full) > FULL_LIMIT:
        print(f"  ERROR: full description too long by {len(full)-FULL_LIMIT} chars")
        fail = True

print("FAIL" if fail else "OK")
raise SystemExit(1 if fail else 0)
