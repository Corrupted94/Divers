package up5.mi.su.jdd;

import java.util.ArrayList;
import java.util.Random;

import up5.mi.su.jddfx.PlateauPanel;

public class IA {
	
	public static void iaPlay (Jeu jeu, PlateauPanel plateau)
	{
		
		ArrayList <Coup> coupsPossibles = new ArrayList <Coup>();
		ArrayList <Integer> indices = new ArrayList <Integer>();
		Random rand = new Random ();
		int valeur=0;
		int max =-20000;
		boolean prise =false;

		for (int i = 0; i<jeu.getTaille(); i++)
		{
			for (int j = 0; j<jeu.getTaille(); j++)
			{
				if (jeu.getPions()[i][j] !=null)
				{
					if (jeu.getPions()[i][j].getCouleur() == Couleur.NOIR)
					{
						for (int k = 0; k<jeu.getPions()[i][j].getDeplacementsPossibles().size(); k++)
						{
							Coordonnee old = new Coordonnee (i,j);
							Coordonnee newC = new Coordonnee (jeu.getPions()[i][j].getDeplacementsPossibles().get(k).getX(), jeu.getPions()[i][j].getDeplacementsPossibles().get(k).getY()); 
			
							if (jeu.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise())
							{
								prise = true;
								Coordonnee pionD = jeu.getPions()[i][j].getDeplacementsPossibles().get(k).getPionDetruit();
								coupsPossibles.add(new Coup (old, newC, jeu.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise(), pionD));
							}
							else coupsPossibles.add(new Coup (old, newC, jeu.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise()));
							
						}
					}
				}
			}		
		}
	
		if (jeu.isTourBlanc()== false);
		{
			System.out.println("IA : Nombre de coups possibles : " + coupsPossibles.size());
			if (coupsPossibles.size() == 1)
			{
				jeu.deplacer(plateau, coupsPossibles.get(0));
			}
			else if (coupsPossibles.size()>1)
			{
				if (prise)
				{
					for (int i = 0; i<coupsPossibles.size(); i++)
					{
						if (coupsPossibles.get(i).isPrise())
						{
							if (jeu.getNiveau().equals("debutant"))
							{
								valeur = minValue (jeu, 2, coupsPossibles.get(i));
							}
							else if (jeu.getNiveau().equals("intermediaire"))
							{
								valeur = minValue (jeu, 4, coupsPossibles.get(i));
							}
							coupsPossibles.get(i).setValeur(valeur);
						}
					}
					for (int i = 0; i<coupsPossibles.size(); i++)
					{
						if (max < coupsPossibles.get(i).getValeur() && coupsPossibles.get(i).getValeur()!=-1) max = coupsPossibles.get(i).getValeur();
					}
					for (int i = 0; i<coupsPossibles.size(); i++)
					{
						if (coupsPossibles.get(i).getValeur() == max && coupsPossibles.get(i).isPrise()) indices.add(i);
					}
					int indice = rand.nextInt(indices.size());
					jeu.deplacer(plateau, coupsPossibles.get(indices.get(indice)));
					
				}
				else
				{
					for (int i = 0; i<coupsPossibles.size(); i++)
					{
						if (jeu.getNiveau().equals("debutant"))
						{
							valeur = minValue (jeu, 2, coupsPossibles.get(i));
						}
						else if (jeu.getNiveau().equals("intermediaire"))
						{
							valeur = minValue (jeu, 4, coupsPossibles.get(i));
						}
						coupsPossibles.get(i).setValeur(valeur);
					}
					
					for (int i = 0; i<coupsPossibles.size(); i++)
					{
						if (max < coupsPossibles.get(i).getValeur() && coupsPossibles.get(i).getValeur()!=-1) max = coupsPossibles.get(i).getValeur();
					}
					for (int i = 0; i<coupsPossibles.size(); i++)
					{
						//System.out.println(coupsPossibles.get(i).getValeur());
						if (coupsPossibles.get(i).getValeur() == max) indices.add(i);
					}
					
					int indice = rand.nextInt(indices.size());
					jeu.deplacer(plateau, coupsPossibles.get(indices.get(indice)));
					
				}

			}
			
		}		
		
	}
	
	
	public static int maxValue(Jeu jeuC, int profondeur, Coup coup)
	{
		ArrayList <Coup> coups = new ArrayList <Coup> ();
		int valeur = 0, val;
		
		if (profondeur ==0 || jeuC.isWin())
		{
			if (jeuC.getNiveau()=="debutant")
				valeur = evaluationD(jeuC);
			else valeur = evaluationI(jeuC);
		}
		else
		{
			valeur = -1000000;
			Jeu jClone = Cloner.cloner(jeuC);
			jClone.deplacer2(coup);
			
			for (int i = 0; i<jClone.getTaille(); i++)
			{
				for (int j = 0; j<jClone.getTaille(); j++)
				{
					if (jClone.getPions()[i][j] !=null)
					{
						if (jClone.getPions()[i][j].getCouleur() == Couleur.BLANC)
						{
							for (int k = 0; k<jClone.getPions()[i][j].getDeplacementsPossibles().size(); k++)
							{
								Coordonnee old = new Coordonnee (i,j);
								Coordonnee newC = new Coordonnee (jClone.getPions()[i][j].getDeplacementsPossibles().get(k).getX(), jClone.getPions()[i][j].getDeplacementsPossibles().get(k).getY()); 
								if (jClone.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise())
								{
									Coordonnee pionD = jClone.getPions()[i][j].getDeplacementsPossibles().get(k).getPionDetruit();
									coups.add(new Coup (old, newC, jClone.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise(), pionD));
								}
								else coups.add(new Coup (old, newC, jClone.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise()));
								
							}
						}
					}
				}		
			}
			for (int i = 0; i < coups.size(); i++)
			{
				val = minValue (jClone, profondeur -1, coups.get(i));
				if (val>valeur) valeur = val;  
			}
		}
		return valeur; 
		
	}
	public static int minValue(Jeu jeuC, int profondeur, Coup coup)
	{
		ArrayList <Coup> coups = new ArrayList <Coup> ();
		int valeur = 0, val;
		
		if (profondeur ==0 || jeuC.isWin())
		{
			if (jeuC.getNiveau()=="debutant")
				valeur = evaluationD(jeuC);
			else valeur = evaluationI(jeuC);
		}
		else
		{
			valeur = 1000000;
			Jeu jClone = Cloner.cloner(jeuC);
			jClone.deplacer2(coup);
			
			for (int i = 0; i<jClone.getTaille(); i++)
			{
				for (int j = 0; j<jClone.getTaille(); j++)
				{
					if (jClone.getPions()[i][j] !=null)
					{
						if (jClone.getPions()[i][j].getCouleur() == Couleur.NOIR)
						{
							for (int k = 0; k<jClone.getPions()[i][j].getDeplacementsPossibles().size(); k++)
							{
								Coordonnee old = new Coordonnee (i,j);
								
								Coordonnee newC = new Coordonnee (jClone.getPions()[i][j].getDeplacementsPossibles().get(k).getX(), jClone.getPions()[i][j].getDeplacementsPossibles().get(k).getY()); 
								if (jClone.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise())
								{
									Coordonnee pionD = jClone.getPions()[i][j].getDeplacementsPossibles().get(k).getPionDetruit();
									coups.add(new Coup (old, newC, jClone.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise(), pionD));
								}
								else coups.add(new Coup (old, newC, jClone.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise()));
								
							}
						}
					}
				}		
			}
			for (int i = 0; i < coups.size(); i++)
			{
				val = maxValue (jClone, profondeur -1, coups.get(i));
				if (val<valeur) valeur = val;  
			}
		}
		return valeur; 
	}
	/**
	 * Fonction evaluation pour le niveau debutant
	 * 
	 */
	public static int evaluationD (Jeu jeu)
	{
		
		int valeur=0; 
		
		//Si on gagne
		if (jeu.isWin() && jeu.getWinner().equals("IA")) valeur +=1000000;
		
		//On incremente de 1000 points par pions noirs presents
		//On decremente de 1000 points par pions blancs (enemies) presents
		valeur -= jeu.getCptBlanc()* 1500;
		valeur += jeu.getCptNoir() * 500;
		
		for (int i = 0; i <jeu.getTaille(); i++)
		{
			for (int j = 0; j<jeu.getTaille(); j++)
			{
				if(jeu.getPions()[i][j] != null)
				{
					//Noir
					//Pour chaque Dame (promotion), on incremente la valeur par 2000
					//Pour chaque pion qui n'est pas bloqué, on incremente par 300
					//Pour chaque pion qui est bloqué, on decremente de 750
					if (jeu.getPions()[i][j].getCouleur() == Couleur.NOIR)
					{
						if (jeu.getPions()[i][j].isPromoted()) valeur += 3000;
						if (jeu.getPions()[i][j].isMovable()) valeur += 500;
						if (!jeu.getPions()[i][j].isMovable()) valeur -= 750; 
					}					
					
				}
			}
		}
			
		return valeur;
	}
	
