package com.rickandmorty.api.tests;

import com.rickandmorty.api.pages.CharacterPage;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharacterTests {
    private CharacterPage characterPage = new CharacterPage();

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que se puede obtener un personaje por ID y que los datos sean correctos.")
    public void testGetCharacterById() {
        Response response = characterPage.getCharacter(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("name", equalTo("Rick Sanchez"))
                .body("status", equalTo("Alive"))
                .body("species", equalTo("Human"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que se pueden obtener todos los personajes y que la respuesta contiene datos.")
    public void testGetAllCharacters() {
        Response response = characterPage.getAllCharacters();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("info.count", notNullValue())
                .body("results", notNullValue());
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que se pueden obtener múltiples personajes por ID.")
    public void testGetMultipleCharacters() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        Response response = characterPage.getMultipleCharacters(ids);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("size()", equalTo(3))
                .body("[0].name", equalTo("Rick Sanchez"))
                .body("[1].name", equalTo("Morty Smith"))
                .body("[2].name", equalTo("Summer Smith"));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que el filtro de personajes devuelve los resultados esperados.")
    public void testFilterCharacters() {
        Response response = characterPage.filterCharacters("Rick", "Alive", "Human", "", "Male");

        response.then()
                .assertThat()
                .statusCode(200)
                .body("results.name", hasItem("Rick Sanchez"));
    }

    @Test
    @Feature("Extras")
    @Story("Character")
    @Description("Verifica que los headers de la respuesta sean los esperados.")
    public void testCharacterResponseHeaders() {
        Response response = characterPage.getCharacter(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");
    }

    @Test
    @Feature("Manejo de Errores")
    @Story("Character")
    @Description("Verifica que un ID de personaje inválido devuelve un error 404.")
    public void testInvalidCharacterId() {
        Response response = characterPage.getCharacter(9999);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("Character not found"));
    }

    @Test
    @Feature("Validación de Esquema JSON")
    @Story("Character")
    @Description("Verifica que la respuesta del endpoint cumple con el esquema JSON esperado.")
    public void testCharacterJsonSchema() {
        Response response = characterPage.getCharacter(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("character-schema.json"))
                .body("id", equalTo(1));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que ciertos campos clave están presentes en la respuesta del personaje.")
    public void testCharacterFields() {
        Response response = characterPage.getCharacter(1);

        response.then()
                .assertThat()
                .statusCode(200)
                .body("url", notNullValue())
                .body("created", notNullValue())
                .body("episode", notNullValue());
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que el primer y el último personaje de la API existan y sean accesibles.")
    public void testFirstAndLastCharacterId() {
        Response firstCharacter = characterPage.getCharacter(1);
        Response lastCharacter = characterPage.getCharacter(826);

        firstCharacter.then()
                .assertThat()
                .statusCode(200);

        lastCharacter.then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que la información de paginación es correcta.")
    public void testCharactersPaginationInfo() {
        Response response = characterPage.getAllCharacters();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("info.count", greaterThan(0))
                .body("info.pages", greaterThan(0))
                .body("info.next", notNullValue())
                .body("info.prev", nullValue()); // En la primera página, prev debe ser nulo
    }

    @Test
    @Feature("Pruebas de Rendimiento")
    @Story("Character")
    @Description("Verifica que el tiempo de respuesta es menor a 2000 ms.")
    public void testResponseTime() {
        Response response = characterPage.getCharacter(1);

        response.then()
                .assertThat()
                .time(lessThan(2000L));
    }

    @Test
    @Feature("Pruebas Funcionales")
    @Story("Character")
    @Description("Verifica que al aplicar un filtro sin coincidencias, la API devuelve un resultado vacío o un error 404.")
    public void testFilterCharactersNoResults() {
        Response response = characterPage.filterCharacters("XYZ123", "", "", "", "");

        response.then()
                .assertThat()
                .statusCode(anyOf(is(200), is(404)))
                .body("info.count", response.getStatusCode() == 200 ? equalTo(0) : anything())
                .body("error", response.getStatusCode() == 404 ? not(emptyOrNullString()) : anything());
    }

    @Test
    @Feature("Validación de Esquema JSON")
    @Story("Character")
    @Description("Verifica que el campo 'created' tiene el formato ISO 8601 correcto.")
    public void testCreatedFieldFormatForCharacter() {
        Response response = characterPage.getCharacter(1);

        response.then()
                .assertThat()
                .body("created", matchesPattern("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z$"));
    }

    @Test
    @Feature("Manejo de Errores")
    @Story("Character")
    @Description("Valida que la API responda con un esquema de error adecuado cuando se consulta un personaje inexistente.")
    public void testCharacterErrorResponseSchema() {
        Response response = characterPage.getCharacter(9999);

        response.then()
                .assertThat()
                .statusCode(404)
                .body("error", notNullValue())
                .body(matchesJsonSchemaInClasspath("error-schema.json"));
    }

    @Test
    @Feature("Extras")
    @Story("Character")
    @Description("Verifica que los personajes están ordenados por ID ascendente.")
    public void testCharactersOrder() {
        Response response = characterPage.getAllCharacters();

        response.then()
                .assertThat()
                .statusCode(200)
                .body("results.id", not(empty())); // Asegura que la lista no esté vacía

        List<Integer> ids = response.jsonPath().getList("results.id", Integer.class);
        List<Integer> sortedIds = new ArrayList<>(ids);
        Collections.sort(sortedIds);

        // Compara la lista obtenida con la ordenada
        org.testng.Assert.assertEquals(ids, sortedIds, "Los IDs deben estar ordenados de forma ascendente");
    }
}
