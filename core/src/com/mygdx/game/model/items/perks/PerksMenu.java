package com.mygdx.game.model.items.perks;

import com.mygdx.game.model.Container;
import com.mygdx.game.model.items.food.Nuggets;
import com.mygdx.game.model.items.food.Potato;

import java.util.ArrayList;

public class PerksMenu extends Container {

    public PerksMenu() {
        this.totalCapacity = 200;

        addSquatTree();
        addBenchTree();
        addDeadliftTree();
    }

    private void addDeadliftTree() {
        addItem(new SquatFirstPerk());
    }

    private void addBenchTree() {
        addItem(new BenchFirstPerk());
    }

    private void addSquatTree() {
        addItem(new DeadliftFirstPerk());
    }

}
