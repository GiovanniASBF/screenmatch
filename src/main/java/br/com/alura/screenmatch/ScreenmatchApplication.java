package br.com.alura.screenmatch;

import br.com.alura.screenmatch.service.ApiConsumption;
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
		ApiConsumption apiConsumption = new ApiConsumption();
		var json = apiConsumption.obtainData("http://www.omdbapi.com/?t=house+of+the+dragon&Season=1&apikey=e4335e62");
		System.out.println(json);
	}
}
