package eu.universome.controller;

import com.jfoenix.controls.JFXButton;

import eu.universome.client.UvmClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddItemController {
	
	@FXML
	private AnchorPane root;
	
    @FXML
    private Label itemLabel;

    @FXML
    private TextField attr1Field;

    @FXML
    private TextField attr2Field;

    @FXML
    private TextField attr3Field;

    @FXML
    private TextField attr4Field;

    @FXML
    private JFXButton addBtn;
	
    
	public void setItem(String text) {
		this.itemLabel.setText(text);
		configureController();
	}
	
	@FXML
	public void addItem(ActionEvent event) {
		
		// Instantiate new client
    	UvmClient uvmClient = new UvmClient();
		
    	try {
			switch (this.itemLabel.getText()) {
				case "Musica":
					uvmClient.addMusic(
							attr1Field.getText(), // Title
							attr2Field.getText(), // Description
							attr3Field.getText()  // Genre 
					); break;
				
				case "Uads": break;
				
				case "Program": break;
				
				case "Episode": break;
			}
			// Get current scene and close it
			Stage stage = (Stage) this.itemLabel.getScene().getWindow();
			stage.close();
			
    	} catch (Exception e) {
    		System.out.println(e);
    	}
	}
	
	public void configureController() {
		
		switch (this.itemLabel.getText()) {
			case "Musica":
				setForMusic();
			
		}
		
	}
	
	public void setForMusic() {
		
		// Configurazione campi
		this.attr1Field.setPromptText("Title");
		this.attr2Field.setPromptText("Description");
		this.attr3Field.setPromptText("Genre");
		
		// Delete not-used field
		VBox vBox = (VBox) root.getChildren().get(0);
		vBox.getChildren().remove(attr4Field);
	}
}
