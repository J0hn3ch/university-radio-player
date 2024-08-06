package eu.universome.controller;

import eu.universome.models.Music;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MusicController {
	
	@FXML
    private ImageView imageView;

    @FXML
    private Label titleLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Button itemBtn;

    private Music music;
    
    public void setData(Music music) {
		
		this.music = music;
		titleLabel.setText(music.getTitle());
		descriptionLabel.setText(music.getDescription());
		genreLabel.setText(music.getGenre());
	}
}
