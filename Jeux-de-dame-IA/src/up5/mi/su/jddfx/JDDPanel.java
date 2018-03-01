package up5.mi.su.jddfx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JDDPanel extends GridPane{

	Button niveauD; 
	Button niveauI;
	Button quitter;
		
	Label lab_choisir = new Label ("Choisir le niveau de difficulté");
	Image image = new Image ("file:Images/logo.png");
	ImageView iv = new ImageView (image);
	
	public JDDPanel (Stage stage)
	{
	
		Button niveauD = new Button ("Niveau Débutant");
		Button niveauI = new Button ("Niveau Intermédiaire");
		Button quitter = new Button ("Quitter");
		
		
		lab_choisir.setPrefHeight(50);
		niveauD.setPrefWidth(150);
		niveauI.setPrefWidth(150);
		quitter.setPrefWidth(150);
		
		niveauD.setOnAction( (event)->
		{
			stage.setScene (new Scene (new PlateauPanel ("debutant", stage), 600, 600));
			stage.centerOnScreen();
			stage.show();
			
		});
		
		niveauI.setOnAction( (event)->
		{
			stage.setScene (new Scene (new PlateauPanel ("intermediaire", stage), 600, 600));
			stage.centerOnScreen();
			stage.show();
		});
		
		quitter.setOnAction( (event)->
		{
			stage.close();
		});
		
		iv.setFitHeight(25);
		iv.setPreserveRatio(true);
		
		this.add(lab_choisir, 1, 0);
		this.add(niveauD, 1, 2);
		this.add(niveauI, 1, 3);
		this.add(quitter, 1, 4);
		this.add(iv, 1, 5);
		
	}

}
