package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

import java.util.ArrayList;

import static com.mygdx.game.MyMethods.makeTextToDraw;
import static com.mygdx.game.MyMethods.textDrawing;

public class Window extends Actor{

   // private final Animation animation;
    private final GlyphLayout layout;
    Group thisGroup, textGroup;
    private String label;


    Window(float x, float y, float width, float height, String name) {
        //TODO Make window label
        thisGroup = new Group();
        textGroup = new Group();

        //
        //TODO unhardcore it
        label = "Выбирай!";
        layout = new GlyphLayout();
        layout.setText(FontFactory.font, label);
       // MyGdxGame.textToDrawMap.put(new String[]{"regrigirator"}, makeTextToDraw(label, getX() + getWidth() / 2 - layout.width / 2, getY() + getHeight() * 0.85f,  1, Color.BLACK));
        set((int)x, (int)y, (int)width, (int)height,name);
       // animation = createAnimation("screens/windows/window.png",1,1,1);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
      //  batch.draw((TextureRegion) animation.getKeyFrame(1),getX(),getY(),getWidth(),getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void dispose(){

    }

    void set(int x, int y, int width, int height, String name) {
        thisGroup.clear();
        textGroup.clear();
        setBounds(x, y, width, height);
        setName(name);
    }

    public void makeList(){
        ListView listView;listView = new ListView(2);
        if(getName().equals("refrigerator")){
            for (int i = 1; i < thisGroup.getChildren().size - 1; i++) {
                listView.add(thisGroup.getChildren().get(i));

            }
        }
        thisGroup.clear();
        for (Actor actor : listView.shownList) {
            thisGroup.addActor(actor);
        }

    }

    void refStuff(){
        for (Actor child : thisGroup.getChildren()) {
            if (child.getName().equals("potato"))
                thisGroup.removeActor(child);
        }
    }

    void ordrIt(){
        if(getName().equals("refrigerator")){
            int y = (int) thisGroup.getChildren().get(1).getY() + (int) thisGroup.getChildren().get(1).getHeight()+ 15,
                x = (int) thisGroup.getChildren().get(1).getX(),
                dX = 0, dY = 1;
            for (int i = 1; i < thisGroup.getChildren().size - 1 ; i++) {
                if(thisGroup.getChildren().get(i).getName().equals("potato"))
                    if(MyGdxGame.playerStats.potatoAmount <= 0)
                        thisGroup.getChildren().get(i).setVisible(false);
                    else
                        thisGroup.getChildren().get(i).setVisible(true);

                if(thisGroup.getChildren().get(i).getName().equals("nuggets"))
                    if(MyGdxGame.playerStats.nuggetsAmount <= 0)
                        thisGroup.getChildren().get(i).setVisible(false);
                    else
                        thisGroup.getChildren().get(i).setVisible(true);

                    if(thisGroup.getChildren().get(i).isVisible()){
                    thisGroup.getChildren().get(i).setX(x + (thisGroup.getChildren().get(i).getWidth() + 20) * dX);
                    thisGroup.getChildren().get(i).setY(y - (thisGroup.getChildren().get(i).getHeight() + 15) * dY);

                    if(dX >= 1){
                        dX = 0;
                        dY ++;
                    }else
                        dX++;


                    if(thisGroup.getChildren().get(i).getName().equals("potato")){
                        textGroup.addActor(makeTextToDraw("х" + MyGdxGame.playerStats.potatoAmount, thisGroup.getChildren().get(i).getX() + thisGroup.getChildren().get(i).getWidth() * 0.67f
                                , thisGroup.getChildren().get(i).getY() + thisGroup.getChildren().get(i).getHeight() * 0.27f,  1, Color.BLACK));
                    }
                    if(thisGroup.getChildren().get(i).getName().equals("nuggets")){
                        textGroup.addActor(makeTextToDraw("х" + MyGdxGame.playerStats.nuggetsAmount, thisGroup.getChildren().get(i).getX() + thisGroup.getChildren().get(i).getWidth() * 0.67f
                                , thisGroup.getChildren().get(i).getY() + thisGroup.getChildren().get(i).getHeight() * 0.27f,  1, Color.BLACK));
                    }

                }
            }
        }
        if(!thisGroup.getChildren().contains(textGroup,true)){
            thisGroup.addActor(textGroup);
        }
    }

    class ListView{
        ArrayList<Actor> allList = new ArrayList<>(),
                        shownList = new ArrayList<>();
        int capacity, currentList;

        public ListView(int capacity) {

            this.capacity = capacity;

            swipeInit();
        }

        void swipeInit(){
            Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

                @Override
                public void onUp() {
                    doList();
                }

                @Override
                public void onRight() {

                }

                @Override
                public void onLeft() {

                }

                @Override
                public void onDown() {

                }
            }));
        }

        public void add(Actor actor) {
            allList.add(actor);
            if(shownList.size() < capacity){
                shownList.add(actor);
            }
            for (int i = 0; i < shownList.size() - 1; i++) {
                shownList.get(i).setY(300 + shownList.get(i).getHeight() * i);
            }
        }

        public void doList(){
            int idx = allList.indexOf(shownList.get(shownList.size() - 1));
            shownList.clear();

            while (idx + 1 < allList.size() /*&& shownList.size() < capacity*/){
                System.out.println(allList.size());
                System.out.println(shownList.size());
                shownList.add(allList.get(idx + 1));
                idx ++;
            }



        }
    }

}

