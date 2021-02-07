package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MyTextButton extends TextButton {
    public MyTextButton(String text, Skin skin) {
        super(text, skin);
    }

    public MyTextButton(String text, Skin skin, String styleName) {
        super(text, skin, styleName);
    }

    public MyTextButton(String text, TextButtonStyle style) {
        super(text, style);
    }
}
