package up5.mi.su.jdd;

import java.util.ArrayList;

public class Pion implements Cloneable{

	private Couleur couleur;
	private String id;
	private boolean canBeMoved;
	private boolean promoted = false;
	private ArrayList <Coordonnee> deplacementsPossibles;
	
	
	public Pion (String id, Couleur couleur, boolean move)
	{
		this.id = id;
		this.couleur = couleur;
		canBeMoved = move;
		this.deplacementsPossibles = new ArrayList<Coordonnee> ();
	}

	public boolean isMovable() {
		return canBeMoved;
	}

	public void setCanBeMoved(boolean canBeMoved) {
		this.canBeMoved = canBeMoved;
	}

	public Couleur getCouleur() {
		return couleur;
	}

	public boolean isPromoted() {
		return promoted;
	}

	public void setPromoted(boolean promoted) {
		this.promoted = promoted;
	}

	public String getId() {
		return id;
	}

	public ArrayList<Coordonnee> getDeplacementsPossibles() {
		return deplacementsPossibles;
	}
	
	public void setDeplacementsPossibles(ArrayList<Coordonnee> deplacementsPossibles) {
		this.deplacementsPossibles = deplacementsPossibles;
	}

	public Object clone ()
	{
		Pion pionclone= null;
		try {
			pionclone = (Pion) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pionclone;
	}
}

