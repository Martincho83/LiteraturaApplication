package com.aluralatam.literalura;

import com.aluralatam.literalura.Model.Author;
import com.aluralatam.literalura.Model.Book;
import com.aluralatam.literalura.Model.BookData;
import com.aluralatam.literalura.Model.Results;
import com.aluralatam.literalura.Repository.AuthorRepository;
import com.aluralatam.literalura.Repository.BookRepository;
import com.aluralatam.literalura.Service.APIConsumer;
import com.aluralatam.literalura.Service.DataConvert;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    Scanner sc = new Scanner(System.in);
    APIConsumer consumoAPI = new APIConsumer();
    private final String URL_BASE = "http://gutendex.com/books/?search=";
    DataConvert convierteDatos = new DataConvert();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private List<Book> libros;
    private List<Author> autores;

    public Main(BookRepository libroRepository, AuthorRepository autorRepository) {
        this.bookRepository = libroRepository;
        this.authorRepository = autorRepository;
    }

    public void muestraElMenu() {
        boolean continuar =true;
        int option;
        while (continuar) {
                var menu = """
                        
                        ******** LITERALURA ********
                        
                        1 - Buscar Libro Por Título 
                        2 - Listar Libros Registrados
                        3 - Listar Autores Registrados
                        4 - Listar Autores Vivos En Un Determinado Año
                        5 - Listar Libros Por Idioma
                        0 - Salir 
                        """;
                System.out.print(menu);
                option = checkIntInput(sc, 0, 5, "Elija una opción válida. OPCIÓN:");

            switch (option) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:listarAutoresVivosEnUnDeterminadoAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    continuar=false;
                    break;
                default:
                    System.out.println("Opción inválida");
                    break;
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("\n --- OPCIÓN 1 : Buscar Libros Por Título ---");
        Results datosResults = getDatosLibro();
        if (datosResults != null) {
            Set<BookData> datosLibroList = datosResults.results();
            for (BookData datosLibro : datosLibroList) {
                Set<Author> autores = datosLibro.authors().stream()
                        .map(e -> {
                            Optional<Author> autor = Optional.ofNullable(authorRepository.findByName(e.getName()));
                            if (autor.isPresent()) {
                                return autor.get();
                            } else {
                                Author newAuthor =  new Author(e.getName(), e.getBirthYear(), e.getDeathYear(), "author") ;
                                authorRepository.save(newAuthor);
                                return newAuthor;
                            }
                        })
                        .collect(Collectors.toSet());
                Optional<Book> libro = Optional.ofNullable(bookRepository.findById(datosLibro.idLibro()));

                if (libro.isPresent()) {
                    System.out.println("LIBRO ENCONTRADO (PRESENTE TAMBIEN EN BASE DE DATOS LOCAL) ");
                    System.out.println(libro.get());
                } else {
                Book nuevolibro = new Book(datosLibro,autores);
                bookRepository.save(nuevolibro);
                System.out.println("Se guardò un nuevo libro: ");
                System.out.println(nuevolibro);
                }
            }
        } else {
            System.out.println("No se encontraron datos con los registros ingresados");
        }
    }

    private void listarLibrosRegistrados() {
        System.out.println("\n --- OPCIÓN 2 : Listar Libros Registrados ---");
        libros = bookRepository.findAll();
        if (!libros.isEmpty()) {
            libros.stream()
                    .sorted(Comparator.comparing(Book::getIdBook))
                    .forEach(System.out::println);
        } else {
            System.out.println("No hay libros registrados");
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n --- OPCIÓN 3 : Listar Autores Registrados ---");
        autores = authorRepository.findByTypeEqualsAuthor();
        if (!autores.isEmpty()) {
            autores.stream()
                    .sorted(Comparator.comparing(Author::getBirthYear))
                    .forEach(System.out::println);
        } else {
            System.out.println("No hay autores registrados");
        }
    }

    private void listarAutoresVivosEnUnDeterminadoAnio() {
        System.out.println("\n --- OPCIÓN 4 : Buscar Autores Vivos En Un Determinado Año ---");
        Integer livingYear = checkIntInput(sc,Integer.MIN_VALUE,Integer.MAX_VALUE, "Ingrese un año para buscar autores vivos en un determinado año: ");

        autores = authorRepository.findByLivingAuthorGivenYear(livingYear);
        if (!autores.isEmpty()) {
            autores.stream()
                    .sorted(Comparator.comparing(Author::getBirthYear))
                    .forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores vivos en el año: "+livingYear);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("\n --- OPCIÓN 5 : Listar Libros Por Idioma ---");
        System.out.println("Seleccione el idioma del libro, de los siguientes idiomas disponibles: ");
        List<String> languages = bookRepository.findAllLanguages();
        languages.stream().forEach(System.out::println);
        String idioma =  checkStringInput(sc,"Ingrese el idioma del libro: ");
        libros = bookRepository.findByLanguagesWhereOption(idioma);
        if (!libros.isEmpty()) {
            libros.stream()
                    .sorted(Comparator.comparing(Book::getIdBook))
                    .forEach(System.out::println);
        } else {
            System.out.println("No se encontraron libros con el idioma ingresado");
        }
    }

    private Results getDatosLibro() {
        String titulo = checkStringInput(sc,"Ingrese el titulo de libro: ");
        var json = consumoAPI.fetchData(URL_BASE+titulo.replace(" ","+"));
        return convierteDatos.getData(json,Results.class);
    }
    
    public static int checkIntInput(Scanner sc, int min, int max, String msg) {
        int input = 0;
        while (true) {
            System.out.println(msg);
            try {
                input = Integer.parseInt(sc.nextLine());
                if (input < min || input > max) continue;
                break;
            } catch (Exception e) {
                System.out.println("Ha introducido un valor no válido");
            }
        }
        return input;
    }
    
    public static String checkStringInput(Scanner sc, String msg) {
        String output;
        while (true) {
            System.out.println(msg);
            output = sc.nextLine();
            if (output.replace(" ", "").equalsIgnoreCase("")){
                System.out.println("No ha introducido nada!");
                continue;
             }
             return output;
        }
    }
}
