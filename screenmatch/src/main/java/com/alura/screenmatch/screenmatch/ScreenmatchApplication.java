package com.alura.screenmatch.screenmatch;

import com.alura.screenmatch.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.screenmatch.model.DatosSeries;
import com.alura.screenmatch.screenmatch.model.DatosTemporadas;
import com.alura.screenmatch.screenmatch.principal.Principal;
import com.alura.screenmatch.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var principal = new Principal();

		principal.muestrMenu();
	}
}
