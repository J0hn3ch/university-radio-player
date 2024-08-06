package eu.universome.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.universome.models.Episode;
import eu.universome.models.Program;
import eu.universome.models.UniversityAds;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EpisodeController implements Initializable{
	
	@FXML
	private HBox episodeBox;
	
	@FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label speakerLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button itemBtn;

    private Episode episode;
    private FXMLLoader fxmlLoader;
    private HomeController homeController;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.episode = null;
		
		// Home Controller Initialization
		this.fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/home.fxml"));
		HomeController homeController = fxmlLoader.getController();
		
		// Set Effect
		this.setEffects();
	}
	
    
    public EpisodeController() {
    	
    }
    
    public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
    
    public void setData(Episode episode) {
		this.episode = episode;
		titleLabel.setText(episode.getTitle());
		descriptionLabel.setText(episode.getDescription());
		speakerLabel.setText(episode.getProgramId().toString());
	}
    
    @FXML
	public void switchToPlay() throws IOException {
    	homeController.loadPlayer(episode);
	}
    
    public void setEffects() {
		//give the items some effect

        episodeBox.setOnMouseEntered(event -> {
        	episodeBox.setStyle("-fx-background-color : #ffcc80");
        });
        episodeBox.setOnMouseExited(event -> {
        	episodeBox.setStyle("-fx-background-color : #ffffff");
        });
	}	
}
