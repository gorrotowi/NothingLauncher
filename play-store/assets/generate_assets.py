#!/usr/bin/env python3
"""
NothingLauncher - Play Store asset generator.

Generates all raster assets required to publish NothingLauncher on Google Play:
  * app icon (512x512)
  * feature graphic (1024x500)
  * promo graphic (180x120)
  * Android TV banner (1280x720)
  * phone screenshots (1080x1920), light & dark

Design language: "nothing" minimalism
  * monochrome black/white with a single green accent (#3DDC84)
  * Questrial typeface (shipped with the app)

Dependencies: Pillow (pip install Pillow)
Run: python3 generate_assets.py
Output: ./output/**
"""

import os
from PIL import Image, ImageDraw, ImageFont

# ----------------------------------------------------------------------------
# Brand tokens
# ----------------------------------------------------------------------------
BLACK = (0, 0, 0, 255)
WHITE = (255, 255, 255, 255)
GREEN = (61, 220, 132, 255)   # #3DDC84 (existing icon accent)
GRAY = (120, 120, 120, 255)
LIGHT_GRAY = (235, 235, 235, 255)

FONT_PATH = os.path.expanduser(
    "~/Nothing/NothingLauncher/app/src/main/res/font/questrialregular.otf"
)
if not os.path.exists(FONT_PATH):
    # Fallback: look for the font in a sibling checkout.
    FONT_PATH = os.path.join(
        os.path.dirname(os.path.dirname(os.path.dirname(os.path.abspath(__file__)))),
        "NothingLauncher",
        "app/src/main/res/font/questrialregular.otf",
    )

OUT = os.path.join(os.path.dirname(os.path.abspath(__file__)), "output")
SCREENSHOT_DIR = os.path.join(OUT, "screenshots")


def font(size):
    return ImageFont.truetype(FONT_PATH, size)


def canvas(size, color):
    return Image.new("RGBA", size, color)


def center_text(draw, box, text, fnt, fill):
    """Center `text` inside `box` (x0, y0, x1, y1)."""
    x0, y0, x1, y1 = box
    bbox = draw.textbbox((0, 0), text, font=fnt)
    w = bbox[2] - bbox[0]
    h = bbox[3] - bbox[1]
    draw.text(
        ((x0 + x1 - w) / 2 - bbox[0], (y0 + y1 - h) / 2 - bbox[1]),
        text,
        font=fnt,
        fill=fill,
    )
    return bbox


def text_size(draw, text, fnt):
    bbox = draw.textbbox((0, 0), text, font=fnt)
    return bbox[2] - bbox[0], bbox[3] - bbox[1]


# ----------------------------------------------------------------------------
# 1. App icon (512x512)
# ----------------------------------------------------------------------------
def make_icon():
    size = 512
    im = canvas((size, size), BLACK)
    d = ImageDraw.Draw(im)

    # White "N" mark + green accent dot (a nod to the original icon green).
    fnt = font(340)
    nw, nh = text_size(d, "N", fnt)
    n_x = (size - nw) / 2
    n_y = (size - nh) / 2
    d.text((n_x, n_y), "N", font=fnt, fill=WHITE)

    # green dot to the bottom-right of the "N" (like a period)
    dot_r = 26
    dot_x = n_x + nw + 34
    dot_y = n_y + nh - dot_r - 8
    d.ellipse(
        [dot_x - dot_r, dot_y - dot_r, dot_x + dot_r, dot_y + dot_r],
        fill=GREEN,
    )

    im.convert("RGB").save(os.path.join(OUT, "icon-512.png"))
    print("wrote icon-512.png")


# ----------------------------------------------------------------------------
# 2. Feature graphic (1024x500)
# ----------------------------------------------------------------------------
def make_feature_graphic():
    w, h = 1024, 500
    im = canvas((w, h), BLACK)
    d = ImageDraw.Draw(im)

    # subtle green baseline accent
    d.rectangle([0, h - 6, w, h], fill=GREEN)

    # wordmark
    fnt = font(120)
    tw, th = text_size(d, "NothingLauncher", fnt)
    d.text(((w - tw) / 2, 120), "NothingLauncher", font=fnt, fill=WHITE)

    # tagline
    fnt2 = font(40)
    tw2, _ = text_size(d, "Minimal. Fast. Nothing else.", fnt2)
    d.text(((w - tw2) / 2, 300), "Minimal. Fast. Nothing else.", font=fnt2, fill=GRAY)

    # small "N" mark on the left as a graphic anchor
    fnt3 = font(90)
    d.text((60, 40), "N.", font=fnt3, fill=GREEN)

    im.convert("RGB").save(os.path.join(OUT, "feature-graphic-1024x500.png"))
    print("wrote feature-graphic-1024x500.png")


# ----------------------------------------------------------------------------
# 3. Promo graphic (180x120)
# ----------------------------------------------------------------------------
def make_promo():
    w, h = 180, 120
    im = canvas((w, h), BLACK)
    d = ImageDraw.Draw(im)
    fnt = font(64)
    tw, th = text_size(d, "N", fnt)
    d.text(((w - tw) / 2, (h - th) / 2 - 4), "N", font=fnt, fill=WHITE)
    r = 9
    dot_x = (w - tw) / 2 + tw + 12
    dot_y = (h - th) / 2 + th - r - 2
    d.ellipse([dot_x - r, dot_y - r, dot_x + r, dot_y + r], fill=GREEN)
    im.convert("RGB").save(os.path.join(OUT, "promo-graphic-180x120.png"))
    print("wrote promo-graphic-180x120.png")


