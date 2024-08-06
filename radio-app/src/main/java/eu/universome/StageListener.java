package eu.universome;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import eu.universome.controller.LoginController;
import eu.universome.controller.UAdsController;
import eu.universome.models.User;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

@Component
public class StageListener implements ApplicationListener<StageReadyEvent> {
	
	private final String applicationTitle;
	private final Resource fxml;
	private final ApplicationContext applicationContext;
	
	private double x, y;
	
	public StageListener(@Value("${spring.application.ui.title}") String applicationTitle,
						 @Value("classpath:view/home.fxml") Resource fxml,
						 ApplicationContext applicationContext) {
		this.applicationTitle = applicationTitle;
		this.fxml = fxml;
		this.applicationContext = applicationContext;
		
	}
	
	@Override
	public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
		
		try {
			
			URL url = this.fxml.getURL();
			FXMLLoader fxmlLoader = new FXMLLoader( url );
			fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
			Parent root = fxmlLoader.load();
			
			Stage primaryStage = stageReadyEvent.getStage();			
			Scene scene = new Scene(root, 800, 480);
			primaryStage.setScene(scene);
			primaryStage.setTitle(this.applicationTitle);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			//drag it here
	        root.setOnMousePressed(event -> {
	            x = event.getSceneX();
	            y = event.getSceneY();
	        });
	        root.setOnMouseDragged(event -> {

	        	primaryStage.setX(event.getScreenX() - x);
	        	primaryStage.setY(event.getScreenY() - y);

	        });
	        
	        primaryStage.show();
			
		/*
		 * 	https://stackoverflow.com/questions/60029114/javafx-overlapping-panes
			StackPane root = new StackPane();
	        Scene scene = new Scene(root, 500, 500);
	        Stage primaryStage = stageReadyEvent.getStage();
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("Node Overlay Demo");
	        primaryStage.show();
	        
	        // Only Horizontal
	        HBox hBox = new HBox(new Button("One"), new Button("Two"));
	        hBox.setPadding(new Insets(10));
	        hBox.setSpacing(10);
	        StackPane hPane = new StackPane(hBox);
	        hPane.setMaxHeight(100);
	        hPane.setVisible(false);
	        hPane.setStyle("-fx-background-color:#AA555550");

	        // Only Vertical
	        VBox vBox = new VBox(new Button("One"), new Button("Two"));
	        vBox.setPadding(new Insets(10));
	        vBox.setSpacing(10);
	        StackPane vPane = new StackPane(vBox);
	        vPane.setMaxWidth(100);
	        vPane.setVisible(false);
	        vPane.setStyle("-fx-background-color:#55BB5550");

	        Button left = new Button("Left");
	        Button top = new Button("Top");
	        Button right = new Button("Right");
	        Button bottom = new Button("Bottom");
	        VBox buttons = new VBox(left, top, right, bottom);
	        buttons.setStyle("-fx-border-width:2px;-fx-border-color:red;");
	        buttons.setSpacing(10);
	        buttons.setAlignment(Pos.CENTER);
	        StackPane.setMargin(buttons, new Insets(15));

	        StackPane content = new StackPane(buttons);
	        content.setOnMouseClicked(e -> {
	            Node node = vPane.isVisible() ? vPane : hPane;
	            FadeTransition ft = new FadeTransition(Duration.millis(300), node);
	            ft.setOnFinished(e1 -> node.setVisible(false));
	            ft.setFromValue(1.0);
	            ft.setToValue(0.0);
	            ft.play();
	        });

	        root.getChildren().addAll(content, hPane, vPane);

	        Stream.of(left, top, right, bottom).forEach(button -> {
	            button.setOnAction(e -> {
	                vPane.setVisible(false);
	                hPane.setVisible(false);
	                Node node;
	                switch (button.getText()) {
	                    case "Left":
	                    case "Right":
	                        node = vPane;
	                        StackPane.setAlignment(vPane, button.getText().equals("Left") ? Pos.CENTER_LEFT : Pos.CENTER_RIGHT);
	                        break;
	                    default:
	                        node = hPane;
	                        StackPane.setAlignment(hPane, button.getText().equals("Top") ? Pos.TOP_CENTER : Pos.BOTTOM_CENTER);
	                }
	                node.setVisible(true);
	                FadeTransition ft = new FadeTransition(Duration.millis(300), node);
	                ft.setFromValue(0.0);
	                ft.setToValue(1.0);
	                ft.play();
	            });
	        });*/
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}			
	}
	
	public Object loadStage(Resource fxml, Class<?> controllerClass) {
		
		try {
			// Load FXML
			URL url = fxml.getURL();
			FXMLLoader fxmlLoader = new FXMLLoader( url );
			fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
			Parent root = fxmlLoader.load();
			
			// Load Controller
			LoginController controller = null;
			if (controllerClass == LoginController.class) {
				controller = (LoginController) fxmlLoader.getController();
			}
			else if (controllerClass == String.class) {
				;//controller = (String) "string";
			}
			else {
				controller = null;
			}
			
			Stage stage = new Stage();		
			Scene scene = new Scene(root, 600, 400);
			stage.setScene(scene);
			// https://stackoverflow.com/questions/34070334/how-could-i-disable-primary-stage-when-a-new-stage-popup
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			
			// Gestione della chiusura della finestra:
			// - https://stackoverflow.com/questions/34590798/how-to-refresh-parent-window-after-closing-child-window-in-javafx
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					System.out.println("Close Window");
				}
			});
			User user = controller.user;
			stage.getOnCloseRequest().handle(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			
			return user;
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}
	/*
	static void setRoot(Resource sceneResource) throws IOException {
		scene.setRoot(loadFXML(sceneResource));
	}*/
	
	private static Parent loadFXML(Resource sceneResource) throws IOException {
		//the method .getClassLoader is an holy method because use it and all works fine
		//FXMLLoader fxmlLoader = new FXMLLoader(App.class.getClassLoader().getResource(fxml + ".fxml"));
		FXMLLoader fxmlLoader = new FXMLLoader(sceneResource.getURL());
		//fxmlLoader.setControllerFactory(aClass -> applicationContext.getBean(aClass));
		return fxmlLoader.load();
	}
	
}
