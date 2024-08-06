package eu.universome.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import eu.universome.client.UvmClient;
import eu.universome.models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

@Component
public class LoginController2 implements Initializable{
	
    @FXML
    private JFXButton anonBtn;
    
    @FXML
    private JFXButton listenerBtn;
    
    @FXML
    private JFXButton speakerBtn;
    
    private String user;
    
    private HomeController homeController;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	// initialization
    }
    
    private void setUser(String user) {
    	this.user = user;
    }
    
    public void anonLogin() {
    	System.out.print("ANON");
    	this.setUser("ANON");
    	handleCloseButtonAction();
    }
    
    public void listenerLogin() {
    	System.out.print("LISTENER");
    	this.setUser("LISTENER");
    	handleCloseButtonAction();
    }
    
    public void speakerLogin() {
    	System.out.print("SPEAKER");
    	this.setUser("SPEAKER");
    	handleCloseButtonAction();
    	
    }
    
    // Set HomeController
    public void setHomeController(HomeController homeController) {
    	this.homeController = homeController;
    }
    
    // Close Stage
    @FXML
    public void handleCloseButtonAction() {
        Stage stage = (Stage) anonBtn.getScene().getWindow();
        stage.close();
    }
    
    String getUser() {
    	return this.user;
    }
}
