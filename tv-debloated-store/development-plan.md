# TV Debloated Store — Plan de desarrollo

> Versión ejecutable del roadmap. Se actualiza conforme avanza el proyecto.

## Visión

Una app para TVs Android que combina **launcher ligero + tienda de homescreens
debloat** creados por la comunidad, con monetización ética y sin lock-in.

## Principios no negociables

1. La TV debe sentirse **sencilla, funcional y rápida**.
2. El núcleo es **gratis para siempre**.
3. Los temas son **declarativos** (JSON + assets), nunca código ejecutable.
4. El usuario **siempre puede exportar** su configuración (sin lock-in).
5. Monetización **ética**: sin anuncios, sin vender datos, sin suscripciones depredadoras.

---

## Fase 0 — Validación (Semanas 1–3)

**Objetivo:** confirmar demanda antes de escribir código del producto.

- [ ] Entrevistar 10–15 usuarios de r/AndroidTV, XDA, Discord de debloat.
- [ ] Validar dolor: "¿querrías el resultado de debloat sin usar ADB?"
- [ ] Validar pago: "¿pagarías $1–5 por un homescreen listo para instalar?"
- [ ] Prototipar el formato `.tvtheme` con 3 configs reales (Projectivy/FLauncher).
- [ ] Revisar política de Google Play sobre launchers y device management.
- [ ] **Salida:** documento go/no-go + spec del MVP.

## Fase 1 — MVP (Semanas 4–15)

**Objetivo:** launcher + motor de temas + tienda de solo lectura.

### 1.1 Launcher y motor de temas
- [ ] Proyecto Kotlin + Jetpack Compose for TV (min SDK 21).
- [ ] Renderizar home con filas de apps navegables por D-pad.
- [ ] Motor de temas: aplicar `manifest.json` (colores, fondo, filas, orden).
- [ ] Importar `.tvtheme` desde archivo local.
- [ ] Rendimiento: arranque < 1s en 1 GB RAM, 60fps.

### 1.2 Tienda (solo lectura)
- [ ] Backend Supabase con catálogo de temas gratuitos.
- [ ] UI de tienda: listar, previsualizar, instalar.
- [ ] Descarga e instalación de temas desde CDN.

### 1.3 Distribución
- [ ] Firma de APK reproducible (CI).
- [ ] GitHub Releases con APK.
- [ ] Código corto de Downloader → URL del APK.
- [ ] Checker OTA (aviso de nueva versión al arrancar).

**Salida:** APK público instalable en Shield / Chromecast / Fire TV.

## Fase 2 — Marketplace (Semanas 16–25)

**Objetivo:** creadores suben y venden temas.

- [ ] Cuentas (anónimo + email opcional).
- [ ] Subida de temas por creadores con cola de moderación.
- [ ] Firma y verificación de temas.
- [ ] Pagos "paga lo que quieras" (mín. $1) con Stripe/RevenueCat.
- [ ] Revenue share 70/30 a creadores.
- [ ] Pro de una sola vez: backup/sync en nube (opcional).
- [ ] Reportes y bloqueo de temas maliciosos.

## Fase 3 — Debloat asistido (Semanas 26–31)

**Objetivo:** el "hook" viral del trend.

- [ ] Módulo ADB por red (depuración por red).
- [ ] Listas seguras/peligrosas de paquetes por fabricante.
- [ ] Confirmación explícita antes de deshabilitar/desinstalar.
- [ ] Solo "lista segura" en MVP; nunca servicios críticos sin aviso.
- [ ] **Sideload-only** (fuera de la versión de Play).
- [ ] Botón "antes/después" para compartir (crecimiento viral).

## Fase 4 — Crecimiento (continuo)

- [ ] F-Droid (si 100% FOSS) y evaluación de Google Play TV.
- [ ] Plantillas y herramientas para creadores.
- [ ] Preview en vivo de temas.
- [ ] Localización ES/EN/PT.
- [ ] Multi-dispositivo y sync en nube.

---

## Stack objetivo

| Capa | Tecnología |
|------|-----------|
| App TV | Kotlin + Compose for TV |
| Backend | Supabase (auth, DB, storage) |
| Pagos | Stripe / RevenueCat |
| CDN | Cloudflare R2 / S3 |
| CI | GitHub Actions (build + firma) |
| Analytics | Mínimo y anónimo (Plausible) |

## Riesgos top

1. **Política de Google Play** → mitigación: debloat fuera de Play, tienda compliant.
2. **Fragmentación de TVs** → mitigación: min SDK 21, testing en matriz de dispositivos.
3. **Temas maliciosos** → mitigación: declarativos + firma + moderación.
4. **Fricción de sideload** → mitigación: Downloader codes + OTA + docs de 1 clic.

## Métricas de éxito

- 20 usuarios instalan un tema sin ayuda (MVP).
- 1 creador vende un tema real (Fase 2).
- Retención D30 > 40%.
- Conversión a pago 3–5% (temas o Pro).
