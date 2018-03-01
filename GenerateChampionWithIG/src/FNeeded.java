import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FNeeded {

	public static String afficheListChamp () throws IOException
	{
		StringBuffer sb = new StringBuffer("List des champions dans le fichier : \n");
		File fic = new File ("listChamps.txt");
		FileInputStream fis = new FileInputStream (fic);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String nextLine;
		while ((nextLine = br.readLine()) != null)
		{
			sb.append(nextLine + "\n"); 
		}
		
		/*for (int i = 0; i <list.size(); i++)
		{
			System.out.println(list.get(i));
		}*/
		br.close();
		
		return sb.toString();
	}
	
	public static ArrayList <String> getList () throws IOException
	{
		File fic = new File ("listChamps.txt");
		FileInputStream fis = new FileInputStream (fic);
		ArrayList <String> list = new ArrayList <String> ();
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		String nextLine;
		while ((nextLine = br.readLine()) != null)
		{
			list.add(nextLine);
		}
		
		/*for (int i = 0; i <list.size(); i++)
		{
			System.out.println(list.get(i));
		}*/
		br.close();
		
		return list;
	}
	public static String addEntry (String s) throws IOException
	{
		ArrayList <String> l = getList();
		if (!l.contains(s))
		{
			FileWriter fw = new FileWriter ("listChamps.txt", true); //boolean = true donnée ajouté à la fin.
			fw.write(s);
		
			fw.close();
			
			return "Le champion : " + s + " a été ajouté dans le fichier";
		}
		else return "Le champion : " + s + " existe déjà dans le fichier : FAIL";
				
	}
	
	public static String deleteEntry (String s)throws IOException
	{
		ArrayList <String> l = getList();
		File fic = new File ("listChamps.txt");
		FileOutputStream fos = new FileOutputStream (fic);
		BufferedWriter bw = new BufferedWriter (new OutputStreamWriter (fos));
		if (l.contains(s))
		{
			l.remove(s);
			
			for (int i = 0; i <l.size(); i++)
			{		
				bw.write(l.get(i) + "\n"); 
			}
			
			bw.close();
			
			return "Le champion : " + s + " a été supprimer du fichier";
		}
		
		else 
		{
			bw.close();
			return "Le champion : " + s + " n'existe pas dans le fichier : FAIL";
		}
		
	}
}
