# Corralón API

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)

API REST para la gestión integral de un ecommerce de materiales de construcción y productos de corralón. Proyecto académico desarrollado en el marco de la UTN.

---

## Descripción General

El sistema permite administrar de forma completa un negocio de corralón con las siguientes funcionalidades:

- **Autenticación y autorización** con JWT y control de roles (GUEST, CUSTOMER, EMPLOYEE, ADMIN)
- **Catálogo de productos** con categorías, marcas y proveedores
- **Variantes de producto** (presentaciones, tamaños, colores) con stock y precios independientes
- **Control de stock** con trazabilidad total de movimientos (ingresos, ventas, cancelaciones, ajustes)
- **Carrito de compras** persistente por usuario
- **Órdenes de compra** con estados, tipos de entrega (envío/retiro) y cancelación con devolución automática de stock
- **Pagos** con múltiples métodos (efectivo, tarjeta, transferencia, Mercado Pago)
- **Ofertas y promociones** aplicables a variantes específicas
- **Notificaciones por email** ante eventos relevantes (registro, compra, cancelación, promociones)
- **Gestión de direcciones** múltiples por usuario

---

## Stack Tecnológico

| Capa              | Tecnología                                      |
|-------------------|------------------------------------------------|
| Framework         | Spring Boot 4.0.6                              |
| Seguridad         | Spring Security + JWT (jjwt 0.13.0)            |
| Encriptación      | BCrypt                                         |
| ORM               | JPA / Hibernate                                |
| Base de datos     | MySQL 8.0                                      |
| Mapeo             | ModelMapper 3.2.1                              |
| Mail              | Spring Mail (asincrónico)                      |
| Documentación     | SpringDoc OpenAPI 2.8.10 (Swagger UI)          |
| Utilidades        | Lombok                                         |
| Contenedorización | Docker + Docker Compose                        |

---

## Requisitos Previos

- **Java 21+** (para ejecución local)
- **Docker y Docker Compose** (para ejecución con contenedores)

---

## Variables de Entorno

El proyecto utiliza un archivo `.env` en la raíz para la configuración. Un ejemplo de `.env`:

```properties
DB_NAME=corralon_db
DB_USER=user
DB_PASSWORD=password
MYSQL_ROOT_PASSWORD=root
JWT_SECRET=clave_de_ejemplo
JWT_EXPIRATION=86400000
```

| Variable             | Descripción                                   | Valor por defecto |
|----------------------|-----------------------------------------------|-------------------|
| `DB_NAME`            | Nombre de la base de datos MySQL              | `corralon_db`     |
| `DB_USER`            | Usuario de la base de datos                   | `user`            |
| `DB_PASSWORD`        | Contraseña de la base de datos                | `password`        |
| `MYSQL_ROOT_PASSWORD`| Contraseña root de MySQL                      | `root`            |
| `JWT_SECRET`         | Clave secreta para firmar tokens JWT          | *(requerido)*     |
| `JWT_EXPIRATION`     | Duración del access token en milisegundos     | `86400000` (24hs) |
| `JWT_REFRESH_EXPIRATION` | Duración del refresh token en milisegundos | `86400000` (24hs) |
| `MAIL_HOST`          | Host del servidor SMTP                        | `localhost`       |
| `MAIL_PORT`          | Puerto del servidor SMTP                      | `1025`            |
| `MAIL_USERNAME`      | Usuario SMTP                                  | *(vacío)*         |
| `MAIL_PASSWORD`      | Contraseña SMTP                               | *(vacío)*         |

> **Nota:** El archivo `.env` está incluido en `.gitignore` y no se sube al repositorio.

---

## Cómo Ejecutar el Proyecto

### Opción A: Docker Compose (recomendada)

Levanta la aplicación junto con MySQL y Mailhog (interceptor de emails para desarrollo):

```bash
docker compose up --build
```

Esto levanta tres servicios:
- **app** — La API en `http://localhost:8080`
- **mysql** — Base de datos MySQL 8.0 en el puerto `3307` (externo)
- **mailhog** — Interceptor de emails en `http://localhost:8025`

