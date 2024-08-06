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
public class LoginController implements Initializable{
	
    @FXML
    private JFXButton loginBtn;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private JFXButton facebookBtn;

    @FXML
    private Hyperlink registerLabel;
    
    @FXML
    private Label adviceLabel;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private FXMLLoader fxmlLoader;
    
    // User
    public User user;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	fxmlLoader = new FXMLLoader();
    	
    	//  Register Label
    	registerLabel.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	try {
					switchToRegistration(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
    }
   
    public String getEmail() {
		return emailField.getText();
	}
	
	public String getPassword() {
		return passwordField.getText();
	}

    
    public void switchToRegistration(ActionEvent event) throws IOException {
    	fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/register.fxml"));
    	Parent root = fxmlLoader.load();
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    private void login(ActionEvent event) throws IOException{
    	
    	// Instantiate new client
    	UvmClient uvmClient = new UvmClient();
    	
    	if(getEmail().isEmpty() || getPassword().isEmpty()) {
    		adviceLabel.setText("Inserire Username e Password");
    	}
    	else {
    		User user = uvmClient.login(getEmail(), getPassword());
    		if (user != null) {
    			// Set the user
    			this.user = user;
    			
    			// Get current scene and close it
    			Stage stage = (Stage) this.registerLabel.getScene().getWindow();
    			stage.close();
    		} else {
    			adviceLabel.setText("Wrong username or password");
    		}
    	}
    }   
}
