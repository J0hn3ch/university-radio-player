package eu.universome.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

//import eu.universome.AppClient;
import eu.universome.models.Episode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;

//@Component
public class PlayerController {
	
	//@FXML
	//private AppClient appClient;
	
	// Media Controller Buttons
	@FXML
	private Button playBtn;

    @FXML
    private Label titleLabel;

    @FXML
    private Label speakerLabel;
    
    private URL mediaUrl;
    private Media media;
    private MediaPlayer player;
    private MediaView mediaView;
    
    private HomeController homeController;
    private Episode episode;

	public PlayerController(){//AppClient appClient) {
		// Auto-Configuration Shared Beans: https://www.youtube.com/watch?v=uPI4Xu7NtI0
		//this.appClient = appClient;
		//user1 = appClient.getUser();
		//System.out.println(">> message = " + appClient.getUser().block());
	}
	
	public void setHomeController(HomeController homeController) {
		this.homeController = homeController;
	 }
	
	
	// Initialize data of the Episode from other scene
	public void initEpisode(Episode episode) {
		this.episode = episode;
		titleLabel.setText(episode.getTitle());
		
	}
	
	 public void setData(Episode episode) throws MalformedURLException {
		 
		 this.mediaUrl = new URL(episode.getUrl());
		 // https://stackoverflow.com/questions/53237287/module-error-when-running-javafx-media-application
		 this.media = new Media(mediaUrl.toExternalForm());
		 this.player = new MediaPlayer(media);
		 
		 // Add a mediaView, to display the media. Its necessary !
	     // This mediaView is added to a Pane
	     this.mediaView = new MediaView(player);
	     
	     // MediaPlayer Property
	     player.setAutoPlay(true);
	     
	     // Button Behavior
	     playBtn.setOnAction(new EventHandler<ActionEvent>() {
	    	 @Override
	         public void handle(ActionEvent event) {
	    		 System.out.println(player.getStatus());
	    		 if (player.getStatus() == Status.PLAYING)
	    			 player.pause();
	    		 else 
	    			 player.play();
	    	 }
	     });
	}
	 
	 
}

/*
 * javafx.scene.media.Media class not found: fix (https://stackoverflow.com/questions/71808695/maven-package-javafx-scene-media-is-not-visible)
 */
