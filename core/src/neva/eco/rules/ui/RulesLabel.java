package neva.eco.rules.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RulesLabel extends Label {

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}

	public RulesLabel(CharSequence text, Skin skin) {
		super(text, skin);
		// TODO Auto-generated constructor stub
	}

	public RulesLabel(CharSequence text, LabelStyle style) {
		super(text, style);
		// TODO Auto-generated constructor stub
	}

	public RulesLabel(CharSequence text, Skin skin, String styleName) {
		super(text, skin, styleName);
		// TODO Auto-generated constructor stub
	}

	public RulesLabel(CharSequence text, Skin skin, String fontName, Color color) {
		super(text, skin, fontName, color);
		// TODO Auto-generated constructor stub
	}

	public RulesLabel(CharSequence text, Skin skin, String fontName,
			String colorName) {
		super(text, skin, fontName, colorName);
		// TODO Auto-generated constructor stub
	}

}
