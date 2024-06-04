package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ApiConsumption;
import br.com.alura.screenmatch.service.DataConvert;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private Scanner scan = new Scanner(System.in);
    private ApiConsumption apiConsumption = new ApiConsumption();
    private DataConvert converter = new DataConvert();

    private final String ADRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e4335e62";

    public void showMenu(){
        System.out.println("Enter the series name for the search: ");
        var serieName = scan.nextLine();
        var json = apiConsumption.obtainData(ADRESS + serieName.replaceAll(" ", "+") + API_KEY);

        //Delivering serie data
        SeriesData data = converter.getData(json, SeriesData.class);
        System.out.println(data);

        //Delivering all seasons data
        List<SeasonData> seasons = new ArrayList<>();
        for (int i = 1; i <= data.totalSeasons() ; i++) {
            json = apiConsumption.obtainData(ADRESS + serieName.replaceAll(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = converter.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);

        /*
        for (int i = 0; i < data.totalSeasons(); i++) {
            List<EpisodeData> seasonEpisodes = seasons.get(i).episodes();
            for (int j = 0; j < seasonEpisodes.size(); j++) {
                System.out.println(seasonEpisodes.get(j).title());
            }
        }
        */

        //Iterating through a list within another list using lambda
        seasons.forEach(seasonData -> seasonData.episodes().forEach(episodeData -> System.out.println(episodeData.title())));

        // Using stream to return the top 5 rated episodes of each series
        List<EpisodeData> episodesData = seasons.stream()
                .flatMap(seasonData -> seasonData.episodes().stream())
                .collect(Collectors.toList());
        System.out.println("\nTop 5 episodes");
        episodesData.stream()
                .filter(episodeData -> !episodeData.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(EpisodeData::rating).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
}
