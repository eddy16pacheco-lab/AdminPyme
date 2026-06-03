-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.11.11-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.10.0.7000
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para db_sistema_adminpyme_final
CREATE DATABASE IF NOT EXISTS `db_sistema_adminpyme_final` /*!40100 DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci */;
USE `db_sistema_adminpyme_final`;

-- Volcando estructura para tabla db_sistema_adminpyme_final.almacenes
CREATE TABLE IF NOT EXISTS `almacenes` (
  `almacen_id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `direccion` text DEFAULT NULL,
  `tipo_almacen` varchar(30) DEFAULT 'BODEGA',
  `estado` varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (`almacen_id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.almacenes: ~2 rows (aproximadamente)
INSERT INTO `almacenes` (`almacen_id`, `codigo`, `nombre`, `direccion`, `tipo_almacen`, `estado`) VALUES
	(1, '0001', 'Almacen de mercancia', 'Calle 19 de abril', 'BODEGA', 'ACTIVO'),
	(2, '0002', 'Almacen de ventas', 'Calle 19 de abril', 'BODEGA', 'ACTIVO');

-- Volcando estructura para vista db_sistema_adminpyme_final.cargar_producto
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `cargar_producto` (
	`producto_id` INT(11) NOT NULL,
	`producto_completo` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`tipo_categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`precio_compra` DOUBLE NULL,
	`precio_venta` DOUBLE NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.categoria
CREATE TABLE IF NOT EXISTS `categoria` (
  `id_categoria` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_categoria` varchar(100) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `estado` enum('Activo','Incativo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id_categoria`),
  UNIQUE KEY `tipo_categoria` (`tipo_categoria`),
  KEY `crear` (`id_usuario`),
  CONSTRAINT `crear` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.categoria: ~2 rows (aproximadamente)
INSERT INTO `categoria` (`id_categoria`, `tipo_categoria`, `descripcion`, `id_usuario`, `estado`) VALUES
	(5, 'Productos secos', '', 15, 'Activo'),
	(6, 'Productos de limpieza', '', 15, 'Activo');

-- Volcando estructura para vista db_sistema_adminpyme_final.categoria_consultar
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `categoria_consultar` (
	`Codigo categoria` INT(11) NOT NULL,
	`tipo_categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`estado` ENUM('Activo','Incativo') NOT NULL COLLATE 'latin1_swedish_ci',
	`producto` BIGINT(21) NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` int(11) DEFAULT NULL,
  `id_parroquia` int(11) DEFAULT NULL,
  `telefono` varchar(50) NOT NULL DEFAULT '',
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `telefeno` (`telefono`),
  KEY `puede_ser` (`id_persona`),
  KEY `estan_ubicado_en` (`id_parroquia`),
  CONSTRAINT `estan_ubicado_en` FOREIGN KEY (`id_parroquia`) REFERENCES `parroquia` (`id_parroquia`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `puede_ser` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.cliente: ~0 rows (aproximadamente)
INSERT INTO `cliente` (`id_cliente`, `id_persona`, `id_parroquia`, `telefono`, `estado`) VALUES
	(15, 31, 8, '04122828181', 'Activo');

-- Volcando estructura para tabla db_sistema_adminpyme_final.cliente_archivo
CREATE TABLE IF NOT EXISTS `cliente_archivo` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `id_persona` int(11) DEFAULT NULL,
  `id_parroquia` int(11) DEFAULT NULL,
  `telefono` varchar(50) NOT NULL DEFAULT '',
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `telefeno` (`telefono`),
  KEY `puede_ser` (`id_persona`),
  KEY `estan_ubicado_en` (`id_parroquia`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.cliente_archivo: ~0 rows (aproximadamente)

-- Volcando estructura para vista db_sistema_adminpyme_final.cliente_consultar
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `cliente_consultar` (
	`Numero de cliente` INT(11) NOT NULL,
	`nombre` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`apellido` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cedula` INT(11) NOT NULL,
	`prefijo` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`numero_principal` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`ubicacion` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`estado` ENUM('Activo','Inactivo') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.cliente_credito
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `cliente_credito` (
	`nombre` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`apellido` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cedula` INT(11) NOT NULL,
	`ventas_credito` BIGINT(21) NOT NULL,
	`Total acreditado` DOUBLE NULL,
	`fecha_cobro` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.consultar_detalle_orden_de_compra
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `consultar_detalle_orden_de_compra` (
	`Codigo de compra` INT(11) NOT NULL,
	`Codigo de producto` INT(11) NOT NULL,
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`forma_entrega` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cantidad empaquetada` INT(11) NULL,
	`Cantidad entrega` INT(11) NULL,
	`Precio por unidad` DOUBLE NULL,
	`Total de unidades` BIGINT(21) NULL,
	`Total de la compra` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.descripcion_pago
CREATE TABLE IF NOT EXISTS `descripcion_pago` (
  `id_descripcion_pago` int(11) NOT NULL AUTO_INCREMENT,
  `id_venta` int(11) DEFAULT NULL,
  `id_pago` int(11) DEFAULT NULL,
  `monto_cancelado` double NOT NULL,
  `ref_pago` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_descripcion_pago`) USING BTREE,
  UNIQUE KEY `ref_pago` (`ref_pago`),
  KEY `tiene_operaciones` (`id_venta`),
  KEY `tiene` (`id_pago`),
  CONSTRAINT `tiene` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tiene_operaciones` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.descripcion_pago: ~0 rows (aproximadamente)
INSERT INTO `descripcion_pago` (`id_descripcion_pago`, `id_venta`, `id_pago`, `monto_cancelado`, `ref_pago`) VALUES
	(39, 30, 3, 200, NULL);

-- Volcando estructura para vista db_sistema_adminpyme_final.descripicon_pago_consulta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `descripicon_pago_consulta` (
	`id_venta` INT(11) NOT NULL,
	`tipo_de_pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`ref_pago` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Monto bolivares` DOUBLE NOT NULL,
	`fecha` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.descripicon_pago_consulta_2
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `descripicon_pago_consulta_2` (
	`id_venta` INT(11) NOT NULL,
	`tipo_venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci',
	`tipo_de_pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`ref_pago` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Monto bolivares` DOUBLE NOT NULL,
	`fecha` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.detalle_de_compra_cliente
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `detalle_de_compra_cliente` (
	`nombre` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`apellido` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cedula` INT(11) NOT NULL,
	`telefono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`nombre_parroquia` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`cantidad_vendida` INT(11) NOT NULL,
	`fecha` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.detalle_orden_de_compra
CREATE TABLE IF NOT EXISTS `detalle_orden_de_compra` (
  `id_detalle_orden_de_compra` int(11) NOT NULL AUTO_INCREMENT,
  `id_orden_de_compra` int(11) DEFAULT NULL,
  `id_entrega` int(11) DEFAULT NULL,
  `producto_id` int(11) DEFAULT NULL,
  `cantidad_entregada` int(11) DEFAULT NULL,
  `precio_unitario` double(7,2) DEFAULT NULL,
  `cantidad_empaquetada` int(11) DEFAULT NULL,
  `almacen_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_detalle_orden_de_compra`),
  KEY `esta_conformado` (`id_orden_de_compra`),
  KEY `anexado` (`id_entrega`),
  KEY `tiene_muchos` (`producto_id`),
  KEY `almacen_id` (`almacen_id`),
  CONSTRAINT `anexado` FOREIGN KEY (`id_entrega`) REFERENCES `entrega` (`id_entrega`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `esta_conformado` FOREIGN KEY (`id_orden_de_compra`) REFERENCES `orden_de_compra` (`id_orden_de_compra`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_almacen` FOREIGN KEY (`almacen_id`) REFERENCES `almacenes` (`almacen_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tiene_muchos` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.detalle_orden_de_compra: ~4 rows (aproximadamente)
INSERT INTO `detalle_orden_de_compra` (`id_detalle_orden_de_compra`, `id_orden_de_compra`, `id_entrega`, `producto_id`, `cantidad_entregada`, `precio_unitario`, `cantidad_empaquetada`, `almacen_id`) VALUES
	(13, 34, 4, 5, 2, 14000.00, 20, 1),
	(16, 52, 2, 7, 5, 10.00, 20, 2),
	(32, 53, 2, 6, 2, 2.00, 2, 2),
	(33, 52, 2, 7, 5, 10.00, 20, 2),
	(35, 52, 2, 7, 5, 10.00, 20, 2),
	(36, 52, 2, 7, 5, 10.00, 20, 2);

-- Volcando estructura para tabla db_sistema_adminpyme_final.detalle_venta
CREATE TABLE IF NOT EXISTS `detalle_venta` (
  `id_detalle_venta` int(11) NOT NULL AUTO_INCREMENT,
  `producto_id` int(11) DEFAULT NULL,
  `id_venta` int(11) DEFAULT NULL,
  `cantidad_vendida` int(11) NOT NULL,
  `monto_venta` double NOT NULL,
  `tipo_venta` enum('Contado','Credito') NOT NULL DEFAULT 'Contado',
  `estado_venta` enum('Aprobado','Pendiente','Devolucion') DEFAULT 'Aprobado',
  `fecha_cobro` date DEFAULT NULL,
  `porcentaje` double DEFAULT NULL,
  PRIMARY KEY (`id_detalle_venta`),
  KEY `pedidos` (`producto_id`),
  KEY `genera` (`id_venta`),
  CONSTRAINT `genera` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `pedidos` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.detalle_venta: ~1 rows (aproximadamente)
INSERT INTO `detalle_venta` (`id_detalle_venta`, `producto_id`, `id_venta`, `cantidad_vendida`, `monto_venta`, `tipo_venta`, `estado_venta`, `fecha_cobro`, `porcentaje`) VALUES
	(57, 6, 30, 10, 200, 'Contado', 'Aprobado', NULL, NULL),
	(65, 7, 30, 10, 200, 'Contado', 'Aprobado', NULL, NULL);

-- Volcando estructura para tabla db_sistema_adminpyme_final.entrega
CREATE TABLE IF NOT EXISTS `entrega` (
  `id_entrega` int(11) NOT NULL AUTO_INCREMENT,
  `forma_entrega` varchar(20) NOT NULL,
  PRIMARY KEY (`id_entrega`),
  UNIQUE KEY `forma_entrega` (`forma_entrega`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.entrega: ~4 rows (aproximadamente)
INSERT INTO `entrega` (`id_entrega`, `forma_entrega`) VALUES
	(4, 'BULTO'),
	(1, 'CAJA'),
	(3, 'PAQUETE'),
	(2, 'SACO');

-- Volcando estructura para vista db_sistema_adminpyme_final.factura_pago
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `factura_pago` (
	`id_venta` INT(11) NOT NULL,
	`tipo_de_pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`ref_pago` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`monto_cancelado` DOUBLE NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.factura_venta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `factura_venta` (
	`Número de Factura` INT(11) NOT NULL,
	`Fecha` DATE NULL,
	`Hora` TIME NULL,
	`Cliente` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cédula` INT(11) NOT NULL,
	`Teléfono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Vendedor` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Descripción` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Cantidad` INT(11) NOT NULL,
	`Precio Unitario` DOUBLE NOT NULL,
	`Subtotal` DOUBLE NOT NULL,
	`Tipo de Venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci',
	`Estado` ENUM('Aprobado','Pendiente','Devolucion') NULL COLLATE 'latin1_swedish_ci',
	`Total a Pagar` DOUBLE NULL,
	`Fecha_cobro` DATE NULL,
	`Porcentaje` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.gastos
CREATE TABLE IF NOT EXISTS `gastos` (
  `id_gastos` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha_gasto` date NOT NULL,
  `monto_gastos` double NOT NULL,
  `motivo_del_gasto` varchar(1000) DEFAULT NULL,
  `categoria` enum('Pago de servicios','Logistica','Otros') NOT NULL,
  `comprobante` varchar(50) NOT NULL,
  `id_pago` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_gastos`),
  UNIQUE KEY `comprobante` (`comprobante`),
  KEY `tiene_multiples` (`id_usuario`),
  KEY `fk_pago2` (`id_pago`),
  CONSTRAINT `fk_pago2` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tiene_multiples` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.gastos: ~0 rows (aproximadamente)

-- Volcando estructura para vista db_sistema_adminpyme_final.gastos_consulta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `gastos_consulta` (
	`id_gastos` INT(11) NOT NULL,
	`nombre_usuario` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`monto_gastos` DOUBLE NOT NULL,
	`motivo_del_gasto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`categoria` ENUM('Pago de servicios','Logistica','Otros') NOT NULL COLLATE 'latin1_swedish_ci',
	`comprobante` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`tipo_de_pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`fecha_gasto` DATE NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.historial_producto
CREATE TABLE IF NOT EXISTS `historial_producto` (
  `id_historial_producto` int(11) NOT NULL AUTO_INCREMENT,
  `fecha_movimiento` date NOT NULL,
  `producto_id` int(11) NOT NULL,
  `tipo_movimiento` enum('ENTRADA','SALIDA','TRANSFERENCIA','CREACION') NOT NULL DEFAULT 'ENTRADA',
  `cantidad_anterior` int(11) DEFAULT NULL,
  `cantidad_nueva` int(11) DEFAULT NULL,
  `almacen_id` int(11) DEFAULT NULL,
  `id_orden_de_compra` int(11) DEFAULT NULL,
  `id_venta` int(11) DEFAULT NULL,
  `id_inventario` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_historial_producto`),
  KEY `producto_id` (`producto_id`),
  KEY `id_orden_de_compra` (`id_orden_de_compra`),
  KEY `kf_almacen` (`almacen_id`),
  KEY `id_venta` (`id_venta`),
  KEY `id_inventario` (`id_inventario`),
  CONSTRAINT `fk_orden_compra` FOREIGN KEY (`id_orden_de_compra`) REFERENCES `orden_de_compra` (`id_orden_de_compra`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_producto` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_venta` FOREIGN KEY (`id_venta`) REFERENCES `venta` (`id_venta`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_inventario` FOREIGN KEY (`id_inventario`) REFERENCES `inventario` (`id_inventario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `kf_almacen` FOREIGN KEY (`almacen_id`) REFERENCES `almacenes` (`almacen_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.historial_producto: ~4 rows (aproximadamente)
INSERT INTO `historial_producto` (`id_historial_producto`, `fecha_movimiento`, `producto_id`, `tipo_movimiento`, `cantidad_anterior`, `cantidad_nueva`, `almacen_id`, `id_orden_de_compra`, `id_venta`, `id_inventario`) VALUES
	(3, '2025-12-09', 7, 'ENTRADA', 100, 200, 2, 52, NULL, 11),
	(9, '2025-12-09', 7, 'SALIDA', 200, 190, 1, NULL, 30, 11),
	(10, '2025-12-09', 9, 'CREACION', 0, 0, 1, NULL, NULL, NULL),
	(11, '2025-12-09', 12, 'CREACION', 0, 0, NULL, NULL, NULL, NULL);

-- Volcando estructura para tabla db_sistema_adminpyme_final.inventario
CREATE TABLE IF NOT EXISTS `inventario` (
  `id_inventario` int(11) NOT NULL AUTO_INCREMENT,
  `producto_id` int(11) DEFAULT NULL,
  `cantidad_dis` int(11) NOT NULL,
  `cantidad_min_stock` int(11) DEFAULT 1,
  `ubicacion_id` int(11) NOT NULL DEFAULT 1,
  `estado_inventario` enum('DISPONIBLE','NO DISPONIBLE') NOT NULL DEFAULT 'DISPONIBLE',
  PRIMARY KEY (`id_inventario`),
  KEY `agregado` (`producto_id`),
  KEY `ubicacion_fisica` (`ubicacion_id`),
  CONSTRAINT `agregado` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`producto_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `estan_ubicados_en` FOREIGN KEY (`ubicacion_id`) REFERENCES `ubicaciones_fisicas` (`ubicacion_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.inventario: ~4 rows (aproximadamente)
INSERT INTO `inventario` (`id_inventario`, `producto_id`, `cantidad_dis`, `cantidad_min_stock`, `ubicacion_id`, `estado_inventario`) VALUES
	(8, 6, 4, 1, 1, 'DISPONIBLE'),
	(11, 7, 190, 1, 1, 'DISPONIBLE'),
	(12, 6, 4, 1, 3, 'DISPONIBLE');

-- Volcando estructura para vista db_sistema_adminpyme_final.inventario_productos_
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `inventario_productos_` (
	`codigo_barras` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Producto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`tipo_categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`precio_compra` DOUBLE NULL,
	`precio_venta` DOUBLE NOT NULL,
	`cantidad_dis` INT(11) NOT NULL,
	`cantidad_min_stock` INT(11) NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.listado_detalle_de_ventas
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `listado_detalle_de_ventas` (
	`Numero de venta` INT(11) NOT NULL,
	`Codigo de producto` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`Cantidad vendida` INT(11) NOT NULL,
	`sub total` DOUBLE NOT NULL,
	`Porcentaje` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci',
	`Total de la venta` DOUBLE NULL,
	`Venta realizada` DATE NULL,
	`Fecha de cobro` DATE NULL,
	`estado_venta` ENUM('Aprobado','Pendiente','Devolucion') NULL COLLATE 'latin1_swedish_ci',
	`tipo_venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.listado_de_ventas
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `listado_de_ventas` (
	`Numero de venta` INT(11) NOT NULL,
	`Informacion del cliente` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Responsable` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cantidad de productos vendida` DECIMAL(32,0) NULL,
	`Total de la venta` DOUBLE NULL,
	`Venta de la fecha` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.listado_de_ventas_2
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `listado_de_ventas_2` (
	`Numero de venta` INT(11) NOT NULL,
	`Codigo de producto` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`Cantidad vendida` INT(11) NOT NULL,
	`sub total` DOUBLE NOT NULL,
	`Porcentaje` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci',
	`Total de la venta` DOUBLE NULL,
	`Venta realizada` DATE NULL,
	`Fecha de cobro` DATE NULL,
	`estado_venta` ENUM('Aprobado','Pendiente','Devolucion') NULL COLLATE 'latin1_swedish_ci',
	`tipo_venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.lista_productos_inventario
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `lista_productos_inventario` (
	`codigo_barras` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`cantidad_dis` INT(11) NOT NULL,
	`producto_id` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para procedimiento db_sistema_adminpyme_final.mover_producto_a_activos
DELIMITER //
CREATE PROCEDURE `mover_producto_a_activos`(
	IN `p_producto_id` INT
)
BEGIN
    DECLARE producto_existente INT DEFAULT 0;
    DECLARE v_id_categoria INT;
    DECLARE v_nombre_producto VARCHAR(50);
    DECLARE v_descripcion VARCHAR(100);
    DECLARE v_precio_compra DOUBLE;
    DECLARE v_precio_venta DOUBLE;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Verificar si el producto existe en inactivos
    SELECT COUNT(*) INTO producto_existente 
    FROM producto_inactivos 
    WHERE producto_id = p_producto_id;
    
    IF producto_existente = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El producto no está en la tabla inactivos';
    END IF;
    
    -- Obtener los datos del producto inactivo
    SELECT 
        id_categoria,
        nombre_producto,
        descripcion,
        precio_compra,
        precio_venta
    INTO 
        v_id_categoria,
        v_nombre_producto,
        v_descripcion,
        v_precio_compra,
        v_precio_venta
    FROM producto_inactivos 
    WHERE producto_id = p_producto_id;
    
    -- Insertar en producto (reactivar)
    INSERT INTO producto (
        producto_id, 
        id_categoria, 
        nombre_producto, 
        descripcion, 
        precio_compra, 
        precio_venta, 
        estado
    )
    VALUES (
        p_producto_id,
        v_id_categoria,
        v_nombre_producto,
        v_descripcion,
        v_precio_compra,
        v_precio_venta,
        'Activo'
    );
    
    -- ELIMINAR de producto_inactivos
    DELETE FROM producto_inactivos WHERE producto_id = p_producto_id;
    
    COMMIT;
    
    SELECT 'Producto reactivado correctamente' AS resultado;
END//
DELIMITER ;

-- Volcando estructura para procedimiento db_sistema_adminpyme_final.mover_producto_a_inactivos
DELIMITER //
CREATE PROCEDURE `mover_producto_a_inactivos`(IN p_producto_id INT)
BEGIN
    DECLARE producto_existente INT DEFAULT 0;
    DECLARE v_id_categoria INT;
    DECLARE v_nombre_producto VARCHAR(50);
    DECLARE v_descripcion VARCHAR(100);
    DECLARE v_precio_compra DOUBLE;
    DECLARE v_precio_venta DOUBLE;
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Verificar si el producto existe en la tabla producto
    SELECT COUNT(*) INTO producto_existente 
    FROM producto 
    WHERE producto_id = p_producto_id;
    
    IF producto_existente = 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El producto no existe en la tabla producto';
    END IF;
    
    -- Verificar si ya está en inactivos
    SELECT COUNT(*) INTO producto_existente 
    FROM producto_inactivos 
    WHERE producto_id = p_producto_id;
    
    IF producto_existente > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El producto ya está en la tabla inactivos';
    END IF;
    
    -- Obtener los datos del producto
    SELECT 
        id_categoria,
        nombre_producto,
        descripcion,
        precio_compra,
        precio_venta
    INTO 
        v_id_categoria,
        v_nombre_producto,
        v_descripcion,
        v_precio_compra,
        v_precio_venta
    FROM producto 
    WHERE producto_id = p_producto_id;
    
    -- Insertar en producto_inactivos
    INSERT INTO producto_inactivos (
        producto_id, 
        id_categoria, 
        nombre_producto, 
        descripcion, 
        precio_compra, 
        precio_venta, 
        estado
    )
    VALUES (
        p_producto_id,
        v_id_categoria,
        v_nombre_producto,
        v_descripcion,
        v_precio_compra,
        v_precio_venta,
        'Inactivo'
    );
    
    -- ELIMINAR de la tabla producto (esto SÍ funciona)
    DELETE FROM producto WHERE producto_id = p_producto_id;
    
    COMMIT;
    
    SELECT 'Producto movido a inactivos correctamente' AS resultado;
END//
DELIMITER ;

-- Volcando estructura para tabla db_sistema_adminpyme_final.municipio
CREATE TABLE IF NOT EXISTS `municipio` (
  `id_municipio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_municipio` varchar(100) NOT NULL,
  PRIMARY KEY (`id_municipio`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.municipio: ~21 rows (aproximadamente)
INSERT INTO `municipio` (`id_municipio`, `nombre_municipio`) VALUES
	(1, 'Acevedo'),
	(2, 'Andrés Bello'),
	(3, 'Baruta'),
	(4, 'Bruz'),
	(5, 'Buroz'),
	(6, 'Carrizal'),
	(7, 'Chacao'),
	(8, 'Cristóbal Rojas'),
	(9, 'El Hatillo'),
	(10, 'Guaicaipuro'),
	(11, 'Independencia'),
	(12, 'Lander'),
	(13, 'Los Salias'),
	(14, 'Páez'),
	(15, 'Paz Castillo'),
	(16, 'Pedro Gual'),
	(17, 'Plaza'),
	(18, 'Simón Bolívar'),
	(19, 'Sucre'),
	(20, 'Urdaneta'),
	(21, 'Zamora');

-- Volcando estructura para tabla db_sistema_adminpyme_final.nomina
CREATE TABLE IF NOT EXISTS `nomina` (
  `id_nomina` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `id_pago` int(11) DEFAULT NULL,
  `ref_pago` varchar(20) DEFAULT NULL,
  `fecha_pago` date NOT NULL,
  `salario_bruto` double NOT NULL,
  `periodo_pago` enum('Diario','Semanal','Quincenal','Mensual') NOT NULL DEFAULT 'Quincenal',
  `estado_pago` enum('Pendiente','Completado','Anulado','Conciliado') NOT NULL DEFAULT 'Completado',
  PRIMARY KEY (`id_nomina`),
  UNIQUE KEY `ref_pago` (`ref_pago`),
  KEY `recibe_multiples` (`id_usuario`),
  KEY `fk_id_pago` (`id_pago`),
  CONSTRAINT `fk_id_pago` FOREIGN KEY (`id_pago`) REFERENCES `pago` (`id_pago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `recibe_multiples` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.nomina: ~0 rows (aproximadamente)

-- Volcando estructura para vista db_sistema_adminpyme_final.nomina_consulta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `nomina_consulta` (
	`id_nomina` INT(11) NOT NULL,
	`Empleado` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cédula` INT(11) NOT NULL,
	`Periodo de Pago` ENUM('Diario','Semanal','Quincenal','Mensual') NOT NULL COLLATE 'latin1_swedish_ci',
	`Salario Bruto` DOUBLE NOT NULL,
	`tipo_de_pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`estado_pago` ENUM('Pendiente','Completado','Anulado','Conciliado') NOT NULL COLLATE 'latin1_swedish_ci',
	`Ref. del Pago` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Fecha del Pago` DATE NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.orden_de_compra
CREATE TABLE IF NOT EXISTS `orden_de_compra` (
  `id_orden_de_compra` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_orden_compra` varchar(50) DEFAULT NULL,
  `id_provedor` int(11) DEFAULT NULL,
  `fecha_orden` date DEFAULT NULL,
  PRIMARY KEY (`id_orden_de_compra`),
  UNIQUE KEY `codigo_orden_compra` (`codigo_orden_compra`),
  KEY `entraga` (`id_provedor`),
  CONSTRAINT `entraga` FOREIGN KEY (`id_provedor`) REFERENCES `provedor` (`id_provedor`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.orden_de_compra: ~8 rows (aproximadamente)
INSERT INTO `orden_de_compra` (`id_orden_de_compra`, `codigo_orden_compra`, `id_provedor`, `fecha_orden`) VALUES
	(34, '001', 7, '2025-12-05'),
	(35, '002', 7, '2025-12-10'),
	(36, '003', 7, '2025-12-11'),
	(40, '004', 7, '2025-12-17'),
	(45, '005', 7, '2025-12-12'),
	(46, '006', 8, '2025-12-18'),
	(50, '009', 8, '2025-12-10'),
	(52, '010', 8, '2025-12-11'),
	(53, '011', 8, '2025-12-11');

-- Volcando estructura para vista db_sistema_adminpyme_final.orden_de_compra_vista
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `orden_de_compra_vista` (
	`Codigo de compra` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`nombre` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`direccion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`telefono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Fecha de solicitud` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.pago
CREATE TABLE IF NOT EXISTS `pago` (
  `id_pago` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_de_pago` varchar(40) NOT NULL,
  PRIMARY KEY (`id_pago`),
  UNIQUE KEY `tipo_de_pago` (`tipo_de_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.pago: ~5 rows (aproximadamente)
INSERT INTO `pago` (`id_pago`, `tipo_de_pago`) VALUES
	(5, 'Biopago'),
	(3, 'Bolivares'),
	(2, 'Divisas'),
	(1, 'Pago Móvil'),
	(4, 'Punto de venta');

-- Volcando estructura para vista db_sistema_adminpyme_final.pago_de_nomina
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `pago_de_nomina` (
	`id_nomina` INT(11) NOT NULL,
	`Empleado` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`tipo_rol` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Periodo de Pago` ENUM('Diario','Semanal','Quincenal','Mensual') NOT NULL COLLATE 'latin1_swedish_ci',
	`Salario Bruto` DOUBLE NOT NULL,
	`Salario Diario` DOUBLE NULL,
	`tipo_de_pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`estado_pago` ENUM('Pendiente','Completado','Anulado','Conciliado') NOT NULL COLLATE 'latin1_swedish_ci',
	`Ref. del Pago` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Fecha del Pago` DATE NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.parroquia
CREATE TABLE IF NOT EXISTS `parroquia` (
  `id_parroquia` int(11) NOT NULL AUTO_INCREMENT,
  `id_municipio` int(11) DEFAULT NULL,
  `nombre_parroquia` varchar(100) NOT NULL,
  PRIMARY KEY (`id_parroquia`),
  KEY `ubicacion` (`id_municipio`),
  CONSTRAINT `ubicacion` FOREIGN KEY (`id_municipio`) REFERENCES `municipio` (`id_municipio`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.parroquia: ~53 rows (aproximadamente)
INSERT INTO `parroquia` (`id_parroquia`, `id_municipio`, `nombre_parroquia`) VALUES
	(1, 1, 'Caucagua'),
	(2, 1, 'Aragüita'),
	(3, 1, 'Arevalo González'),
	(4, 1, 'Capaya'),
	(5, 1, 'El Café'),
	(6, 1, 'Marizapa'),
	(7, 1, 'Panaquire'),
	(8, 1, 'Ribas'),
	(9, 2, 'San José de Barlovento'),
	(10, 2, 'Cumbo'),
	(11, 3, 'Baruta'),
	(12, 3, 'El Cafetal'),
	(13, 3, 'Las Minas'),
	(14, 4, 'Higuerote'),
	(15, 4, 'Curiepe'),
	(16, 4, 'Tacarigua'),
	(17, 5, 'Mamporal'),
	(18, 6, 'Carrizal'),
	(19, 7, 'Chacao'),
	(20, 8, 'Charallave'),
	(21, 8, 'Las Brisas'),
	(22, 9, 'El Hatillo'),
	(23, 10, 'Los Teques'),
	(24, 10, 'Altagracia de la Montaña'),
	(25, 10, 'Cecilio Acosta'),
	(26, 10, 'El Jarillo'),
	(27, 10, 'Paracotos'),
	(28, 10, 'San Pedro'),
	(29, 10, 'Tácata'),
	(30, 11, 'Santa Teresa del Tuy'),
	(31, 11, 'El Cartanal'),
	(32, 12, 'Ocumare del Tuy'),
	(33, 12, 'La Democracia'),
	(34, 13, 'San Antonio de los Altos'),
	(35, 14, 'Río Chico'),
	(36, 14, 'El Guapo'),
	(37, 14, 'Tacarigua de la Laguna'),
	(38, 14, 'Paparo'),
	(39, 14, 'San Fernando del Guapo'),
	(40, 15, 'Santa Lucía'),
	(41, 16, 'Cúpira'),
	(42, 16, 'Machurucuto'),
	(43, 17, 'Guarenas'),
	(44, 18, 'San Francisco de Yare'),
	(45, 19, 'Petare'),
	(46, 19, 'Caucagüita'),
	(47, 19, 'Fila de Mariches'),
	(48, 19, 'La Dolorita'),
	(49, 19, 'Leoncio Martínez'),
	(50, 20, 'Cúa'),
	(51, 20, 'Nueva Cúa'),
	(52, 21, 'Guatire'),
	(53, 21, 'Bolívar');

-- Volcando estructura para tabla db_sistema_adminpyme_final.persona
CREATE TABLE IF NOT EXISTS `persona` (
  `id_persona` int(11) NOT NULL AUTO_INCREMENT,
  `cedula` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `apellido` varchar(30) NOT NULL,
  PRIMARY KEY (`id_persona`),
  UNIQUE KEY `cedula` (`cedula`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.persona: ~2 rows (aproximadamente)
INSERT INTO `persona` (`id_persona`, `cedula`, `nombre`, `apellido`) VALUES
	(29, 1, 'Admin', 'Admin'),
	(30, 31394074, 'Eddy', 'Pacheco'),
	(31, 12952192, 'Mariela', 'Mijares');

-- Volcando estructura para tabla db_sistema_adminpyme_final.producto
CREATE TABLE IF NOT EXISTS `producto` (
  `producto_id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_barras` varchar(50) NOT NULL DEFAULT '',
  `id_categoria` int(11) NOT NULL,
  `nombre_producto` varchar(50) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `capacidad_unidad` int(11) NOT NULL,
  `unidad_medida` enum('KG','GR','LT','ML','UN') NOT NULL DEFAULT 'UN',
  `precio_compra` double DEFAULT NULL,
  `precio_venta` double NOT NULL,
  `fecha_vencimiento` date NOT NULL,
  `estado` enum('Activo','Inactivo','Descontinuado') NOT NULL DEFAULT 'Activo',
  `IVA` enum('TIENE IVA','EXCENTO DE IVA') DEFAULT 'TIENE IVA',
  PRIMARY KEY (`producto_id`) USING BTREE,
  UNIQUE KEY `nombre_producto` (`nombre_producto`) USING BTREE,
  UNIQUE KEY `codigo_barras` (`codigo_barras`),
  KEY `pertenece` (`id_categoria`) USING BTREE,
  CONSTRAINT `pertenece` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.producto: ~6 rows (aproximadamente)
INSERT INTO `producto` (`producto_id`, `codigo_barras`, `id_categoria`, `nombre_producto`, `descripcion`, `capacidad_unidad`, `unidad_medida`, `precio_compra`, `precio_venta`, `fecha_vencimiento`, `estado`, `IVA`) VALUES
	(5, '7591234560018', 6, 'Harina pan blanca', 'paquete amarilllo', 1, 'KG', 20, 30, '2025-05-31', 'Activo', 'TIENE IVA'),
	(6, '7292234560018', 5, 'Refresco Glup', '', 1, 'LT', 275, 300, '2025-12-27', 'Activo', 'TIENE IVA'),
	(7, '7591234560118', 5, 'Harina La lucha', 'paquete blanco', 1, 'UN', 20, 30, '2025-12-27', 'Activo', 'TIENE IVA'),
	(8, '7591234160018', 5, 'Harina Kaly', 'paquete amarilllo', 1, 'UN', 20, 30, '2025-12-27', 'Activo', 'TIENE IVA'),
	(9, '7591234520018', 6, 'Arroz Mary', 'Tradicional', 900, 'GR', 15, 20, '2025-05-31', 'Activo', 'TIENE IVA'),
	(12, '7591234563018', 6, 'Arroz Mary Clasico', 'Clasico', 900, 'GR', 20, 30, '2025-05-31', 'Activo', 'TIENE IVA');

-- Volcando estructura para vista db_sistema_adminpyme_final.productos_stock_min
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `productos_stock_min` (
	`codigo de producto` INT(11) NOT NULL,
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`Disponibilidad actual` INT(11) NOT NULL,
	`Disponibilidad minima` INT(11) NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.producto_buscar
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `producto_buscar` (
	`producto_id` INT(11) NOT NULL,
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`tipo_categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`precio_compra` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.producto_consulta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `producto_consulta` (
	`producto_id` INT(11) NOT NULL,
	`tipo_categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`precio_compra` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.producto_consulta_inactivo
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `producto_consulta_inactivo` (
	`producto_id` INT(11) NOT NULL,
	`tipo_categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`precio_venta` DOUBLE NOT NULL,
	`precio_compra` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.producto_inactivos
CREATE TABLE IF NOT EXISTS `producto_inactivos` (
  `producto_id` int(11) NOT NULL AUTO_INCREMENT,
  `id_categoria` int(11) DEFAULT NULL,
  `nombre_producto` varchar(50) NOT NULL,
  `descripcion` varchar(100) NOT NULL,
  `precio_compra` double DEFAULT NULL,
  `precio_venta` double NOT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`producto_id`),
  UNIQUE KEY `nombre_producto` (`nombre_producto`),
  KEY `pertenece` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.producto_inactivos: ~0 rows (aproximadamente)

-- Volcando estructura para tabla db_sistema_adminpyme_final.provedor
CREATE TABLE IF NOT EXISTS `provedor` (
  `id_provedor` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `correo_electronico` varchar(254) NOT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `estado` enum('Activo','Inactivo') DEFAULT 'Activo',
  PRIMARY KEY (`id_provedor`),
  UNIQUE KEY `telefeno` (`telefono`) USING BTREE,
  UNIQUE KEY `correo_electronico` (`correo_electronico`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.provedor: ~0 rows (aproximadamente)
INSERT INTO `provedor` (`id_provedor`, `nombre`, `telefono`, `correo_electronico`, `direccion`, `estado`) VALUES
	(7, 'proveedor1', '0412-7372311', 'proveedor1@gmail.com', 'Caracas', 'Activo'),
	(8, 'proveedor2', '0412-0321212', 'proveedor2@gmail.com', 'Caracas', 'Activo');

-- Volcando estructura para vista db_sistema_adminpyme_final.reporte_de_compra
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `reporte_de_compra` (
	`Codigo de compra` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Proveedor` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Codigo de producto` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Precio unitario` DOUBLE NULL,
	`Informacion de entrega` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Total de unidades` BIGINT(21) NULL,
	`Total de la compra` DOUBLE NULL,
	`Fecha de compra` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.reporte_disponibilidad_inventario
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `reporte_disponibilidad_inventario` (
	`Codigo de Barra` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`P. de Compra` DOUBLE NULL,
	`P. de Venta` DOUBLE NULL,
	`Cantidad Disponible` DECIMAL(32,0) NULL,
	`Can. Minima Aceptada` INT(11) NULL,
	`Ubicacion de Pasillo` ENUM('P-A','P-B','P-C','P-D') NOT NULL COLLATE 'latin1_swedish_ci',
	`Fecha Vence.` DATE NOT NULL,
	`Estado` ENUM('DISPONIBLE','NO DISPONIBLE') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.reporte_producto_mas_vendido
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `reporte_producto_mas_vendido` (
	`Total de Ventas` BIGINT(21) NOT NULL,
	`Empleado` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Precio de compra` DOUBLE NULL,
	`Cantidad en Inv.` DECIMAL(32,0) NULL,
	`Valor en Inv.` DOUBLE NULL,
	`Precio de venta` DOUBLE NOT NULL,
	`Cantidad Vendida` DECIMAL(32,0) NULL,
	`Valor Vendido` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id_rol` int(11) NOT NULL AUTO_INCREMENT,
  `tipo_rol` varchar(30) NOT NULL,
  `descripción_rol` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE KEY `tipo_rol` (`tipo_rol`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.roles: ~2 rows (aproximadamente)
INSERT INTO `roles` (`id_rol`, `tipo_rol`, `descripción_rol`) VALUES
	(1, 'Administrador', NULL),
	(2, 'Empleado', NULL);

-- Volcando estructura para tabla db_sistema_adminpyme_final.tasa_dolar
CREATE TABLE IF NOT EXISTS `tasa_dolar` (
  `id_tasa_dolar` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` datetime DEFAULT NULL,
  `valor` double DEFAULT NULL,
  PRIMARY KEY (`id_tasa_dolar`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.tasa_dolar: ~2 rows (aproximadamente)
INSERT INTO `tasa_dolar` (`id_tasa_dolar`, `fecha`, `valor`) VALUES
	(12, '2025-12-04 00:00:00', 254.8705),
	(13, '2025-12-06 00:00:00', 257.9287);

-- Volcando estructura para tabla db_sistema_adminpyme_final.ubicaciones_fisicas
CREATE TABLE IF NOT EXISTS `ubicaciones_fisicas` (
  `ubicacion_id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo_ubicacion` varchar(50) NOT NULL,
  `almacen_id` int(11) NOT NULL,
  `pasillo` enum('P-A','P-B','P-C','P-D') NOT NULL DEFAULT 'P-A',
  `estante` varchar(10) DEFAULT NULL,
  `tipo_ubicacion` varchar(20) DEFAULT 'RESERVA',
  `estado_ubicacion` varchar(20) DEFAULT 'DISPONIBLE',
  PRIMARY KEY (`ubicacion_id`),
  UNIQUE KEY `codigo_ubicacion` (`codigo_ubicacion`),
  KEY `almacen_id` (`almacen_id`),
  CONSTRAINT `ubicaciones_fisicas_ibfk_1` FOREIGN KEY (`almacen_id`) REFERENCES `almacenes` (`almacen_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.ubicaciones_fisicas: ~8 rows (aproximadamente)
INSERT INTO `ubicaciones_fisicas` (`ubicacion_id`, `codigo_ubicacion`, `almacen_id`, `pasillo`, `estante`, `tipo_ubicacion`, `estado_ubicacion`) VALUES
	(1, '001', 1, 'P-A', '1', 'RESERVA', 'DISPONIBLE'),
	(3, '002', 1, 'P-B', '1', 'RESERVA', 'DISPONIBLE'),
	(4, '003', 1, 'P-C', '1', 'RESERVA', 'DISPONIBLE'),
	(5, '004', 1, 'P-C', '1', 'RESERVA', 'DISPONIBLE'),
	(7, '005', 2, 'P-A', '1', 'VENTA', 'DISPONIBLE'),
	(8, '006', 2, 'P-B', '1', 'VENTA', 'DISPONIBLE'),
	(9, '007', 2, 'P-C', '1', 'VENTA', 'DISPONIBLE'),
	(10, '008', 2, 'P-A', '1', 'VENTA', 'DISPONIBLE');

-- Volcando estructura para tabla db_sistema_adminpyme_final.unidades_medida
CREATE TABLE IF NOT EXISTS `unidades_medida` (
  `unidad_id` int(11) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(10) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `tipo` enum('unidad','peso','volumen','longitud','area','tiempo') DEFAULT NULL,
  PRIMARY KEY (`unidad_id`),
  UNIQUE KEY `codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.unidades_medida: ~5 rows (aproximadamente)
INSERT INTO `unidades_medida` (`unidad_id`, `codigo`, `nombre`, `tipo`) VALUES
	(1, 'KG', 'Kilgoramos', 'peso'),
	(2, 'GR', 'Gramos', 'peso'),
	(3, 'LT', 'Litros', 'volumen'),
	(4, 'Ml', 'Minilitros', 'volumen'),
	(5, 'UN', 'Individual', 'unidad');

-- Volcando estructura para tabla db_sistema_adminpyme_final.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(20) NOT NULL,
  `contraseña_usuario` varchar(20) NOT NULL,
  `correo_electronico` varchar(35) NOT NULL,
  `estado` enum('Activo','Bloqueado','Inactivo') NOT NULL DEFAULT 'Activo',
  `id_persona` int(11) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  UNIQUE KEY `correo_electronica` (`correo_electronico`),
  KEY `puede__ser` (`id_persona`),
  KEY `posee` (`id_rol`),
  CONSTRAINT `posee` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id_rol`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `puede__ser` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.usuario: ~2 rows (aproximadamente)
INSERT INTO `usuario` (`id_usuario`, `nombre_usuario`, `contraseña_usuario`, `correo_electronico`, `estado`, `id_persona`, `id_rol`) VALUES
	(15, 'Admin', 'Admin', 'Admin@gmail.com', 'Activo', 29, 1),
	(16, 'eddy_199', 'eddy_199', 'eddy_19@gmail.com', 'Activo', 30, 1);

-- Volcando estructura para tabla db_sistema_adminpyme_final.usuario_archivo
CREATE TABLE IF NOT EXISTS `usuario_archivo` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(20) NOT NULL,
  `contraseña_usuario` varchar(20) NOT NULL,
  `correo_electronico` varchar(35) NOT NULL,
  `estado` enum('Activo','Bloqueado','Inactivo') NOT NULL,
  `id_persona` int(11) DEFAULT NULL,
  `id_rol` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`),
  UNIQUE KEY `correo_electronica` (`correo_electronico`),
  KEY `puede__ser` (`id_persona`),
  KEY `posee` (`id_rol`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.usuario_archivo: ~0 rows (aproximadamente)

-- Volcando estructura para vista db_sistema_adminpyme_final.usuario_consulta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `usuario_consulta` (
	`nombre_usuario` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`contraseña_usuario` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`correo_electronico` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`tipo_rol` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`estado` ENUM('Activo','Bloqueado','Inactivo') NOT NULL COLLATE 'latin1_swedish_ci',
	`id_usuario` INT(11) NOT NULL,
	`persona` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cedula` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para tabla db_sistema_adminpyme_final.venta
CREATE TABLE IF NOT EXISTS `venta` (
  `id_venta` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) DEFAULT NULL,
  `id_cliente` int(11) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`id_venta`),
  KEY `resive` (`id_usuario`),
  KEY `realiza` (`id_cliente`),
  CONSTRAINT `realiza` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `resive` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Volcando datos para la tabla db_sistema_adminpyme_final.venta: ~0 rows (aproximadamente)
INSERT INTO `venta` (`id_venta`, `id_usuario`, `id_cliente`, `fecha`) VALUES
	(30, 16, 15, '2025-12-06');

-- Volcando estructura para vista db_sistema_adminpyme_final.ventas_totales_por_empleado
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `ventas_totales_por_empleado` (
	`id_venta` INT(11) NULL,
	`Empleado` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Producto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Cantidad Vendida` INT(11) NULL,
	`Monto` DOUBLE NULL,
	`%` DOUBLE NULL,
	`Tipo` ENUM('Contado','Credito') NULL COLLATE 'latin1_swedish_ci',
	`Estado` ENUM('Aprobado','Pendiente','Devolucion') NULL COLLATE 'latin1_swedish_ci',
	`Fecha de Cobro` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci',
	`Fecha de Venta` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.venta_consulta
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `venta_consulta` (
	`id_venta` INT(11) NOT NULL,
	`nombre_usuario` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`nombre` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`apellido` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cedula` INT(11) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_consulta_cliente_ventas
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_consulta_cliente_ventas` (
	`id_venta` INT(11) NOT NULL,
	`Nombre y apellido` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cedula` INT(11) NOT NULL,
	`telefono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Ubicacion` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`fecha` DATE NULL,
	`tipo_venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci',
	`estado_venta` ENUM('Aprobado','Pendiente','Devolucion') NULL COLLATE 'latin1_swedish_ci',
	`Total de venta` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_ganancias_mensuales
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_ganancias_mensuales` (
	`Año` INT(5) NULL,
	`Mes` INT(3) NULL,
	`Ventas Contado` BIGINT(21) NOT NULL,
	`Monto Contado` DOUBLE NULL,
	`Ventas Crédito` BIGINT(21) NOT NULL,
	`Monto Crédito` DOUBLE NULL,
	`Ganancia Neta` DOUBLE NULL,
	`Total Ganancias` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_ganancias_por_categoria
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_ganancias_por_categoria` (
	`Categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`VentasContado` BIGINT(21) NOT NULL,
	`MontoContado` DOUBLE NULL,
	`VentasCredito` BIGINT(21) NOT NULL,
	`MontoCredito` DOUBLE NULL,
	`GananciaNeta` DOUBLE NULL,
	`MargenGanancia` DOUBLE NULL,
	`fecha` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_historial_producto
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_historial_producto` (
	`Datos del producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Almacen` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cantidad_anterior` INT(11) NULL,
	`cantidad_nueva` INT(11) NULL,
	`tipo_movimiento` ENUM('ENTRADA','SALIDA','TRANSFERENCIA','CREACION') NOT NULL COLLATE 'latin1_swedish_ci',
	`Observacion` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci',
	`fecha_movimiento` DATE NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_historial_ventas_cliente
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_historial_ventas_cliente` (
	`id_venta` INT(11) NOT NULL,
	`fecha` DATE NULL,
	`nombre_cliente` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`telefono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`vendedor` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`producto_id` INT(11) NULL,
	`nombre_producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`descripcion_producto` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`cantidad_vendida` INT(11) NOT NULL,
	`precio_unitario` DOUBLE NOT NULL,
	`total_venta` DOUBLE NOT NULL,
	`tipo_venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci',
	`estado_venta` ENUM('Aprobado','Pendiente','Devolucion') NULL COLLATE 'latin1_swedish_ci',
	`fecha_cobro` DATE NULL,
	`tipo_venta_descripcion` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci',
	`estado_venta_descripcion` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_orden_de_compra
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_orden_de_compra` (
	`Codigo de compra` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`nombre` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`direccion` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`telefono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`fecha_orden` DATE NULL,
	`Numero de entregas` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_perdidas_creditos_vencidos
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_perdidas_creditos_vencidos` (
	`ID Venta Crédito` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Descripción` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Cantidad` INT(11) NOT NULL,
	`Monto Base` DOUBLE NOT NULL,
	`Interés` DOUBLE NULL,
	`Total Adeudado` DOUBLE NULL,
	`Fecha Vencimiento` DATE NULL,
	`Dias Vencidos` INT(8) NULL,
	`Cliente` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cédula` INT(11) NOT NULL,
	`Teléfono` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Nivel Riesgo` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_perdidas_devoluciones
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_perdidas_devoluciones` (
	`ID Devolución` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Descripción` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cantidad Devuelta` INT(11) NOT NULL,
	`Monto Perdido` DOUBLE NOT NULL,
	`Interés Perdido` DOUBLE NULL,
	`Total Pérdida` DOUBLE NULL,
	`Fecha Venta` DATE NULL,
	`Fecha Cobro Esperado` DATE NULL,
	`Cliente` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cédula Cliente` INT(11) NOT NULL,
	`Vendedor` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_perdidas_gastos
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_perdidas_gastos` (
	`ID Gasto` INT(11) NOT NULL,
	`Monto Gasto` DOUBLE NOT NULL,
	`Motivo` VARCHAR(1) NULL COLLATE 'latin1_swedish_ci',
	`Categoría Gasto` ENUM('Pago de servicios','Logistica','Otros') NOT NULL COLLATE 'latin1_swedish_ci',
	`Comprobante` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Fecha Gasto` DATE NOT NULL,
	`Método Pago` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Responsable` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Empleado` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_perdidas_mensuales
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_perdidas_mensuales` (
	`Año` INT(5) NULL,
	`Mes` INT(3) NULL,
	`Devoluciones` BIGINT(21) NOT NULL,
	`Monto Devoluciones` DOUBLE NULL,
	`Créditos Vencidos` BIGINT(21) NOT NULL,
	`Monto Créditos Vencidos` DOUBLE NULL,
	`Gastos` BIGINT(21) NOT NULL,
	`Monto Gastos` DOUBLE NULL,
	`Total Pérdidas` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_resumen_ganancias
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_resumen_ganancias` (
	`Tipo Ganancia` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Cantidad Transacciones` BIGINT(21) NOT NULL,
	`Monto Total` DOUBLE NULL,
	`Promedio por Transacción` DOUBLE NULL,
	`Fecha Inicio` DATE NULL,
	`Fecha Fin` DATE NULL,
	`Periodo` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_resumen_general_ganancias
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_resumen_general_ganancias` (
	`Concepto` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Cantidad` BIGINT(21) NOT NULL,
	`Monto Total` DOUBLE NULL,
	`Porcentaje` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_resumen_inventario
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_resumen_inventario` (
	`cantidad_total_productos` DECIMAL(32,0) NULL,
	`valor_total_inventario` DOUBLE NULL,
	`productos_stock_minimo` BIGINT(21) NOT NULL,
	`total_categorias` BIGINT(21) NOT NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_resumen_perdidas
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_resumen_perdidas` (
	`Tipo Pérdida` VARCHAR(1) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Cantidad Casos` BIGINT(21) NOT NULL,
	`Monto Total` DOUBLE NULL,
	`Promedio por Caso` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_ventas_recientes
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_ventas_recientes` (
	`ID Venta` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Categoria` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cliente` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cantidad Vendida` INT(11) NOT NULL,
	`Total Venta` DOUBLE NOT NULL,
	`Fecha Venta` DATE NULL,
	`Vendedor` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Tipo Venta` ENUM('Contado','Credito') NOT NULL COLLATE 'latin1_swedish_ci'
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_ventas_semanales
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_ventas_semanales` (
	`semana` INT(6) NULL,
	`periodo_semanal` VARCHAR(1) NULL COLLATE 'utf8mb4_general_ci',
	`fecha_inicio_semana` DATE NULL,
	`fecha_fin_semana` DATE NULL,
	`cantidad_ventas_credito` BIGINT(21) NOT NULL,
	`monto_total_credito` DOUBLE NULL,
	`monto_total_credito_con_interes` DOUBLE NULL,
	`cantidad_ventas_contado` BIGINT(21) NOT NULL,
	`monto_total_contado` DOUBLE NULL,
	`total_ventas` BIGINT(21) NOT NULL,
	`monto_total_ventas` DOUBLE NULL,
	`monto_total_general` DOUBLE NULL
) ENGINE=MyISAM;

-- Volcando estructura para vista db_sistema_adminpyme_final.vista_ventas_top_productos
-- Creando tabla temporal para superar errores de dependencia de VIEW
CREATE TABLE `vista_ventas_top_productos` (
	`ID Producto` INT(11) NOT NULL,
	`Producto` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Categoría` VARCHAR(1) NOT NULL COLLATE 'latin1_swedish_ci',
	`Cantidad Vendida` DECIMAL(32,0) NULL,
	`Precio Venta` DOUBLE NOT NULL,
	`Total Vendido` DOUBLE NULL,
	`Ganancia Producto` DOUBLE NULL,
	`Margen Ganancia` DOUBLE NULL,
	`fecha` DATE NULL
) ENGINE=MyISAM;

-- Volcando estructura para disparador db_sistema_adminpyme_final.cliente_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `cliente_before_insert` BEFORE INSERT ON `cliente` FOR EACH ROW BEGIN
DECLARE id INT;
SELECT COUNT(*) INTO id FROM cliente WHERE id_persona=NEW.id_persona; 
    -- Si ya hay 2 registros con este id_persona, lanzar error
    IF id >= 1 THEN
      SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'No se puede registra a la misma persona dos veces';
	 END if;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador db_sistema_adminpyme_final.prevent_duplicate_nomina_payment
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER prevent_duplicate_nomina_payment
BEFORE INSERT ON nomina
FOR EACH ROW
BEGIN
    DECLARE existing_count INT;
    
    -- Verificar si ya existe un registro para el mismo usuario en la misma fecha
    SELECT COUNT(*) INTO existing_count
    FROM nomina 
    WHERE id_usuario = NEW.id_usuario 
    AND DATE(fecha_pago) = DATE(NEW.fecha_pago)
    AND id_nomina != NEW.id_nomina; -- Excluir el registro actual en caso de update
    
    -- Si ya existe un registro, generar error
    IF existing_count > 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Ya existe un registro de pago de nómina para este usuario en la fecha especificada';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador db_sistema_adminpyme_final.tasa_dolar_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `tasa_dolar_before_insert` BEFORE INSERT ON `tasa_dolar` FOR EACH ROW BEGIN
DECLARE dolar DOUBLE;
SELECT valor INTO dolar FROM tasa_dolar WHERE valor = NEW.valor LIMIT 1; 
    -- Si el valor ya existe, generar un error
    IF dolar IS NOT NULL THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: El valor del dólar ya existe en la base de datos';
    END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador db_sistema_adminpyme_final.tr_detalle_orden_compra_after_insert_historial
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
DELIMITER //
CREATE TRIGGER `tr_detalle_orden_compra_after_insert_historial` 
AFTER INSERT ON `detalle_orden_de_compra` 
FOR EACH ROW
BEGIN
    DECLARE v_cantidad_total INT;
    DECLARE v_cantidad_anterior INT DEFAULT 0;
    DECLARE v_cantidad_nueva INT;
    DECLARE v_inventario_existente INT;
    DECLARE v_inventario_id INT;
    
    -- Calcular la cantidad total entregada
    SET v_cantidad_total = NEW.cantidad_empaquetada * NEW.cantidad_entregada;
    
    -- Verificar si ya existe inventario para este producto
    SELECT COUNT(*), COALESCE(MAX(id_inventario), 0), COALESCE(MAX(cantidad_dis), 0)
    INTO v_inventario_existente, v_inventario_id, v_cantidad_anterior
    FROM inventario
    WHERE producto_id = NEW.producto_id;
    
    -- Calcular nueva cantidad
    SET v_cantidad_nueva = v_cantidad_anterior + v_cantidad_total;
    
    IF v_inventario_existente > 0 THEN
        -- Actualizar cantidad existente en inventario
        UPDATE inventario 
        SET 
            cantidad_dis = v_cantidad_nueva,
            estado_inventario = CASE 
                WHEN v_cantidad_nueva > 0 THEN 'DISPONIBLE'
                ELSE 'NO DISPONIBLE'
            END
        WHERE id_inventario = v_inventario_id;
    ELSE
        -- Insertar nuevo registro de inventario
        INSERT INTO inventario (
            producto_id,
            cantidad_dis,
            cantidad_min_stock,
            ubicacion_id,
            estado_inventario
        ) VALUES (
            NEW.producto_id,
            v_cantidad_total,
            1, -- Cantidad mínima por defecto
            1, -- Ubicación por defecto
            CASE 
                WHEN v_cantidad_total > 0 THEN 'DISPONIBLE'
                ELSE 'NO DISPONIBLE'
            END
        );
        
        -- Obtener el ID del inventario recién insertado
        SET v_inventario_id = LAST_INSERT_ID();
        SET v_cantidad_nueva = v_cantidad_total;
    END IF;
    
    -- Registrar en el historial de productos
    INSERT INTO historial_producto (
        fecha_movimiento,
        producto_id,
        tipo_movimiento,
        cantidad_anterior,
        cantidad_nueva,
        almacen_id,
        id_orden_de_compra,
        id_inventario
    ) VALUES (
        CURDATE(), -- Fecha actual
        NEW.producto_id,
        'ENTRADA', -- Tipo de movimiento: ENTRADA por compra
        v_cantidad_anterior,
        v_cantidad_nueva,
        NEW.almacen_id, -- Almacén especificado en el detalle
        NEW.id_orden_de_compra, -- Orden de compra relacionada
        v_inventario_id -- ID del inventario actualizado/creado
    );
    
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador db_sistema_adminpyme_final.tr_detalle_venta_after_insert_historial
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
DELIMITER //
CREATE TRIGGER `tr_detalle_venta_after_insert_historial` AFTER INSERT ON `detalle_venta` FOR EACH ROW BEGIN
    DECLARE v_cantidad_anterior INT DEFAULT 0;
    DECLARE v_cantidad_nueva INT;
    DECLARE v_inventario_id INT;
    DECLARE v_ubicacion_id INT;
    DECLARE v_almacen_id INT;
    DECLARE v_usuario_id INT;
    
    -- Obtener datos del inventario actual
    SELECT i.id_inventario, i.cantidad_dis, i.ubicacion_id, a.almacen_id, v.id_usuario
    INTO v_inventario_id, v_cantidad_anterior, v_ubicacion_id, v_almacen_id, v_usuario_id
    FROM inventario i
    INNER JOIN ubicaciones_fisicas uf ON i.ubicacion_id = uf.ubicacion_id
    INNER JOIN almacenes a ON uf.almacen_id = a.almacen_id
    INNER JOIN venta v ON NEW.id_venta = v.id_venta
    WHERE i.producto_id = NEW.producto_id
    LIMIT 1;
    
    -- Verificar si hay suficiente stock
    IF v_cantidad_anterior < NEW.cantidad_vendida THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Error: Stock insuficiente para realizar la venta';
    END IF;
    
    -- Calcular nueva cantidad en inventario
    SET v_cantidad_nueva = v_cantidad_anterior - NEW.cantidad_vendida;
    
    -- Actualizar inventario
    UPDATE inventario 
    SET 
        cantidad_dis = v_cantidad_nueva,
        estado_inventario = CASE 
            WHEN v_cantidad_nueva > 0 THEN 'DISPONIBLE'
            ELSE 'NO DISPONIBLE'
        END
    WHERE id_inventario = v_inventario_id;
    
    -- Registrar en el historial de productos (SALIDA por venta)
    INSERT INTO historial_producto (
        fecha_movimiento,
        producto_id,
        tipo_movimiento,
        cantidad_anterior,
        cantidad_nueva,
        almacen_id,
        id_venta,
        id_inventario
    ) VALUES (
        CURDATE(), -- Fecha actual
        NEW.producto_id,
        'SALIDA', -- Tipo de movimiento: SALIDA por venta
        v_cantidad_anterior,
        v_cantidad_nueva,
        v_almacen_id, -- Almacén de donde se descontó
        NEW.id_venta, -- Venta relacionada
        v_inventario_id -- ID del inventario actualizado
    );
    
    
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Volcando estructura para disparador db_sistema_adminpyme_final.tr_producto_after_insert_historial
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO';
DELIMITER //
CREATE TRIGGER `tr_producto_after_insert_historial` AFTER INSERT ON `producto` FOR EACH ROW BEGIN
    -- Registrar en el historial de productos (CREACION de nuevo producto)
    INSERT INTO historial_producto (
        fecha_movimiento,
        producto_id,
        tipo_movimiento,
        cantidad_anterior,
        cantidad_nueva,
        almacen_id,
        id_inventario
    ) VALUES (
        CURDATE(), -- Fecha de creación
        NEW.producto_id,
        'CREACION', -- Tipo de movimiento: CREACION de producto
        0, -- Cantidad anterior (siempre 0 para creación)
        0, -- Cantidad nueva (se inicializa en 0)
        NULL, -- Almacén por defecto (puedes ajustar según tu lógica)
        NULL -- Aún no hay inventario asociado
    );
    
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `cargar_producto`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `cargar_producto` AS SELECT 
    p.producto_id,
    CONCAT(p.nombre_producto,' ',p.descripcion) AS producto_completo,
    c.tipo_categoria,
    p.precio_compra,
    p.precio_venta 
FROM producto p
INNER JOIN categoria c ON p.id_categoria=c.id_categoria 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `categoria_consultar`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `categoria_consultar` AS SELECT  
    c.id_categoria AS 'Codigo categoria',
    c.tipo_categoria,
    c.descripcion,
    c.estado,
    (SELECT COUNT(p.producto_id) FROM producto p WHERE p.id_categoria=c.id_categoria) AS 'producto'
FROM categoria c
INNER JOIN usuario u ON c.id_usuario=u.id_usuario 
GROUP BY c.id_categoria 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `cliente_consultar`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `cliente_consultar` AS SELECT
    c.id_cliente AS 'Numero de cliente',
    p.nombre,
    p.apellido,
    p.cedula,
    LEFT(telefono, 4) AS prefijo,
    RIGHT(telefono, 7) AS numero_principal,
    CONCAT(pa.nombre_parroquia,' ',m.nombre_municipio) AS ubicacion,
    c.estado
FROM cliente c
INNER JOIN persona p ON c.id_persona=p.id_persona 
INNER JOIN parroquia pa ON c.id_parroquia=pa.id_parroquia
INNER JOIN municipio m ON pa.id_municipio=m.id_municipio 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `cliente_credito`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `cliente_credito` AS SELECT 
    p.nombre,
    p.apellido,
    p.cedula,
    COUNT(v.id_venta) AS ventas_credito,
    (SUM(dv.monto_venta) + SUM(dv.monto_venta*dv.porcentaje/100)) AS 'Total acreditado',
    dv.fecha_cobro
FROM persona p
INNER JOIN cliente c ON p.id_persona=c.id_persona
INNER JOIN venta v ON v.id_cliente=c.id_cliente
INNER JOIN detalle_venta dv ON v.id_venta=dv.id_venta
WHERE dv.tipo_venta='Credito' AND dv.estado_venta='Pendiente'
GROUP BY p.cedula 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `consultar_detalle_orden_de_compra`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `consultar_detalle_orden_de_compra` AS SELECT 
    odc.id_orden_de_compra AS 'Codigo de compra',
    p.producto_id AS 'Codigo de producto',
    p.nombre_producto,
    p.descripcion,
    p.precio_venta,
    e.forma_entrega,
    dodc.cantidad_empaquetada AS 'Cantidad empaquetada',
    dodc.cantidad_entregada AS 'Cantidad entrega',
    p.precio_compra AS 'Precio por unidad',
    (dodc.cantidad_empaquetada * dodc.cantidad_entregada) AS 'Total de unidades',
    ((dodc.cantidad_empaquetada * dodc.cantidad_entregada) * p.precio_compra) AS 'Total de la compra'
FROM detalle_orden_de_compra dodc
INNER JOIN orden_de_compra odc ON dodc.id_orden_de_compra=odc.id_orden_de_compra
INNER JOIN producto p ON dodc.producto_id=p.producto_id
INNER JOIN entrega e ON dodc.id_entrega=e.id_entrega 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `descripicon_pago_consulta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `descripicon_pago_consulta` AS SELECT DISTINCT 
    v.id_venta,
    p.tipo_de_pago,
    dp.ref_pago,
    dp.monto_cancelado AS 'Monto bolivares',
    v.fecha
FROM descripcion_pago dp
INNER JOIN pago p ON dp.id_pago=p.id_pago 
INNER JOIN venta v ON dp.id_venta=v.id_venta 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `descripicon_pago_consulta_2`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `descripicon_pago_consulta_2` AS SELECT DISTINCT 
    v.id_venta,
    dv.tipo_venta,
    p.tipo_de_pago,
    dp.ref_pago,
    dp.monto_cancelado AS 'Monto bolivares',
    v.fecha
FROM descripcion_pago dp
INNER JOIN pago p ON dp.id_pago=p.id_pago 
INNER JOIN venta v ON dp.id_venta=v.id_venta
INNER JOIN detalle_venta dv ON v.id_venta=dv.id_venta 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `detalle_de_compra_cliente`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `detalle_de_compra_cliente` AS SELECT 
    p.nombre,
    p.apellido,
    p.cedula,
    c.telefono,
    pa.nombre_parroquia,
    pr.nombre_producto,
    pr.descripcion,
    pr.precio_venta,
    dv.cantidad_vendida,
    v.fecha
FROM persona p
INNER JOIN cliente c ON p.id_persona=c.id_persona
INNER JOIN parroquia pa ON c.id_parroquia=pa.id_parroquia
INNER JOIN venta v ON v.id_cliente=c.id_cliente
INNER JOIN detalle_venta dv ON dv.id_venta=v.id_venta
INNER JOIN producto pr ON dv.producto_id=pr.producto_id 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `factura_pago`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `factura_pago` AS SELECT
    v.id_venta,
    p.tipo_de_pago,
    dp.ref_pago,
    dp.monto_cancelado
FROM descripcion_pago dp
INNER JOIN pago p ON dp.id_pago=p.id_pago
INNER JOIN venta v ON dp.id_venta=v.id_venta 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `factura_venta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `factura_venta` AS SELECT 
    v.id_venta AS 'Número de Factura',
    DATE(v.fecha) AS 'Fecha',
    TIME(v.fecha) AS 'Hora',
    
    -- Información del cliente
    CONCAT(p.nombre, ' ', p.apellido) AS 'Cliente',
    p.cedula AS 'Cédula',
    c.telefono AS 'Teléfono',
         
    -- Información del vendedor
    u.nombre_usuario AS 'Vendedor',
    
    -- Detalles de productos
    pr.nombre_producto AS 'Producto',
    pr.descripcion AS 'Descripción',
    dv.cantidad_vendida AS 'Cantidad',
    pr.precio_venta AS 'Precio Unitario',
    dv.monto_venta AS 'Subtotal',
    
    -- Información de la venta
    dv.tipo_venta AS 'Tipo de Venta',
    dv.estado_venta AS 'Estado',
    
    -- Cálculo del total
    CASE 
        WHEN dv.porcentaje IS NULL OR dv.porcentaje = 0 THEN dv.monto_venta
        ELSE (dv.monto_venta + (dv.monto_venta * dv.porcentaje / 100))
    END AS 'Total a Pagar',
    dv.fecha_cobro AS 'Fecha_cobro',
    dv.porcentaje AS 'Porcentaje'
   
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
INNER JOIN producto pr ON dv.producto_id = pr.producto_id
INNER JOIN cliente c ON v.id_cliente = c.id_cliente
INNER JOIN persona p ON c.id_persona = p.id_persona
INNER JOIN usuario u ON v.id_usuario = u.id_usuario
GROUP BY v.id_venta, dv.id_detalle_venta
ORDER BY dv.id_detalle_venta 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `gastos_consulta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `gastos_consulta` AS SELECT DISTINCT
    g.id_gastos,
    u.nombre_usuario,
    g.monto_gastos,
    g.motivo_del_gasto,
    g.categoria,
    g.comprobante,
    pa.tipo_de_pago, 
    g.fecha_gasto
FROM gastos g
INNER JOIN usuario u ON g.id_usuario=u.id_usuario 
INNER JOIN pago pa ON g.id_pago=pa.id_pago 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `inventario_productos_`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `inventario_productos_` AS SELECT 
    p.codigo_barras,
    CONCAT(p.nombre_producto,' ',p.descripcion) AS 'Producto',
    c.tipo_categoria,
    p.precio_compra,
    p.precio_venta,
    i.cantidad_dis,
    i.cantidad_min_stock
FROM inventario i
INNER JOIN producto p ON i.producto_id=p.producto_id
INNER JOIN categoria c ON p.id_categoria=c.id_categoria
GROUP BY i.producto_id 
ORDER BY i.id_inventario DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `listado_detalle_de_ventas`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `listado_detalle_de_ventas` AS SELECT 
    v.id_venta AS 'Numero de venta',
    p.producto_id AS 'Codigo de producto',
    CONCAT(p.nombre_producto,' ',p.descripcion) AS 'Producto',
    p.precio_venta,
    dv.cantidad_vendida AS 'Cantidad vendida',
    dv.monto_venta AS 'sub total',
    CONCAT(COALESCE(dv.porcentaje, 0),'%') AS 'Porcentaje',
    CASE 
        WHEN dv.porcentaje IS NULL THEN dv.monto_venta
        ELSE (dv.monto_venta + (dv.monto_venta * dv.porcentaje / 100))
    END AS 'Total de la venta',
    v.fecha AS 'Venta realizada',
    dv.fecha_cobro AS 'Fecha de cobro',
    dv.estado_venta,
    dv.tipo_venta
    
FROM venta v
INNER JOIN detalle_venta dv ON dv.id_venta = v.id_venta
INNER JOIN producto p ON dv.producto_id = p.producto_id
ORDER BY `Numero de venta` DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `listado_de_ventas`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `listado_de_ventas` AS SELECT 
    v.id_venta AS 'Numero de venta',
    CONCAT('N°',c.id_cliente,' ',pa.nombre,' ',pa.apellido,' V-',pa.cedula) AS 'Informacion del cliente',
    u.nombre_usuario AS 'Responsable',
    SUM(dv.cantidad_vendida) AS 'Cantidad de productos vendida',
    SUM(dv.monto_venta) AS 'Total de la venta',
    v.fecha AS 'Venta de la fecha'
FROM venta v
INNER JOIN cliente c ON v.id_cliente=c.id_cliente
INNER JOIN usuario u ON v.id_usuario=u.id_usuario
INNER JOIN persona pa ON pa.id_persona=c.id_persona 
INNER JOIN detalle_venta dv ON dv.id_venta=v.id_venta
GROUP BY v.id_venta HAVING SUM(dv.monto_venta) 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `listado_de_ventas_2`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `listado_de_ventas_2` AS SELECT 
    v.id_venta AS 'Numero de venta',
    p.producto_id AS 'Codigo de producto',
    CONCAT(p.nombre_producto,'',p.descripcion) AS 'Producto',
    p.precio_venta,
    dv.cantidad_vendida AS 'Cantidad vendida',
    dv.monto_venta AS 'sub total',
    CONCAT(COALESCE(dv.porcentaje, 0),'%') AS 'Porcentaje',
    CASE 
        WHEN dv.porcentaje IS NULL THEN dv.monto_venta
        ELSE (dv.monto_venta + (dv.monto_venta * dv.porcentaje / 100))
    END AS 'Total de la venta',
    v.fecha AS 'Venta realizada',
    dv.fecha_cobro AS 'Fecha de cobro',
    dv.estado_venta,
    dv.tipo_venta
    
FROM venta v
INNER JOIN detalle_venta dv ON dv.id_venta = v.id_venta
INNER JOIN producto p ON dv.producto_id = p.producto_id
ORDER BY `Numero de venta` DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `lista_productos_inventario`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `lista_productos_inventario` AS SELECT DISTINCT 
    p.codigo_barras,
    p.nombre_producto,
    p.descripcion,
    p.precio_venta,
    i.cantidad_dis,
	 p.producto_id
FROM producto p
INNER JOIN inventario i ON i.producto_id=p.producto_id 
WHERE p.estado='Activo' 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `nomina_consulta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `nomina_consulta` AS SELECT  
    n.id_nomina,
    CONCAT(p.nombre,' ',p.apellido) AS 'Empleado',
    p.cedula AS 'Cédula',
    n.periodo_pago AS 'Periodo de Pago',
    n.salario_bruto AS 'Salario Bruto',
    pa.tipo_de_pago,
    n.estado_pago,
    n.ref_pago AS 'Ref. del Pago',
    n.fecha_pago AS 'Fecha del Pago'
FROM persona p
JOIN usuario u ON u.id_persona = p.id_persona
JOIN nomina n ON n.id_usuario = u.id_usuario
INNER JOIN pago pa ON n.id_pago=pa.id_pago 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `orden_de_compra_vista`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `orden_de_compra_vista` AS SELECT 
    odc.codigo_orden_compra AS 'Codigo de compra',
    pr.nombre,
    pr.direccion,
    pr.telefono,
    odc.fecha_orden AS 'Fecha de solicitud'
FROM orden_de_compra odc
INNER JOIN provedor pr ON odc.id_provedor=pr.id_provedor
GROUP BY odc.id_orden_de_compra 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `pago_de_nomina`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `pago_de_nomina` AS SELECT  
    n.id_nomina,
    CONCAT(p.nombre,' ',p.apellido) AS 'Empleado',
    r.tipo_rol,
    n.periodo_pago AS 'Periodo de Pago',
    n.salario_bruto AS 'Salario Bruto',
    (n.salario_bruto / CASE
        WHEN n.periodo_pago = 'Quincenal' THEN 15
        WHEN n.periodo_pago = 'Mensual' THEN 30
        WHEN n.periodo_pago = 'Diario' THEN 1
        WHEN n.periodo_pago= 'Semanal' THEN 7
        ELSE NULL
    END) AS 'Salario Diario',
    pa.tipo_de_pago,
    n.estado_pago,
    n.ref_pago AS 'Ref. del Pago',
    n.fecha_pago AS 'Fecha del Pago'
FROM persona p
JOIN usuario u ON u.id_persona = p.id_persona
JOIN nomina n ON n.id_usuario = u.id_usuario
INNER JOIN roles r ON u.id_rol=r.id_rol 
INNER JOIN pago pa ON n.id_pago=pa.id_pago 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `productos_stock_min`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `productos_stock_min` AS SELECT
    p.producto_id AS 'codigo de producto',
    p.nombre_producto,
    p.descripcion,
    c.tipo_categoria AS 'categoria',
    p.precio_venta,
    i.cantidad_dis AS 'Disponibilidad actual',
    i.cantidad_min_stock AS 'Disponibilidad minima'
FROM inventario i
INNER JOIN producto p ON i.producto_id =p.producto_id
INNER JOIN categoria c ON p.id_categoria=c.id_categoria 
GROUP BY p.producto_id HAVING i.cantidad_dis <= i.cantidad_min_stock 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `producto_buscar`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `producto_buscar` AS SELECT DISTINCT 
    p.producto_id,
    p.nombre_producto,
    p.descripcion,
    c.tipo_categoria,
    p.precio_compra
FROM producto p 
INNER JOIN categoria c ON p.id_categoria=c.id_categoria 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `producto_consulta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `producto_consulta` AS SELECT DISTINCT 
    p.producto_id,
    c.tipo_categoria,
    p.nombre_producto,
    p.descripcion,
    p.precio_venta,
    p.precio_compra
FROM producto p
INNER JOIN categoria c ON p.id_categoria=c.id_categoria 
ORDER BY p.producto_id ASC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `producto_consulta_inactivo`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `producto_consulta_inactivo` AS SELECT DISTINCT 
    p.producto_id,
    c.tipo_categoria,
    p.nombre_producto,
    p.descripcion,
    p.precio_venta,
    p.precio_compra
FROM producto_inactivos p
INNER JOIN categoria c ON p.id_categoria=c.id_categoria 
ORDER BY p.producto_id ASC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `reporte_de_compra`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `reporte_de_compra` AS SELECT 
    odc.codigo_orden_compra AS 'Codigo de compra',
    pr.nombre AS 'Proveedor',
    p.producto_id AS 'Codigo de producto',
    CONCAT(p.nombre_producto,' ',p.descripcion) AS 'Producto',
    p.precio_compra AS 'Precio unitario',
    CONCAT(dodc.cantidad_entregada,' ',e.forma_entrega,' ','c/u de: ',dodc.cantidad_empaquetada,' unidades') AS 'Informacion de entrega',
    (dodc.cantidad_empaquetada * dodc.cantidad_entregada) AS 'Total de unidades',
    (dodc.cantidad_empaquetada * dodc.cantidad_entregada)* p.precio_compra AS 'Total de la compra',
    odc.fecha_orden AS 'Fecha de compra'
FROM detalle_orden_de_compra dodc
INNER JOIN orden_de_compra odc ON dodc.id_orden_de_compra=odc.id_orden_de_compra
INNER JOIN producto p ON dodc.producto_id=p.producto_id
INNER JOIN provedor pr ON odc.id_provedor=pr.id_provedor
INNER JOIN entrega e ON dodc.id_entrega=e.id_entrega
ORDER BY dodc.id_orden_de_compra 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `reporte_disponibilidad_inventario`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `reporte_disponibilidad_inventario` AS SELECT
p.codigo_barras AS 'Codigo de Barra',
CONCAT(p.nombre_producto,' - ',p.capacidad_unidad,' ',p.unidad_medida) AS 'Producto',
SUM(p.precio_compra) AS 'P. de Compra',
SUM(p.precio_venta) AS 'P. de Venta',
SUM(i.cantidad_dis) AS 'Cantidad Disponible',
i.cantidad_min_stock AS 'Can. Minima Aceptada',
uf.pasillo AS 'Ubicacion de Pasillo',
p.fecha_vencimiento AS 'Fecha Vence.',
i.estado_inventario AS 'Estado'
FROM inventario i
JOIN ubicaciones_fisicas uf ON uf.ubicacion_id=i.ubicacion_id
JOIN producto p ON p.producto_id=i.producto_id
WHERE i.estado_inventario = 'DISPONIBLE'
GROUP BY p.codigo_barras 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `reporte_producto_mas_vendido`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `reporte_producto_mas_vendido` AS SELECT
COUNT(dv.id_venta) AS 'Total de Ventas',
u.nombre_usuario AS 'Empleado',
CONCAT(pr.nombre_producto,' - ',pr.capacidad_unidad,' ',pr.unidad_medida) AS 'Producto',
pr.precio_compra AS 'Precio de compra',
SUM(i.cantidad_dis) AS 'Cantidad en Inv.',
SUM(pr.precio_compra * i.cantidad_dis) AS 'Valor en Inv.',
pr.precio_venta AS 'Precio de venta',
SUM(dv.cantidad_vendida) AS 'Cantidad Vendida',
SUM((pr.precio_venta) * dv.cantidad_vendida) AS 'Valor Vendido'
FROM detalle_venta dv
JOIN producto pr ON pr.producto_id=dv.producto_id
JOIN inventario i ON i.producto_id=pr.producto_id
JOIN venta v ON v.id_venta=dv.id_venta
JOIN usuario u ON u.id_usuario=v.id_usuario
JOIN cliente c ON c.id_cliente=v.id_cliente
JOIN persona p ON p.id_persona=c.id_persona
GROUP BY pr.codigo_barras
ORDER BY COUNT(dv.id_venta) DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `usuario_consulta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `usuario_consulta` AS SELECT DISTINCT 
    u.nombre_usuario,
    u.`contraseña_usuario`,
    u.correo_electronico,
    r.tipo_rol,
    u.estado,
    u.id_usuario,
    CONCAT(p.nombre,' ',p.apellido) AS 'persona',
    p.cedula
FROM usuario u
INNER JOIN persona p ON u.id_persona=p.id_persona
INNER JOIN roles r ON u.id_rol=r.id_rol 
WHERE u.nombre_usuario!='Admin' 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `ventas_totales_por_empleado`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `ventas_totales_por_empleado` AS SELECT
dv.id_venta,
u.nombre_usuario AS 'Empleado',
CONCAT(pr.nombre_producto,' - ',pr.capacidad_unidad,' ',pr.unidad_medida) AS 'Producto',
dv.cantidad_vendida AS 'Cantidad Vendida',
SUM(
    CASE
        WHEN dv.porcentaje IS NULL THEN (pr.precio_venta * dv.cantidad_vendida)
        ELSE ((pr.precio_venta * dv.cantidad_vendida) * ((dv.porcentaje)/100))
    END
) AS 'Monto',
CASE
    WHEN dv.porcentaje IS NULL THEN 0
    ELSE dv.porcentaje
END AS '%',
dv.tipo_venta AS 'Tipo',
dv.estado_venta AS 'Estado',
CASE
    WHEN dv.fecha_cobro IS NULL THEN 'Ninguna'
    ELSE dv.fecha_cobro
END AS 'Fecha de Cobro',
v.fecha AS 'Fecha de Venta'
FROM detalle_venta dv
JOIN producto pr ON pr.producto_id=dv.producto_id
JOIN inventario i ON i.producto_id=pr.producto_id
JOIN venta v ON v.id_venta=dv.id_venta
JOIN usuario u ON u.id_usuario=v.id_usuario
JOIN cliente c ON c.id_cliente=v.id_cliente
JOIN persona p ON p.id_persona=c.id_persona
ORDER BY v.fecha DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `venta_consulta`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `venta_consulta` AS SELECT DISTINCT 
    v.id_venta,
    u.nombre_usuario,
    p.nombre,
    p.apellido,
    p.cedula
FROM venta v
INNER JOIN usuario u ON v.id_usuario=u.id_usuario 
INNER JOIN cliente c ON v.id_cliente=c.id_cliente
INNER JOIN persona p ON c.id_persona=p.id_persona 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_consulta_cliente_ventas`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_consulta_cliente_ventas` AS SELECT 
    v.id_venta,
    CONCAT(p.nombre, ' ', p.apellido) AS 'Nombre y apellido',
    p.cedula,
    c.telefono,
    CONCAT(par.nombre_parroquia, ', ', m.nombre_municipio) AS Ubicacion,
    v.fecha,
    dv.tipo_venta,
    dv.estado_venta,
    SUM(dv.monto_venta) AS 'Total de venta'
FROM venta v
INNER JOIN cliente c ON v.id_cliente = c.id_cliente
INNER JOIN persona p ON c.id_persona = p.id_persona
INNER JOIN parroquia par ON c.id_parroquia = par.id_parroquia
INNER JOIN municipio m ON par.id_municipio = m.id_municipio
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
GROUP BY v.id_venta 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_ganancias_mensuales`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_ganancias_mensuales` AS SELECT 
    YEAR(v.fecha) AS Año,
    MONTH(v.fecha) AS Mes,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Contado' THEN v.id_venta END) AS 'Ventas Contado',
    SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Contado',
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Credito' THEN v.id_venta END) AS 'Ventas Crédito',
    SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END) AS 'Monto Crédito',
    (SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) - 
     COALESCE(SUM(CASE WHEN g.categoria = 'Pago de servicios' THEN g.monto_gastos ELSE 0 END), 0)) AS 'Ganancia Neta',
    (SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) + 
     SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END)) AS 'Total Ganancias'
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
LEFT JOIN gastos g ON YEAR(g.fecha_gasto) = YEAR(v.fecha) AND MONTH(g.fecha_gasto) = MONTH(v.fecha)
WHERE dv.estado_venta = 'Aprobado'
GROUP BY YEAR(v.fecha), MONTH(v.fecha)
ORDER BY Año DESC, Mes DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_ganancias_por_categoria`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_ganancias_por_categoria` AS SELECT 
    c.tipo_categoria AS Categoria,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Contado' THEN v.id_venta END) AS VentasContado,
    SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) AS MontoContado,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Credito' THEN v.id_venta END) AS VentasCredito,
    SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END) AS MontoCredito,
    (SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) - 
     COALESCE(SUM(CASE WHEN p.precio_compra IS NOT NULL THEN (p.precio_compra * dv.cantidad_vendida) ELSE 0 END), 0)) AS GananciaNeta,
    CASE 
        WHEN COALESCE(SUM(p.precio_compra * dv.cantidad_vendida), 0) > 0 THEN
            ((SUM(dv.monto_venta) - COALESCE(SUM(p.precio_compra * dv.cantidad_vendida), 0)) / COALESCE(SUM(p.precio_compra * dv.cantidad_vendida), 1)) * 100
        ELSE 0 
    END AS MargenGanancia,
    MAX(v.fecha) AS fecha
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
INNER JOIN producto p ON dv.producto_id = p.producto_id
INNER JOIN categoria c ON p.id_categoria = c.id_categoria
WHERE dv.estado_venta = 'Aprobado'
GROUP BY c.id_categoria, c.tipo_categoria
ORDER BY GananciaNeta DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_historial_producto`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_historial_producto` AS SELECT 
    CONCAT(p.codigo_barras,' ',p.nombre_producto) AS 'Datos del producto',
    al.nombre AS 'Almacen',
    hp.cantidad_anterior,
    hp.cantidad_nueva,
    hp.tipo_movimiento,
    CASE 
        WHEN hp.tipo_movimiento = 'ENTRADA' AND hp.id_orden_de_compra IS NOT NULL 
            THEN CONCAT('Compra N° ', hp.id_orden_de_compra)
        WHEN hp.tipo_movimiento = 'SALIDA' AND hp.id_venta IS NOT NULL 
            THEN CONCAT('Venta N° ', hp.id_venta)
	WHEN hp.tipo_movimiento = 'CREACION'
	         THEN 'Creacion de un nuevo producto'
	 END AS 'Observacion',
    hp.fecha_movimiento
FROM historial_producto hp
INNER JOIN producto p ON hp.producto_id = p.producto_id
INNER JOIN almacenes al ON hp.almacen_id = al.almacen_id
ORDER BY hp.fecha_movimiento DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_historial_ventas_cliente`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_historial_ventas_cliente` AS SELECT 
    v.id_venta,
    v.fecha,
    CONCAT(p.nombre, ' ', p.apellido) AS nombre_cliente,
    c.telefono,
    u.nombre_usuario AS vendedor,
    dv.producto_id,
    pr.nombre_producto,
    pr.descripcion AS descripcion_producto,
    cat.tipo_categoria AS categoria,
    dv.cantidad_vendida,
    pr.precio_venta AS precio_unitario,
    dv.monto_venta AS total_venta,
    dv.tipo_venta,
    dv.estado_venta,
    dv.fecha_cobro,
    CASE 
        WHEN dv.tipo_venta = 'Contado' THEN 'Pago Inmediato'
        WHEN dv.tipo_venta = 'Credito' THEN 'Pago Diferido'
    END AS tipo_venta_descripcion,
    CASE 
        WHEN dv.estado_venta = 'Aprobado' THEN 'Completada'
        WHEN dv.estado_venta = 'Pendiente' THEN 'Pendiente de Pago'
        WHEN dv.estado_venta = 'Devolucion' THEN 'Producto Devuelto'
    END AS estado_venta_descripcion
FROM venta v
INNER JOIN cliente c ON v.id_cliente = c.id_cliente
INNER JOIN persona p ON c.id_persona = p.id_persona
INNER JOIN usuario u ON v.id_usuario = u.id_usuario
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
INNER JOIN producto pr ON dv.producto_id = pr.producto_id
INNER JOIN categoria cat ON pr.id_categoria = cat.id_categoria
ORDER BY v.fecha DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_orden_de_compra`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_orden_de_compra` AS SELECT 
    odc.codigo_orden_compra AS 'Codigo de compra',
    pr.nombre,
    pr.direccion,
    pr.telefono,
   odc.fecha_orden,
    COUNT(dodc.id_detalle_orden_de_compra) AS 'Numero de entregas'
FROM orden_de_compra odc
INNER JOIN provedor pr ON odc.id_provedor=pr.id_provedor
LEFT JOIN detalle_orden_de_compra dodc ON odc.id_orden_de_compra=dodc.id_orden_de_compra
GROUP BY odc.id_orden_de_compra 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_perdidas_creditos_vencidos`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_perdidas_creditos_vencidos` AS SELECT 
    dv.id_detalle_venta AS 'ID Venta Crédito',
    p.nombre_producto AS Producto,
    p.descripcion AS 'Descripción',
    dv.cantidad_vendida AS Cantidad,
    dv.monto_venta AS 'Monto Base',
    (dv.monto_venta * COALESCE(dv.porcentaje, 0) / 100) AS 'Interés',
    (dv.monto_venta + (dv.monto_venta * COALESCE(dv.porcentaje, 0) / 100)) AS 'Total Adeudado',
    dv.fecha_cobro AS 'Fecha Vencimiento',
    DATEDIFF(CURDATE(), dv.fecha_cobro) AS 'Dias Vencidos',
    CONCAT(per.nombre, ' ', per.apellido) AS Cliente,
    per.cedula AS 'Cédula',
    c.telefono AS 'Teléfono',
    CASE 
        WHEN DATEDIFF(CURDATE(), dv.fecha_cobro) <= 7 THEN 'Bajo'
        WHEN DATEDIFF(CURDATE(), dv.fecha_cobro) <= 30 THEN 'Medio'
        ELSE 'Alto'
    END AS 'Nivel Riesgo'
FROM detalle_venta dv
INNER JOIN producto p ON dv.producto_id = p.producto_id
INNER JOIN venta v ON dv.id_venta = v.id_venta
INNER JOIN cliente c ON v.id_cliente = c.id_cliente
INNER JOIN persona per ON c.id_persona = per.id_persona
WHERE dv.tipo_venta = 'Credito' 
    AND dv.estado_venta = 'Pendiente'
    AND dv.fecha_cobro < CURDATE()
ORDER BY 'Dias Vencidos' DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_perdidas_devoluciones`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_perdidas_devoluciones` AS SELECT 
    dv.id_detalle_venta AS 'ID Devolución',
    p.nombre_producto AS Producto,
    p.descripcion AS 'Descripción',
    c.tipo_categoria AS Categoria,
    dv.cantidad_vendida AS 'Cantidad Devuelta',
    dv.monto_venta AS 'Monto Perdido',
    (dv.monto_venta * COALESCE(dv.porcentaje, 0) / 100) AS 'Interés Perdido',
    (dv.monto_venta + (dv.monto_venta * COALESCE(dv.porcentaje, 0) / 100)) AS 'Total Pérdida',
    v.fecha AS 'Fecha Venta',
    dv.fecha_cobro AS 'Fecha Cobro Esperado',
    CONCAT(per.nombre, ' ', per.apellido) AS Cliente,
    per.cedula AS 'Cédula Cliente',
    u.nombre_usuario AS Vendedor
FROM detalle_venta dv
INNER JOIN producto p ON dv.producto_id = p.producto_id
INNER JOIN categoria c ON p.id_categoria = c.id_categoria
INNER JOIN venta v ON dv.id_venta = v.id_venta
INNER JOIN cliente cl ON v.id_cliente = cl.id_cliente
INNER JOIN persona per ON cl.id_persona = per.id_persona
INNER JOIN usuario u ON v.id_usuario = u.id_usuario
WHERE dv.estado_venta = 'Devolucion'
ORDER BY v.fecha DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_perdidas_gastos`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_perdidas_gastos` AS SELECT 
    g.id_gastos AS 'ID Gasto',
    g.monto_gastos AS 'Monto Gasto',
    g.motivo_del_gasto AS Motivo,
    g.categoria AS 'Categoría Gasto',
    g.comprobante AS Comprobante,
    g.fecha_gasto AS 'Fecha Gasto',
    pa.tipo_de_pago AS 'Método Pago',
    u.nombre_usuario AS Responsable,
    CONCAT(p.nombre, ' ', p.apellido) AS Empleado
FROM gastos g
INNER JOIN usuario u ON g.id_usuario = u.id_usuario
INNER JOIN persona p ON u.id_persona = p.id_persona
INNER JOIN pago pa ON g.id_pago = pa.id_pago
ORDER BY g.fecha_gasto DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_perdidas_mensuales`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_perdidas_mensuales` AS SELECT 
    YEAR(COALESCE(dv.fecha_cobro, g.fecha_gasto, v.fecha)) AS Año,
    MONTH(COALESCE(dv.fecha_cobro, g.fecha_gasto, v.fecha)) AS Mes,
    COUNT(DISTINCT CASE WHEN dv.estado_venta = 'Devolucion' THEN dv.id_detalle_venta END) AS Devoluciones,
    SUM(CASE WHEN dv.estado_venta = 'Devolucion' THEN dv.monto_venta ELSE 0 END) AS 'Monto Devoluciones',
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Pendiente' AND dv.fecha_cobro < CURDATE() THEN dv.id_detalle_venta END) AS 'Créditos Vencidos',
    SUM(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Pendiente' AND dv.fecha_cobro < CURDATE() THEN dv.monto_venta ELSE 0 END) AS 'Monto Créditos Vencidos',
    COUNT(DISTINCT g.id_gastos) AS Gastos,
    SUM(COALESCE(g.monto_gastos, 0)) AS 'Monto Gastos',
    (SUM(CASE WHEN dv.estado_venta = 'Devolucion' THEN dv.monto_venta ELSE 0 END) +
     SUM(CASE WHEN dv.tipo_venta = 'Credito' AND dv.estado_venta = 'Pendiente' AND dv.fecha_cobro < CURDATE() THEN dv.monto_venta ELSE 0 END) +
     SUM(COALESCE(g.monto_gastos, 0))) AS 'Total Pérdidas'
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
LEFT JOIN gastos g ON YEAR(g.fecha_gasto) = YEAR(v.fecha) AND MONTH(g.fecha_gasto) = MONTH(v.fecha)
GROUP BY YEAR(COALESCE(dv.fecha_cobro, g.fecha_gasto, v.fecha)), MONTH(COALESCE(dv.fecha_cobro, g.fecha_gasto, v.fecha))
ORDER BY Año DESC, Mes DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_resumen_ganancias`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_resumen_ganancias` AS SELECT 
    'Ventas Contado' AS 'Tipo Ganancia',
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Contado' THEN v.id_venta END) AS 'Cantidad Transacciones',
    SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Total',
    AVG(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE NULL END) AS 'Promedio por Transacción',
    MIN(v.fecha) AS 'Fecha Inicio',
    MAX(v.fecha) AS 'Fecha Fin',
    CONCAT(DATEDIFF(MAX(v.fecha), MIN(v.fecha)), ' días') AS Periodo
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado' AND dv.tipo_venta = 'Contado'

UNION ALL

SELECT 
    'Ventas Crédito' AS 'Tipo Ganancia',
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Credito' THEN v.id_venta END) AS 'Cantidad Transacciones',
    SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END) AS 'Monto Total',
    AVG(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE NULL END) AS 'Promedio por Transacción',
    MIN(v.fecha) AS 'Fecha Inicio',
    MAX(v.fecha) AS 'Fecha Fin',
    CONCAT(DATEDIFF(MAX(v.fecha), MIN(v.fecha)), ' días') AS Periodo
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado' AND dv.tipo_venta = 'Credito'

UNION ALL

SELECT 
    'Total Ventas' AS 'Tipo Ganancia',
    COUNT(DISTINCT v.id_venta) AS 'Cantidad Transacciones',
    SUM(dv.monto_venta) AS 'Monto Total',
    AVG(dv.monto_venta) AS 'Promedio por Transacción',
    MIN(v.fecha) AS 'Fecha Inicio',
    MAX(v.fecha) AS 'Fecha Fin',
    CONCAT(DATEDIFF(MAX(v.fecha), MIN(v.fecha)), ' días') AS Periodo
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado' 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_resumen_general_ganancias`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_resumen_general_ganancias` AS SELECT 
    'Ventas Totales' AS Concepto,
    COUNT(DISTINCT v.id_venta) AS Cantidad,
    SUM(dv.monto_venta) AS 'Monto Total',
    (SUM(dv.monto_venta) / SUM(SUM(dv.monto_venta)) OVER()) * 100 AS Porcentaje
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado'

UNION ALL

SELECT 
    'Ventas Contado' AS Concepto,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Contado' THEN v.id_venta END) AS Cantidad,
    SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) AS 'Monto Total',
    (SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) / SUM(SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END)) OVER()) * 100 AS Porcentaje
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado'

UNION ALL

SELECT 
    'Ventas Crédito' AS Concepto,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Credito' THEN v.id_venta END) AS Cantidad,
    SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END) AS 'Monto Total',
    (SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END) / SUM(SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END)) OVER()) * 100 AS Porcentaje
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado' 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_resumen_inventario`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_resumen_inventario` AS SELECT 
    SUM(i.cantidad_dis) AS cantidad_total_productos,
    SUM(p.precio_venta * i.cantidad_dis) AS valor_total_inventario,
    COUNT(CASE WHEN i.cantidad_dis <= i.cantidad_min_stock THEN 1 END) AS productos_stock_minimo,
    COUNT(DISTINCT p.id_categoria) AS total_categorias
FROM inventario i
INNER JOIN producto p ON i.producto_id = p.producto_id 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_resumen_perdidas`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_resumen_perdidas` AS SELECT 
    'Devoluciones' AS 'Tipo Pérdida',
    COUNT(DISTINCT dv.id_detalle_venta) AS 'Cantidad Casos',
    SUM(dv.monto_venta) AS 'Monto Total',
    AVG(dv.monto_venta) AS 'Promedio por Caso'
FROM detalle_venta dv
WHERE dv.estado_venta = 'Devolucion'

UNION ALL

SELECT 
    'Créditos Vencidos' AS 'Tipo Pérdida',
    COUNT(DISTINCT dv.id_detalle_venta) AS 'Cantidad Casos',
    SUM(dv.monto_venta) AS 'Monto Total',
    AVG(dv.monto_venta) AS 'Promedio por Caso'
FROM detalle_venta dv
WHERE dv.tipo_venta = 'Credito' 
    AND dv.estado_venta = 'Pendiente'
    AND dv.fecha_cobro < CURDATE()

UNION ALL

SELECT 
    'Gastos Operativos' AS 'Tipo Pérdida',
    COUNT(DISTINCT g.id_gastos) AS 'Cantidad Casos',
    SUM(g.monto_gastos) AS 'Monto Total',
    AVG(g.monto_gastos) AS 'Promedio por Caso'
FROM gastos g 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_ventas_recientes`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_ventas_recientes` AS SELECT 
    v.id_venta AS 'ID Venta',
    p.nombre_producto AS Producto,
    c.tipo_categoria AS Categoria,
    CONCAT(per.nombre, ' ', per.apellido) AS Cliente,
    dv.cantidad_vendida AS 'Cantidad Vendida',
    dv.monto_venta AS 'Total Venta',
    v.fecha AS 'Fecha Venta',
    u.nombre_usuario AS Vendedor,
    dv.tipo_venta AS 'Tipo Venta'
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
INNER JOIN producto p ON dv.producto_id = p.producto_id
INNER JOIN categoria c ON p.id_categoria = c.id_categoria
INNER JOIN cliente cl ON v.id_cliente = cl.id_cliente
INNER JOIN persona per ON cl.id_persona = per.id_persona
INNER JOIN usuario u ON v.id_usuario = u.id_usuario
WHERE dv.estado_venta = 'Aprobado'
ORDER BY v.fecha DESC
LIMIT 50 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_ventas_semanales`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_ventas_semanales` AS SELECT 
    YEARWEEK(v.fecha) AS semana,
    CONCAT('Semana ', WEEK(v.fecha), ' del ', YEAR(v.fecha)) AS periodo_semanal,
    MIN(v.fecha) AS fecha_inicio_semana,
    MAX(v.fecha) AS fecha_fin_semana,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Credito' THEN v.id_venta END) AS cantidad_ventas_credito,
    SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN dv.monto_venta ELSE 0 END) AS monto_total_credito,
    SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN (dv.monto_venta + (dv.monto_venta * COALESCE(dv.porcentaje, 0) / 100)) ELSE 0 END) AS monto_total_credito_con_interes,
    COUNT(DISTINCT CASE WHEN dv.tipo_venta = 'Contado' THEN v.id_venta END) AS cantidad_ventas_contado,
    SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) AS monto_total_contado,
    COUNT(DISTINCT v.id_venta) AS total_ventas,
    SUM(dv.monto_venta) AS monto_total_ventas,
    (SUM(CASE WHEN dv.tipo_venta = 'Contado' THEN dv.monto_venta ELSE 0 END) + 
     SUM(CASE WHEN dv.tipo_venta = 'Credito' THEN (dv.monto_venta + (dv.monto_venta * COALESCE(dv.porcentaje, 0) / 100)) ELSE 0 END)) AS monto_total_general
FROM venta v
INNER JOIN detalle_venta dv ON v.id_venta = dv.id_venta
WHERE dv.estado_venta = 'Aprobado'
GROUP BY YEARWEEK(v.fecha)
ORDER BY semana DESC 
;

-- Eliminando tabla temporal y crear estructura final de VIEW
DROP TABLE IF EXISTS `vista_ventas_top_productos`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `vista_ventas_top_productos` AS SELECT 
    p.producto_id AS 'ID Producto',
    p.nombre_producto AS Producto,
    c.tipo_categoria AS 'Categoría',
    SUM(dv.cantidad_vendida) AS 'Cantidad Vendida',
    p.precio_venta AS 'Precio Venta',
    SUM(dv.monto_venta) AS 'Total Vendido',
    SUM(dv.monto_venta - (p.precio_compra * dv.cantidad_vendida)) AS 'Ganancia Producto',
    CASE 
        WHEN SUM(p.precio_compra * dv.cantidad_vendida) > 0 THEN
            ((SUM(dv.monto_venta) - SUM(p.precio_compra * dv.cantidad_vendida)) / SUM(p.precio_compra * dv.cantidad_vendida)) * 100
        ELSE 0 
    END AS 'Margen Ganancia',
    MAX(v.fecha) AS fecha
FROM producto p
INNER JOIN categoria c ON p.id_categoria = c.id_categoria
INNER JOIN detalle_venta dv ON p.producto_id = dv.producto_id
INNER JOIN venta v ON dv.id_venta = v.id_venta
WHERE dv.estado_venta = 'Aprobado'
GROUP BY p.producto_id, p.nombre_producto, c.tipo_categoria, p.precio_venta
ORDER BY 'Cantidad Vendida' DESC
LIMIT 20 
;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
