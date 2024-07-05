package com.aluralatam.literalura.Repository;

import com.aluralatam.literalura.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);




    @Query("select a from Author a WHERE a.type = 'author'")
    List<Author> findByTypeEqualsAuthor();

    @Query("select a from Author a WHERE a.birthYear<:livingYear AND a.deathYear>:livingYear AND a.type='author'")
    List<Author> findByLivingAuthorGivenYear(Integer livingYear);
}
