package br.com.alura.screenmatch;

import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumption;
import br.com.alura.screenmatch.service.DataConvert;
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
		ApiConsumption apiConsumption = new ApiConsumption();
		var json = apiConsumption.obtainData("http://www.omdbapi.com/?t=house+of+the+dragon&apikey=e4335e62");
		System.out.println(json);

		DataConvert converter = new DataConvert();
		//Delivering serie data
		SeriesData data = converter.getData(json, SeriesData.class);
		System.out.println(data);

		//Delivering episode data
		json = apiConsumption.obtainData("http://www.omdbapi.com/?t=house+of+the+dragon&season=1&episode=4&apikey=e4335e62");
		EpisodeData episodeData = converter.getData(json, EpisodeData.class);
		System.out.println(episodeData);

		//Delivering all seasons data
		List<SeasonData> seasons = new ArrayList<>();
		for (int i = 1; i <= data.totalSeasons() ; i++) {
			json = apiConsumption.obtainData("http://www.omdbapi.com/?t=house+of+the+dragon&season=" + i +"&apikey=e4335e62");
			SeasonData seasonData = converter.getData(json, SeasonData.class);
			seasons.add(seasonData);
		}
		seasons.forEach(System.out::println);
	}
}
