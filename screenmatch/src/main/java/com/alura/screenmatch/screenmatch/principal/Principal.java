package com.alura.screenmatch.screenmatch.principal;

import com.alura.screenmatch.screenmatch.model.DatosEpisodio;
import com.alura.screenmatch.screenmatch.model.DatosSeries;
import com.alura.screenmatch.screenmatch.model.DatosTemporadas;
import com.alura.screenmatch.screenmatch.model.Episodio;
import com.alura.screenmatch.screenmatch.service.ConsumoAPI;
import com.alura.screenmatch.screenmatch.service.ConvierteDatos;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
                .peek(e -> System.out.println("Primer filtro NA " + e))
                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                .peek(e -> System.out.println("Segundo ordenacion M>m"))
                .map(e -> e.titulo().toUpperCase())
                .limit(5)
                .peek(e -> System.out.println("Limit"))
                .forEach(System.out::println);

        //comvitiendo lista a tipo episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream().
                        map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

        //episodios.forEach(System.out::println);

        //busqueda de episodios a partir de año x
        System.out.println("Ingresa el Año en que se transmitió el episodio");
        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//        episodios.stream()
//                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada " + e.getTemporada() +
//                                "Episodio " + e.getTitulo() +
//                                "Fecha Lanzamiento " + dtf.format(e.getFechaLanzamiento())
//                ));
    }
}