package dev.b718.musicpediabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dev.b718.GeniusMusicInfo.GeniusMusicInfo;
import dev.b718.WikiPediaTexts.WikiPediaTexts;

@SpringBootApplication
public class MusicpediaBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicpediaBackEndApplication.class, args);
		System.out.println("Musicpedia Back End Application Started");

		WikiPediaTexts test = new WikiPediaTexts("ASAP Rocky");
		GeniusMusicInfo GMI = new GeniusMusicInfo("ASAP Rocky");
		System.out.println(test.getInformationOnArtist());
		System.out.println("");
		System.out.println(GMI.showArtistData());

	}

}
