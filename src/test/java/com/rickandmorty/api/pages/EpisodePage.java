package com.rickandmorty.api.pages;

import io.restassured.response.Response;
import java.util.List;
import static io.restassured.RestAssured.given;

public class EpisodePage {
    private static final String BASE_URL = "https://rickandmortyapi.com/api";

    // Obtener un episodio por ID
    public Response getEpisode(int id) {
        return given()
                .when()
                .get(BASE_URL + "/episode/" + id);
    }

    // Obtener todos los episodios
    public Response getAllEpisodes() {
        return given()
                .when()
                .get(BASE_URL + "/episode");
    }

    // Obtener múltiples episodios por IDs
    public Response getMultipleEpisodes(List<Integer> ids) {
        String idsParam = ids.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + "," + b)
                .orElse("");
        return given()
                .when()
                .get(BASE_URL + "/episode/" + idsParam);
    }

    // Filtrar episodios por parámetros
    public Response filterEpisodes(String name, String episodeCode) {
        return given()
                .queryParam("name", name)
                .queryParam("episode", episodeCode)
                .when()
                .get(BASE_URL + "/episode/");
    }
}