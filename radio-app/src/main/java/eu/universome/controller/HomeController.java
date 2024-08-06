package eu.universome.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

//import com.gluonhq.charm.glisten.control.BottomNavigationButton;

import eu.universome.StageListener;
import eu.universome.client.SpreakerClient;
import eu.universome.client.UvmClient;
import eu.universome.models.Episode;
import eu.universome.models.Music;
import eu.universome.models.Program;
import eu.universome.models.UniversityAds;
import eu.universome.models.User;
import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Component
public class HomeController implements Initializable {

	@FXML
	private AnchorPane rootHome;
	
	@FXML
    private VBox mainContent;
	
	@FXML
	private Hyperlink loginLink;
	
	@FXML
	private Label sectionLabel;
	
	@FXML
	private Button actionBtn;
	
	@FXML
    private ScrollPane scroll;
    
    @FXML
    private Button programBtn;
    
    @FXML
    private Button liveBtn;

    @FXML
    private Button uadsBtn;

    @FXML
    private Button musicBtn;
    
    @Lazy
    @Autowired
    private StageListener stageListener;
    
    private final HostServices hostServices;
    
    // User
  	private User user = null;
    
    // Listing programs, episodes, unime advices, music
 	private List<Program> programs = new ArrayList<>();
 	private List<Episode> episodes = new ArrayList<>();
 	private List<UniversityAds> universityAds = new ArrayList<>();
 	private List<Music> music = new ArrayList<>();
 	
 	// Clients
 	private WebClient webClient1, webClient2, webClient3;
 	private SpreakerClient spreakerClient;
 	private UvmClient uvmClient;
 	
