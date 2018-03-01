package up5.mi.su.jdd;

public enum Couleur {
	
	BLANC ("BLANC"),
	NOIR ("NOIR")
	;
	
	private String couleur;
	
	private Couleur (String str)
	{
		this.couleur = str;
	}
	
	public String toString ()
	{
		return couleur;
	}
}




