# 🛒 AdminPyme - Sistema de Gestión de Ventas

![Java](https://img.shields.io/badge/Java-21-blue.svg)
![Swing](https://img.shields.io/badge/GUI-Swing-orange.svg)
![Database](https://img.shields.io/badge/Base%20de%20datos-MariaDB%20%7C%20MySQL-green.svg)
![Estado](https://img.shields.io/badge/Estado-Producción%20Listo-brightgreen.svg)

## 📋 Descripción General

**AdminPyme** es un sistema de información automatizado de escritorio diseñado para optimizar la gestión de ventas, inventario y facturación en la tienda *"La Frontera del Sabor C.G."*, ubicada en Marizapa, Venezuela.

El proyecto evolucionó desde un prototipo básico hasta una solución completa que resuelve los principales problemas operativos: falta de registro de clientes, ausencia de control de cuentas por cobrar (créditos) y limitaciones en el inventario. El sistema proporciona datos en tiempo real, reduce errores manuales y mejora la experiencia del cliente mediante un servicio personalizado y opciones de pago flexibles.

> 🎓 **Contexto Académico:** Desarrollado como proyecto de grado para el *PNF en Ingeniería en Informática, Universidad Politécnica Territorial de Barlovento "Argelia Laya"* (Trayecto III, Sección U).

---

## ✨ Características Principales

### 🧑‍🤝‍🧑 Gestión de Usuarios y Seguridad
- Registro, actualización, inhabilitación de usuarios.
- Roles con privilegios (Administrador, Supervisor, Empleado).
- Autenticación segura y validación de formularios.

### 👥 Gestión de Clientes
- Registro completo (nombre, cédula, teléfono, dirección).
- Estado del cliente (Activo/Inactivo).
- Historial de compras para servicio personalizado.
- Clasificación de clientes.

### 📦 Control de Productos e Inventario
- Registro de productos por categoría (precio venta, precio compra, cantidad, IVA).
- Movimientos de inventario (entradas, salidas, transferencias).
- Actualización de stock en tiempo real y alertas de stock bajo.
- Gestión de categorías (activar/desactivar).
- Registro de proveedores.

### 🛍️ Procesamiento de Ventas
- **Ventas al contado** con pagos mixtos (efectivo, tarjeta, etc.).
- **Ventas a crédito** con modificación de fecha de pago y seguimiento de saldo.
- Agrupación de ventas a crédito como incobrables.
- Cálculo automático de la tasa del dólar según BCV.
- Devolución y cancelación de ventas al contado.

### 📊 Reportes e Informes
- Reportes de ventas diarias, semanales, mensuales.
- Productos más vendidos, entradas y salidas de stock.
- Informes de ingresos totales, gastos mensuales (variables y fijos).
- Reporte de pagos por medios de pago.
- Informe de pérdidas y pago de nómina.

### 🧾 Nómina y Contabilidad
- Registro de pagos de nómina (diario, semanal, quincenal, mensual).
- Módulo contable con libro diario y balance general.

---

## 🛠️ Tecnologías Utilizadas

| Área               | Tecnología                                      |
|--------------------|-------------------------------------------------|
| Lenguaje           | Java 21                                         |
| Interfaz gráfica   | Java Swing + FlatLaf (tema moderno)            |
| Base de datos      | MariaDB / MySQL                                 |
| Entorno de desarrollo | Apache NetBeans                              |
| Modelado           | UML (StarUML), Toad Data Modeler               |
| Metodología        | RUP (Rational Unified Process) - iterativo     |
| Control de dependencias | Maven (opcional, se puede usar MVN Repository) |

---

## 🗂️ Estructura de la Base de Datos (Principales Entidades)

- **Persona** (nombre, apellido, cédula)
- **Usuario** (nombre_usuario, contraseña, correo, rol, estado)
- **Cliente** (hereda de Persona + teléfono, dirección)
- **Rol** (tipo_rol, descripción)
- **Producto** (nombre, descripción, categoría, precio_venta, precio_compra, cantidad, unidad_medida, estado, IVA)
- **Categoría** (nombre, descripción, estado)
- **Inventario** (producto, cantidad_disponible, fecha)
- **Proveedor** (nombre, teléfono, dirección, estado)
- **Venta_contado** (producto, cliente, usuario, fecha)
- **Venta_credito** (producto, cliente, usuario, fecha_credito, estado, saldo)
- **Pago** (tipo_pago, monto, referencia, cliente)
- **Nómina** (usuario, periodo, monto, estado_pago)

> 📌 El modelo completo incluye enumerados (`enum`) para estados, tipos de movimiento, unidades de medida, etc.

---

## 🖥️ Módulos del Sistema (Interfaz de Usuario)

Según el manual de usuario, el sistema cuenta con los siguientes módulos accesibles desde el menú principal:

1. **Gestión de Usuarios** – Registrar, modificar, inhabilitar.
2. **Gestión de Clientes** – ABM (CRUD) de clientes.
3. **Compra** – Proveedores y gestión de compras de mercancía.
4. **Gestión de Productos** – Almacén, nuevos productos, categorías.
5. **Ventas** – Módulo principal de punto de venta (contado/crédito), cálculo de tasa BCV, pagos mixtos.
6. **Reportes** – Todos los informes descritos.
7. **Movimiento Contable** – Libro diario, balance general.
8. **Pagos de Nómina** – Crear y consultar pagos.
9. **Cerrar Sesión / Salir** – Seguridad.

---

## 🚀 Instalación y Ejecución

### Requisitos Previos
- Java JDK 21 o superior.
- Servidor MariaDB o MySQL (ej. XAMPP, WAMP, o instalación directa).
- NetBeans IDE (recomendado, aunque puede ejecutarse desde terminal).

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/AdminPyme.git
   cd AdminPyme