Para detener:

```bash
docker compose down
```

Para detener y eliminar los datos de la base de datos:

```bash
docker compose down -v
```

### Opción B: Ejecución local

1. Levantar un servidor MySQL local y crear la base de datos:

```sql
CREATE DATABASE corralon_db;
```

2. Copiar el archivo de ejemplo y configurar las variables:

```bash
cp .env.example .env
```

3. Ejecutar la aplicación:

```bash
./mvnw spring-boot:run
```

La aplicación estará disponible en `http://localhost:8081`.

> **Nota:** La configuración de `application.yaml` espera las variables de entorno definidas en `.env`. En ejecución local usa `localhost:3306` por defecto para MySQL.

---

## Documentación API (Swagger)

Una vez que la aplicación está corriendo, la documentación interactiva de la API está disponible en:

```
http://localhost:8080/swagger-ui.html
```

Los endpoints públicos (`/api/auth/**`) son accesibles sin autenticación. El resto requiere un token JWT válido en el header `Authorization`.

---

## Autenticación y Autorización

### Flujo de autenticación

1. **Registro** — `POST /api/auth/register` crea un usuario con rol `ROLE_CUSTOMER` por defecto
2. **Login** — `POST /api/auth/login` con email y password → devuelve un JWT token
3. **Uso del token** — Enviar el token en el header: `Authorization: Bearer <token>`
4. Los tokens expiran según la variable `JWT_EXPIRATION` (por defecto 24 horas)

### Ejemplo con curl

