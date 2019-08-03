package com.mygdx.game;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.HashMap;
import java.util.Map;

import static com.mygdx.game.MyMethods.LoadImg;
import static com.mygdx.game.MyMethods.format;
import static com.mygdx.game.MyMethods.textDrawing;

public class StatBar extends Actor {

    private RadialSprite radialSpriteBG, radialSprite;
    private  Texture texMid;
    Sprite mid;
    private  Texture texBot;

    private float capacity = 1f;

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
    int timer = 100;
    private Texture texUp;

    StatBar(String name) {
        createTextures();
        setLeveled(false,0,0);
        setName(name);
        setCurrentAmount(0);
    }

    StatBar(String name, float x, float y, float width, float height) {
        createTextures();
        setLeveled(false,0,0);
        setName(name);
        setBounds(x,y,width,height);
        setCurrentAmount(0);
    }

    StatBar(String name, float x, float y, float width, float height, int cur_level, int maxLevel) {
        createTextures();
        setLeveled(true,cur_level,maxLevel);
        setName(name);
        setBounds(x,y,width,height);
        setCurrentAmount(0);
    }

    private void createTextures(){
        texUp = LoadImg("statUp.png");
        texMid = LoadImg("statMid.png");
        mid = new Sprite(texMid);
        texBot = LoadImg("statBot.png");
        setColor(Color.CORAL);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        mid.setColor(color);
    }

    boolean isShowAlways() {
        return showAlways;
    }

    void setShowAlways(boolean showAlways) {
        this.showAlways = showAlways;
        timer = 0;
    }

    void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

    void makeLevelTextDrawning(boolean drawTextLevel, float level_text_x, float level_text_y, float level_text_scale, Color text_level_color){
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
        if(isLeveled){
            check_lvl();
            curr_amount_check();
        }
        hide();
    }

    boolean isLeveled() {
        return isLeveled;
    }

    int getLevel() {
        return level;
    }

    private void check_lvl() {
        if(level > maxLevel){
            level = maxLevel;
            currentAmount = capacity;
        }else if(level < 0){
            currentAmount = 0;
            level = 0;
        }
    }

    private void curr_amount_check() {
        if(currentAmount > capacity){
            if(isLeveled) {
                currentAmount = 0;
                level++;
            } else currentAmount = capacity;
        } else if(currentAmount < 0){
            if(isLeveled){
                currentAmount = capacity;
                level--;
            } else currentAmount = 0;
        }
    }

    void addProgress(float num) {
        timer = 0;
        float difference = 0;
        if(isLeveled){
            if(num > 0){
                while (num > 0 && 1f - currentAmount >= 0){
                    difference = 1f - currentAmount;
                    currentAmount += num;
                    num -= difference;
                }
            }else if(num < 0){
                while (num < 0 && level >= 0) {
                    difference = Math.abs(currentAmount);
                    currentAmount += num;
                    num += difference;
                }
            }
        }else currentAmount += num;
        curr_amount_check();
    }

    float getCurrentAmount() {return currentAmount;}

    void setLeveled(boolean leveled, int cur_level, int max_level) {
        isLeveled = leveled;
        level = cur_level;
        maxLevel = max_level;
    }

    int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
            if (radian) {
                radialSpriteBG.draw(batch, getX(), getY(), getWidth(), getHeight());
                radialSprite.draw(batch, getX(), getY(), getWidth(), getHeight());
                radialSprite.setAngle(359 - 359 * currentAmount);
            } else {
                batch.draw(texBot, getX(), getY(), getWidth(), getHeight());
                mid.draw(batch);
                mid.setBounds( getX() + 5, getY() + 5, ((getWidth()  - 10) * currentAmount), getHeight() - 10);
                batch.draw(texUp, getX(), getY(), getWidth(), getHeight());
            }

            if (isLeveled && doTextLevel) {
                textDrawing(batch, level + "", level_text_x, level_text_y, level_text_scale, text_level_color);
            }
            if (doTextCurAmount) {
                textDrawing(batch, format("%.0f", currentAmount * 100), cur_amount_x, cur_amount_y, cur_amount_scale, cur_amount_color);
            }
    }

    private void hide() {
        timer += (showAlways ? 0 : 1);
        setVisible(timer < 100);
    }

    void makeRadian(Color color) {
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

    Map<String, Object> save(Preferences prefs) {
        Map<String, Object> data = new HashMap<>();
        data.put(getName()+ " capacity", capacity);
        prefs.putFloat(getName()+ "capacity",capacity);

        data.put(getName()+ " currentAmount", currentAmount);
        prefs.putFloat(getName()+ "currentAmount",currentAmount);

        data.put(getName()+ " level", level);
        prefs.putInteger(getName()+ "level",level);

        data.put(getName()+ " maxLevel", maxLevel);
        prefs.putInteger(getName()+ "maxLevel",maxLevel);

        data.put(getName()+ " showAlways", showAlways);
        prefs.putBoolean(getName()+ "showAlways",showAlways);
        prefs.flush();
        return data;
    }

    void load(Preferences prefs) {
        currentAmount = prefs.getFloat(getName()+ " currentAmount", currentAmount);
        level = prefs.getInteger(getName()+ " level", level);
    }

    void loadFromCloud(Map data) {
        currentAmount = ((Double) data.get(getName() + " currentAmount")).floatValue();
        level = ((Long) data.get(getName() +" level")).intValue();

    }
}
