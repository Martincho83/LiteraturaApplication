package com.aluralatam.literalura.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    private Integer idBook;

    private String title;
    private String subjects;
    private String bookshelves;
    private String languages;
    private Boolean copyright;
    private String media_type;
    private Integer download_count;

    //@ManyToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ManyToMany
    @JoinTable(
            name = "author_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;


    public Book(BookData bookData,Set<Author> authors) {
        this.idBook = bookData.idLibro();
        this.title = bookData.title().length() > 255 ? bookData.title().substring(0, 255) : bookData.title();
        this.subjects = !bookData.subjects().isEmpty() ? bookData.subjects().get(0) : "N/A  ";
        this.bookshelves = !bookData.bookshelves().isEmpty() ? bookData.bookshelves().get(0) : "N/A  ";
        this.languages = !bookData.languages().isEmpty() ? bookData.languages().get(0) : "N/A  ";
        this.copyright = bookData.copyright();
        this.authors = authors;
        this.media_type = bookData.media_type();
        this.download_count = bookData.download_count();

    }

    public Book() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdBook() {
        return idBook;
    }

    public void setIdBook(Integer idLibro) {
        this.idBook = idLibro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public Integer getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Integer download_count) {
        this.download_count = download_count;
    }

    public Set<Author> getAutores() {
        return authors ;
    }

    public void setAutores(HashSet<Author> autores) {
        this.authors  = authors;
    }

    @Override
    public String toString() {
        return  "IdBook=" + idBook +
                ", Titulo: '" + title + '\'' +
                ", Género: '" + subjects + '\'' +
                ", Colecciòn: '" + bookshelves + '\'' +
                ", Idioma= '" + languages + '\'' +
                ", Copyright: " + (copyright?"Tiene":"No Tiene") +
                ", Cantidad de Descargas: " + download_count;
    }



}
