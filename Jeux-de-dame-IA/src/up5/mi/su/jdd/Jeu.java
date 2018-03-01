package up5.mi.su.jdd;

import up5.mi.su.jddfx.PlateauPanel;

public class Jeu implements Cloneable{

	private final static int taille = 8;
	private Pion [][] pions = new Pion[taille][taille];
	private boolean tourBlanc = true;
	private boolean win = false;
	private String winner = "";
	private int cptNoir = 12;
	private int cptBlanc = 12;
	private String niveau; 
	
	public Jeu (String niveau)
	{
		init ();
		this.niveau = niveau;
		updateMovable ();
		updatePrise();	
	}
	/**
	 * Methode pour initialiser le tableau 2D 
	 */
	public void init ()
	{

		String sb = "";
		int k = 1, l = 1;
		for (int i = 0; i < taille; i++)
		{		
			int j = -1;
			if (i == 1 || i == taille- 3 || i == taille - 1) j = 0;
			else if (i == 0 || i == 2 || i == taille -2) j =1;
				
			
			while (j!=-1 && j<taille)
			{
				if (i==0 || i == 1 || i ==2)
				{
					sb = "N" + k;
					if (i == 0 || i == 1)
						pions[i][j] = new Pion (sb, Couleur.NOIR, false);
					
					else 
						pions[i][j] = new Pion (sb, Couleur.NOIR, true);
					
					k++;
				}
				
				else 
				{
					sb = "B" + l;
					if (i == taille -1 || i == taille -2)
						pions[i][j] = new Pion (sb, Couleur.BLANC, false);
					
					else 
						pions[i][j] = new Pion (sb, Couleur.BLANC, true);
					
					l++;
				}
				j+=2;
			}
				
		}
		
	}
	
	
	public void afficher ()
	{
		for (int i = 0; i<taille; i++)
			for (int j = 0; j <taille; j++)
			{
				if (pions [i][j] !=null)
				{
					System.out.println(i + "   " + j   + "   " + pions [i][j].getCouleur() + "   "+ pions [i][j].isMovable() + "   " +pions [i][j].getId());
					for (int k = 0; k < pions [i][j].getDeplacementsPossibles().size(); k++) System.out.println("\t" + pions [i][j].getDeplacementsPossibles().get(k));

				}
			}
	}
	
	

