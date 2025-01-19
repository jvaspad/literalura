package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anoDeNacimiento;
    private Integer anoDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libro> libros;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoDeNacimiento() {
        return anoDeNacimiento;
    }

    public void setAnoDeNacimiento(Integer anoDeNacimiento) {
        this.anoDeNacimiento = anoDeNacimiento;
    }

    public Integer getAnoDeFallecimiento() {
        return anoDeFallecimiento;
    }

    public void setAnoDeFallecimiento(Integer anoDeFallecimiento) {
        this.anoDeFallecimiento = anoDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
