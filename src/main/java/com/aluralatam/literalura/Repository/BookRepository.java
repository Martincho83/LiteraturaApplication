package com.aluralatam.literalura.Repository;

import com.aluralatam.literalura.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    Book findByTitle(String title);

    Book findById(Integer id);

    @Query("select distinct l.languages from Book l")
    List<String> findAllLanguages();

    @Query("select l from Book l where l.languages=:languages")
    List<Book> findByLanguagesWhereOption(String languages);
}
