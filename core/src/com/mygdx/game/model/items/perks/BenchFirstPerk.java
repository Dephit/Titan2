package com.mygdx.game.model.items.perks;

public class BenchFirstPerk extends PerkItem {

    public BenchFirstPerk() {
        title = "BenchFirstPerk";
        styleName = "perks/bench_first_perk";
        description = "BenchFirstPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
        childPerk.add(
                new WristUsePerk()
        );
    }
}

