package up5.mi.su.jddfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JDDApp extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		stage.setTitle("Jeu de dames");
		stage.setScene(new Scene (new JDDPanel(stage), 155, 155));
		stage.show();
		
	}
	
	public static void main (String []args)
	{
		launch (args);
	}

}
