# Rick and Morty API Automation Tests

Este proyecto contiene pruebas automatizadas para la API de Rick and Morty utilizando **RestAssured** y el patrón de diseño **Page Object Model (POM)**.

## Descripción
El objetivo de este proyecto es automatizar la validación de la API de Rick and Morty, centrándose en los recursos **Character**, **Location** y **Episode**. Las pruebas incluyen:
- Validación de códigos de estado HTTP.
- Validación del payload de respuesta (incluyendo validación de JSON Schema).
- Validación de headers de respuesta.
- Filtrado de recursos (personajes, ubicaciones y episodios).
- Manejo de errores (por ejemplo, recurso no encontrado).

## Requisitos
- **Java 21**
- **Maven**
- **IntelliJ IDEA** (opcional, pero recomendado)

## Tecnologías Utilizadas
- **Java 21**
- **Maven**
- **Rest-Assured**
- **TestNG**
- **Allure Reports** (para generación de reportes)
- **JSON Schema Validator**

## Ajustar la versión de Java
Si deseas ejecutar el proyecto con una versión de Java diferente a la 21, modifica las propiedades del `pom.xml`:

```xml
<properties>
    <maven.compiler.source>Tu-versión</maven.compiler.source>
    <maven.compiler.target>Tu-versión</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```
        
Luego, ejecuta `mvn clean install` para recompilar el proyecto con la nueva versión de Java.

## Configuración

1. Clona el repositorio.
```bash
git clone https://github.com/tonypeanut/challengeBenchQA.git
```
2. Importa el proyecto en IntelliJ IDEA como un proyecto Maven.
3. Navega al directorio del proyecto.
```bash
cd challengeBenchQA
```

## Ejecución de Pruebas

### Ejecutar Todas las Pruebas

Para ejecutar todas las pruebas, usa el siguiente comando en la terminal:

```bash 
mvn clean test
```

### Ejecutar Pruebas Específicas
Si deseas ejecutar pruebas específicas (por ejemplo, solo las pruebas de Character), usa el siguiente comando:
```bash 
mvn clean test -Dtest=CharacterTests
```

## Generación de Reportes con Allure
### 1. Generar los Resultados de las Pruebas
Ejecuta las pruebas para generar los resultados en la carpeta `target/allure-results`:
```bash
mvn clean test
```
### 2. Generar el Reporte de Allure
Una vez ejecutadas las pruebas, genera el reporte de Allure con el siguiente comando:
```bash
mvn allure:report
```
El reporte se generará en la carpeta `target/site/allure-maven-plugin`.

### 3. Ver el Reporte
Para ver el reporte generado, abre el archivo index.html en la carpeta `target/site/allure-maven-plugin`:

```bash
mvn allure:serve
```

## Estructura del Proyecto
El proyecto está organizado de la siguiente manera:
- `src/test/java/com/rickandmorty/api/models`: Contiene las clases de modelos (por ejemplo, Character, Origin, Location).
- `src/test/java/com/rickandmorty/api/pages`: Contiene las clases Page Object para encapsular las llamadas a la API.
- `src/test/java/com/rickandmorty/api/tests`: Contiene los casos de prueba.
- `src/test/resources`: Aquí se almacenan los archivos de recursos, como los esquemas JSON para validación.

## Estrategia de Pruebas
Para conocer la estrategia de pruebas utilizada en este proyecto, revisa el documento [Testing Strategy](TESTING-STRATEGY.md).

## Documentación de la API
Para más información sobre la API, consulta la [documentación oficial](https://rickandmortyapi.com/documentation).
