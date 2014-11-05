package neva.eco.rules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainScreen implements Screen {
	final DmTools game;
    OrthographicCamera camera;
    
    private Stage stage;
	private Skin skin;

    public MainScreen(DmTools g) {
        this.game = g;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
	    
	    skin = new Skin(Gdx.files.internal("data/uiskin.json"));
	    final Label nameLabel = new Label("Name:", skin);
	    	    
	    stage.addActor(nameLabel);
	    
	   	    
	    stage.addListener(new InputListener() {
	    	boolean labelTouch = false;
	    	
	        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	            //System.out.println("down");
	            //nameLabel.addAction(Actions.moveTo(x, y, 0.1f));

	            labelTouch = true;
	            return true;
	        }

	        public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        	labelTouch = false;
	        	//System.out.println("up");
	        }
	        
	        @Override
	        public void touchDragged(InputEvent event, float x, float y,
	        		int pointer) {
	        	nameLabel.setBounds(x, y, 100, 30);
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
	    TextButton plusButon = new TextButton ( "Add", skin );	    
	    
	    Table table = new Table();	
	    table.setBounds(50, 200, 200, 200);
	    
	    table.add(addressLabel);
	    table.row();
	    table.add(addressText).width(100);
	    table.row();
	    table.add(plusButon).width(100);
	    table.row();    
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

}
