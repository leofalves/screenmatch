package io.leofalves.screenmatch;

import model.DadosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import services.ConsumoAPI;
import services.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {

		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//String url = "https://www.omdbapi.com/?t=gilmore+girls&season=1&apikey=c6c97426";
		String url = "https://www.omdbapi.com/?t=gilmore+girls&apikey=c6c97426";
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados(url);
		System.out.println(json);

		ConverteDados conversor = new ConverteDados();
		DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
		System.out.println(dadosSerie);

		//json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");
		//System.out.println(json);
	}
}