```bash
# 1. Registrar un usuario
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@ejemplo.com",
    "name": "Juan",
    "lastName": "Pérez",
    "password": "miPassword123"
  }'

# 2. Iniciar sesión
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "usuario@ejemplo.com",
    "password": "miPassword123"
  }'

# Respuesta: { "token": "eyJhbGciOiJIUzI1NiJ9..." }

# 3. Usar el token en endpoints protegidos
curl http://localhost:8080/api/products/search \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

### Roles y Permisos

El sistema implementa un modelo de seguridad basado en roles con permisos granulares (RBAC):

| Rol | Descripción | Permisos principales |
|-----|-------------|---------------------|
| **GUEST** | Usuario no autenticado | Ver catálogo de productos, categorías, marcas y ofertas |
| **CUSTOMER** | Cliente registrado | Todo lo de GUEST + carrito, órdenes, pagos, direcciones, notificaciones |
| **EMPLOYEE** | Empleado del corralón | Gestión de productos, variantes, stock, categorías, marcas, proveedores, ofertas y órdenes |
| **ADMIN** | Administrador | Acceso total a todos los permisos del sistema |

Los permisos se verifican mediante `@PreAuthorize` en cada endpoint. Un usuario puede tener múltiples roles simultáneamente.

---

## Endpoints Disponibles

A continuación un resumen de los endpoints agrupados por dominio. Para la documentación detallada con schemas y ejemplos, consultar Swagger UI.

### Autenticación (`/api/auth`)

| Método | Path         | Descripción                     | Auth |
|--------|-------------|---------------------------------|------|
| POST   | `/register` | Registrar nuevo usuario         | No   |
| POST   | `/login`    | Iniciar sesión, obtener JWT     | No   |

### Usuarios (`/api/users`)

| Método | Path               | Descripción                | Permiso       |
|--------|-------------------|-----------------------------|---------------|
| POST   | `/`               | Crear usuario               | `USER_CREATE` |
| GET    | `/`               | Listar todos los usuarios    | `USER_LIST`   |
| GET    | `/{externalId}`   | Obtener usuario por ID       | `USER_READ`   |
| PUT    | `/{externalId}`   | Actualizar usuario           | `USER_UPDATE` |
| DELETE | `/{externalId}`   | Eliminar usuario (baja lógica)| `USER_DELETE` |

### Productos (`/api/products`)

| Método | Path                      | Descripción                      | Permiso               |
|--------|--------------------------|----------------------------------|------------------------|
| GET    | `/search`                | Buscar productos activos         | `PRODUCT_LIST`         |
| GET    | `/inactive`              | Buscar productos inactivos       | `PRODUCT_LIST_INACTIVE`|
| GET    | `/{externalId}`          | Obtener producto por ID          | `PRODUCT_READ`         |
| POST   | `/`                      | Crear producto                   | `PRODUCT_CREATE`       |
| PUT    | `/{externalId}`          | Actualizar producto              | `PRODUCT_UPDATE`       |
| DELETE | `/{externalId}`          | Dar de baja (soft delete)        | `PRODUCT_DELETE`       |
| PATCH  | `/{externalId}/activate` | Reactivar producto               | `PRODUCT_ACTIVATE`     |

### Variantes de Producto (`/api/product-variants`)

| Método | Path                          | Descripción                           | Permiso                      |
|--------|-------------------------------|---------------------------------------|------------------------------|
| GET    | `/search`                     | Buscar variantes activas              | `PRODUCT_VARIANT_LIST`       |
| GET    | `/inactive`                   | Buscar variantes inactivas            | `PRODUCT_VARIANT_LIST_INACTIVE`|
| GET    | `/{externalId}`               | Obtener variante por ID               | `PRODUCT_VARIANT_READ`       |
| POST   | `/`                           | Crear variante                        | `PRODUCT_VARIANT_CREATE`     |
| PUT    | `/{externalId}`               | Actualizar variante                   | `PRODUCT_VARIANT_UPDATE`     |
| DELETE | `/{externalId}`               | Dar de baja (soft delete)             | `PRODUCT_VARIANT_DELETE`     |
| PATCH  | `/{externalId}/activate`      | Reactivar variante                    | `PRODUCT_VARIANT_ACTIVATE`   |
| POST   | `/stock/entry`                | Registrar ingreso de stock            | `STOCK_ENTRY`                |
| POST   | `/stock/adjustment`           | Ajustar stock                         | `STOCK_ADJUSTMENT`           |
| GET    | `/{externalId}/stock`         | Consultar stock disponible            | `STOCK_READ`                 |

### Movimientos de Stock (`/api/stock-movements`)

| Método | Path                              | Descripción                              | Permiso                        |
|--------|-----------------------------------|------------------------------------------|--------------------------------|
| GET    | `/variant/{variantId}`            | Movimientos de una variante              | `STOCK_MOVEMENT_LIST_BY_VARIANT`|
| GET    | `/{externalId}`                   | Detalle de un movimiento                 | `STOCK_MOVEMENT_READ`          |

### Categorías (`/api/categories`)

| Método | Path                      | Descripción                       | Permiso                 |
|--------|--------------------------|-----------------------------------|-------------------------|
| GET    | `/`                      | Listar categorías activas         | `CATEGORY_LIST`         |
| GET    | `/inactive`              | Listar categorías inactivas       | `CATEGORY_LIST_INACTIVE`|
| GET    | `/{externalId}`          | Obtener categoría por ID          | `CATEGORY_READ`         |
| POST   | `/`                      | Crear categoría                   | `CATEGORY_CREATE`       |
| PUT    | `/{externalId}`          | Actualizar categoría              | `CATEGORY_UPDATE`       |
| DELETE | `/{externalId}`          | Dar de baja (soft delete)         | `CATEGORY_DELETE`       |
| PATCH  | `/{externalId}/activate` | Reactivar categoría               | `CATEGORY_ACTIVATE`     |

### Marcas (`/api/brands`)

| Método | Path                      | Descripción                       | Permiso             |
|--------|--------------------------|-----------------------------------|----------------------|
| GET    | `/`                      | Listar marcas activas             | `BRAND_LIST`         |
| GET    | `/inactive`              | Listar marcas inactivas           | `BRAND_LIST_INACTIVE`|
| GET    | `/{externalId}`          | Obtener marca por ID              | `BRAND_READ`         |
| POST   | `/`                      | Crear marca                       | `BRAND_CREATE`       |
| PUT    | `/{externalId}`          | Actualizar marca                  | `BRAND_UPDATE`       |
| DELETE | `/{externalId}`          | Dar de baja (soft delete)         | `BRAND_DELETE`       |
| PATCH  | `/{externalId}/activate` | Reactivar marca                   | `BRAND_ACTIVATE`     |

### Proveedores (`/api/suppliers`)

| Método | Path                      | Descripción                       | Permiso                |
|--------|--------------------------|-----------------------------------|-------------------------|
| GET    | `/`                      | Listar proveedores activos        | `SUPPLIER_LIST`         |
| GET    | `/inactive`              | Listar proveedores inactivos      | `SUPPLIER_LIST_INACTIVE`|
| GET    | `/{externalId}`          | Obtener proveedor por ID          | `SUPPLIER_READ`         |
| POST   | `/`                      | Crear proveedor                   | `SUPPLIER_CREATE`       |
| PUT    | `/{externalId}`          | Actualizar proveedor              | `SUPPLIER_UPDATE`       |
| DELETE | `/{externalId}`          | Dar de baja (soft delete)         | `SUPPLIER_DELETE`       |
| PATCH  | `/{externalId}/activate` | Reactivar proveedor               | `SUPPLIER_ACTIVATE`     |

### Ofertas (`/api/offers`)

| Método | Path                                             | Descripción                       | Permiso                |
|--------|--------------------------------------------------|-----------------------------------|------------------------|
| GET    | `/`                                              | Listar ofertas activas            | `OFFER_LIST`           |
| GET    | `/inactive`                                      | Listar ofertas inactivas          | `OFFER_LIST_INACTIVE`  |
| GET    | `/{externalId}`                                  | Obtener oferta por ID             | `OFFER_READ`           |
| POST   | `/`                                              | Crear oferta                      | `OFFER_CREATE`         |
| POST   | `/{offerExternalId}/products`                    | Agregar producto a oferta         | `OFFER_ADD_PRODUCT`    |
| DELETE | `/{offerExternalId}/products/{variantExternalId}`| Quitar producto de oferta         | `OFFER_REMOVE_PRODUCT` |
| PATCH  | `/{externalId}/activate`                         | Activar oferta                    | `OFFER_ACTIVATE`       |
| PATCH  | `/{externalId}/deactivate`                       | Desactivar oferta                 | `OFFER_DEACTIVATE`     |

### Carrito (`/api/carts`)

| Método | Path                                             | Descripción                       | Permiso                   |
|--------|--------------------------------------------------|-----------------------------------|---------------------------|
| POST   | `/`                                              | Crear o actualizar carrito        | `CART_CREATE_OR_UPDATE`   |
| GET    | `/{userId}`                                      | Ver carrito de un usuario         | `CART_READ`               |
| PATCH  | `/{userId}/items/{productVariantId}`             | Cambiar cantidad de un ítem       | `CART_UPDATE_ITEM_QUANTITY`|
| DELETE | `/{userId}`                                      | Vaciar carrito                    | `CART_CLEAR`              |
| POST   | `/{userId}/checkout`                             | Checkout → crear orden            | `CART_CHECKOUT`           |

> **Checkout:** Acepta parámetros `deliveryType` (`DELIVERY` o `PICKUP`) y opcionalmente `addressId` (requerido para `DELIVERY`).

### Órdenes (`/api/orders`)

| Método | Path                        | Descripción                          | Permiso / Rol                    |
|--------|-----------------------------|--------------------------------------|----------------------------------|
| GET    | `/admin`                    | Listar todas las órdenes (admin)     | `ROLE_ADMIN` o `ROLE_EMPLOYEE`   |
| GET    | `/admin/{externalId}`       | Detalle completo de orden (admin)    | `ROLE_ADMIN` o `ROLE_EMPLOYEE`   |
| GET    | `/{externalId}`             | Ver orden propia por ID              | `ORDER_READ`                     |
| GET    | `/user/{userExternalId}`    | Órdenes de un usuario                | `ORDER_READ_BY_USER`             |
| POST   | `/{externalId}/cancel`      | Cancelar orden                       | `ORDER_CANCEL_OWN`               |

### Direcciones (`/api/addresses`)

| Método | Path                              | Descripción                       | Permiso                 |
|--------|-----------------------------------|-----------------------------------|-------------------------|
| GET    | `/`                               | Listar todas las direcciones       | `ADDRESS_LIST`          |
| GET    | `/user/{userExternalId}`          | Direcciones de un usuario          | `ADDRESS_LIST_BY_USER`  |
| GET    | `/{externalId}`                   | Obtener dirección por ID           | `ADDRESS_READ`          |
| POST   | `/`                               | Crear dirección                    | `ADDRESS_CREATE`        |
| PUT    | `/{externalId}`                   | Actualizar dirección               | `ADDRESS_UPDATE`        |
| DELETE | `/{externalId}?userExternalId=`   | Eliminar dirección                 | `ADDRESS_DELETE`        |

### Pagos (`/api/payments`)

| Método | Path                    | Descripción                       | Permiso         |
|--------|------------------------|-----------------------------------|-----------------|
| POST   | `/pay`                 | Registrar pago de una orden        | `PAYMENT_CREATE`|
| GET    | `/order/{orderId}`     | Consultar pago por orden           | `PAYMENT_READ`  |

### Notificaciones (`/api/notifications`)

| Método | Path                                          | Descripción                       | Permiso                        |
|--------|-----------------------------------------------|-----------------------------------|--------------------------------|
| POST   | `/`                                           | Crear notificación                | `NOTIFICATION_CREATE`          |
| GET    | `/user/{userId}`                              | Notificaciones de un usuario      | `NOTIFICATION_READ_BY_USER`    |
| PATCH  | `/{notificationExternalId}/read`              | Marcar como leída                 | `NOTIFICATION_MARK_AS_READ`    |
| DELETE | `/{notificationExternalId}`                   | Eliminar notificación             | `NOTIFICATION_DELETE`          |
| PATCH  | `/user/{userId}/read-all`                     | Marcar todas como leídas          | `NOTIFICATION_MARK_ALL_AS_READ`|

---

## Modelo de Datos

```
┌─────────────────────────────────────────────────────────────────────┐
│                        SEGURIDAD                                    │
│                                                                     │
│  CredentialsEntity ──1:1──► UserEntity                              │
│       │                          │                                  │
│       │ M:N                      │ 1:N                              │
│       ▼                          ▼                                  │
│  RoleEntity ──M:N──► PermitEntity    AddressEntity                  │
│                                                                     │
├─────────────────────────────────────────────────────────────────────┤
│                     CATÁLOGO DE PRODUCTOS                           │
│                                                                     │
│  Supplier ──┐                                                       │
│  Category ──┼──► Product ──1:N──► ProductVariant                    │
│  Brand ─────┘                       │                               │
│                                     ├── 1:N ──► StockMovement       │
│                                     ├── N:M ──► OfferProduct ◄── Offer│
│                                     ├── N:1 ──► CartItem ◄── Cart   │
│                                     └── N:1 ──► OrderItem ◄── Order │
│                                                                     │
├─────────────────────────────────────────────────────────────────────┤
│                     FLUJO DE COMPRA                                 │
│                                                                     │
│  User ──1:1──► Cart ──1:N──► CartItem                               │
│                          (checkout)                                 │
│                              ▼                                      │
│  User ──1:N──► Order ──1:N──► OrderItem                             │
│                  │                                                  │
│                  ├── 1:1 ──► Payment                                │
│                  ├── N:1 ──► Address (nullable, PICKUP)             │
│                  └── estados: PENDING_PAYMENT → PAID / CANCELLED    │
│                                                                     │
│  User ──1:N──► Notification                                         │
└─────────────────────────────────────────────────────────────────────┘
```

### Entidades principales

| Entidad            | Descripción                                        |
|--------------------|----------------------------------------------------|
| `UserEntity`       | Datos personales del usuario (nombre, email, etc.) |
| `CredentialsEntity`| Credenciales de auth, roles y refresh token        |
| `ProductEntity`    | Artículo del catálogo (Cemento, Arena, etc.)       |
| `ProductVariantEntity` | Presentación comercial de un producto (Bolsa 25kg, etc.) |
| `StockMovementEntity`  | Registro de todo cambio de stock               |
| `CartEntity`       | Carrito persistente por usuario                    |
| `OrderEntity`      | Compra confirmada con total congelado              |
| `PaymentEntity`    | Pago asociado a una orden                          |
| `OfferEntity`      | Promoción con porcentaje de descuento y vigencia   |
| `NotificationEntity` | Notificación por email/evento                   |

---

## Reglas de Negocio Clave

| Regla | Detalle |
|-------|---------|
| Stock negativo prohibido | Nunca puede existir stock menor a cero |
| Compra sin stock prohibida | No se puede comprar más de lo disponible |
| Emails únicos | No pueden existir dos usuarios con el mismo email |
| Toda modificación de stock genera un movimiento | Sin excepción, con tipo: `ENTRY`, `SALE`, `CANCELLATION`, `ADJUSTMENT` |
| Total de orden congelado | El total se calcula al momento de creación y no varía si el precio cambia después |
| Baja lógica | Los productos, categorías, marcas, proveedores y variantes se desactivan, no se eliminan físicamente |
| Cancelación devuelve stock | Al cancelar una orden en `PENDING_PAYMENT`, el stock se repone automáticamente |
| Precio mayorista | Si la cantidad comprada alcanza `wholesaleMinQty`, se aplica el precio mayorista a todas las unidades |

---

## Manejo de Errores

La API devuelve respuestas de error estructuradas:

| Código HTTP | Excepción                    | Situación                              |
|-------------|-----------------------------|----------------------------------------|
| 400         | `BadRequestException`       | Datos de entrada inválidos             |
| 401         | *(no autenticado)*          | Token JWT ausente o inválido           |
| 403         | `UnauthorizedException`     | Sin permisos para la operación         |
| 404         | `ResourceNotFoundException` | Recurso no encontrado                  |
| 409         | `BusinessRuleException`     | Violación de regla de negocio general  |
| 409         | `EmailAlreadyExistsException` | Email duplicado al registrar        |
| 409         | `StockInsufficientException` | Stock insuficiente para la compra    |

Las validaciones de campos (`@Valid`) devuelven un mapa de errores con los campos y mensajes correspondientes.

---

## Estructura del Proyecto

```
src/main/java/com/utn/corralon/
├── CorralonApplication.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   ├── BusinessRuleException.java
│   ├── EmailAlreadyExistsException.java
│   ├── StockInsufficientException.java
│   └── UnauthorizedException.java
└── features/
    ├── auth/                    # JWT, Spring Security, roles, permisos
    ├── user/                    # Gestión de usuarios
    ├── address/                 # Direcciones
    ├── product/                 # Productos
    ├── productVariant/          # Variantes + stock
    ├── stockMovement/           # Movimientos de stock
    ├── category/                # Categorías
    ├── brand/                   # Marcas
    ├── supplier/                # Proveedores
    ├── offer/                   # Ofertas
    ├── offer_product/           # Relación oferta-producto
    ├── cart/                    # Carrito
    ├── cart_item/               # Ítems del carrito
    ├── order/                   # Órdenes
    ├── orderItem/               # Ítems de la orden
    ├── payment/                 # Pagos
    └── notifications/           # Notificaciones
```

Cada feature sigue la estructura:

```
feature/
├── controller/      # Endpoints REST
├── dto/             # Request y Response DTOs
├── entity/          # Entidades JPA
├── mapper/          # Mapeos con ModelMapper
├── repository/      # Repositorios Spring Data
└── service/         # Interfaz y implementación del servicio
```

---

## Licencia

Proyecto académico — UTN (Universidad Tecnológica Nacional).
