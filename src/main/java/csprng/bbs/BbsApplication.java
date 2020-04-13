package csprng.bbs;

import csprng.bbs.services.BBSAlgorithm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class BbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbsApplication.class, args);

		BBSAlgorithm bbsAlgorithm = new BBSAlgorithm();
		bbsAlgorithm.calculate();
		File f = new File("a.txt");

	}


}
