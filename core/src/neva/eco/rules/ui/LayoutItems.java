package neva.eco.rules.ui;

import java.util.HashMap;
import java.util.Map;

import neva.eco.rules.layout.Bound;

import com.badlogic.gdx.scenes.scene2d.InputEvent;



public class LayoutItems {
	public HashMap <String, ItemInf> items;

	public LayoutItems() {
		items = new HashMap <String, ItemInf> ();
	}
	
	public void setUnFocus ( String selectedItemName )
	{

		for (Map.Entry<String,ItemInf> e : items.entrySet()){			
			ItemInf item = e.getValue();
			System.out.println ("Item Name = " + item.getName());
			if ( !item.getName().equalsIgnoreCase(selectedItemName) )
			{
				item.setFocused(false);		
			}		
		}
	}

	public void touchDragged(InputEvent event, float x, float y,
    		int pointer) {
		for (Map.Entry<String,ItemInf> e : items.entrySet()){
			ItemInf item = e.getValue();
			if ( !item.getClass().equals("neva.eco.rules.ui.RulesLabel") )
			{
				RulesLabel it = (RulesLabel) e.getValue();		
				if ( it.isFocused())
		    	{		    		
		    		it.table.setBounds(x, y, 100, 30);
		    		it.getItem().setBound(new Bound ((long)x, (long)y, 100, 30 ));
		    	}	 
			}		
		}   	
    }

	public void add(RulesLabel nameLabel, String name) {
		items.put(name, nameLabel);
		
	}

}
