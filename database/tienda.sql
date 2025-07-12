-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 12-07-2025 a las 20:40:42
-- Versión del servidor: 8.2.0
-- Versión de PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `abonos`
--

DROP TABLE IF EXISTS `abonos`;
CREATE TABLE IF NOT EXISTS `abonos` (
  `id_abono` int NOT NULL AUTO_INCREMENT,
  `id_detalleDeuda` int NOT NULL,
  `fecha_abono` datetime NOT NULL,
  `monto_abono` double(10,2) NOT NULL,
  PRIMARY KEY (`id_abono`),
  KEY `fk_id_detalleDeuda` (`id_detalleDeuda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `id_categoria` int NOT NULL AUTO_INCREMENT,
  `nombre_categoria` varchar(70) NOT NULL,
  PRIMARY KEY (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id_categoria`, `nombre_categoria`) VALUES
(1, 'Bebidas'),
(2, 'Abarrotes'),
(3, 'Limpieza'),
(4, 'Cuidado personal'),
(5, 'farmacia'),
(6, 'Papeleria'),
(7, 'Alcohol'),
(8, 'Frutas y verduras'),
(9, 'Hogar'),
(10, 'Cremeria');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

DROP TABLE IF EXISTS `clientes`;
CREATE TABLE IF NOT EXISTS `clientes` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nombre_cliente` varchar(50) NOT NULL,
  `apellidos_cliente` varchar(50) NOT NULL,
  `telefono_cliente` varchar(20) NOT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id_cliente`, `nombre_cliente`, `apellidos_cliente`, `telefono_cliente`) VALUES
(1, 'Dani', 'Gallardoo', '21421'),
(3, 'Mary', 'Guerr', '12314'),
(6, 'rodrigo', 'ibarra', '7711226273'),
(9, 'Liz', 'San Agustin', '771234142'),
(10, 'angelica', 'guerrero', '771239184'),
(11, 'Maria', 'Olvera', '12314'),
(12, 'victor', 'islas', '1234567891');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `corte`
--

