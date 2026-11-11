
## Parte 1. Cacería de antipatrones

| Fragmento | Antipatrón | Síntoma que lo delata | Consecuencia en el consulado |
|---|---|---|---|
| 1. `OficinaDeTodo` | **God Object** | Una sola clase valida el pasaporte, cobra, guarda el expediente y envía el correo. | Si Ramiro falla, todo el consulado se detiene. Cualquier cambio toca la misma clase y es difícil de probar por separado. |
| 2. `CalculadoraDeTarifas` | **Copy-Paste Programming** | Tres métodos con la misma lógica `50 * meses + 10`, que solo cambia en el factor final. | Al subir la tarifa base se corrigieron dos copias y la tercera quedó con el precio viejo, así que se cobran precios inconsistentes. |
| 3. `EvaluadorMagico` | **Magic Numbers** | `tipo == 3`, `edad > 17`, `0.5` y `"APROBADA"` aparecen sin nombre ni significado documentado. | Nadie confirma qué significa el 3. Un tipo inválido (como el 9) se rechaza en silencio, sin explicar por qué. |
| 4. `SolicitudAcoplada` | **Acoplamiento fuerte** | La solicitud crea los servicios con `new` y los llama a mano, uno por uno. | Un viernes se olvidaron de `pagos` y un viajero perdió su reembolso. Agregar un aviso nuevo obliga a editar la solicitud. |

---

## Reflexión: cómo evitamos volver a caer en los antipatrones

Nuestra implementación evita caer de nuevo en los antipatrones porque cada decisión de diseño tiene un único punto de cambio. Contra el **God Object**, cada tarea del trámite (validar, cobrar, guardar y notificar) vive en su propia clase y `TramiteVisa` solo coordina, por lo que no existe un nuevo Ramiro. Contra el **Copy-Paste**, el precio base y el cargo fijo viven únicamente en `TarifaBase` y cada tipo de visa es una estrategia que lo reutiliza: subir la tarifa es una sola edición y un nuevo tipo de visa es una nueva clase `EstrategiaTarifa`. Contra los **Magic Numbers**, los códigos y rangos de edad viven en el enum `TipoVisa`, los factores en constantes con nombre y los resultados en el enum `Veredicto`, y los códigos inválidos lanzan un mensaje claro en vez de rechazarse en silencio. Contra el **acoplamiento fuerte**, `SolicitudVisa` solo conoce la interfaz `ObservadorSolicitud`, así que agregar un aviso nuevo es crear un observador y registrarlo, sin tocar la solicitud ni olvidar a nadie.

---
## Salida en Consola

```
================================================================================
CASO 1 - God Object
================================================================================
--- Heredada ---
[Ramiro] Cobrando $190.0 a Camila
[Ramiro] Guardando expediente de Camila
[Ramiro] Correo para Camila: su visa va en camino
[Ramiro] Tramite completado (el solito)
--- Rescatada ---
[ValidadorPasaporte] Pasaporte valido
[ProcesadorCobro] Cobrando $190.0 a Camila
[RepositorioExpedientes] Guardando expediente de Camila
[NotificadorCorreo] Correo para Camila: su visa va en camino
[TramiteVisa] Tramite completado (cada quien hizo lo suyo)
Ambas aprueban el tramite: true

================================================================================
CASO 2 - Copy-Paste Programming (base 50 -> 60.0)
================================================================================
Lugares editados (clonada): 3
Lugares editados (Strategy): 1
TURISTA: clonada=$190.0 | Strategy=$190.0 | coinciden=true
ESTUDIANTE: clonada=$95.0 | Strategy=$95.0 | coinciden=true
TRABAJO: clonada=$285.0 | Strategy=$285.0 | coinciden=true

================================================================================
CASO 3 - Magic Numbers
================================================================================
Heredada  evaluar(2, 20)          -> APROBADA
Rescatada evaluar(ESTUDIANTE, 20) -> APROBADA
--- Tipo 9 ---
Heredada  evaluar(9, 20) -> RECHAZADA (en silencio)
Rescatada desdeCodigo(9) -> Tipo de visa invalido: 9. Tipos validos: [TURISTA, ESTUDIANTE, TRABAJO]

================================================================================
CASO 4 - Acoplamiento fuerte
================================================================================
--- Heredada ---
  [Correo] Aviso de cancelacion enviado
  [Citas] Cupo liberado en la agenda
Reembolso procesado? false  <-- REEMBOLSO PERDIDO
--- Con Observer ---
  [CorreoObservador] Aviso de cancelacion enviado a Camila
  [CitasObservador] Cupo liberado para Camila
  [PagosObservador] Reembolso procesado para Camila
Reembolsos procesados: 1

================================================================================
CASO 5 - Integracion: solicitar, evaluar, cobrar y cancelar
================================================================================
1) SOLICITAR: visa TRABAJO para Camila (ACTIVA)
2) EVALUAR: APROBADA
3) COBRAR:
[ValidadorPasaporte] Pasaporte valido
[ProcesadorCobro] Cobrando $465.0 a Camila
[RepositorioExpedientes] Guardando expediente de Camila
[NotificadorCorreo] Correo para Camila: su visa va en camino
[TramiteVisa] Tramite completado (cada quien hizo lo suyo)
4) CANCELAR:
  [CorreoObservador] Aviso de cancelacion enviado a Camila
  [CitasObservador] Cupo liberado para Camila
  [PagosObservador] Reembolso procesado para Camila
   Estado: CANCELADA | reembolsos procesados: 1

TABLA FINAL: ANTIPATRONES CAPTURADOS
ANTIPATRON               | SINTOMA                                | SOLUCION
God Object               | Una clase hacia todo (Ramiro)          | Una clase por responsabilidad
Copy-Paste Programming   | Tarifa clonada (3 ediciones)           | Strategy con base unica (1 edicion)
Magic Numbers            | tipo == 3, 0.5, rechazo silencioso     | Enum TipoVisa y constantes con nombre
Acoplamiento fuerte      | Servicios llamados a mano              | Observer: la solicitud no los conoce

Process finished with exit code 0
```