package com.alura.screenmatch.screenmatch.principal;

import com.alura.screenmatch.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.screenmatch.model.DatosSeries;
import com.alura.screenmatch.screenmatch.model.DatosTemporadas;
import com.alura.screenmatch.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.screenmatch.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=c131c757";


    public void muestrMenu() {
        System.out.println("por favor escribe el nombre de la serie");
        var serie = teclado.nextLine();

        //datos generales de la serie
        var json = consumoAPI.obtenerDatos(URL_BASE + serie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSeries.class);
        System.out.println(datos);
        //busca los datos de todas las temporadas
        List<DatosTemporadas> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(URL_BASE + serie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
            temporadas.add(datosTemporada);
        }

    //temporadas.forEach(System.out::println);

        //Mostrar solo el titulo de las temporadas
//        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
//            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //(argumentos) -> { cuerpo-de-la-función }
        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));//lambda

        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //top 5 episodios
        datosEpisodios.stream()
                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
}
