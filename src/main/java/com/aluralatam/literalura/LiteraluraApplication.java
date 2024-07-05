package com.aluralatam.literalura;

import com.aluralatam.literalura.Repository.AuthorRepository;
import com.aluralatam.literalura.Repository.BookRepository;
import com.aluralatam.literalura.Service.APIConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private BookRepository libroRepository;
	@Autowired
	private AuthorRepository autorRepository;

	@Override
	public void run(String... args) throws Exception {
		Main m = new Main(libroRepository,autorRepository);
		m.muestraElMenu();
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}
}
