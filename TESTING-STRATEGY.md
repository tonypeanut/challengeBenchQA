# Testing Strategy for API Automation

## 1. Resumen
Este documento describe la estrategia de pruebas para la automatización de pruebas de API utilizando Rest-Assured y TestNG. El objetivo es garantizar la correctitud, confiabilidad y rendimiento de la API, manteniendo una suite de pruebas escalable y fácil de mantener.

## 2. Objetivos
- Validar las respuestas de la API en función del comportamiento esperado.
- Garantizar el cumplimiento con definiciones de esquema JSON.
- Probar varios endpoints para verificar su funcionalidad.
- Evaluar métricas de rendimiento, como tiempos de respuesta.
- Manejar casos límite y escenarios de error.

## 3. Enfoque de Pruebas

El enfoque de pruebas para este proyecto se basa en garantizar la calidad de la API de Rick and Morty mediante la validación de su funcionalidad, rendimiento y robustez. Se han definido los siguientes tipos de pruebas:

### 3.1 Pruebas Funcionales
- Validar que los endpoints (/character, /location, /episode) funcionen correctamente según los requisitos.
- Verificar que los campos de respuesta (como name, status, air_date, dimension, etc.) sean correctos y estén presentes.
- Probar filtros y paginación para asegurar que los resultados sean consistentes y precisos.

### 3.2 Validación de Esquema JSON
- Asegurar que las respuestas de la API cumplan con los esquemas JSON definidos para cada recurso (Character, Location, Episode).
- Validar la estructura y los tipos de datos de los campos en las respuestas.

### 3.3 Manejo de Errores
- Probar casos límite, como IDs inválidos o recursos no existentes, para verificar que la API maneje correctamente los errores y devuelva los códigos de estado adecuados (por ejemplo, 404 Not Found).
- Validar que los mensajes de error sean claros y útiles.

### 3.4 Pruebas de Rendimiento
- Medir los tiempos de respuesta de la API para garantizar que estén dentro de los límites aceptables (por ejemplo, menos de 2 segundos por solicitud).
- Evaluar el comportamiento de la API bajo carga normal.

### 3.5 Pruebas de Seguridad
- Verificar que los headers de respuesta (como Content-Type) sean correctos y cumplan con los estándares esperados.

## 4 Estructura de Pruebas

El proyecto sigue una estructura organizada y modular basada en el patrón Page Object Model (POM) para garantizar la mantenibilidad y escalabilidad de las pruebas. La estructura se divide en:

### 4.1 Modelos:
- Clases que representan las entidades de la API (Character, Location, Episode).
- Estas clases contienen los campos y métodos necesarios para mapear las respuestas de la API.

### 4.2 Page Objects:
- Clases que encapsulan las llamadas a los endpoints de la API (por ejemplo, CharacterPage, LocationPage, EpisodePage).
- Estas clases contienen métodos para realizar solicitudes HTTP (GET, POST, etc.) y devolver las respuestas.

### 4.3 Casos de Prueba:
- Clases que contienen los métodos de prueba para cada recurso (CharacterTests, LocationTests, EpisodeTests).
- Cada método de prueba valida un aspecto específico de la API, como códigos de estado, payloads, headers, etc.

### 4.4 Recursos:
- Archivos JSON Schema almacenados en src/test/resources para validar la estructura de las respuestas.
- Configuraciones adicionales, como archivos de propiedades o datos de prueba.

### 4.5 Herramientas:
- Rest-Assured: Para realizar solicitudes HTTP y validar respuestas.
- TestNG: Como framework de ejecución de pruebas.
- JSON Schema Validator: Para validar la estructura de las respuestas JSON.

## 5. Diseño de Casos de Prueba

Los casos de prueba se han diseñado para cubrir los siguientes aspectos clave de la API:

### 5.1 Validación de Códigos de Estado:
- Verificar que los códigos HTTP sean correctos (por ejemplo, 200 OK para respuestas exitosas y 404 Not Found para recursos no existentes).

### 5.2 Validación del Payload de Respuesta:
- Confirmar que los campos y valores esperados estén presentes en las respuestas.
- Validar el formato de campos específicos (por ejemplo, fechas en formato ISO 8601).

### 5.3 Validación de Encabezados:
- Asegurar que los headers de respuesta (como Content-Type) sean correctos y cumplan con los estándares esperados.

### 5.4 Paginación y Filtros:
- Evaluar endpoints que devuelven listas con parámetros de consulta (por ejemplo, /character?name=Rick).
- Verificar que la información de paginación (info.count, info.pages, info.next, info.prev) sea correcta.

### 5.5 Casos Límite:
- Probar entradas inválidas, parámetros faltantes y condiciones especiales (por ejemplo, IDs fuera de rango).
- Validar que la API maneje correctamente estos casos y devuelva los errores adecuados.

### 5.6 Orden de los Resultados:
- Verificar que los recursos estén ordenados correctamente (por ejemplo, IDs en orden ascendente).

### 5.7 Tiempos de Respuesta:
- Medir y validar que los tiempos de respuesta estén dentro de los límites aceptables.


## 6 Estrategia de Mantenimiento

Para garantizar la mantenibilidad y escalabilidad de las pruebas, se ha definido la siguiente estrategia:

### 6.1 Revisión Periódica de Casos de Prueba:
- Revisar y actualizar los casos de prueba cada vez que la API cambie o se añadan nuevos endpoints.
- Asegurar que los esquemas JSON estén actualizados con los últimos cambios en la API.

### 6.2 Automatización de Pruebas:
- Ejecutar las pruebas automáticamente en cada cambio de código mediante integración continua (CI).
- Generar reportes de pruebas para identificar rápidamente fallos o regresiones.

### 6.3 Documentación:
- Mantener actualizada la documentación del proyecto (README, Testing Strategy) para reflejar los cambios en las pruebas y la API.
- Añadir comentarios en el código para explicar la lógica de las pruebas.

### 6.4 Refactorización:
- Refactorizar el código de pruebas para eliminar redundancias y mejorar la legibilidad.
- Utilizar métodos auxiliares para reutilizar código común (por ejemplo, validación de esquemas JSON).


