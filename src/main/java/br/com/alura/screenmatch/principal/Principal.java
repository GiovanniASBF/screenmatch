package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.service.ApiConsumption;

import java.util.Scanner;

public class Principal {

    private Scanner scan = new Scanner(System.in);
    private final String ADRESS = "http://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=e4335e62";
    private ApiConsumption apiConsumption = new ApiConsumption();
    public void showMenu(){
        System.out.println("Enter the series name for the search: ");
        var serieName = scan.nextLine();

        var json = apiConsumption.obtainData(ADRESS + serieName.replaceAll(" ", "+") + API_KEY);
    }
}
