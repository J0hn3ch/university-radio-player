package eu.universome;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Application.Parameters;
import javafx.application.HostServices;
import javafx.stage.Stage;

public class StageManager extends Application{
	
	protected ConfigurableApplicationContext applicationContext;

	@Override
    public void init() throws Exception {
		
		ApplicationContextInitializer<GenericApplicationContext> initializer = 
			new ApplicationContextInitializer<GenericApplicationContext>() {
            @Override
            public void initialize(GenericApplicationContext genericApplicationContext) {
                genericApplicationContext.registerBean(Application.class, () -> StageManager.this);
                genericApplicationContext.registerBean(Parameters.class, () -> getParameters());
                genericApplicationContext.registerBean(HostServices.class, () -> getHostServices());
            }
        };
		
		this.applicationContext = new SpringApplicationBuilder()
				.sources(AppMain.class)
				.initializers(initializer)
				.build()
				.run(getParameters().getRaw().toArray(new String[0]));
		
		//this.applicationContext = springBootApplicationContext();
    }
	
	// ==== Preloader ====
	// Splash Screen Video: https://www.youtube.com/watch?v=VrnujZPHtKk
	// Splash Screen same window: https://www.youtube.com/watch?v=muz6QLIgrC0
	// https://blog.codecentric.de/en/2015/09/javafx-how-to-easily-implement-application-preloader-2/
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.applicationContext.publishEvent(new StageReadyEvent(primaryStage));
	}
	
	@Override
	public void stop() throws Exception {
		this.applicationContext.close();
		Platform.exit();
	}
	

	/*
	@SuppressWarnings("serial")
	static class StageReadyEvent extends ApplicationEvent {
		
		public StageReadyEvent(Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			return ((Stage) getSource());
		}
	}
	
	private ConfigurableApplicationContext springBootApplicationContext() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(AppMain.class);
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);
        return builder.run(args);
    }*/
}

// StageReadyEvent Class
class StageReadyEvent extends ApplicationEvent {
	
	public Stage getStage () {
		return Stage.class.cast(getSource());
	}
	
	public StageReadyEvent(Object source) {
		super(source);
	}
}
