# 🛒 Sistema de Punto de Venta para Tienda de Abarrotes

Este proyecto es un sistema de punto de venta (POS) desarrollado en **Java** con base de datos **MySQL**, diseñado específicamente para una tienda de abarrotes. Su objetivo es automatizar tareas clave como ventas, control de inventario y gestión de deudas, ofreciendo una herramienta tecnológica accesible para usuarios con conocimientos básicos en informática.

---

## 📌 Características principales

- Inicio de sesión con autenticación de usuarios.
- Registro de monto inicial de caja al iniciar jornada.
- Gestión de ventas (contado y fiado).
- Control de deudas y registro de abonos.
- Administración de inventario: entradas, salidas, productos bajos, categorías.
- Gestión de clientes (alta, modificación, historial de deuda).
- Devoluciones de productos con actualización automática.
- Generación de cortes de caja y reportes de productos vendidos.
- Interfaz gráfica intuitiva, construida para facilitar el uso diario.

---

## 🎯 Objetivos del Proyecto

- Automatizar el cálculo de montos a cobrar.
- Gestionar ventas a crédito y saldos pendientes.
- Controlar el inventario en tiempo real.
- Asegurar el almacenamiento persistente de datos con MySQL.
- Ofrecer una interfaz clara, funcional y amigable.

---

## 🧰 Tecnologías Utilizadas

- **Lenguaje de programación:** Java
- **Base de datos:** MySQL
- **Patrón de diseño:** MVC (Modelo-Vista-Controlador)
- **Librerías externas:**
  - [JCalendar](https://toedter.com/jcalendar/)
  - [FlatLaf](https://www.formdev.com/flatlaf/)

---

## ✅ Requisitos previos

- Tener instalado **Java JDK 20**
- Tener instalado **MySQL Server** (local o remoto).
- Tener un IDE como **NetBeans**, **IntelliJ IDEA** o **Eclipse**.

---

## 🚀 Instalación y uso

1. **Clona el repositorio:**

```bash
git clone https://github.com/AngelicaGuerOl/PointOfSaleSystem.git
cd PointOfSaleSystem
```
2. **Abre el proyecto en tu IDE Java preferido (IntelliJ, NetBeans, Eclipse, etc.).**
3. **Configura la conexión a la base de datos:**
   -Crea una base de datos vacía llamada tienda en MySQL.
   -Importa el archivo SQL incluido en /database.
   -Modifica la clase Conexion.java para configurar las credenciales de acceso a la base de datos.
4. Ejecuta el sistema:
   -Compila el proyecto.
   -Ejecuta la clase principal Login.java
   -Registrate y inicia sesión.

## 🧑‍💻 Autor

Desarrollado por: [Angelica Guerrero Olvera](https://github.com/AngelicaGuerOl)
