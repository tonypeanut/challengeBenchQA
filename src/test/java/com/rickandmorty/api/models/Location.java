package com.rickandmorty.api.models;

import lombok.Data;

@Data
public class Location {
    private int id;
    private String name;
    private String type;
    private String dimension;
    private String[] residents; // Lista de URLs de residentes
    private String url; // URL de la ubicación
    private String created; // Fecha de creación
}