	/**
	 * Fonction d'evaluation pour le niveau intermediaire
	 * @param jeu
	 * @return
	 */
	public static int evaluationI (Jeu jeu)
	{
		int valeur=0; 
		
		//Si on gagne
		if (jeu.isWin() && jeu.getWinner().equals("IA")) valeur +=100000;
		
		//On cherche à défavoriser les prises risquées
		//On incremente de 750 points par pions noirs presents
		//On decremente de 1500 points par pions blancs (enemies) presents
		valeur -= jeu.getCptBlanc()* 1500;
		valeur += jeu.getCptNoir() * 750;
		
		for (int i = 0; i <jeu.getTaille(); i++)
		{
			for (int j = 0; j<jeu.getTaille(); j++)
			{
				if(jeu.getPions()[i][j] != null)
				{
					//Noir
					//Pour chaque Dame (promotion), on incremente la valeur par 2000
					//Pour chaque pion qui n'est pas bloqué, on incremente par 500
					//Pour chaque pion qui est bloqué, on decremente de 750
					if (jeu.getPions()[i][j].getCouleur() == Couleur.NOIR)
					{
						if (jeu.getPions()[i][j].isPromoted()) valeur += 1500;
						if (jeu.getPions()[i][j].isMovable()) valeur += 500;
						if (!jeu.getPions()[i][j].isMovable()) valeur -= 1250; 
					}
					
				}
			}
		}
			
		return valeur;
	}
}
