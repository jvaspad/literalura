package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosResultados {

    @JsonProperty("results")
    private List<DatosLibro> libros;

    public List<DatosLibro> getLibros() {
        return libros;
    }

    public void setLibros(List<DatosLibro> libros) {
        this.libros = libros;
    }
}
