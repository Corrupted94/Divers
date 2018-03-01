
import java.util.ArrayList;
import java.util.Random;

public class ChooseForMe {

	private ArrayList <String> list = new ArrayList<String>();
	
	public ChooseForMe (ArrayList<String> list)
	{
		this.list = list;
	}
	
	public String randomChampion (int n) 
	{
		
		StringBuffer sb = new StringBuffer ("Ranking : ");
		ArrayList <Integer>listNumber = new ArrayList<Integer>();
		int taille = list.size();
		Random rand = new Random ();
		int nombreAleatoire;
		
		for (int i= 0; i<list.size(); i++)
		{
			listNumber.add(0);
		}
		
		for (int i = 0; i <n; i++)
		{
			nombreAleatoire = rand.nextInt(taille);
			listNumber.set(nombreAleatoire, listNumber.get(nombreAleatoire)+1);
		}
		
		/*int k = list.indexOf("Lulu");
		listNumber.set(k, listNumber.get(k)+25);*/
		
		
		/* ------------------Filtrer-----------------------*/
		
		int indiceMax=0;
		int temp; String tempS;
		for (int i = 0; i<listNumber.size()-1; i++)
		{
			for (int j =i+1; j<listNumber.size(); j++)
			{
				indiceMax = (listNumber.get(indiceMax) > listNumber.get(j)) ? indiceMax : j;
			}
			if (indiceMax != i)
			{
				temp = listNumber.get(indiceMax);
				tempS = list.get(indiceMax);
				
				listNumber.set(indiceMax, listNumber.get(i));
				list.set(indiceMax, list.get(i));
				
				listNumber.set(i, temp);
				list.set(i, tempS);
				
			}
		}
		
		for (int i = 0; i<list.size(); i++)
		{
			sb.append("\n" + list.get(i) + "\t\t" + listNumber.get(i));
		}
		
		return sb.toString();
	}
}
