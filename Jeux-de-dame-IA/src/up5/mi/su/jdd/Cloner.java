package up5.mi.su.jdd;


public class Cloner {

	public static Jeu cloner (Jeu jeu)
	{
		Jeu jClone;
		
		jClone = new Jeu (jeu.getNiveau());
		jClone.setCptBlanc(jeu.getCptBlanc());
		jClone.setCptNoir(jeu.getCptNoir());
		jClone.setTourBlanc(jeu.isTourBlanc());
		jClone.setWinner(jeu.getWinner());
		jClone.setWin(jeu.isWin());
		
		for (int i = 0; i<jeu.getTaille(); i++)
		{
			for (int j =0; j<jeu.getTaille(); j++)
			{
				if (jeu.getPions()[i][j]!=null)
				{
					jClone.getPions()[i][j] = new Pion (jeu.getPions()[i][j].getId(), jeu.getPions()[i][j].getCouleur(), jeu.getPions()[i][j].isMovable());
					jClone.getPions()[i][j].setPromoted(jeu.getPions()[i][j].isPromoted());
					for (int k = 0; k<jeu.getPions()[i][j].getDeplacementsPossibles().size(); k++)
					{
						jClone.getPions()[i][j].getDeplacementsPossibles().add(new Coordonnee(jeu.getPions()[i][j].getDeplacementsPossibles().get(k).getX(), jeu.getPions()[i][j].getDeplacementsPossibles().get(k).getY(), jeu.getPions()[i][j].getDeplacementsPossibles().get(k).isPrise(), jeu.getPions()[i][j].getDeplacementsPossibles().get(k).getPionDetruit()));
					}
				}
				
				else jClone.getPions()[i][j] = null;
				
			}
		}
		return jClone;	
	}
}
