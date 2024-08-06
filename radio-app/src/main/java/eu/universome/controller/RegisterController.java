package eu.universome.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;

import eu.universome.client.UvmClient;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@Component
public class RegisterController implements Initializable {
	
	@FXML
    private TextField nameField;
	
	@FXML
    private TextField surnameField;
	
    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;
    
    @FXML
    private Label adviceLabel;

    @FXML
    private JFXButton registerBtn;

    @FXML
    private Hyperlink loginLink;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    private FXMLLoader fxmlLoader;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	
    	fxmlLoader = new FXMLLoader();
    	
    	//  Register Label
    	loginLink.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	try {
					switchToLogin(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }
		});
    }
    
    public void switchToLogin(ActionEvent event) throws IOException {
    	fxmlLoader.setLocation(getClass().getClassLoader().getResource("view/login.fxml"));
    	Parent root = fxmlLoader.load();
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }
    
    @FXML
    public void register(ActionEvent event) {
    	
    	UvmClient uvmClient = new UvmClient();
    	
    	if ( nameField.getText() == "" || 
    	     surnameField.getText() == "" ||  
    	     emailField.getText() == "" ||
    	     passwordField.getText() == "" ) {
    		adviceLabel.setText("Inserire Username e Password");
    	}
    }
}
