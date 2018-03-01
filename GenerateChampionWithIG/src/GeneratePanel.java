import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;


public class GeneratePanel extends BorderPane{

	Label title = new Label ("GENERATE CHAMPION");
	Button printListChamp = new Button ("Show List");
	Label lab_try = new Label ("NB TRY");
	TextField tf_try = new TextField ();
	Button b_try = new Button ("GENERATE");
	TextArea ta_console = new TextArea ();
	
	Label lab_add = new Label ("Champ to Add/Delete");
	TextField tf_add = new TextField ();
	Button b_add = new Button ("ADD");
	Button b_del = new Button ("DEL");
	
	public GeneratePanel()
	{
		GridPane gp = new GridPane ();
		ta_console.setPrefSize(600, 400);
		gp.add(title, 2, 0);
		gp.add(printListChamp, 0, 2);
		gp.add(lab_try, 0, 3);
		gp.add(tf_try, 1, 3);
		gp.add(b_try, 2, 3);
		gp.add(lab_add, 0, 5);
		gp.add(tf_add, 1, 5);
		gp.add(b_add, 2, 5);
		gp.add(b_del, 3, 5);
		
		this.setCenter(gp);
		this.setBottom(ta_console);
		
		printListChamp.setOnAction(event ->
		{
			ta_console.clear();
			try {
				ta_console.setText(FNeeded.afficheListChamp());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		b_try.setOnAction(event ->
		{
			
			if (tf_try.getText()!=null) 
			{
				int k= Integer.parseInt(tf_try.getText());
				if (k == (int) k)
				{
					try {
						ChooseForMe cfm = new ChooseForMe (FNeeded.getList());
						ta_console.clear();
						ta_console.setText(cfm.randomChampion(k));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				else ta_console.setText("I need a int value\n" );
			}
			
		});
		
		b_add.setOnAction(new AddDel());
		b_del.setOnAction(new AddDel());
		
	}
	
	
	class AddDel implements EventHandler <ActionEvent>
	{

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if (event.getSource() == b_add) 
			{
				try {
					ta_console.setText(FNeeded.addEntry(tf_add.getText()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if (event.getSource() == b_del)
			{
				try {
					ta_console.setText(FNeeded.deleteEntry(tf_add.getText()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
