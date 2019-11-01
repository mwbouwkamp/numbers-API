package nl.limakajo.numbers.RABO.API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "nl.limakajo.numbers.RABO.API")
public class NumbersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumbersApiApplication.class, args);
	}

}
