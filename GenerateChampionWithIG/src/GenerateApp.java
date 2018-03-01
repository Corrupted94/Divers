import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GenerateApp extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		
		stage.setTitle("Generate Next Champion 2 Buy");
		stage.setScene(new Scene (new GeneratePanel (), 800, 600));
		
		stage.show();
	}

	public static void main (String []args)
	{
		launch(args);
	}
}
