package principal;

import model.DadosEpisodio;
import model.DadosSerie;
import model.DadosTemporada;
import services.ConsumoAPI;
import services.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=c6c97426";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para pesquisa:");
        var nomeSerie = sc.nextLine();
        var json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

		// Temporadas
		List<DadosTemporada> temporadas = new ArrayList<>();
		for (int i=1; i<=dadosSerie.totalTemporadas(); i++){
            json = consumoAPI.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);


        /// Loop em todas episódios de todas temporadas
//        for (int i = 0; i < dadosSerie.totalTemporadas(); i++){
//            System.out.println("####### Temporada: " + temporadas.get(i).number());
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
//            for (DadosEpisodio dadosEpisodio : episodiosTemporada) {
//                System.out.println(dadosEpisodio.titulo());
//            }
//        }

        /// Fazendo os loops usando lambda
//        temporadas.forEach(t -> {
//            System.out.println("####### Temporada: " + t.number());
//            t.episodios().forEach(e -> System.out.println(e.titulo()));
//        });

        /// Colocando todos episódios de todas teporadas numa só lista

        List<DadosEpisodio> dadosEpisodios = temporadas
                .stream()
                .flatMap(t -> t.episodios().stream())
                //.toList()  o toList() retorna uma lista imutável, vc não pode adicionar outros itens na lista
                .collect(Collectors.toList()); // o collect retorna uma lista mutável

        System.out.println("\nTOP 5");
        dadosEpisodios
                .stream()
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed()) //ordena pela avaliacao desc
                .limit(5) // limita em 5 itens
                .forEach(System.out::println);

    }
}
