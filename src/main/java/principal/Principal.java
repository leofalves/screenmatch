package principal;

import model.DadosEpisodio;
import model.DadosSerie;
import model.DadosTemporada;
import services.ConsumoAPI;
import services.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        temporadas.forEach(t -> {
            System.out.println("####### Temporada: " + t.number());
            t.episodios().forEach(e -> System.out.println(e.titulo()));
        });

    }
}
