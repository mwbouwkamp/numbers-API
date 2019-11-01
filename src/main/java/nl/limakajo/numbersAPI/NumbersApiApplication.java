package nl.limakajo.numbersAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "nl.limakajo.numbersAPI")
public class NumbersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NumbersApiApplication.class, args);
	}

}
