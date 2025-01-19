package com.alura.literalura.principal;

import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.DatosLibro;
import com.alura.literalura.model.DatosResultados;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Libro;
import com.alura.literalura.service.AutorService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibroService;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Principal {

    @Autowired
    private LibroService libroService;

    @Autowired
    private AutorService autorService;

    @Autowired
    private ConsumoAPI consumoAPI;

    @Autowired
    private ConvierteDatos convierteDatos;

    private static final String BASE_URL = "https://gutendex.com/books/";

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do{
            System.out.println("**********************************************************");
            System.out.println("¡Bienvenid@ a LiterAlura! Elije una opción válida:");
            System.out.println("1) Buscar libro por título");
            System.out.println("2) Listar libros registrados");
            System.out.println("3) Listar autores registrados");
            System.out.println("4) Listar autores vivos en un año determinado");
            System.out.println("5) Listar libros por idioma");
            System.out.println("0) Salir");
            System.out.println("**********************************************************");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch(opcion) {
                case 1:
                    System.out.println("Ingrese el nombre del libro que desea buscar: ");
                    String titulo = scanner.nextLine();
                    try {
                        String encondedTitulo = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                        String json = consumoAPI.obtenerDatos(BASE_URL + "?search=" + encondedTitulo);

                        DatosResultados datosResultados = convierteDatos.obtenerDatos(json, DatosResultados.class);
                        List<DatosLibro> datosLibros = datosResultados.getLibros();

                        if (datosLibros.isEmpty()) {
                            System.out.println("Libro no encontrado.\n");
                        } else {
                            DatosLibro primerDatosLibro = datosLibros.get(0);

                            Optional<Libro> libroExistente = libroService.obtenerLibroPorTitulo(primerDatosLibro.titulo());
                            if (libroExistente.isPresent()) {
                                System.out.println("No se puede registrar el mismo libro más de una vez.\n");
                            } else {
                                Libro libro = new Libro();
                                libro.setTitulo(primerDatosLibro.titulo());
                                libro.setIdioma(primerDatosLibro.idiomas().get(0));
                                libro.setNumeroDeDescargas(primerDatosLibro.numeroDeDescargas());

                                DatosAutor primerDatosAutor = primerDatosLibro.autores().get(0);
                                Autor autor = autorService.obtenerAutorPorNombre(primerDatosAutor.nombre())
                                        .orElseGet(() -> {
                                            Autor nuevoAutor = new Autor();
                                            nuevoAutor.setNombre(primerDatosAutor.nombre());
                                            nuevoAutor.setAnoDeNacimiento(primerDatosAutor.anoDeNacimiento());
                                            nuevoAutor.setAnoDeFallecimiento(primerDatosAutor.anoDeFallecimiento());
                                            return autorService.crearAutor(nuevoAutor);
                                        });

                                libro.setAutor(autor);

                                libroService.crearLibro(libro);
                                System.out.println("Libro registrado: '" + libro.getTitulo() + "'\n");
                                mostrarDetallesLibro(primerDatosLibro);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    libroService.listarLibros().forEach(libro -> {
                        System.out.println("---------------LIBRO---------------");
                        System.out.println("Título: " + libro.getTitulo());
                        System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
                        System.out.println("Idioma: " + libro.getIdioma());
                        System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                        System.out.println("------------------------------------\n");
                    });
                    break;
                case 3:
                    autorService.listarAutores().forEach(autor -> {
                        System.out.println("---------------AUTOR---------------");
                        System.out.println("Autor: " + autor.getNombre());
                        System.out.println("Fecha de nacimiento: " + (autor.getAnoDeNacimiento() != null ? autor.getAnoDeNacimiento() : "Desconocido"));
                        System.out.println("Fecha de fallecimiento: " + (autor.getAnoDeFallecimiento() != null ? autor.getAnoDeFallecimiento() : "Desconocido"));
                        String libros = autor.getLibros().stream()
                                .map(Libro::getTitulo)
                                .collect(Collectors.joining(", "));
                        System.out.println("Libros: [" + libros + "]");
                        System.out.println("------------------------------------\n");
                    });
                    break;
                case 4:
                    System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
                    int ano = scanner.nextInt();
                    scanner.nextLine();
                    List<Autor> autoresVivos = autorService.listarAutoresVivosEnAno(ano);
                    if (autoresVivos.isEmpty()) {
                        System.out.println("No se encontraron autores vivos en el año " + ano + ".\n");
                    } else {
                        autoresVivos.forEach(autor -> {
                            System.out.println("---------------AUTOR---------------");
                            System.out.println("Autor: " + autor.getNombre());
                            System.out.println("Fecha de nacimiento: " + (autor.getAnoDeNacimiento() != null ? autor.getAnoDeNacimiento() : "Desconocido"));
                            System.out.println("Fecha de fallecimiento: " + (autor.getAnoDeFallecimiento() != null ? autor.getAnoDeFallecimiento() : "Desconocido"));
                            String libros = autor.getLibros().stream()
                                    .map(Libro::getTitulo)
                                    .collect(Collectors.joining(", "));
                            System.out.println("Libros: [" + libros + "]");
                            System.out.println("------------------------------------\n");
                        });
                    }
                    break;
                case 5:
                    System.out.println("Ingrese un idioma válido:");
                    System.out.println("es - español");
                    System.out.println("en - inglés");
                    System.out.println("fr - francés");
                    System.out.println("pt - portugués");
                    String idioma = scanner.nextLine();
                    if ("es".equalsIgnoreCase(idioma) || "en".equalsIgnoreCase(idioma) || "fr".equalsIgnoreCase(idioma) || "pt".equalsIgnoreCase(idioma)) {
                        libroService.listarLibrosPorIdioma(idioma).forEach(libro -> {
                            System.out.println("---------------LIBRO---------------");
                            System.out.println("Título: " + libro.getTitulo());
                            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
                            System.out.println("Idioma: " + libro.getIdioma());
                            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                            System.out.println("------------------------------------\n");
                        });
                    } else {
                        System.out.println("Idioma no válido. Intente de nuevo.\n");
                    }
                    break;
                case 0:
                    System.out.println("¡Gracias por utilizar LiterAlura! ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.\n");
            }
        } while (opcion != 0);

        scanner.close();
    }

    private void mostrarDetallesLibro(DatosLibro datosLibro) {
        System.out.println("---------------LIBRO---------------");
        System.out.println("Título: " + datosLibro.titulo());
        System.out.println("Autor: " + (datosLibro.autores().isEmpty() ? "Desconocido" : datosLibro.autores().get(0).nombre()));
        System.out.println("Idioma: " + datosLibro.idiomas().get(0));
        System.out.println("Número de descargas: " + datosLibro.numeroDeDescargas());
        System.out.println("------------------------------------\n");
    }
}
