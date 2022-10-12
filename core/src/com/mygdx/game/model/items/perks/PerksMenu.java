package com.mygdx.game.model.items.perks;

import com.mygdx.game.model.Container;

public class PerksMenu extends Container {

    public PerksMenu() {
        this.totalCapacity = 200;

        addDeadliftTree();
        addSquatTree();
        addBenchTree();
        addItem(new SquatEnergyFirstPerk());
    }

    private void addDeadliftTree() {
        addItems(
                new SquaterPerk(), new SquatFirstPerk()
        );
    }

    private void addBenchTree() {addItems(new BenchFirstPerk());}

    private void addSquatTree() {
        addItems(new DeadliftFirstPerk());
    }

}
