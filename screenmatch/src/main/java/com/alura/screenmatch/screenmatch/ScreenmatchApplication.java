package com.alura.screenmatch.screenmatch;

import com.alura.screenmatch.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.screenmatch.model.DatosSeries;
import com.alura.screenmatch.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?i=tt3896198&apikey=c131c757");

		System.err.println(json);


		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSeries.class);

		System.err.println(datos);


		json = consumoAPI.obtenerDatos("http://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=c131c757");
		DatosEpisodio datosEpisodio = conversor.obtenerDatos(json, DatosEpisodio.class);

		System.out.println(datosEpisodio);
	}
}
