package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

import static com.mygdx.game.PlayerCondition.bench;
import static com.mygdx.game.PlayerCondition.deadlift;
import static com.mygdx.game.PlayerCondition.legPress;
import static com.mygdx.game.PlayerCondition.squat;

public class Npc extends BaseActor {


    public PlayerCondition playerCondition = PlayerCondition.stay;
    private PlayerCondition nextPlayerCondition = PlayerCondition.stay;

    private final Vector2 speed;
    public Vector2 lastWalkablePosition = new Vector2();
    private final TextureAtlas textureAtlas;
    private final Map<String, Animation<TextureRegion>> animMap;
    protected final TextureRegion currentFrame = new TextureRegion();
    public float animationTime = 0;
    public float nextX;
    public float nextY;
    private boolean playAnim = false;

    public List<Grid2d.MapNode> path = new LinkedList<Grid2d.MapNode>();
    protected Grid2d map2d;
    private Runnable nextAction;


    private OnPlayerConditionChangeListener onPlayerConditionChangeListener;

    public Npc(String name) {
        Preffics preffics = Preffics.getInstance();
        setName(name);
        String name2 = "player";
        speed = new Vector2();
        textureAtlas = new TextureAtlas(preffics.getPath() + name2 + "/" + name2 + ".atlas");
        animMap = new TreeMap<>();

        final ArrayList<PlayerAnimationData> objectList = preffics.fromObjectFromJson(name2 + "/animation.json", ArrayList.class);
        for (PlayerAnimationData animationData : objectList) {
            log(animationData.name + " " + animationData.colls + " " + animationData.frameDur + " " + animationData.rows);
            animMap.put(animationData.name.toLowerCase(), getNpcAnimation(animationData.name, animationData.colls, animationData.rows, animationData.frameDur));
        }
        setParameters();
    }

