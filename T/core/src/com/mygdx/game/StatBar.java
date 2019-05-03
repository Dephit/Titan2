package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import static com.mygdx.game.MyMethods.LoadImg;
import static com.mygdx.game.MyMethods.createProceduralPixmap;
import static com.mygdx.game.MyMethods.format;
import static com.mygdx.game.MyMethods.textDrawing;

public class StatBar extends Actor {

    private RadialSprite radialSpriteBG, radialSprite;
    private  Texture tex2;
    private  Texture tex3;

    private float capacity=1f;

    private float currentAmount;

    private boolean isLeveled=false;

    private int level, maxLevel;
    private boolean radian=false;

    private boolean doTextLevel;
    private float level_text_x;
    private float level_text_y;
    private float level_text_scale;
    private Color text_level_color;

    private boolean doTextCurAmount;
    private float cur_amount_x, cur_amount_y, cur_amount_scale;
    private Color cur_amount_color;

    private boolean showAlways;

    StatBar(String name, float x, float y, float width, float height) {
        Pixmap pix2 = createProceduralPixmap(1, 1, 1, 0.5f, 0);
        tex2=LoadImg("statbar.png");

        Pixmap pix3 = createProceduralPixmap(1, 1, 1, 1, 0);
        tex3=new Texture(pix3);
        setLeveled(false,0,0);
        setName(name);
        setBounds(x,y,width,height);
        setCurrentAmount(0);
    }

    public boolean isShowAlways() {
        return showAlways;
    }

    public void setShowAlways(boolean showAlways) {
        this.showAlways = showAlways;
    }

    public void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

    public void makeLevelTextDrawning(boolean drawTextLevel, float level_text_x,float level_text_y, float level_text_scale,Color text_level_color){
        this.doTextLevel=drawTextLevel;
        this.level_text_x=level_text_x;
        this.level_text_y=level_text_y;
        this.level_text_scale=level_text_scale;
        this.text_level_color=text_level_color;
    }

    public void makeCurrentAmountTextDrawning(boolean drawCurAmountText, float cur_amount_x, float cur_amount_y, float cur_amount_scale, Color cur_amount_color){
        this.doTextCurAmount=drawCurAmountText;
        this.cur_amount_x=cur_amount_x;
        this.cur_amount_y=cur_amount_y;
        this.cur_amount_scale=cur_amount_scale;
        this.cur_amount_color=cur_amount_color;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isLeveled)
            check_lvl();
    }

    public boolean isLeveled() {
        return isLeveled;
    }

    public int getLevel() {
        return level;
    }

    private void check_lvl() {
        if(level>maxLevel){
            level=maxLevel;
            currentAmount=capacity;
        }else if(level<0){
            currentAmount=0;
            level=0;
        }
    }

    private void curr_amount_check() {
        if(currentAmount>capacity){
            if(isLeveled) {
                currentAmount=0;
                level++;
            }
            else
                currentAmount=capacity;
        }
        else if(currentAmount<0){
            if(isLeveled){
                currentAmount=capacity;
                level--;
            }
            else currentAmount=0;
        }
    }

    public void addProgress(float num) {
        float difference=0;
        if(isLeveled){
            if(num>0){
                while (num>0 && 1f - currentAmount>0){
                    difference = 1f - currentAmount;
                    currentAmount += num;
                    num -= difference;
                    if(num == 0)num = 0;
                }
            }else if(num<0){
                while (num<0 && level>0) {
                    difference = Math.abs(currentAmount);
                    currentAmount += num;
                    num += difference;
                }
            }
        }else currentAmount+=num;
        curr_amount_check();

    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void setLeveled(boolean leveled, int cur_level, int max_level) {
        isLeveled = leveled;
        level=cur_level;
        maxLevel=max_level;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(radian) {
            radialSpriteBG.draw(batch, getX(), getY(), getWidth(), getHeight());
            radialSprite.draw(batch, getX(), getY(), getWidth(), getHeight());
            radialSprite.setAngle(359 - 359 * currentAmount);
        }else {
            batch.draw(tex3, getX(), getY(), getWidth() * currentAmount, getHeight());
            batch.draw(tex2, getX(), getY(), getWidth(), getHeight());
        }

        if(isLeveled && doTextLevel){
            textDrawing(batch,level +"",level_text_x,level_text_y,level_text_scale,text_level_color);
        }
        if(doTextCurAmount){
            textDrawing(batch,format("%.0f",currentAmount*100),cur_amount_x,cur_amount_y,cur_amount_scale,cur_amount_color);
        }

    }

    public void makeRadian(Color color) {
        radian=true;
        TextureRegion tr=new TextureRegion();
        Texture tx=LoadImg("rad.png");
        Texture bg=LoadImg("bg.png");
        tr.setRegion(bg);
        radialSpriteBG =new RadialSprite(tr);
        tr.setRegion(tx);
        radialSprite =new RadialSprite(tr);
        radialSprite.setColor(color);
        radialSprite.setRoundEnded(true);
        radialSpriteBG.setColor(new Color(color.r,color.g,color.b,0.35f));
    }
}
