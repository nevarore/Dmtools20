package neva.eco.rules;

import java.util.HashMap;
import java.util.Map;

import neva.eco.rules.json.LayoutJsonReader;
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
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen, ItemsListener {
	final DmTools game;
    OrthographicCamera camera;
    
    private Stage stage;
	private Skin skin;
	private LayoutItems items;
		
 
    public MainScreen(DmTools g) {
        this.game = g;
                
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		
		loadLayout ();      	
	    
	    
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
	    
	    Table table = new Table();	
	    table.setBounds(50, 200, 200, 200);
	    
	    table.add(addressLabel);
	    table.row();
	    table.add(addressText).width(100);
	    table.row();
	    table.add(plusButton).width(100);
	    table.row();    
	    //table.setFillParent(true);
	    //table.setOrigin(100, 500);
	    
	    stage.addActor(table);
	    
	    
	    // multi input test
	    /*InputMultiplexer inputMultiplexer = new InputMultiplexer(Gdx.input.getInputProcessor());
        //inputMultiplexer.addProcessor(new IsoCamController());
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);*/

	    
	    
	    
	    
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
	
	public void loadLayout ()
	{
		items = new LayoutItems();
		
		HashMap<String, LayoutItem> var;
		try {
			var = LayoutJsonReader.jsonReader("bin/layout.txt");
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
		    items.add ( label, v.name);	    	    
		    stage.addActor(label.table);
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
