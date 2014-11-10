package neva.eco.rules.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;

public class RulesEditDialog extends Dialog {
	 String[] listEntries = {"This is a list entry", "And another one", "The meaning of life", "Is hard to come by",
             "This is a list entry", "And another one", "The meaning of life", "Is hard to come by", "This is a list entry",           
             "Is hard to come by"};
	
	 

	public RulesEditDialog(String title, Skin skin) {
		super(title, skin);	
		init ( skin );
	}

	public RulesEditDialog(String title, WindowStyle windowStyle) {
		super(title, windowStyle);		
	}

	public RulesEditDialog(String title, Skin skin, String windowStyleName) {
		super(title, skin, windowStyleName);	
		init ( skin );
	}
	
	// sera execute pour chaque constructeur
	void init (Skin skin)
	{
		text ("Edit Rules");
		
		 ListStyle listStyle = skin.get(ListStyle.class) ;
		 final List list = new List(listStyle );
		 list.setItems(listEntries);
         final ScrollPane scrollPane2 = new ScrollPane(list, skin );
         scrollPane2.setForceScroll(false, true);
                  
         //final SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, getStage(), skin.getStyle("default-horizontal",
         //        SplitPaneStyle.class), "split");
         add(scrollPane2);         
		
		button("Ok", true);
		button("Cancel", false);
		
		
	}

	@Override
	protected void result(Object object) {		
		super.result(object);
	}

}
