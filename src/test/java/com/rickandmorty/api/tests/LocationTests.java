package com.rickandmorty.api.tests;

import com.rickandmorty.api.pages.LocationPage;
import io.qameta.allure.*;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class LocationTests {
    private LocationPage locationPage = new LocationPage();

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Verifica que se puede obtener una ubicación por ID y que los datos sean correctos.")
    public void testGetLocationById() {
        Response response = locationPage.getLocation(3);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Citadel of Ricks"))
                .body("type", equalTo("Space station"))
                .body("dimension", equalTo("unknown"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Valida que se pueden obtener todas las ubicaciones y que la respuesta contiene información.")
    public void testGetAllLocations() {
        Response response = locationPage.getAllLocations();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("info.count", notNullValue())
                .body("results", notNullValue());
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Confirma que se pueden obtener múltiples ubicaciones mediante sus respectivos IDs.")
    public void testGetMultipleLocations() {
        List<Integer> ids = Arrays.asList(3, 21);
        Response response = locationPage.getMultipleLocations(ids);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("[0].name", equalTo("Citadel of Ricks"))
                .body("[1].name", equalTo("Testicle Monster Dimension"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Comprueba que el filtro de ubicaciones devuelve los resultados esperados.")
    public void testFilterLocations() {
        Response response = locationPage.filterLocations("Citadel of Ricks", "Space station", "unknown");

        response.then()
                .assertThat()
                .statusCode(200)
                .body("results.name", hasItem("Citadel of Ricks"));
    }

    @Test
    @Feature("Extras")
    @Story("Location")
    @Description("Valida que los headers de la respuesta sean los esperados.")
    public void testLocationResponseHeaders() {
        Response response = locationPage.getLocation(3);

        response.then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    @Feature("Manejo de Errores")
    @Story("Location")
    @Description("Confirma que al solicitar una ubicación con un ID inválido, la API devuelve un error 404.")
    public void testInvalidLocationId() {
        Response response = locationPage.getLocation(9999);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("Location not found"));
    }

    @Test
    @Feature("Validación de Esquema JSON")
    @Story("Location")
    @Description("Asegura que la respuesta del endpoint cumple con el esquema JSON esperado.")
    public void testLocationJsonSchema() {
        Response response = locationPage.getLocation(3);
        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("location-schema.json"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Asegura que ciertos campos clave están presentes en la respuesta de una ubicación.")
    public void testLocationFields() {
        Response response = locationPage.getLocation(3);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("url", notNullValue())
                .body("created", notNullValue())
                .body("residents", notNullValue());
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Verifica que la primera y la última ubicación de la API existan y sean accesibles.")
    public void testFirstAndLastLocationId() {
        Response firstLocation = locationPage.getLocation(1);
        Response lastLocation = locationPage.getLocation(126);

        firstLocation.then()
                .assertThat()
                .statusCode(200);

        lastLocation.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Valida que la información de paginación es correcta.")
    public void testLocationPaginationInfo() {
        Response response = locationPage.getAllLocations();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("info.count", greaterThan(0))
                .body("info.pages", greaterThan(0))
                .body("info.next", notNullValue())
                .body("info.prev", nullValue());
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Location")
    @Description("Verifica que al aplicar un filtro sin coincidencias, la API devuelva un resultado vacío o un error 404.")
    public void testFilterLocationNoResults() {
        Response response = locationPage.filterLocations("XYZ123", "", "");

        response.then()
                .assertThat()
                .statusCode(anyOf(is(200), is(404)))
                .body("info.count", response.getStatusCode() == 200 ? equalTo(0) : anything())
                .body("error", response.getStatusCode() == 404 ? not(emptyOrNullString()) : anything());
    }

    @Test
    @Feature("Pruebas de Rendimiento")
    @Story("Location")
    @Description("Comprueba que el tiempo de respuesta sea menor a 2000 ms.")
    public void testResponseTime() {
        Response response = locationPage.getLocation(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .time(lessThan(2000L));
    }

    @Test
    @Feature("Validación de Esquema JSON")
    @Story("Location")
    @Description("Valida que el campo created tenga el formato ISO 8601 correcto.")
    public void testCreatedFieldFormatForLocation() {
        Response response = locationPage.getLocation(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("created", matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$"));
    }

    @Test
    @Feature("Manejo de Errores")
    @Story("Location")
    @Description("Verifica que la API responda con un esquema de error adecuado cuando se consulta una ubicación inexistente.")
    public void testLocationErrorResponseSchema() {
        Response response = locationPage.getLocation(9999);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("error", notNullValue())
                .body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    @Feature("Extras")
    @Story("Location")
    @Description("Asegura que las ubicaciones están ordenadas por ID de forma ascendente.")
    public void testLocationsOrder() {
        Response response = locationPage.getAllLocations();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("results.id", not(empty()));


        List<Integer> ids = response.jsonPath().getList("results.id", Integer.class);
        List<Integer> sortedIds = new ArrayList<>(ids);
        Collections.sort(sortedIds);

        // Compara la lista obtenida con la ordenada
        org.testng.Assert.assertEquals(ids, sortedIds, "Los IDs deben estar ordenados de forma ascendente");
    }
}