package com.rickandmorty.api.pages;

import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;

public class LocationPage {
    private static final String BASE_URL = "https://rickandmortyapi.com/api";

    // Obtener una ubicación por ID
    public Response getLocation(int id) {
        return given()
                .when()
                .get(BASE_URL + "/location/" + id);
    }

    // Obtener todas las ubicaciones
    public Response getAllLocations() {
        return given()
                .when()
                .get(BASE_URL + "/location");
    }

    // Obtener múltiples ubicaciones por IDs
    public Response getMultipleLocations(List<Integer> ids) {
        String idsParam = ids.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        return given()
                .when()
                .get(BASE_URL + "/location/" + idsParam);
    }

    // Filtrar ubicaciones por parámetros
    public Response filterLocations(String name, String type, String dimension) {
        return given()
                .queryParam("name", name)
                .queryParam("type", type)
                .queryParam("dimension", dimension)
                .when()
                .get(BASE_URL + "/location/");
    }
}