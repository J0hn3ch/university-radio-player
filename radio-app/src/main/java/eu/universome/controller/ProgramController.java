package eu.universome.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import eu.universome.models.Program;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

@Component
public class ProgramController implements Initializable {
	
	@FXML
	private VBox programBox;
	
	@FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label speakerLabel;

    private Program program;
    
    //private MyListener myListener;
    private HomeController homeController;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.program = null;
		
		// Home Controller Initialization
		
		// Set Effect
		this.setEffects();
	}
	
    
    public ProgramController() {
    	
    }
    
	public void setData(Program program) {
		this.program = program;
		program.setSpeaker("Gianluca");
		titleLabel.setText(program.getTitle());
		speakerLabel.setText(program.getSpeaker());
		
		Image image = new Image("img/musicassetta-190x200.jpg");
		imageView.setImage(image);
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	@FXML
    private void click() {
    	System.out.println(program.getId());
    }
    
	
	// When click on a card of a program, load episode list
	@FXML
	public void loadEpisodes() throws JsonProcessingException, URISyntaxException {
		if (this.program != null) {
			this.homeController.loadEpisodes(program);
		}
	}
	
	public void setEffects() {
		//give the items some effect

        programBox.setOnMouseEntered(event -> {
        	programBox.setStyle("-fx-background-color : #ffcc80");
        });
        programBox.setOnMouseExited(event -> {
        	programBox.setStyle("-fx-background-color : #ffffff");
        });
	}
	
	
}
