package com.rickandmorty.api.models;

import lombok.Data;

@Data
public class Episode {
    private int id;
    private String name;
    private String air_date;
    private String episode;
    private String[] characters; // Lista de URLs de personajes
    private String url; // URL del episodio
    private String created; // Fecha de creación
}
