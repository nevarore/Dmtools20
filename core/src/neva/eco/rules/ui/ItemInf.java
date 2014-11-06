package neva.eco.rules.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public interface ItemInf {

	public abstract void draw(Batch batch, float parentAlpha);

	public abstract void initialize(Skin skin);

	public abstract boolean isFocused();

	public abstract void setFocused(boolean focused);
	
	public abstract String getName() ;
	public abstract void setName(String name);
		

}