package com.alura.literatura.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autores", joinColumns = @JoinColumn(name = "libro_id"), inverseJoinColumns = @JoinColumn(name = "autor_id"))
    private List<Autores> autores = new ArrayList<>();

    private String idioma;

    @Column(name = "numero_de_descargas")
    private Integer numeroDeDescargas;

    public Libros() {
        // Constructor por defecto
    }

    public Libros(Optional<DatosLibros> datos) {
        datos.ifPresent(datosLibros -> {
            this.titulo = datosLibros.titulo();
            this.idioma = datosLibros.idiomas().isEmpty() ? null : datosLibros.idiomas().get(0);
            this.numeroDeDescargas = datosLibros.numeroDeDescargas();
        });
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public List<Autores> getAutores() {
        return autores;
    }

    public void setAutores(List<Autores> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        String nombreAutor = autores.isEmpty() ? "Desconocido" : autores.get(0).getNombre();
        return String.format(
            "\n------- LIBRO --------\n Titulo: %s\n Autor: %s\n Idioma: %s\n Numero de descargas: %d\n",
            titulo, nombreAutor, idioma, numeroDeDescargas);
    }
}

