package com.alura.screenmatch.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)//para ignorar lo que no se mapeó
public record DatosSeries(
        @JsonAlias("Title") String titulo,

        @JsonAlias("totalSeasons") Integer totalDeTemporadas,

        @JsonAlias("imdbRating") String evaluacion
) {
}
