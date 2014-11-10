package neva.eco.rules.ui;

import java.util.ArrayList;

import neva.eco.rules.layout.LayoutItem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RulesLabel extends Label implements ItemInf  {
	public Table table;
	boolean focused = false;
	String name;
	
	LayoutItem item;
	
	TextButton moveButton=null;	    
    TextButton deleteButton=null;	
    TextButton okButton=null;	
    TextButton editButton=null;	
    
    Skin skin;
    ScrollPane scrollPane2;

	/* (non-Javadoc)
	 * @see neva.eco.rules.ui.ItemInf#draw(com.badlogic.gdx.graphics.g2d.Batch, float)
	 */
	
	

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}

	public RulesLabel(CharSequence text, Skin skin) {
		super(text, skin);
		initialize ( skin); 
	}

	public RulesLabel(CharSequence text, Skin skin, String styleName) {
		super(text, skin, styleName);
		initialize ( skin); 
	}

	public RulesLabel(CharSequence text, Skin skin, String fontName, Color color) {
		super(text, skin, fontName, color);
		initialize ( skin); 
	}

	public RulesLabel(CharSequence text, Skin skin, String fontName,
			String colorName) {
		super(text, skin, fontName, colorName);
		initialize ( skin); 
	}
	
	/* (non-Javadoc)
	 * @see neva.eco.rules.ui.ItemInf#initialize(com.badlogic.gdx.scenes.scene2d.ui.Skin)
	 */
	@Override
	public void initialize ( final Skin skin) 
	{
		// menu
		this.skin = skin;

		moveButton = new TextButton ( "<>", skin );
		editButton = new TextButton ( "R", skin );
	    deleteButton = new TextButton ( "-", skin );	
	    okButton = new TextButton ( "ok", skin );	
	    	    
	    table = new Table();	
	    //table.setFillParent(true);
	    
	    table.add(this);
	    table.row();
	    table.add(moveButton);	 
	    table.add(editButton);	
	    table.add(deleteButton);
	    table.add(okButton);
	    
	    table.row();    
	    //table.setFillParent(true);
	    //table.setOrigin(250, 250);	    
	    //stage.addActor(table);
	    
	    okButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	          
	            //((Game)Gdx.app.getApplicationListener()).setScreen(new Splash());
	        	System.out.println("Ok clicked");       	
	        	scrollPane2.setVisible(false);
	        	setFocused(false);
	        }
	    });
	    
	    deleteButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	          
	            //((Game)Gdx.app.getApplicationListener()).setScreen(new Splash());
	        	System.out.println("Delete clicked");
	        }
	    });
	    
	    moveButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	            
	        	System.out.println("Move clicked");
	        }
	    });
	    
	    editButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	            
	        	System.out.println("Edit clicked");
	        	//RulesEditDialog ed = new RulesEditDialog("Rules", skin );
	        	// pas ideale table.addActor(ed);
	        	//ed.show(getStage());
	        	editRules ();
	        	
	        }
	    });
	    
	    this.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	          
	            System.out.println("RulesLabel clicked");
	        		        	
	        	setFocused(true);
	        	sendMessage ();
	        }
	    });
	    
	    if ( item != null)
	    	this.setText(item.title);    	    	    
	    
	}

	/* (non-Javadoc)
	 * @see neva.eco.rules.ui.ItemInf#isFocused()
	 */
	@Override
	public boolean isFocused() {
		return focused;
	}

	/* (non-Javadoc)
	 * @see neva.eco.rules.ui.ItemInf#setFocused(boolean)
	 */
	@Override
	public void setFocused(boolean focused) {
		okButton.setVisible(focused); 
    	moveButton.setVisible(focused); 
    	deleteButton.setVisible(focused); 
		this.focused = focused;
		
		if ( focused ) table.setZIndex(1000);
		else table.setZIndex(0);
	}
	
	public void editRules ()
	{	String[] listEntries = {"This is a list entry", "And another one", "The meaning of life", "Is hard to come by",
				"This is a list entry", "And another one", "The meaning of life", "Is hard to come by", "This is a list entry",           
		"Is hard to come by"};
		ListStyle listStyle = skin.get(ListStyle.class) ;
		
		List mylist = new List(listStyle );
		mylist.setItems(listEntries);
		scrollPane2 = new ScrollPane(mylist, skin );
		scrollPane2.setForceScroll(false, true);
		scrollPane2.setBounds(300, 300, 100, 90);
		table.getStage().addActor(scrollPane2);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}	

	public LayoutItem getItem() {
		return item;
	}

	public void setItem(LayoutItem item) {
		this.item = item;
	}

	java.util.List<ItemsListener> listeners = new java.util.ArrayList<ItemsListener>();

	public void addListener(ItemsListener toAdd) {
		listeners.add(toAdd);
	}

	public void sendMessage() {
		System.out.println("Hello!!");

		// Notify everybody that may be interested.
		for (ItemsListener hl : listeners)
			hl.handleMessage(this);
	}
	


}