	public void deplacer (PlateauPanel plateau, Coup coup)
	{
		if (!coup.isPrise())
		{
			if (pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()].getCouleur()== Couleur.BLANC)
				plateau.imagesVs[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setStyle("-fx-image: url(file:Images/CaseNoirPB.png);");
			
			else plateau.imagesVs[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setStyle("-fx-image: url(file:Images/CaseNoirPN.png);");
			
			plateau.imagesVs[coup.getOldCoord().getX()][coup.getOldCoord().getY()].setStyle("-fx-image: url(file:Images/CaseNoir.png);");
			pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()] = pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()];
			pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()] = null;
		}
		else
		{
			
			if (pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()].getCouleur()== Couleur.BLANC)
			{
				plateau.imagesVs[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setStyle("-fx-image: url(file:Images/CaseNoirPB.png);");
				cptNoir--;
			}
			
			else 
			{
				plateau.imagesVs[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setStyle("-fx-image: url(file:Images/CaseNoirPN.png);");
				cptBlanc--;
			}
			
			plateau.imagesVs[coup.getOldCoord().getX()][coup.getOldCoord().getY()].setStyle("-fx-image: url(file:Images/CaseNoir.png);");
			plateau.imagesVs[coup.getPionDetruit().getX()][coup.getPionDetruit().getY()].setStyle("-fx-image: url(file:Images/CaseNoir.png);");
			
			pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()] = pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()];
			pions[coup.getPionDetruit().getX()][coup.getPionDetruit().getY()] = null;
			pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()] = null;
		}
	
		if (pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()].getCouleur() == Couleur.BLANC) 
		{
			if (coup.getNewCoord().getX() == 0) pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setPromoted(true);
			tourBlanc = false;
				
		}
		else 
		{
			if (coup.getNewCoord().getX() == taille-1) pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setPromoted(true);
			tourBlanc = true;
		}
		updateMovable ();
		updatePrise();
		win();

	}
	
	/**
	 * Mise à jour des prises possibles
	 */
	public void updatePrise ()
	{
		for(int i = 0; i <taille; i++)
		{
			for (int j = 0 ; j< taille; j++)
			{
				if (pions [i][j]!= null)
				{
					if (pions[i][j].isPromoted()!= true)
					{
						if (pions[i][j].getCouleur().equals(Couleur.BLANC))
						{
							if ( i> 0 && j>0 && j <taille-1)
							{
								if (i>1 && j>1) if ((pions[i-1][j-1] != null) && (pions[i-2][j-2] == null))
								{
									if (!pions[i-1][j-1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i-2, j-2,true, new Coordonnee(i-1, j-1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
								if (i>1 && j<taille -2) if ((pions[i-1][j+1] != null) && (pions[i-2][j+2] == null))
								{
									if (!pions[i-1][j+1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i-2, j+2,true, new Coordonnee(i-1, j+1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
							}
							else if ( ((j==0 ||j==1) && i>1))
							{
								if ((pions[i-1][j+1] != null) && (pions[i-2][j+2] == null))
								{
									if (!pions[i-1][j+1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i-2, j+2,true, new Coordonnee(i-1, j+1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
								
							}
							else if ( ((j==taille -1|| j ==taille-2) && i >1))
							{
								if ((pions[i-1][j-1] != null) && (pions[i-2][j-2] == null))
								{
									if (!pions[i-1][j-1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i-2, j-2,true, new Coordonnee(i-1, j-1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
							}
						} 
						else if (pions[i][j].getCouleur().equals(Couleur.NOIR))
						{
							if ( j> 1 && j<taille-2 && i <taille-2)
							{
								
								if ((pions[i+1][j-1] != null) && (pions[i+2][j-2] == null))
								{
									if (!pions[i+1][j-1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i+2, j-2,true, new Coordonnee(i+1, j-1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
								if ((pions[i+1][j+1] != null) && (pions[i+2][j+2] == null))
								{
									if (!pions[i+1][j+1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i+2, j+2,true, new Coordonnee(i+1, j+1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
							}
							else if (( (j==0||j==1) && i<taille-2))
							{
								if ((pions[i+1][j+1] != null) && (pions[i+2][j+2] == null))
								{
									if (!pions[i+1][j+1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i+2, j+2,true, new Coordonnee(i+1, j+1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
							}
							else if (((j==taille -1||j==taille -2) && i <taille-2))
							{
								if ((pions[i+1][j-1] != null) && (pions[i+2][j-2] == null))
								{
									if (!pions[i+1][j-1].getCouleur().equals(pions[i][j].getCouleur()))
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee(i+2, j-2,true, new Coordonnee(i+1, j-1)));
										pions[i][j].setCanBeMoved(true);
									}
								}
								
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Mise à jour pour chaque pion existant, les déplacements possibles.
	 */
	public void updateMovable()
	{
		for (int i = 0; i < taille; i ++)
		{
			for (int j = 0; j<taille; j++)
			{
				if (pions[i][j] !=null)
				{
					pions[i][j].getDeplacementsPossibles().clear();
					if (pions[i][j].isPromoted()== true)
					{
						boolean testDep = false;
						int k = i-1, l = j-1;
						while (k >-1 && l >-1)
						{
							if (pions[k][l]== null)
							{
								pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k, l));
								testDep = true;
							}
							else
							{
								if (k-1 > -1 && l-1> -1 && !pions[k][l].getCouleur().equals(pions[i][j].getCouleur()))
								{
									if (pions [k-1][l-1] == null)
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k-1, l-1, true, new Coordonnee (k,l)));
										testDep = true;
										break;
									}
										
								}
							}
							k--; l--;
						}
						k = i-1; l = j+1;
						while (k>-1 && l<taille)
						{
							if (pions[k][l]== null)
							{
								pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k, l));
								testDep = true;
							}
							else
							{
								if (k-1 > -1 && l+1<taille && !pions[k][l].getCouleur().equals(pions[i][j].getCouleur()))
								{
									if (pions [k-1][l+1] == null)
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k-1, l+1, true, new Coordonnee (k,l)));
										testDep = true;
										break;
									}
										
								}
							}
							k--; l++;
						}
						k = i+1; l = j-1;
						while (k<taille && l>-1)
						{
							if (pions[k][l]== null)
							{
								pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k, l));
								testDep = true;
								
							}
							else
							{
								if (k+1 < taille && l-1> -1 && !pions[k][l].getCouleur().equals(pions[i][j].getCouleur()))
								{
									if (pions [k+1][l-1] == null)
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k+1, l-1, true, new Coordonnee (k,l)));
										testDep = true;
										break;
									}
										
								}
							}
							k++; l--;
						}
						k = i+1; l = j+1;
						while (k<taille && l<taille)
						{
							if (pions[k][l]== null)
							{
								pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k, l));
								testDep = true;
							}
							else
							{
								if (k+1 <taille && l+1 <taille && !pions[k][l].getCouleur().equals(pions[i][j].getCouleur()))
								{
									if (pions [k+1][l+1] == null)
									{
										pions[i][j].getDeplacementsPossibles().add(new Coordonnee (k+1, l+1, true, new Coordonnee (k,l)));
										testDep = true;
										break;
									}
										
								}
							}
							k++; l++;
						}
						pions[i][j].setCanBeMoved(testDep);
						
					}
					else
					{
						if (pions[i][j].getCouleur().equals(Couleur.BLANC))
						{
							if ( i> 0 && j>0 && j <taille-1)
							{
								if (pions[i-1][j-1] !=null && pions[i-1][j+1]!=null)
								{
									pions[i][j].setCanBeMoved(false);
									//
								}
								else 
								{
									pions[i][j].setCanBeMoved(true);
									if (pions[i-1][j-1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i-1, j-1));
									if (pions[i-1][j+1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i-1, j+1));
			
								}
							}
							else if ((j==0 && i>0))
							{
								if (pions[i-1][j+1] !=null)
								{
									pions[i][j].setCanBeMoved(false);
								}
								else 
								{
									pions[i][j].setCanBeMoved(true);
									if (pions[i-1][j+1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i-1, j+1));
						
								}
							}
							else if ((j==taille -1 && i >0))
							{
								if (pions[i-1][j-1] !=null)
								{
									pions[i][j].setCanBeMoved(false);
									//
								}
								else 
								{
									pions[i][j].setCanBeMoved(true);
									if (pions[i-1][j-1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i-1, j-1));
								}
							}
						}
						else if (pions[i][j].getCouleur().equals(Couleur.NOIR))
						{
							if ( j> 0 && j<taille-1 && i <taille-1)
							{
								if (pions[i+1][j-1] !=null && pions[i+1][j+1]!=null)
								{
									pions[i][j].setCanBeMoved(false);
									//
								}
								else 
								{
									pions[i][j].setCanBeMoved(true);
									if (pions[i+1][j-1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i+1, j-1));
									if (pions[i+1][j+1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i+1, j+1));
								}
							}
							else if ((j==0 && i<taille-1))
							{
								if (pions[i+1][j+1] !=null)
								{
									pions[i][j].setCanBeMoved(false);
									//
								}
								else
								{
									pions[i][j].setCanBeMoved(true);
									if (pions[i+1][j+1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i+1, j+1));
								}
							}
							else if ((j==taille -1 && i <taille-1))
							{
								if (pions[i+1][j-1] !=null)
								{
									pions[i][j].setCanBeMoved(false);
									//
								}
								else 
								{
									pions[i][j].setCanBeMoved(true);
									if (pions[i+1][j-1] ==null) pions[i][j].getDeplacementsPossibles().add(new Coordonnee (i+1, j-1));
								}
							}
						}
					}
				}
			}
		}
	}

	public boolean isTourBlanc() {
		return tourBlanc;
	}

	public void setTourBlanc(boolean tourBlanc) {
		this.tourBlanc = tourBlanc;
	}
	
	public void win ()
	{
		if (cptNoir == 0)
		{
			winner = "Player";
		}
		else if (cptBlanc == 0)
		{
			winner = "IA";
		}
		
		else
		{
			int compteurMove = 0;
			for (int i = 0; i<taille; i++)
			{
				for (int j = 0 ; j<taille; j++)
				{
					if (tourBlanc)
					{
						if (pions[i][j]!=null)
						{
							if (pions[i][j].getCouleur() == Couleur.BLANC)
							{
								compteurMove ++;
							}
						}
					}
					else
					{
						if (pions[i][j]!=null)
						{
							if (pions[i][j].getCouleur() == Couleur.NOIR)
							{
								compteurMove ++;
							}
						}
					}
				}
			}
			if (compteurMove ==0 && tourBlanc)
			{
				winner = "IA";
			}
			else if(compteurMove== 0 && !tourBlanc)
			{
				winner = "Player";
			}
		}
		if(winner != "")
		{
			win = true;
		}

	}


	public String getWinner() {
		return winner;
	}
	
	public boolean isWin() {
		return win;
	}

	public int getCptNoir() {
		return cptNoir;
	}

	public int getCptBlanc() {
		return cptBlanc;
	}
	public Object clone ()
	{
		Jeu jeuclone= null;
		try {
			jeuclone = (Jeu) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jeuclone;
	}

	public String getNiveau() {
		return niveau;
	}
	public void setWin(boolean win) {
		this.win = win;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public void setCptNoir(int cptNoir) {
		this.cptNoir = cptNoir;
	}

	public void setCptBlanc(int cptBlanc) {
		this.cptBlanc = cptBlanc;
	}
	
	/**
	 * Fonction qui permet de déplacer un pion / d'effectuer un coup
	 * @param coup
	 */
	public void deplacer2 (Coup coup)
	{
		if (!coup.isPrise())
		{
			pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()] = pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()];
			pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()] = null;
		}
		else
		{
			if (pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()].getCouleur()== Couleur.BLANC)
			{
				cptNoir--;
				tourBlanc = false;
			}
			
			else 
			{
				cptBlanc--;
				tourBlanc = true;
			}

			
			pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()] = pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()];
			pions[coup.getPionDetruit().getX()][coup.getPionDetruit().getY()] = null;
			
			pions[coup.getOldCoord().getX()][coup.getOldCoord().getY()] = null;
		}
		
		if (pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()].getCouleur()== Couleur.BLANC)
		{
			if (coup.getNewCoord().getX() == 0) pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setPromoted(true);
			tourBlanc = false;
		}
			
		
		else
		{
			if (coup.getNewCoord().getX() == 0) pions[coup.getNewCoord().getX()][coup.getNewCoord().getY()].setPromoted(true);
			tourBlanc = true;
		}
			
		
		updateMovable ();
		updatePrise();
		win();

	}

	public int getTaille() {
		return taille;
	}

	public Pion[][] getPions() {
		return pions;
	}

}
