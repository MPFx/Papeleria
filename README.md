# Sistema de Papelería

## Versión
**1.0**

## Descripción
Sistema de gestión de inventario para papelería desarrollado en Java. Permite gestionar artículos, proveedores, control de stock y generar reportes.

## Alcance

### ✅ Qué hace
- Gestión de inventario por categorías (Librería, Oficina, Escolar)
- Control de stock con alertas de stock mínimo
- Gestión de proveedores y asignación de artículos
- Sistema de descuentos (por categoría, especial, por cantidad)
- Generación de reportes de inventario y proveedores
- Validación de ventas antes de realizar
- Cálculo de IVA (16%) según categoría

### ❌ Qué NO hace
- No tiene interfaz gráfica (solo consola)
- No persiste datos en base de datos real
- No procesa pagos reales
- No envía alertas por email reales

## Tipos de Artículo

| Tipo | Características | Descuento especial |
|------|----------------|-------------------|
| Librería | Tipo papel, color tinta, peso | 10% si peso > 100g |
| Oficina | Tamaño, tipo empaque, peso | 15% si stock > 50 |
| Escolar | Grado educativo, obligatorio | 20% en agosto |

## Escala de Descuentos por Cantidad

| Cantidad | Descuento |
|----------|-----------|
| 10 - 19 | 5% |
| 20 - 49 | 10% |
| 50 - 99 | 15% |
| 100+ | 20% |

## Documentación Javadoc
[https://mpfx.github.io/Papeleria/](https://mpfx.github.io/Papeleria/)

## Tecnologías
- Java
- Javadoc
- GitHub Pages

## Modo de uso
**Este proyecto NO es una aplicación visual/gráfica.**
Funciona exclusivamente por consola (CLI - Command Line Interface).

## Propósito
**Educativo / Pedagógico**
Este proyecto fue desarrollado con fines de aprendizaje y práctica de:
- Programación orientada a objetos en Java
- Herencia y clases abstractas
- Documentación técnica con Javadoc
- Control de versiones con Git y GitHub Pages

## Derechos de autor
**© 2026 ISC Israel de Jesus Mar Parada (MPFx++)**

Todos los derechos reservados.

Este software se proporciona "tal cual", sin garantías de ningún tipo. 
No está permitida su reproducción, distribución o modificación sin autorización expresa del autor.
