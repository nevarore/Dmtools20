package neva.eco.rules.ui;

import java.util.ArrayList;
import java.util.List;

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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RulesLabel extends Label implements ItemInf  {
	public Table table;
	boolean focused = false;
	String name;
	
	LayoutItem item;

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
	public void initialize ( Skin skin) 
	{
		// menu

		final TextButton moveButton = new TextButton ( "<->", skin );	    
	    final TextButton deleteButton = new TextButton ( "-", skin );	
	    final TextButton okButton = new TextButton ( "ok", skin );	       
	    
	    table = new Table();	
	    //table.setFillParent(true);
	    
	    table.add(this);
	    table.row();
	    table.add(moveButton);	    
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
	        	okButton.setVisible(false); 
	        	moveButton.setVisible(false); 
	        	deleteButton.setVisible(false); 
	        	
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
	    
	    this.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	          
	            System.out.println("RulesLabel clicked");
	        	okButton.setVisible(true); 
	        	moveButton.setVisible(true); 
	        	deleteButton.setVisible(true); 
	        	
	        	setFocused(true);
	        	sendMessage ();
	        }
	    });

	    	    	    
	    
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
		this.focused = focused;
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

	List<ItemsListener> listeners = new ArrayList<ItemsListener>();

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
