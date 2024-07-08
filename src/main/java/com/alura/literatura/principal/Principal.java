package com.alura.literatura.principal;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.alura.literatura.model.ApiResponse;
import com.alura.literatura.model.Autores;
import com.alura.literatura.model.DatosAutores;
import com.alura.literatura.model.DatosLibros;
import com.alura.literatura.model.Libros;
import com.alura.literatura.repository.AutoresRepository;
import com.alura.literatura.repository.LibrosRepository;
import com.alura.literatura.service.ConsumoApi;
import com.alura.literatura.service.ConvierteDatos;

public class Principal {
    private final Scanner input = new Scanner(System.in);
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final String URL_BASE = "https://gutendex.com/books/";
    private final ConvierteDatos convierteDatos = new ConvierteDatos();
    private final LibrosRepository librosRepository;
    private final AutoresRepository autoresRepository;

    public Principal(LibrosRepository librosRepository, AutoresRepository autoresRepository) {
        this.librosRepository = librosRepository;
        this.autoresRepository = autoresRepository;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    --------------------------------
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma

                    0 - Salir
                    """);
            opcion = input.nextInt();
            input.nextLine(); // Limpiar el buffer del scanner
            switch (opcion) {
                case 1:
                    buscarYGuardarLibros();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorFecha();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    private Optional<DatosLibros> consultarDatosLibro() {
        System.out.println("\nEscribe el titulo del libro que deseas buscar");
        String tituloLibro = input.nextLine();
        String json = consumoApi.obtenerDatosDeApi(URL_BASE + "?search=" + tituloLibro.toLowerCase().replace(" ", "+"));
        ApiResponse datos = convierteDatos.obtenerDatos(json, ApiResponse.class);
        return datos.libros().stream()
                .findFirst()
                .map(l -> new DatosLibros(l.titulo(),
                        l.autores().stream()
                                .map(a -> new DatosAutores(a.nombre(), a.fechaNacimiento(), a.fechaFallecimiento()))
                                .collect(Collectors.toList()),
                        l.idiomas(), l.numeroDeDescargas()));
    }

    private void buscarYGuardarLibros() {
        Optional<DatosLibros> datos = consultarDatosLibro();
        if (datos.isPresent()) {
            Libros libro = new Libros(datos);
            Libros tituloLibro = librosRepository.findByTitulo(libro.getTitulo());
            if (tituloLibro != null) {
                System.out.println("-------------------\nEl libro ya está registrado y no se puede volver a registrar");
            } else {
                List<Autores> autoresList = datos.get().autores().stream()
                        .map(this::buscarOGuardarAutor)
                        .collect(Collectors.toList());

                libro.setAutores(autoresList);
                System.out.println(libro);
                librosRepository.save(libro);
            }
        } else {
            System.out.println("----------------\nNo se encontraron datos para el libro especificado.");
        }
    }

    private Autores buscarOGuardarAutor(DatosAutores datosAutores) {
        Optional<Autores> autorExistente = autoresRepository.findByNombreAndFechaNacimientoAndFechaFallecimiento(
                datosAutores.nombre(), datosAutores.fechaNacimiento(), datosAutores.fechaFallecimiento());
        return autorExistente.orElseGet(() -> autoresRepository.save(new Autores(datosAutores.nombre(),
                datosAutores.fechaNacimiento(), datosAutores.fechaFallecimiento())));
    }

    public void listarLibrosRegistrados() {
        List<Libros> libros = librosRepository.findAll();
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("-------------\nNo hay libros registrados");
        }
    }

    public void listarAutoresRegistrados() {
        List<Autores> autores = autoresRepository.findAll();
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("-------------\nNo hay autores registrados");
        }
    }

    public void listarAutoresVivosPorFecha() {
        System.out.println("Ingrese el año de nacimiento del autor que desea buscar");
        int fecha = input.nextInt();
        List<Autores> autores = autoresRepository.buscarAutoresVivosPorAnio(fecha);
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("------------------\nNo hay autores registrados nacidos en ese año");
        }
    }

    public void listarLibrosPorIdioma() {
        System.out.println("""
                Ingrese el idioma del libro que desea buscar:
                es - Español
                en - Inglés
                jp - Japonés
                fr - Francés
                pt - Portugués
                """);
        String idioma = input.nextLine();
        List<Libros> libros = librosRepository.buscarLibrosPorIdioma(idioma);
        if (!libros.isEmpty()) {
            libros.forEach(System.out::println);
        } else {
            System.out.println("---------------------------------\nNo hay libros registrados en ese idioma");
        }
    }
}