 	// Constructor
    public HomeController(HostServices hostServices) 
    {
    	this.hostServices = hostServices;
    	this.spreakerClient = new SpreakerClient();
    	this.uvmClient = new UvmClient();
    	
    	//System.out.println(this.hostServices.getDocumentBase());
    	// Other clients
    	// Web Client
    	webClient1 = WebClient.create("http://localhost:8080");
    	
    	// Web Client Builder
    	webClient2 = WebClient.builder()
    			.baseUrl("http://localhost:8080/api/v1")
    			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    			.build();
    	
    	// Spreaker Web Client
    	webClient3 = WebClient.builder()
    			.baseUrl("https://api.spreaker.com/v2")
    			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    			.build();
    	
    	// https://howtodoinjava.com/spring-webflux/webclient-get-post-example/
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	// User Login
    	// https://stackoverflow.com/questions/59198935/using-javafx-client-with-spring-boot-and-spring-security
    	
    	// Get current Window
    	try {
			homeInitialization();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    }
    
    // - Open Login Stage
    // - https://stackoverflow.com/questions/30464238/javafx-getscene-returns-null
    @FXML
    public void openUserLogin(ActionEvent event) {
    	
    	Stage popUpStage = new Stage();

    	// Before open a new Stage, blur the first one
    	
        // Here add the FXML content to the Scene
        try {
        	ResourceLoader resourceLoader = new DefaultResourceLoader();
        	Resource loginResource = resourceLoader.getResource("classpath:view/login2.fxml");
            FXMLLoader loader = new FXMLLoader(loginResource.getURL());
            AnchorPane root = (AnchorPane) loader.load();
            LoginController2 controller = loader.getController();
            Scene popUpScene = new Scene(root);
            popUpStage.setScene(popUpScene);
            
            Window window = ((Hyperlink)event.getTarget()).getScene().getWindow();
            
            // Calculate the center position of the parent Stage
            double centerXPosition = window.getX() + window.getWidth()/2d;
            double centerYPosition = window.getY() + window.getHeight()/2d;

            // Hide the pop-up stage before it is shown and becomes relocated
            popUpStage.setOnShowing(ev -> popUpStage.hide());

            // Relocate the pop-up Stage
            
            popUpStage.setOnShown(ev -> {
            	popUpStage.setX(centerXPosition - popUpStage.getWidth()/2d);
                popUpStage.setY(centerYPosition - popUpStage.getHeight()/2d);
                popUpStage.show();
            });

            popUpStage.showAndWait();
            
            // - https://stackoverflow.com/questions/40676656/how-to-return-value-from-a-stage-before-closing-it
            String u = controller.getUser();
            //String u = controller.user.getRole();
            dashboardLoad(u);
            
        } catch (Exception except) {
        	except.printStackTrace();
        }
    }
    
    // Login Checker
    private void dashboardLoad(String user) throws JsonProcessingException, URISyntaxException {
    	
    	// Check if User attribute of this controller is set
    	
    	if (user.equals("ANON")) { // Load Anonymous Dashboard
        	System.out.println("Load Anonymous Dashboard");
        	if (actionBtn.isVisible())
        		actionBtn.setVisible(false);
        	homeInitialization();
        	// Disable all add button and login link
        }
        else if (user.equals("LISTENER")) { // Load Listener Dashboard
        	System.out.println("Load Listener Dashboard");
        	
        	// Enable section Playlist
        	if (!actionBtn.isVisible())
        		actionBtn.setVisible(true);
        	actionBtn.setText("Playlist");
        	homeInitialization();
        	// Load User logo container
        }
        else if (user.equals("SPEAKER")) { // Load Speaker Dashboard
        	System.out.println("Load Speaker Dashboard");
        	
        	// Enable Button add content
        	if (!actionBtn.isVisible())
        		actionBtn.setVisible(true);
        	actionBtn.setText("Add program");
        	this.user = new User();
        	this.user.setRole("SPEAKER");
        	homeInitialization();
        	// Enable Button edit content
        	// Load Speaker logo container
        }
        else {
        	System.out.println("ERROR");
        	System.exit(1);
        }

    }
    
    // ==== Content Getter Methods ====
	/* Function getEpisodes: Not used, WebClient3 getEpisode is used instead, this is for localhost formatted JSON
	private List<Episode> getEpisodes(Long programId) {
		
		List<Episode> episode_list = new ArrayList<>();
		Episode episode;
		
		WebClient.ResponseSpec response = 
				this.webClient2
					.get()
					.uri("/episode")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve();
		
		// If the JSON is like [ { ... } ], use bodyToFlux, else if is like { ... }
		Flux<Episode> episode_flux = response.bodyToFlux(Episode.class);
		System.out.println("[ GET Request ]:" + episode_flux.blockFirst());
		Iterator<Episode> episode_it = episode_flux.toIterable().iterator();
		
		while (episode_it.hasNext()) {
			episode = episode_it.next();
			episode_list.add(episode);
		}

		//Mono<Episode> episode_mono = response.bodyToMono(Episode.class);
		return episode_list;
	}*/
	
	private List<UniversityAds> getUAds() {
		List<UniversityAds> uadsList = new ArrayList<>();
 		UniversityAds uAds;
		
		WebClient.ResponseSpec response = 
				this.webClient2
					.get()
					.uri("/uads")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve();
		
		// If the JSON is like [ { ... } ], use bodyToFlux, else if is like { ... }
		Flux<UniversityAds> uads_flux = response.bodyToFlux(UniversityAds.class);
		System.out.println("[ GET Request ]:" + uads_flux.blockFirst());
		Iterator<UniversityAds> uads_it = uads_flux.toIterable().iterator();
		
		while (uads_it.hasNext()) {
			uAds = uads_it.next();
			uadsList.add(uAds);
		}

		//Mono<Episode> episode_mono = response.bodyToMono(Episode.class);
		return uadsList;
	}
	
	private void homeInitialization() throws URISyntaxException, JsonProcessingException {
		// Clean active players
		loadPrograms(null);		
    }
    
    @FXML
    private void loadPrograms(ActionEvent event) throws URISyntaxException, JsonProcessingException {  
    	
    	// Titolo sezione e funzionalita'
    	// Title
    	sectionLabel.setText("Programmi");
    	
    	// Add Button
    	if (user != null && user.getRole().equals("SPEAKER")) {
    		actionBtn.setText("Add program");
    		actionBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					// Creo lo stage
	    			Stage popUpStage = new Stage(); 
	    			
	    			// Creazione componente per il loading del file FXML
	    			ResourceLoader resourceLoader = new DefaultResourceLoader();
	            	Resource addResource = resourceLoader.getResource("classpath:view/addItem.fxml");
	                FXMLLoader loader = null;
					try {
						loader = new FXMLLoader(addResource.getURL());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// Caricamento della schermata nel Pannello
	                AnchorPane root = null;
					try {
						root = (AnchorPane) loader.load();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                
	                // Caricamento del Controller
	                AddItemController controller = loader.getController();
	                
	                
	                Scene popUpScene = new Scene(root);
	                popUpStage.setScene(popUpScene);
	                
	                Window window = ((Button)event.getTarget()).getScene().getWindow();
	                
	                // Calculate the center position of the parent Stage
	                double centerXPosition = window.getX() + window.getWidth()/2d;
	                double centerYPosition = window.getY() + window.getHeight()/2d;

	                // Hide the pop-up stage before it is shown and becomes relocated
	                popUpStage.setOnShowing(ev -> popUpStage.hide());

	                // Relocate the pop-up Stage
	                
	                popUpStage.setOnShown(ev -> {
	                	popUpStage.setX(centerXPosition - popUpStage.getWidth()/2d);
	                    popUpStage.setY(centerYPosition - popUpStage.getHeight()/2d);
	                    popUpStage.show();
	                });

	                popUpStage.showAndWait();
				}
    		});
    	}
    	
    	// Prepare VBox
    	HBox programLayout = new HBox();
    	programLayout.setId("programLayout");
    	programLayout.setPrefWidth(790.0);
    	programLayout.setPrefHeight(275.0);
    	programLayout.setSpacing(10.0);
    	programLayout.setPadding(new Insets(5, 0, 5, 0));
    	
    	// Substitute the content of the ScrollPane
    	// From HBox to VBox
    	// https://openjfx.io/javadoc/12/javafx.controls/javafx/scene/control/ScrollPane.html
    	scroll.setContent(programLayout);
		
    	this.programs.clear();
    	this.programs.addAll( uvmClient.getPrograms() );
    	try {
    		for (int i = 0; i < programs.size(); i++) {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/program.fxml"));
				AnchorPane rootProgram = fxmlLoader.load();
				
				ProgramController programController = fxmlLoader.getController();
				programController.setHomeController(this);
	    		programController.setData(programs.get(i));
	    		
	    		programLayout.getChildren().add(rootProgram);
	    	}
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	System.out.println("[Initialization]: Carico i programmi nella Home"); 
    	
    	// Set Up Add Button
    }
    
    protected void loadEpisodes(Program program) throws URISyntaxException, JsonProcessingException {
    	
    	// Titolo sezione e funzionalita'
    	// Title
    	sectionLabel.setText("Episodi " + program.getTitle());
    	// Add Button
    	if (user != null && user.getRole().equals("SPEAKER")) {
    		actionBtn.setText("Add Episode");
    		/*
    		actionBtn.setOnAction(e -> {
    			
    			// Creo lo stage
    			Stage popUpStage = new Stage();
    			
    			// Creazione componente per il loading del file FXML
    			ResourceLoader resourceLoader = new DefaultResourceLoader();
            	Resource addResource = resourceLoader.getResource("classpath:view/addItem.fxml");
                FXMLLoader loader = null;
				try {
					loader = new FXMLLoader(addResource.getURL());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                // Caricamento della schermata nel Pannello
                AnchorPane root = null;
				try {
					root = (AnchorPane) loader.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                
                // Caricamento del Controller
                AddItemController controller = loader.getController();
                
                
                Scene popUpScene = new Scene(root);
                popUpStage.setScene(popUpScene);
    		});*/
    	}
    	
    	// Prepare VBox
    	VBox itemLayout = new VBox();
    	itemLayout.setId("itemLayout");
    	itemLayout.setPrefWidth(790.0);
    	itemLayout.setPrefHeight(275.0);
    	itemLayout.setSpacing(10.0);
    	itemLayout.setMargin(itemLayout, new Insets(10, 0, 10, 0));
    	
    	scroll.setContent(itemLayout);
    	episodes.clear();
    	episodes.addAll( spreakerClient.getEpisodes(program.getId()) );
    	
    	try {
    		for (int i = 0; i < episodes.size(); i++) {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/episodeItem.fxml"));
				HBox hBox = fxmlLoader.load();
				
				EpisodeController episodeController = fxmlLoader.getController();
				episodeController.setHomeController(this);
				episodeController.setData(episodes.get(i));
	    		
	    		itemLayout.getChildren().add(hBox);
	    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	System.out.println("[Button Event]: Gli episodi del programma scelto");

    }
    
    protected void loadPlayer(Episode content) throws IOException {
    	FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/player.fxml"));
		AnchorPane playerContainer = fxmlLoader.load();
		
		// Load Controller
		PlayerController playerController = fxmlLoader.getController();
		playerController.setHomeController(this);
		playerController.setData(content);
		
		
		scroll.setContent(playerContainer);
    }
    
    @FXML
    private void loadLive(ActionEvent event) throws URISyntaxException, IOException { 
    
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/player.fxml"));
		AnchorPane playerContainer = fxmlLoader.load();
    	
		// Load Controller 
		PlayerController playerController = fxmlLoader.getController();
		
		playerController.setData( spreakerClient.getLive() );
		
		// Remove ScrollPane Node
		// https://stackoverflow.com/questions/18853265/how-to-replace-remove-add-and-replace-panes
		//mainContent.getChildren().remove(1);
		//mainContent.getChildren().add(playerContainer);
		
		// Adding Player Node
		scroll.setContent(playerContainer);
		
		
    	System.out.println("[Button Event]: Carico la schermata Live"); 
    }
    
    @FXML
    private void loadUAds(ActionEvent event) throws URISyntaxException { 
    	
    	// Prepare VBox
    	VBox itemLayout = new VBox();
    	itemLayout.setId("itemLayout");
    	itemLayout.setPrefWidth(790.0);
    	itemLayout.setPrefHeight(275.0);
    	itemLayout.setSpacing(10.0);
    	itemLayout.setMargin(itemLayout, new Insets(10, 0, 10, 0));
    	
    	// Substitute the content of the ScrollPane
    	// From HBox to VBox
    	// https://openjfx.io/javadoc/12/javafx.controls/javafx/scene/control/ScrollPane.html
    	scroll.setContent(itemLayout);
    	
    	universityAds.clear();
    	universityAds.addAll(getUAds());
    	try {
    		for (int i = 0; i < universityAds.size(); i++) {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/uAdsItem.fxml"));
				HBox hBox = fxmlLoader.load();
				
				UAdsController uadsController = fxmlLoader.getController();
				uadsController.setData(universityAds.get(i));
	    		
	    		itemLayout.getChildren().add(hBox);
	    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    	System.out.println("[Button Event]: Carico gli avvisi unime"); 
    	
    	// Set Up Add Button
    }
    
    @FXML
    private void loadMusic(ActionEvent event) throws URISyntaxException { 
    	
    	// Titolo sezione e funzionalita'
    	// Title
    	sectionLabel.setText("Catalogo Musica");
    	
    	// Add Button
    	if (user != null && user.getRole().equals("SPEAKER")) {
    		actionBtn.setText("Add music");
    		actionBtn.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					// Creo lo stage
	    			Stage popUpStage = new Stage(); 
	    			
	    			// Creazione componente per il loading del file FXML
	    			ResourceLoader resourceLoader = new DefaultResourceLoader();
	            	Resource addResource = resourceLoader.getResource("classpath:view/addItem.fxml");
	                FXMLLoader loader = null;
					try {
						loader = new FXMLLoader(addResource.getURL());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					// Caricamento della schermata nel Pannello
	                AnchorPane root = null;
					try {
						root = (AnchorPane) loader.load();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	                
	                // Caricamento del Controller
	                AddItemController controller = loader.getController();
	                
	                // Configurazione del elemento da aggiungere
	                controller.setItem("Musica");
	                
	                Scene popUpScene = new Scene(root);
	                popUpStage.setScene(popUpScene);
	                
	                Window window = ((Button)event.getTarget()).getScene().getWindow();
	                
	                // Calculate the center position of the parent Stage
	                double centerXPosition = window.getX() + window.getWidth()/2d;
	                double centerYPosition = window.getY() + window.getHeight()/2d;

	                // Hide the pop-up stage before it is shown and becomes relocated
	                popUpStage.setOnShowing(ev -> popUpStage.hide());

	                // Relocate the pop-up Stage
	                
	                popUpStage.setOnShown(ev -> {
	                	popUpStage.setX(centerXPosition - popUpStage.getWidth()/2d);
	                    popUpStage.setY(centerYPosition - popUpStage.getHeight()/2d);
	                    popUpStage.show();
	                });

	                popUpStage.showAndWait();
	                
	                // Reload section
	                musicBtn.fire();	                
				}
    			
    		});
    	}
    	
    	// Create and Prepare VBox
    	VBox itemLayout = new VBox();
    	itemLayout.setId("itemLayout");
    	itemLayout.setPrefWidth(790.0);
    	itemLayout.setPrefHeight(275.0);
    	itemLayout.setSpacing(10.0);
    	itemLayout.setMargin(itemLayout, new Insets(10, 0, 10, 0));
    	
    	// Substitute the content of the ScrollPane
    	// From HBox to VBox
    	scroll.setContent(itemLayout);
    	
    	// Get Music List
    	music.clear();
    	music.addAll(uvmClient.getMusic());
    	try {
    		for (int i = 0; i < music.size(); i++) {
	    		FXMLLoader fxmlLoader = new FXMLLoader();
	    		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/musicItem.fxml"));
				HBox hBox = fxmlLoader.load();
				
				MusicController itemController = fxmlLoader.getController();
				itemController.setData(music.get(i));
	    		
	    		itemLayout.getChildren().add(hBox);
	    	}
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
    	}
    
    	System.out.println("[Button Event]: Carico le playlist musicali"); 
    	
    	// Set Up Add Button
    }

}

