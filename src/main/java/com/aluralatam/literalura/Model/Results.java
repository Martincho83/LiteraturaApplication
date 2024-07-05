package com.aluralatam.literalura.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Results(
        @JsonAlias("results") Set<BookData> results
) {
}
