package sample;



import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {
	public static void main (String[] args) {
		launch(args);
	}
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		Parent root = FXMLLoader.load(getClass().getResource("basic_send_email.fxml"));
		primaryStage.setTitle("Send Email");
		Scene scene = new Scene(root,630,630);
		primaryStage.setScene(scene);
		primaryStage.setHeight(primaryScreenBounds.getHeight());
		primaryStage.show();
		
		scene.widthProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
		    	primaryStage.setWidth(630);
		    }
		});
		
		scene.heightProperty().addListener(new ChangeListener<Number>() {
		    @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {	
		    	primaryStage.setHeight(primaryScreenBounds.getHeight());
		    }
		});
	}
	

}
