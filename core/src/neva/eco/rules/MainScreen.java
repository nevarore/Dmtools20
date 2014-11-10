package neva.eco.rules;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import neva.eco.rules.core.Cell;
import neva.eco.rules.core.TableCell;
import neva.eco.rules.core.Variable;
import neva.eco.rules.files.TableTextReader;
import neva.eco.rules.json.LayoutJsonReader;
import neva.eco.rules.json.RulesJsonReader;
import neva.eco.rules.layout.LayoutItem;
import neva.eco.rules.ui.ItemInf;
import neva.eco.rules.ui.ItemsListener;
import neva.eco.rules.ui.LayoutItems;
import neva.eco.rules.ui.RulesLabel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen, ItemsListener {
	final DmTools game;
    OrthographicCamera camera;
    
    private Stage stage;
	private Skin skin;
	private ScreenViewport screen;
	
	private LayoutItems items;
	
	HashMap <String, TableCell> table;
	HashMap<String, Variable> var;
		
 
    public MainScreen(DmTools g) {
        this.game = g;
                
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        screen = new ScreenViewport();
        stage = new Stage(screen);
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		initTable ();
		loadInitialLayout ();    
		loadRules ();
	    
	    
	    final RulesLabel nameLabel = new RulesLabel("label 1:", skin);
	    nameLabel.table.setBounds(150, 200, 200, 200);
	    nameLabel.addListener(this);
	    nameLabel.setName("label1");
	    items.add ( nameLabel, "label1");	    	    
	    stage.addActor(nameLabel.table);
	    
	   	    
	    stage.addListener(new InputListener() {	    	
	    	
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            //System.out.println("down");
	            //nameLabel.addAction(Actions.moveTo(x, y, 0.1f));	        	
	            
	            return true;
	        }

	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	
	        	//System.out.println("up");
	        }
	        
	        @Override
	        public void touchDragged(InputEvent event, float x, float y,
	        		int pointer) {
	        		        	
	        	items.touchDragged(event, x, y, pointer);	        	
	        	super.touchDragged(event, x, y, pointer);
	        }
	        
	        // ne pas implementer public boolean mouseMoved(InputEvent event, float x, float y)
	        // car entre en coflit avec touch 
	       
	        
	    });
	    
	    addMenu();
	       
	  
	    
	    // multi input test
	    /*InputMultiplexer inputMultiplexer = new InputMultiplexer(Gdx.input.getInputProcessor());
        //inputMultiplexer.addProcessor(new IsoCamController());
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);*/

	    assignRules2Layout ();
	    
	    
	    
    }
    
    public void addMenu ()
    {
    	//*********************************************************************
	    // menu
	    TextField nameText = new TextField("tet", skin);
	    nameText.setBounds(10, 10, 100, 30);
	    Label addressLabel = new Label("Address:", skin);
	    TextField addressText = new TextField("", skin);
	    
	    TextButton plusButton = new TextButton ( "Add", skin );	   
	    plusButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	            
	        	System.out.println("New layout item");
	        	final RulesLabel rule = new RulesLabel("Label 2:", skin);
	        	rule.table.setBounds(250, 200, 200, 200);
	        	rule.setName("label 2");
	        	items.add(rule, "label 2");
	        	stage.addActor(rule.table);	 
	        	rule.addListener(MainScreen.this);
	        }
	    });
	    
	    TextButton evalButton = new TextButton ( "Eval", skin );	   
	    evalButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	            
	        	System.out.println("Eval");
	        	evalRules();
	        	assignRules2Layout();
	        	
	        }
	    });
	    
	    TextButton loadButton = new TextButton ( "Load", skin );	   
	    loadButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	            
	        	System.out.println("Load");
	        	loadLayout();
	        	assignRules2Layout();
	        	
	        }
	    });
	    
	    TextButton saveButton = new TextButton ( "Save", skin );	   
	    saveButton.addListener(new ClickListener(){
	        @Override
	        public void clicked(InputEvent event, float x, float y) {	            
	        	System.out.println("Save");
	        	saveLayout();
	        	assignRules2Layout();
	        	
	        }
	    });
	    
	    Table table = new Table();	
	    table.setBounds(50, screen.getScreenHeight()-40, 400, 40);
	    
	    //table.add(addressLabel);
	    //table.row();
	    //table.add(addressText).width(100);
	    //table.row();
	    table.add(plusButton).width(100);
	    table.add(evalButton).width(100);
	    table.add(loadButton).width(100);
	    table.add(saveButton).width(100);
	    
	    //table.setFillParent(true);
	    //table.setOrigin(100, 500);
	    
	    stage.addActor(table);
    }
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act(delta);
	    stage.draw();
	    
	    //System.out.println ("render delta");
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		stage.dispose();
		
	}
	
	
	public void initTable ()
	{				
		
		table = new HashMap<String, TableCell> ();
		try {
			table.put("xp", TableTextReader.readFileScanner ( new File ("xp.txt"), 3) );
			table.put("cleric", TableTextReader.readFileScanner ( new File ("cleric.txt"), 13) );
			table.put("armor", TableTextReader.readFileScanner ( new File ("armor.txt"), 7) );
			table.put("weapons", TableTextReader.readFileScanner ( new File ("armor.txt"), 5) );
			table.put("cleric spells", TableTextReader.readFileScanner ( new File ("cleric spells.txt"), 9) );
			table.put("wizard spells", TableTextReader.readFileScanner ( new File ("wizards spells.txt"), 11) );
			table.put("abilityScore", TableTextReader.readFileScanner ( new File ("abilityScore.txt"), 2) );
			table.put("Classes", TableTextReader.readFileScanner ( new File ("classes.txt"), 6) );
			
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		

	}
	
	public void loadInitialLayout ()
	{
		items = new LayoutItems();
		
		HashMap<String, LayoutItem> var;
		try {
			var = LayoutJsonReader.jsonReader("layout.txt");
		} catch (Exception e1) {			
			e1.printStackTrace();
			return;
		}
		
		// eval
		for (Map.Entry<String,LayoutItem> e : var.entrySet()){
			LayoutItem v = e.getValue();
			System.out.println ("=========== Name " + v.name + " ===========");						
			System.out.println ("Item: " + v.title + " = [" + v.layoutClassName + "] ->" + v.variableName);
			
			RulesLabel label = new RulesLabel(v.name, skin);
			label.setItem(v);
			
			label.table.setBounds(v.bound.x, v.bound.y, v.bound.sizex,v.bound.sizey);
			label.addListener(this);
			label.setName("label1");
			label.setFocused(false);
		    items.add ( label, v.name);		    
		    stage.addActor(label.table);
		}
		
	}
	
	public void loadLayout ()
	{
		items = new LayoutItems();
		stage.getActors().clear();
		addMenu();
		
		HashMap<String, LayoutItem> layout;
		try {
			layout = LayoutJsonReader.jsonReader("layout_w.txt");
		} catch (Exception e1) {			
			e1.printStackTrace();
			return;
		}
		
		// eval
		for (Map.Entry<String,LayoutItem> e : layout.entrySet()){
			LayoutItem v = e.getValue();
			System.out.println ("=========== Name " + v.name + " ===========");						
			System.out.println ("Item: " + v.title + " = [" + v.layoutClassName + "] ->" + v.variableName);
			
			RulesLabel label = new RulesLabel(v.name, skin);
			label.setItem(v);
			
			label.table.setBounds(v.bound.x, v.bound.y, v.bound.sizex,v.bound.sizey);
			label.addListener(this);
			label.setName("label1");
			label.setFocused(false);
		    items.add ( label, v.name);		    
		    stage.addActor(label.table);
		}
		
	}
	
	
	public void saveLayout ()
	{
		HashMap<String, LayoutItem> layout = new HashMap<String, LayoutItem>();

		// eval
		for (Entry<String, ItemInf> e : items.items.entrySet()){
			RulesLabel v = (RulesLabel) e.getValue();
			LayoutItem it = v.getItem();
			if ( it != null && it.title != null)
				layout.put(it.title, it);
			else
				System.out.println ("Item not saved");
		}

		System.out.println ("=========== test WriterJSON ===========");
		LayoutJsonReader.jsonWriter("layout_w.txt", layout);
	}

	
	public void loadRules ()
	{
		System.out.println ("=========== test JSON ===========");
		
		try {
			var = RulesJsonReader.jsonReader("variables.txt", table);
		} catch (InstantiationException e1) {			
			e1.printStackTrace();
			return;
		} catch (IllegalAccessException e1) {			
			e1.printStackTrace();
			return;
		} catch (IOException e1) {			
			e1.printStackTrace();
			return;
		}

		// eval
		for (Map.Entry<String,Variable> e : var.entrySet()){
			Variable v = e.getValue();
			System.out.println ("=========== Eval " + v.name + " ===========");
			if ( !v.rules.isAlreadyEval() || v.repeatable ) v.eval ( var, table);

			System.out.println ("Variable: " + v.name + " = [" + v.value.getsValue() + "] ->" + v.value.getnValue());
		}

		// final var status 
		System.out.println ("=========== Final Result ===========");
		for (Map.Entry<String,Variable> e : var.entrySet()){
			Variable v = e.getValue();

			System.out.println ("Variable: " + v.name + " = [" + v.value.getsValue() + "] ->" + v.value.getnValue());
			if ( v.rules.getCell() != null)
			{
				for ( int i = 0; i<v.rules.getCell().length; i++)
				{
					System.out.println ("=========> " + v.rules.getCell()[i].getsValue() );
				}
			}
		}
	}
	
	public void evalRules ()
	{
		System.out.println ("=========== Eval Rules ===========");		
		
		// eval
		for (Map.Entry<String,Variable> e : var.entrySet()){
			Variable v = e.getValue();
			System.out.println ("=========== Eval " + v.name + " ===========");
			if ( !v.rules.isAlreadyEval() || v.repeatable ) v.eval ( var, table);

			System.out.println ("Variable: " + v.name + " = [" + v.value.getsValue() + "] ->" + v.value.getnValue());
		}

		// final var status 
		System.out.println ("=========== Final Result ===========");
		for (Map.Entry<String,Variable> e : var.entrySet()){
			Variable v = e.getValue();

			System.out.println ("Variable: " + v.name + " = [" + v.value.getsValue() + "] ->" + v.value.getnValue());
			if ( v.rules.getCell() != null)
			{
				for ( int i = 0; i<v.rules.getCell().length; i++)
				{
					System.out.println ("=========> " + v.rules.getCell()[i].getsValue() );
				}
			}
		}
	}
	
	public void assignRules2Layout ()
	{
		System.out.println ("========= assign Rules 2 Layout ======================" );		
		
		for (Map.Entry<String,ItemInf> e : items.items.entrySet()){			
					
			if ( e.getValue().getClass().getName().equals("neva.eco.rules.ui.RulesLabel"))
			{
				RulesLabel r = (RulesLabel) e.getValue();
				// look for 
				if ( r.getItem() != null  )
				{	
					String varName = r.getItem().variableName;
					// look for 
					Variable v = var.get(varName);
					if ( v != null )
					{
						r.getItem().title = v.value.getsValue();
						r.setText(v.value.getsValue());
						System.out.println ("Assign" + v.value.getsValue() + " to "  + r.getName () );
					}
				}

			}
		}
			
	}

	
	
	
	public class IsoCamController extends InputAdapter {
        @Override
        public boolean touchDragged (int x, int y, int pointer) {
        	System.out.println ("Iso cam down");
			return false;
         // stuff
        }
        public boolean touchUp (int x, int y, int pointer) {
        	System.out.println ("Iso cam up");
			return false;
         // stuff
        }
}

	@Override
	public void handleMessage( ItemInf item) {		
		System.out.println ("MainScreen receive message");
		items.setUnFocus(item.getName());
	}


}
