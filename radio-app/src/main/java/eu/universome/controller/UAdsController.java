package eu.universome.controller;

import eu.universome.models.UniversityAds;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class UAdsController {
	
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

    private UniversityAds uAds;
    
	public void setData(UniversityAds uAds) {
		this.uAds = uAds;
		titleLabel.setText(uAds.getTitle());
		descriptionLabel.setText(uAds.getDescription());
		departmentLabel.setText(uAds.getDepartment());
		speakerLabel.setText(uAds.getSpeaker());
	}
}