DROP TABLE IF EXISTS `corte`;
CREATE TABLE IF NOT EXISTS `corte` (
  `id_corte` int NOT NULL AUTO_INCREMENT,
  `fecha_corte` date NOT NULL,
  `fondo_caja` double(10,2) NOT NULL,
  `ventas_efectivo` double(10,2) NOT NULL,
  `ventas_fiadas` double(10,2) NOT NULL,
  `abonos_efectivo` double(10,2) NOT NULL,
  `entradas_efectivo` double(10,2) NOT NULL,
  `salidas_efectivo` double(10,2) NOT NULL,
  PRIMARY KEY (`id_corte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_deudas`
--

DROP TABLE IF EXISTS `detalle_deudas`;
CREATE TABLE IF NOT EXISTS `detalle_deudas` (
  `id_detalleDeuda` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `total_deuda` double(10,2) NOT NULL,
  `total_abonado` double(10,2) NOT NULL,
  `deuda_pendiente` double(10,2) NOT NULL,
  PRIMARY KEY (`id_detalleDeuda`),
  KEY `fk_id_cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `detalle_deudas`
--

INSERT INTO `detalle_deudas` (`id_detalleDeuda`, `id_cliente`, `total_deuda`, `total_abonado`, `deuda_pendiente`) VALUES
(1, 6, 135.00, 0.00, 135.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ventas`
--

DROP TABLE IF EXISTS `detalle_ventas`;
CREATE TABLE IF NOT EXISTS `detalle_ventas` (
  `id_detalleVenta` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `fecha_venta` datetime NOT NULL,
  `importe_total` int NOT NULL,
  `tipo_venta` varchar(50) NOT NULL,
  PRIMARY KEY (`id_detalleVenta`),
  KEY `fk_id_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `detalle_ventas`
--

INSERT INTO `detalle_ventas` (`id_detalleVenta`, `id_usuario`, `fecha_venta`, `importe_total`, `tipo_venta`) VALUES
(2, 7, '2025-05-09 19:26:34', 440, 'Contado'),
(4, 5, '2025-05-10 20:11:09', 45, 'Fiado'),
(5, 5, '2025-07-12 14:26:27', 90, 'Fiado'),
(6, 5, '2025-07-12 14:27:10', 30, 'Contado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `deudas`
--

DROP TABLE IF EXISTS `deudas`;
CREATE TABLE IF NOT EXISTS `deudas` (
  `id_deuda` int NOT NULL AUTO_INCREMENT,
  `id_cliente` int NOT NULL,
  `id_detalleVenta` int NOT NULL,
  `id_detalleDeuda` int NOT NULL,
  `fecha_deuda` date NOT NULL,
  `monto_deuda` double(10,2) NOT NULL,
  PRIMARY KEY (`id_deuda`),
  KEY `fk_idDeuda_cliente` (`id_cliente`),
  KEY `fk_idDetalle_venta` (`id_detalleVenta`),
  KEY `fk_idDetalle_deuda` (`id_detalleDeuda`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `deudas`
--

INSERT INTO `deudas` (`id_deuda`, `id_cliente`, `id_detalleVenta`, `id_detalleDeuda`, `fecha_deuda`, `monto_deuda`) VALUES
(1, 6, 4, 1, '2025-05-10', 45.00),
(2, 6, 5, 1, '2025-07-12', 90.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `efectivo_inicial`
--

DROP TABLE IF EXISTS `efectivo_inicial`;
CREATE TABLE IF NOT EXISTS `efectivo_inicial` (
  `id` int NOT NULL AUTO_INCREMENT,
  `monto` double(10,2) NOT NULL,
  `fecha` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `efectivo_inicial`
--

INSERT INTO `efectivo_inicial` (`id`, `monto`, `fecha`) VALUES
(1, 1000.50, '2025-05-09'),
(2, 300.00, '2025-05-10'),
(3, 200.00, '2025-05-10'),
(4, 300.00, '2025-07-12'),
(5, 100.00, '2025-07-12'),
(6, 200.00, '2025-07-12'),
(7, 1000.00, '2025-07-12');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `entradas_salidas`
--

DROP TABLE IF EXISTS `entradas_salidas`;
CREATE TABLE IF NOT EXISTS `entradas_salidas` (
  `id_entrada_salida` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `tipo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `monto` double(10,2) NOT NULL,
  `descripcion` varchar(180) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_entrada_salida`),
  KEY `fk_idEnt_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `entradas_salidas`
--

INSERT INTO `entradas_salidas` (`id_entrada_salida`, `fecha`, `tipo`, `monto`, `descripcion`, `id_usuario`) VALUES
(1, '2025-05-01 11:27:36', 'Entrada', 600.00, 'dinero extra', 1),
(2, '2025-05-01 11:32:21', 'Salida', 700.00, 'Pago a proovedor coca', 1),
(3, '2025-05-08 00:11:50', 'Salida', 200.00, 'Pago luz', 5),
(4, '2025-05-09 19:26:04', 'Entrada', 500.00, 'pago de productos fiados ', 7),
(5, '2025-05-09 19:29:02', 'Salida', 45.00, 'torta', 7);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos_inventario`
--

DROP TABLE IF EXISTS `movimientos_inventario`;
CREATE TABLE IF NOT EXISTS `movimientos_inventario` (
  `id_movimiento` int NOT NULL AUTO_INCREMENT,
  `fecha` datetime NOT NULL,
  `id_producto` int NOT NULL,
  `tipo_movimiento` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cantidad` double(10,2) NOT NULL,
  `cantidad_anterior` double(10,2) NOT NULL,
  `cantidad_actual` double(10,2) NOT NULL,
  `id_usuario` int NOT NULL,
  PRIMARY KEY (`id_movimiento`),
  KEY `fk_idMov_producto` (`id_producto`),
  KEY `fk_idMov_usuario` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `movimientos_inventario`
--

INSERT INTO `movimientos_inventario` (`id_movimiento`, `fecha`, `id_producto`, `tipo_movimiento`, `cantidad`, `cantidad_anterior`, `cantidad_actual`, `id_usuario`) VALUES
(1, '2025-05-09 19:19:05', 1, 'SALIDA', 1.00, 3.00, 2.00, 7),
(2, '2025-05-09 19:26:34', 6, 'SALIDA', 2.00, 2.00, 0.00, 7),
(3, '2025-05-09 19:26:34', 14, 'SALIDA', 2.00, 12.00, 10.00, 7),
(4, '2025-05-09 19:26:34', 5, 'SALIDA', 2.00, 4.00, 2.00, 7),
(5, '2025-05-09 19:26:34', 15, 'SALIDA', 1.00, 1.00, 0.00, 7),
(6, '2025-05-09 19:26:34', 18, 'SALIDA', 1.00, 10.00, 9.00, 7),
(7, '2025-05-09 19:26:34', 2, 'SALIDA', 1.00, 0.60, -0.40, 7),
(8, '2025-05-09 19:32:53', 20, 'ENTRADA', 5.00, 0.00, 5.00, 7),
(9, '2025-05-09 19:37:05', 1, 'DEVOLUCION', 1.00, 2.00, 3.00, 7),
(10, '2025-05-09 19:37:20', 19, 'SALIDA', 1.00, 10.00, 9.00, 7),
(11, '2025-05-09 19:38:24', 19, 'DEVOLUCION', 1.00, 9.00, 10.00, 7),
(12, '2025-05-10 20:11:09', 1, 'SALIDA', 1.00, 3.00, 2.00, 5),
(13, '2025-07-12 14:26:27', 1, 'SALIDA', 2.00, 2.00, 0.00, 5),
(14, '2025-07-12 14:27:11', 4, 'SALIDA', 1.00, 4.00, 3.00, 5),
(15, '2025-07-12 14:31:19', 21, 'ENTRADA', 3.00, 0.00, 3.00, 5),
(16, '2025-07-12 14:37:21', 22, 'ENTRADA', 2.00, 0.00, 2.00, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `id_producto` int NOT NULL AUTO_INCREMENT,
  `id_categoria` int NOT NULL,
  `codigo_producto` varchar(40) NOT NULL,
  `descripcion_producto` varchar(180) NOT NULL,
  `unidad_producto` varchar(30) NOT NULL,
  `precio_costo` double(10,2) NOT NULL,
  `precio_venta` double(10,2) NOT NULL,
  `existencias_producto` double(10,2) NOT NULL,
  PRIMARY KEY (`id_producto`),
  KEY `fk_idProd_categoria` (`id_categoria`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id_producto`, `id_categoria`, `codigo_producto`, `descripcion_producto`, `unidad_producto`, `precio_costo`, `precio_venta`, `existencias_producto`) VALUES
(1, 2, '123', 'Cocacola', 'Pieza', 40.00, 45.00, 0.00),
(2, 2, '21', 'Queso', 'Granel', 32.00, 50.00, -1.00),
(3, 2, '214', 'Huevo', 'Granel', 60.00, 70.00, 4.00),
(4, 3, '45', 'jabon', 'Pieza', 20.00, 30.00, 3.00),
(5, 1, '245', 'Sprite', 'Pieza', 70.00, 80.00, 2.00),
(6, 2, '1', 'Jitomate', 'Granel', 10.00, 20.00, 0.00),
(7, 3, '1231', 'Suavitel', 'Pieza', 10.00, 22.00, 0.00),
(8, 2, '111', 'Arizona', 'Pieza', 18.00, 22.00, 0.00),
(9, 2, '3145', 'Frijoles', 'Pieza', 14.00, 18.00, 4.00),
(10, 2, '1234', 'Agua ciel', 'Pieza', 6.00, 8.00, 2.00),
(11, 4, '456', 'Crema', 'Pieza', 10.00, 13.00, 1.00),
(12, 1, '1112', 'Jarrito', 'Pieza', 12.00, 15.00, 0.00),
(13, 3, '4567', 'Mechudo', 'Pieza', 20.00, 30.00, 0.00),
(14, 2, '123456789', 'Papas ala francesa ', 'Pieza', 30.00, 45.00, 10.00),
(15, 1, '222', 'Cerveza caguama', 'Pieza', 50.00, 60.00, 0.00),
(16, 2, '678', 'Arroz por kilo', 'Pieza', 22.00, 25.00, 10.00),
(17, 8, '123', 'Manzana', 'Granel', 30.00, 40.00, 5.00),
(18, 2, '58', 'Salchicha', 'Granel', 20.00, 40.00, 9.00),
(19, 3, '444', 'tequila', 'Pieza', 300.00, 330.00, 10.00),
(20, 2, 'ghjgjhgjgjgjh', 'sopa maruchan', 'Pieza', 10.00, 3.00, 5.00),
(21, 3, '123123', 'Escoba', 'Pieza', 10.00, 5.00, 3.00),
(22, 1, '57328', 'Jugo mango Jumex', 'Pieza', 10.00, 5.00, 2.00);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(100) NOT NULL,
  `password_usuario` varchar(20) NOT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `password_usuario`) VALUES
(1, 'admina', '12345'),
(4, 'Hola', '123'),
(5, 'Angelica', 'uno'),
(7, 'victor', 'victor11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

DROP TABLE IF EXISTS `ventas`;
CREATE TABLE IF NOT EXISTS `ventas` (
  `id_venta` int NOT NULL AUTO_INCREMENT,
  `id_detalleVenta` int NOT NULL,
  `id_producto` int NOT NULL,
  `cantidad` double(10,2) NOT NULL,
  `precio_unitario` double(10,2) NOT NULL,
  `importe` double(10,2) NOT NULL,
  PRIMARY KEY (`id_venta`),
  KEY `fk_idVent_producto` (`id_producto`),
  KEY `fk_idDV_detalleVenta` (`id_detalleVenta`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `id_detalleVenta`, `id_producto`, `cantidad`, `precio_unitario`, `importe`) VALUES
(2, 2, 6, 2.00, 20.00, 40.00),
(3, 2, 14, 2.00, 45.00, 90.00),
(4, 2, 5, 2.00, 80.00, 160.00),
(5, 2, 15, 1.00, 60.00, 60.00),
(6, 2, 18, 1.00, 40.00, 40.00),
(7, 2, 2, 1.00, 50.00, 50.00),
(9, 4, 1, 1.00, 45.00, 45.00),
(10, 5, 1, 2.00, 45.00, 90.00),
(11, 6, 4, 1.00, 30.00, 30.00);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `abonos`
--
ALTER TABLE `abonos`
  ADD CONSTRAINT `fk_id_detalleDeuda` FOREIGN KEY (`id_detalleDeuda`) REFERENCES `detalle_deudas` (`id_detalleDeuda`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `detalle_deudas`
--
ALTER TABLE `detalle_deudas`
  ADD CONSTRAINT `fk_id_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `detalle_ventas`
--
ALTER TABLE `detalle_ventas`
  ADD CONSTRAINT `fk_id_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `deudas`
--
ALTER TABLE `deudas`
  ADD CONSTRAINT `fk_idDetalle_deuda` FOREIGN KEY (`id_detalleDeuda`) REFERENCES `detalle_deudas` (`id_detalleDeuda`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_idDetalle_venta` FOREIGN KEY (`id_detalleVenta`) REFERENCES `detalle_ventas` (`id_detalleVenta`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_idDeuda_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `entradas_salidas`
--
ALTER TABLE `entradas_salidas`
  ADD CONSTRAINT `fk_idEnt_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `movimientos_inventario`
--
ALTER TABLE `movimientos_inventario`
  ADD CONSTRAINT `fk_idMov_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_idMov_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `fk_idProd_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id_categoria`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `fk_idDV_detalleVenta` FOREIGN KEY (`id_detalleVenta`) REFERENCES `detalle_ventas` (`id_detalleVenta`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `fk_idVent_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
