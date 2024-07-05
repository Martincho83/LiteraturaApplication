
package com.aluralatam.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "authors", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonAlias("birth_year")
    private Integer birthYear;
    @JsonAlias("death_year")
    private Integer deathYear;
    private String type;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> book;

    public Author() {}

    public Author(String name, Integer birthYear, Integer deathYear, String type) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return name + ", Año de Nacimiento: " + birthYear + ", Año de Muerte: " + deathYear;
    }
}