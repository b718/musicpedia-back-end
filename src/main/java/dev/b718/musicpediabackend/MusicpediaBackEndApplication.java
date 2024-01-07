package dev.b718.musicpediabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.b718.WikiPediaPortion.WikiPediaPortion;

@SpringBootApplication
public class MusicpediaBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicpediaBackEndApplication.class, args);
		System.out.println("Musicpedia Back End Application Started");

		WikiPediaPortion test = new WikiPediaPortion("Kanye West");
		test.getInformationOnArtist();

	}

}