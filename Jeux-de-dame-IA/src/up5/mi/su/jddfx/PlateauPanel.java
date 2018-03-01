package up5.mi.su.jddfx;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import up5.mi.su.jdd.*;

public class PlateauPanel extends GridPane{

	Jeu jeu;
	Pion pions [][];
	Image img;
	public ImageView [][] imagesVs;
	int x = 0; int y =0;
	Coordonnee coordPrec = null;
	Couleur couleurPrec = null;
	ArrayList <Coordonnee> cases = new ArrayList <Coordonnee>();
	boolean eatable = false;
	Stage primStage;
	public Label cptPion;
	
	
	public PlateauPanel (String niveau, Stage primStage)
	{
		jeu = new Jeu (niveau);
		pions = jeu.getPions();
		imagesVs = new ImageView [jeu.getTaille()][jeu.getTaille()];
		init ();
		this.primStage = primStage;

	}
	
	public void init ()
	{
		for (int i = 0; i <jeu.getTaille(); i++)
		{
			for (int j = 0; j<jeu.getTaille(); j++)
			{
				
				if (pions[i][j] != null)
				{
					if (pions[i][j].getCouleur().equals(Couleur.NOIR))
					{
						img = new Image ("file:Images/CaseNoirPN.png");
						imagesVs[i][j] = new ImageView (img);
					}
					else if (pions[i][j].getCouleur().equals(Couleur.BLANC))
					{
						img = new Image ("file:Images/CaseNoirPB.png");
						imagesVs[i][j] = new ImageView (img);
						
					}
				}
				else 
				{
					if (i%2 == 0 || i==0)
					{
						if (j%2 != 0)
						{
							img = new Image ("file:Images/CaseNoir.png");
							imagesVs[i][j] = new ImageView (img);
						}
						else 
						{
							img = new Image ("file:Images/CaseBlanche.png");
							imagesVs[i][j] = new ImageView (img);
						}
						
					}
					else
					{
						if (j%2 != 0 && j!=0)
						{
							img = new Image ("file:Images/CaseBlanche.png");
							imagesVs[i][j] = new ImageView (img);
						}
						else 
						{
							img = new Image ("file:Images/CaseNoir.png");
							imagesVs[i][j] = new ImageView (img);
						}
						
					}
				}
			}
		}
		
		for (int i = 0; i  <jeu.getTaille(); i++)
		{
			for (int j = 0; j <jeu.getTaille(); j++ )
			{
				if (imagesVs[i][j] !=null)
				{
						imagesVs[i][j].setOnMouseClicked(new CaseEvent());
						this.add(imagesVs[i][j] , j, i);
						//System.out.println(i+ " " + j + " "+ imagesVs[i][j]);
				}
			}
		}
	}
	
	class CaseEvent implements EventHandler <MouseEvent>
	{

		@Override
		public void handle(MouseEvent event) {
			// TODO Auto-generated method stub
			if (couleurPrec != null)
			{
				if (couleurPrec == Couleur.BLANC) imagesVs[x][y].setStyle("-fx-image: url(file:Images/CaseNoirPB.png);");
				else imagesVs[x][y].setStyle("-fx-image: url(file:Images/CaseNoirPN.png);");
				coordPrec = new Coordonnee (x,y);
				couleurPrec = null;
			}
			
			
			if (event.getSource().getClass().equals(new ImageView().getClass()))
			{
				ImageView iv = (ImageView) event.getSource();
				
				for (int i = 0; i<jeu.getTaille(); i++)
				{
					for (int j = 0; j <jeu.getTaille(); j++)
					{
						if (imagesVs[i][j].equals(iv)) 
						{
							x= i;
							y= j;
							boolean testExistence = false;
							
							for (int a = 0; a <cases.size(); a++)
							{
								if (x==cases.get(a).getX() && y==cases.get(a).getY()) 
								{
									testExistence = true;
									eatable = cases.get(a).isPrise();
									break;
								}
							}
							for (int k = 0; k<cases.size(); k++)
							{
								cases.get(k);
								imagesVs[cases.get(k).getX()][cases.get(k).getY()].setStyle("-fx-image: url(file:Images/CaseNoir.png);");
							}
							cases.clear();
							
							if (testExistence)
							{
								Coup coup;
								if (eatable)
								{
									Coordonnee pionD = null;
									for (int p = 0; p <pions[coordPrec.getX()][coordPrec.getY()].getDeplacementsPossibles().size(); p++)
									{
										if (pions[coordPrec.getX()][coordPrec.getY()].getDeplacementsPossibles().get(p).getX() == x && pions[coordPrec.getX()][coordPrec.getY()].getDeplacementsPossibles().get(p).getY() == y)
										{
											pionD = pions[coordPrec.getX()][coordPrec.getY()].getDeplacementsPossibles().get(p).getPionDetruit();
											
										}
									}
									coup = new Coup (coordPrec, new Coordonnee (x,y), eatable, pionD);
								}
								else
								{
									coup = new Coup (coordPrec, new Coordonnee (x,y), eatable);
								}
								
								jeu.deplacer(PlateauPanel.this, coup);
								if (jeu.isWin())
								{
									BorderPane p = new BorderPane();
									p.setCenter(new Label(jeu.getWinner()));
									Stage stage = new Stage ();
									stage.setScene(new Scene(p, 50, 50));
									stage.setAlwaysOnTop(true);
									stage.show();
									stage.setOnCloseRequest( (e)->
									{
										primStage.setScene(new Scene (new JDDPanel (primStage), 155, 155));
										primStage.centerOnScreen();
										stage.close();
									});
									
								}
								IA.iaPlay(jeu, PlateauPanel.this);
								if (jeu.isWin())
								{
									BorderPane p = new BorderPane();
									p.setCenter(new Label(jeu.getWinner()));
									Stage stage = new Stage ();
									stage.setScene(new Scene(p, 50, 50));
									stage.setAlwaysOnTop(true);
									stage.show();
									stage.setOnCloseRequest( (e)->
									{
										primStage.setScene(new Scene (new JDDPanel (primStage), 155, 155));
										primStage.centerOnScreen();
										stage.close();
									});
									
								}
							}
							
							else
							{
								if (pions[i][j] !=null)
								{
									if (pions[i][j].isMovable())
								{
									couleurPrec = pions[i][j].getCouleur();
									if (pions[i][j].getCouleur().equals(Couleur.BLANC) && jeu.isTourBlanc())
									{
										imagesVs[i][j].setStyle("-fx-image: url(file:Images/CaseSelecPB.png);");
						
										

										for (int k = 0; k<pions[i][j].getDeplacementsPossibles().size(); k++)
										{
									
											imagesVs[pions[i][j].getDeplacementsPossibles().get(k).getX()][pions[i][j].getDeplacementsPossibles().get(k).getY()].setStyle("-fx-image: url(file:Images/CaseSugg.png);");
											cases.add(pions[i][j].getDeplacementsPossibles().get(k));
										}
										break;
									}}
							}
							

						}
						}
						


					}
				}
				
				
			}
			event.consume();
		}
		
	}

}
