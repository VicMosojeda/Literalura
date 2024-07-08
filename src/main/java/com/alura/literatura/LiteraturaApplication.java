package com.alura.literatura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.alura.literatura.principal.Principal;
import com.alura.literatura.repository.AutoresRepository;
import com.alura.literatura.repository.LibrosRepository;

@SpringBootApplication
public class LiteraturaApplication implements CommandLineRunner {

  @Autowired
  private AutoresRepository autoresRepository;

  @Autowired
  private LibrosRepository librosRepository;
  
	public static void main(String[] args) {
		SpringApplication.run(LiteraturaApplication.class, args);
	}
  
  @Override
  public void run(String... args) throws Exception {
    Principal principal = new Principal(librosRepository, autoresRepository);
    principal.muestraElMenu();
  }

}