# ----------------------------------------------------------------------------
# 4. Android TV banner (1280x720)
# ----------------------------------------------------------------------------
def make_tv_banner():
    w, h = 1280, 720
    im = canvas((w, h), BLACK)
    d = ImageDraw.Draw(im)
    d.rectangle([0, h - 8, w, h], fill=GREEN)
    fnt = font(150)
    tw, th = text_size(d, "NothingLauncher", fnt)
    d.text(((w - tw) / 2, 150), "NothingLauncher", font=fnt, fill=WHITE)
    fnt2 = font(52)
    tw2, _ = text_size(d, "A calm, minimal home for your screen", fnt2)
    d.text(((w - tw2) / 2, 420), "A calm, minimal home for your screen", font=fnt2, fill=GRAY)
    im.convert("RGB").save(os.path.join(OUT, "tv-banner-1280x720.png"))
    print("wrote tv-banner-1280x720.png")


# ----------------------------------------------------------------------------
# Screenshot helpers
# ----------------------------------------------------------------------------
def draw_status_bar(d, w, dark):
    fg = WHITE if dark else BLACK
    h = 70
    # status bar background (subtle)
    d.rectangle([0, 0, w, h], fill=(0, 0, 0, 0))
    fnt = font(34)
    d.text((60, 24), "9:41", font=fnt, fill=fg)
    # right side: signal + battery glyphs (simple)
    x = w - 190
    d.ellipse([x, 28, x + 20, 48], outline=fg, width=3)
    d.rectangle([x + 30, 30, x + 62, 46], outline=fg, width=3)
    d.rectangle([x + 64, 36, x + 70, 40], fill=fg)
    d.text((x + 90, 24), "100%", font=fnt, fill=fg)


def draw_home_screen(im, dark):
    w, h = im.size
    d = ImageDraw.Draw(im)
    bg = BLACK if dark else WHITE
    fg = WHITE if dark else BLACK
    d.rectangle([0, 0, w, h], fill=bg)
    draw_status_bar(d, w, dark)
    fnt = font(120)
    tw, th = text_size(d, "Nothing", fnt)
    d.text(((w - tw) / 2, (h - th) / 2), "Nothing", font=fnt, fill=fg)
    # tiny green dot after "Nothing"
    r = 14
    dot_x = (w - tw) / 2 + tw + 20
    dot_y = (h - th) / 2 + th - r - 12
    d.ellipse([dot_x - r, dot_y - r, dot_x + r, dot_y + r], fill=GREEN)


def draw_app_list_screen(im, dark):
    w, h = im.size
    d = ImageDraw.Draw(im)
    bg = BLACK if dark else WHITE
    fg = WHITE if dark else BLACK
    d.rectangle([0, 0, w, h], fill=bg)
    draw_status_bar(d, w, dark)
    apps = [
        ("Camera", (240, 200, 255)),
        ("Maps", (180, 240, 180)),
        ("Phone", (160, 220, 255)),
        ("Messages", (255, 200, 160)),
        ("Music", (255, 180, 180)),
        ("Settings", (210, 210, 210)),
        ("Gallery", (200, 180, 255)),
        ("Clock", (255, 240, 160)),
        ("Calendar", (255, 170, 210)),
        ("Browser", (160, 255, 220)),
    ]
    top = 130
    row_h = 158
    for i, (name, color) in enumerate(apps):
        y = top + i * row_h
        cx, cy = 110, y + row_h // 2
        r = 46
        d.ellipse([cx - r, cy - r, cx + r, cy + r], fill=color)
        fnt = font(56)
        d.text((200, y + 40), name, font=fnt, fill=fg)
        if i < len(apps) - 1:
            line = LIGHT_GRAY if not dark else (40, 40, 40, 255)
            d.rectangle([60, y + row_h - 1, w - 60, y + row_h], fill=line)


# ----------------------------------------------------------------------------
# 5. Screenshots (1080x1920)
# ----------------------------------------------------------------------------
def make_screenshots():
    os.makedirs(SCREENSHOT_DIR, exist_ok=True)
    specs = [
        ("phone-1-home-light-1080x1920.png", False, "home"),
        ("phone-2-apps-light-1080x1920.png", False, "apps"),
        ("phone-3-home-dark-1080x1920.png", True, "home"),
        ("phone-4-apps-dark-1080x1920.png", True, "apps"),
    ]
    for fname, dark, kind in specs:
        im = canvas((1080, 1920), BLACK if dark else WHITE)
        if kind == "home":
            draw_home_screen(im, dark)
        else:
            draw_app_list_screen(im, dark)
        im.convert("RGB").save(os.path.join(SCREENSHOT_DIR, fname))
        print("wrote screenshots/%s" % fname)


def main():
    os.makedirs(OUT, exist_ok=True)
    os.makedirs(SCREENSHOT_DIR, exist_ok=True)
    if not os.path.exists(FONT_PATH):
        raise SystemExit("Font not found: %s" % FONT_PATH)
    make_icon()
    make_feature_graphic()
    make_promo()
    make_tv_banner()
    make_screenshots()
    print("Done. Assets written to %s" % OUT)


if __name__ == "__main__":
    main()
