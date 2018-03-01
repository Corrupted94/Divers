package up5.mi.su.jdd;

public class Coordonnee implements Cloneable{

	private int x;
	private int y;
	private boolean prise = false;
	private Coordonnee pionDetruit; 
	
	public Coordonnee (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	public Coordonnee (int x, int y, boolean prise, Coordonnee pionDetruit)
	{
		this.x = x;
		this.y = y;
		this.prise = prise;
		this.pionDetruit = pionDetruit;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public String toString ()
	{
		return x + "\t" + y;
	}

	public boolean isPrise() {
		return prise;
	}

	public void setPrise(boolean prise) {
		this.prise = prise;
	}
	
	public Object clone ()
	{
		Coordonnee coordclone= null;
		try {
			coordclone = (Coordonnee) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return coordclone;
	}
	public Coordonnee getPionDetruit() {
		return pionDetruit;
	}
	public void setPionDetruit(Coordonnee pionDetruit) {
		this.pionDetruit = pionDetruit;
	}
}
