package com.alura.literatura.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.*;

@Entity
@Table(name = "autores", uniqueConstraints = @UniqueConstraint(columnNames = { "nombre", "fecha_nacimiento", "fecha_fallecimiento" }))
public class Autores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(name = "fecha_nacimiento")
    private Integer fechaNacimiento;

    @Column(name = "fecha_fallecimiento")
    private Integer fechaFallecimiento;

    @ManyToMany(mappedBy = "autores", fetch = FetchType.EAGER)
    private List<Libros> libros = new ArrayList<>();

    public Autores() {
    }

    public Autores(String nombre, Integer fechaNacimiento, Integer fechaFallecimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        // Uniendo los títulos de los libros en una sola cadena
        String titulosLibros = libros.stream()
                .map(Libros::getTitulo)
                .collect(Collectors.joining(", "));

        // Formateando la cadena de texto usando String.format()
        return String.format(
            "\n--- AUTOR ---\nNombre: %s\nFecha de nacimiento: %d\nFecha de fallecimiento: %d\nLibros: %s\n----------------\n",
            nombre, fechaNacimiento, fechaFallecimiento, titulosLibros);
    }
}

