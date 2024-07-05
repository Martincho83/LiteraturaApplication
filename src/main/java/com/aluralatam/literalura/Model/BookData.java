package com.aluralatam.literalura.Model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
    public record BookData(
        @JsonAlias("id") Integer idLibro,
        @JsonAlias("title") String title,
        @JsonAlias("authors") Set<Author> authors,
        @JsonAlias("translators") List<TranslatorData> translators,
        @JsonAlias("subjects") List<String> subjects,
        @JsonAlias("bookshelves") List<String> bookshelves,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("copyright") Boolean copyright,
        @JsonAlias("media_type") String media_type,
        @JsonAlias("download_count") Integer download_count
    ){


}
