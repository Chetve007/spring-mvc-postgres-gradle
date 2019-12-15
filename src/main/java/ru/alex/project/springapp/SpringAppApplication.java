package ru.alex.project.springapp;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class SpringAppApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpringAppApplication.class, args);

//		new SpringApplicationBuilder().bannerMode(Banner.Mode.OFF).sources(SpringAppApplication.class).main(SpringAppApplication.class).run(args);

		Banner banner = (environment, sourceClass, out) -> out.println("" +
				"I       IIIIII    XX     XX    IIIIII\n" +
				"I       I          XX   XX     I    I\n" +
				"I       IIII        XX XX      IIIIII\n" +
				"I       I           XX XX      I    I\n" +
				"IIIIII  IIIIII    XX     XX    I    I\n");

		new SpringApplicationBuilder()
				.banner(banner)
				.sources(SpringAppApplication.class)
				.main(SpringAppApplication.class)
				.run(args);
	}
}