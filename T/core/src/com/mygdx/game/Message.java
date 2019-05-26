package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.MyMethods.LoadImg;
import static com.mygdx.game.MyMethods.textDrawing;

public class Message extends Actor{
    private final String text;
    private boolean flip = false;
    int x, y, textX, textY;
    float width = 31 * 5, heigth = 16 * 5, mult = 1.5f;
    Texture message=LoadImg("screens/message.png");

    public Message(int x, int y, String text, boolean flip) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.flip = flip;
        manageSize();
    }

    private void manageSize() {
        GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(FontFactory.font, text);
        float width = layout.width;// contains the width of the current set text
        float height = layout.height; // contains the height of the current set text
        if(!flip)
            textX = (int) (x + (this.width * 0.95f) * mult / 2 - width / 2);
        else textX = (int) (x + (this.width * 1.05f) * mult / 2 - width / 2);
        textY = (int) (y + (this.heigth ) * mult / 2 + height / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(Gdx.input.isTouched())
            this.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(!flip)
            batch.draw(message, x, y,  width * mult, heigth * mult );
            else batch.draw(message, x + width * mult, y ,  -width * mult, heigth * mult );

        textDrawing(batch, text,textX,textY,1, Color.BLACK);
    }
}