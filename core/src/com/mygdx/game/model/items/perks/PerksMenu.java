package com.mygdx.game.model.items.perks;

import com.mygdx.game.model.Container;

public class PerksMenu extends Container {

    public PerksMenu() {
        this.totalCapacity = 200;

        addSquatTree();
        addBenchTree();
        addDeadliftTree();
        addItem(new SquatEnergyFirstPerk());
    }

    private void addDeadliftTree() {
        addItems(
                new SquaterPerk(), new SquatFirstPerk()
        );
    }

    private void addBenchTree() {
        addItems(new BencherPerk(), new BenchFirstPerk());
    }

    private void addSquatTree() {
        addItems(new DeadliftPerk(), new DeadliftFirstPerk());
    }

}
