package up5.mi.su.jdd;

public class Coup {

	private Coordonnee oldCoord;
	private boolean prise;
	private Coordonnee newCoord;
	private Coordonnee pionDetruit;
	private int valeur; 
	
	/**
	 * Un coup est identifié par les coordonnées de la case de départ et les coordonnées de la case d'arrivée
	 * Et d'un boolean qui permet de savoir si ce coup est une prise
	 * @param oldC
	 * @param newC
	 * @param prise
	 */
	public Coup (Coordonnee oldC, Coordonnee newC, boolean prise)
	{
		this.setOldCoord(oldC);
		this.setNewCoord(newC);
		this.pionDetruit = null;
		this.prise = prise;
		setValeur(-1);
	}
	public Coup (Coordonnee oldC, Coordonnee newC, boolean prise, Coordonnee pionDetruit)
	{
		this.setOldCoord(oldC);
		this.setNewCoord(newC);
		this.setPionDetruit(pionDetruit);
		this.prise = prise;
		setValeur(-1);
	}

	public Coordonnee getNewCoord() {
		return newCoord;
	}

	public void setNewCoord(Coordonnee newCoord) {
		this.newCoord = newCoord;
	}

	public Coordonnee getOldCoord() {
		return oldCoord;
	}

	public void setOldCoord(Coordonnee oldCoord) {
		this.oldCoord = oldCoord;
	}

	public boolean isPrise() {
		return prise;
	}

	public void setPrise(boolean prise) {
		this.prise = prise;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	public Coordonnee getPionDetruit() {
		return pionDetruit;
	}

	public void setPionDetruit(Coordonnee pionDetruit) {
		this.pionDetruit = pionDetruit;
	}
	

}
