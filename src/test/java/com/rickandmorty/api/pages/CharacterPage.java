package com.rickandmorty.api.pages;

import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;


public class CharacterPage {
    private static final String BASE_URL = "https://rickandmortyapi.com/api";

    // Obtener un personaje por ID
    public Response getCharacter(int id) {
        return given()
                .when()
                .get(BASE_URL + "/character/" + id);
    }

    // Obtener todos los personajes
    public Response getAllCharacters() {
        return given()
                .when()
                .get(BASE_URL + "/character");
    }

    // Obtener múltiples personajes por IDs
    public Response getMultipleCharacters(List<Integer> ids) {
        String idsParam = ids.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        return given()
                .when()
                .get(BASE_URL + "/character/" + idsParam);
    }

    // Filtrar personajes por parámetros
    public Response filterCharacters(String name, String status, String species, String type, String gender) {
        return given()
                .queryParam("name", name)
                .queryParam("status", status)
                .queryParam("species", species)
                .queryParam("type", type)
                .queryParam("gender", gender)
                .when()
                .get(BASE_URL + "/character/");
    }
}