    private Animation getNpcAnimation(String name, int colls, int rows, float frameDuration) {
        TextureRegion[][] tmp = textureAtlas.findRegion(name).split((textureAtlas.findRegion(name).originalWidth / colls), (textureAtlas.findRegion(name).originalHeight / rows));
        TextureRegion[] animation = new TextureRegion[colls * rows];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colls; j++) {
                animation[index++] = tmp[i][j];
            }
        }
        return new Animation(frameDuration, animation);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (currentFrame.getTexture() != null)
            batch.draw(currentFrame, getX() - getWidth() / 2, getY(), getWidth(), getHeight());
    }

    void setParameters() {
        setBounds(400, 100, 145, 245);
        animationTime = 0;
        playerCondition = PlayerCondition.stay;
        nextPlayerCondition = PlayerCondition.stay;
        lastWalkablePosition.set(getX(), getY());
        nextX = 400;
        nextY = 100;
    }

    private void playAnimation() {
        try {
            currentFrame.setRegion(animMap.get(playerCondition.toString().toLowerCase()).getKeyFrame(animationTime, true));
            int sizeMult = 5;
            setSize(currentFrame.getRegionWidth() * sizeMult, currentFrame.getRegionHeight() * sizeMult);
        } catch (NullPointerException ignored) {
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isAnimationFinished()) {
            onAnimationEnd();
            animationTime = 0;
        }

        if (playAnim) {
            manageExerciseAnim(delta);
        }
        playAnimation();
        changePlayerCondition();
        walk();
    }

    public void onAnimationEnd() {

    }

    private void manageExerciseAnim(float delta) {
        if (playerCondition == squat || playerCondition == bench || playerCondition == deadlift) {
            if (animationTime == 0) {
                if (new Random().nextInt(500) > 450)
                    animationTime += delta / 1.5f;
                else {
                    animationTime = 0;
                }
            } else {
                if (playerCondition == squat)
                    animationTime += delta / 1.5f;
                else
                    animationTime += delta;
            }
        } else {
            animationTime += delta;
        }
    }

    boolean isAnimationFinished() {
        return animMap.get(playerCondition.toString().toLowerCase()) != null && animMap.get(playerCondition.toString().toLowerCase()).getAnimationDuration() < animationTime;
    }

    boolean isAnimationStarted() {
        return animationTime > 0;
    }

    void changePlayerCondition() {
        if (path != null)
            if (path.isEmpty()) {
                if (nextAction != null) {
                    nextAction.run();
                }
                playerCondition = nextPlayerCondition;
                if (nextX > 0 || nextY > 0) {
                    setPosition(nextX, nextY);
                }
            } else if (speed.x > 0) playerCondition = PlayerCondition.right;
            else if (speed.x < 0) playerCondition = PlayerCondition.left;
            else if (speed.y > 0) playerCondition = PlayerCondition.up;
            else if (speed.y < 0) playerCondition = PlayerCondition.down;
    }

    public void setPlayersAction(PlayerCondition playerCondition, int x, int y, Runnable runnable) {
        this.nextAction = runnable;
        this.nextPlayerCondition = playerCondition;
        nextX = x;
        nextY = y;
    }

    private void walk() {
        Preffics preffics = Preffics.getInstance();
        setX(getX() + speed.x);
        setY(getY() + speed.y);
        speed.setZero();
        if (path != null)
            if (!path.isEmpty()) {
                if (path.get(0).x > getX() / preffics.mapCoordinateCorrector) {
                    speed.x = 5f;
                }
                if (path.get(0).x < getX() / preffics.mapCoordinateCorrector) {
                    speed.x = -5f;
                }
                if (path.get(0).y > getY() / preffics.mapCoordinateCorrector) {
                    speed.y = 5f;
                }
                if (path.get(0).y < getY() / preffics.mapCoordinateCorrector) {
                    speed.y = -5f;
                }
                if (path.get(0).x == (int) getX() / preffics.mapCoordinateCorrector && path.get(0).y == (int) getY() / preffics.mapCoordinateCorrector) {
                    path.remove(0);
                    lastWalkablePosition.set(getX(), getY());
                }
            }
    }

    public void goToDestination(int xGoal, int yGoal, PlayerCondition playerCondition) {
        goToDestination(xGoal, yGoal, xGoal, yGoal, playerCondition, null);
    }

    public void goToDestination(int xGoal, int yGoal, int xDestination, int yDestination, PlayerCondition playerCondition) {
        goToDestination(xGoal, yGoal, xDestination, yDestination, playerCondition, null);
    }

    public void goToDestination(int xGoal, int yGoal, int xDestination, int yDestination, PlayerCondition playerCondition, Runnable runnable) {
        Preffics preffics = Preffics.getInstance();
        checkIfInWalkableZone();
        int pX = (int) getX() / preffics.mapCoordinateCorrector, pY = (int) getY() / preffics.mapCoordinateCorrector;

        if (path != null && !path.isEmpty() && (path.get(0).x != pX || path.get(0).y != pY)) {
            lastWalkablePosition.set(getX(), getY());
        }

        if (xGoal / preffics.mapCoordinateCorrector != map2d.xGoal || yGoal / preffics.mapCoordinateCorrector != map2d.yGoal) {
            setPosition(lastWalkablePosition.x, lastWalkablePosition.y);
            ceilPos();

            path = map2d.findPath(
                pX,
                pY,
                xGoal / preffics.mapCoordinateCorrector,
                yGoal / preffics.mapCoordinateCorrector);
        }
        playAnim = true;
        setPlayersAction(playerCondition, xDestination, yDestination, runnable);
    }

    public void setPlayerCondition(PlayerCondition playerCondition) {
        if (onPlayerConditionChangeListener != null && this.playerCondition != playerCondition) {
            onPlayerConditionChangeListener.onChange(this.playerCondition, playerCondition);
        }

        this.playerCondition = playerCondition;
    }

    void log(String msg) {
        System.out.println(getName() + ": " + msg);
    }

    public void goToDestination(List<Grid2d.MapNode> path) {
        this.path = path;
    }

    void ceilPos() {
        setPosition(getX() % 10 != 5f ? getX() - getX() % 10 : getX(), getY() % 10 != 5 && getY() % 10 != 0 ? getY() - getY() % 10 : getY());
    }

    public void clearPath() {
        if (path != null) {
            path.clear();
        }
        map2d = new Grid2d(Preffics.getInstance().mapArr, false);
    }

    public void setSquatExercise() {
        goToDestination(890, 125, 865, 125, PlayerCondition.squat);
    }

    public void setDeadliftExercise() {
        goToDestination(890, 100, 865, 70, PlayerCondition.deadlift);
    }

    public void setBenchExercise() {
        goToDestination(1337, 160, 1353, 75, PlayerCondition.bench);
    }

    public void setHyperExercise() {
        goToDestination(565, 160, 555, 50, PlayerCondition.hiper);
    }

    public void set1BenchSitting() {
        goToDestination(260, 240, 210, 280, PlayerCondition.sitting);
    }

    public void set2BenchSitting() {
        goToDestination(1680, 260, 1720, 280, PlayerCondition.sittingRev);
    }

    public void setPullUps() {
        goToDestination(800, 430, 820, 440, PlayerCondition.pullUps);
    }

    public void setPushUps() {
        goToDestination(800, 430, 820, 440, PlayerCondition.pushUps);
    }

    public void setLegPress() {
        goToDestination(1550, 160, 1695, 65, legPress);
    }

    public void checkIfInWalkableZone() {
        Preffics preffics = Preffics.getInstance();
        double[][] mapArr = preffics.mapArr;
        int pX = (int) getX() / preffics.mapCoordinateCorrector, pY = (int) getY() / preffics.mapCoordinateCorrector;
        if (mapArr[pY][pX] == -1) {
            setPosition(lastWalkablePosition.x, lastWalkablePosition.y);
            setPlayerCondition(PlayerCondition.stay);
        }
    }

    public boolean isActive = true;

    public void setPeriodicEvent(Player player) {
        Thread thread = new Thread(
            () -> {
                while (isActive) {
                    int rnd = new Random().nextInt(10);
                    if (rnd == 0) {
                        if (player.playerCondition != squat) {
                            setSquatExercise();
                        }
                    } else if (rnd == 1) {
                        if (player.playerCondition != deadlift) {
                            setDeadliftExercise();
                        }
                    } else if (rnd == 2) {
                        if (player.playerCondition != bench) {
                            setBenchExercise();
                        }
                    } else if (rnd == 3) {
                        if (player.playerCondition != PlayerCondition.hiper) {
                            setHyperExercise();
                        }
                    } else if (rnd == 4) {
                        if (player.playerCondition != PlayerCondition.sitting) {
                            set1BenchSitting();
                        }
                    } else if (rnd == 5) {
                        if (player.playerCondition != PlayerCondition.sittingRev) {
                            set2BenchSitting();
                        }
                    } else if (rnd == 6) {
                        if (player.playerCondition != PlayerCondition.pullUps) {
                            setPullUps();
                        }
                    } else if (rnd == 7) {
                        if (player.playerCondition != legPress) {
                            setLegPress();
                        }
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        thread.start();
    }

    public void setPlayerPosition(int x, int y) {
        lastWalkablePosition.set(x, y);
        nextY = y;
        nextX = x;
        setPosition(x, y);
    }

    public void setPlayerPosition(int x, int y, PlayerCondition playerCondition) {
        setPlayerPosition(x, y);
        setPlayerCondition(playerCondition);
    }

    public boolean doesOverDrawExercise() {
        return playerCondition.equals(PlayerCondition.bench) ||
            playerCondition.equals(PlayerCondition.pullUps) ||
            playerCondition.equals(PlayerCondition.legPress) ||
            playerCondition.equals(PlayerCondition.sitting) ||
            playerCondition.equals(PlayerCondition.sittingRev) ||
            playerCondition.equals(PlayerCondition.hiper) ||
            playerCondition.equals(PlayerCondition.pushUps) ||
            playerCondition.equals(PlayerCondition.pcSitting);
    }

    public void setOnPlayerConditionChangeListener(OnPlayerConditionChangeListener onPlayerConditionChangeListener) {
        this.onPlayerConditionChangeListener = onPlayerConditionChangeListener;
    }
}

