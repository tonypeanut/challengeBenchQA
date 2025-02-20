package com.rickandmorty.api.tests;

import com.rickandmorty.api.pages.EpisodePage;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class EpisodeTests {
    private EpisodePage episodePage = new EpisodePage();

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Verifica que se puede obtener un episodio por ID y que los datos sean correctos.")
    public void testGetEpisodeById() {
        Response response = episodePage.getEpisode(28);

        response.then()
            .assertThat()
            .statusCode(200)
            .body("name", equalTo("The Ricklantis Mixup"))
            .body("air_date", equalTo("September 10, 2017"))
            .body("episode", equalTo("S03E07"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Valida que se pueden obtener todos los episodios y que la respuesta contenga datos.")
    public void testGetAllEpisodes() {
        Response response = episodePage.getAllEpisodes();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("info.count", notNullValue())
                .body("results", not(empty()));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Confirma que se pueden obtener múltiples episodios por sus respectivos IDs.")
    public void testGetMultipleEpisodes() {
        List<Integer> ids = Arrays.asList(10, 28);
        Response response = episodePage.getMultipleEpisodes(ids);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("", hasSize(2))
                .body("[0].name", equalTo("Close Rick-counters of the Rick Kind"))
                .body("[1].name", equalTo("The Ricklantis Mixup"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Comprueba que el filtro de episodios devuelve los resultados esperados.")
    public void testFilterEpisodes() {
        Response response = episodePage.filterEpisodes("The Ricklantis Mixup", "S03E07");

        response.then()
                .assertThat()
                .statusCode(200)
                .body("results.name", hasItem("The Ricklantis Mixup"));
    }

    @Test
    @Feature("Extras")
    @Story("Episode")
    @Description("Valida que los headers de la respuesta sean los esperados.")
    public void testEpisodeResponseHeaders() {
        Response response = episodePage.getEpisode(28);

        response.then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    @Feature("Manejo de Errores")
    @Story("Episode")
    @Description("Confirma que al solicitar un episodio con un ID inválido, la API devuelve un error 404.")
    public void testInvalidEpisodeId() {
        Response response = episodePage.getEpisode(9999);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("Episode not found"));
    }

    @Test
    @Feature("Validación de Esquema JSON")
    @Story("Episode")
    @Description("Asegura que la respuesta del endpoint cumple con el esquema JSON esperado.")
    public void testEpisodeJsonSchema() {
        Response response = episodePage.getEpisode(28);

        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("episode-schema.json"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Asegura que ciertos campos clave están presentes en la respuesta de un episodio.")
    public void testEpisodeFields() {
        Response response = episodePage.getEpisode(28);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("url", notNullValue())
                .body("created", notNullValue())
                .body("characters", notNullValue());
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Verifica que el primer y el último episodio de la API existan y sean accesibles.")
    public void testFirstAndLastEpisodeId() {
        Response firstEpisode = episodePage.getEpisode(1);
        Response lastEpisode = episodePage.getEpisode(51);

        firstEpisode.then()
                .assertThat()
                .statusCode(200);

        lastEpisode.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Episode")
    @Description("Valida que la información de paginación es correcta.")
    public void testEpisodePaginationInfo() {
        Response response = episodePage.getAllEpisodes();

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
    @Story("Episode")
    @Description("Verifica que al aplicar un filtro sin coincidencias, la API devuelva un resultado vacío o un error 404.")
    public void testFilterEpisodeNoResults() {
        Response response = episodePage.filterEpisodes("XYZ123", "");

        response.then()
                .assertThat()
                .statusCode(anyOf(is(200), is(404)))
                .body("info.count", response.getStatusCode() == 200 ? equalTo(0) : anything())
                .body("error", response.getStatusCode() == 404 ? not(emptyOrNullString()) : anything());
    }

    @Test
    @Feature("Pruebas de Rendimiento")
    @Story("Episode")
    @Description("Comprueba que el tiempo de respuesta sea menor a 2000 ms.")
    public void testResponseTime() {
        Response response = episodePage.getEpisode(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .time(lessThan(2000L));
    }

    @Test
    @Feature("Validación de Esquema JSON")
    @Story("Episode")
    @Description("Valida que el campo created tenga el formato ISO 8601 correcto.")
    public void testCreatedFieldFormatForEpisode() {
        Response response = episodePage.getEpisode(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("created", matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$"));
    }

    @Test
    @Feature("Manejo de Errores")
    @Story("Episode")
    @Description("Verifica que la API responda con un esquema de error adecuado cuando se consulta un episodio inexistente.")
    public void testEpisodeErrorResponseSchema() {
        Response response = episodePage.getEpisode(9999);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("error", notNullValue())
                .body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    @Feature("Extras")
    @Story("Episode")
    @Description("Asegura que los episodios están ordenados por ID de forma ascendente.")
    public void testEpisodesOrder() {
        Response response = episodePage.getAllEpisodes();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("results.id",not(empty()));

        List<Integer> ids = response.jsonPath().getList("results.id", Integer.class);
        List<Integer> sortedIds = new ArrayList<>(ids);
        Collections.sort(sortedIds);

        // Compara la lista obtenida con la ordenada
        org.testng.Assert.assertEquals(ids, sortedIds, "Los IDs deben estar ordenados de forma ascendente");
    }

}