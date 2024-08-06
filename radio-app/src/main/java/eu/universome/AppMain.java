package eu.universome;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;

@SpringBootApplication
//@ComponentScan({"eu.universome.controller"})
public class AppMain {
	public static void main(final String[] args) {
		// SpringApplication.run(AppMain.class, args);
        Application.launch(StageManager.class, args);
    }
}
















