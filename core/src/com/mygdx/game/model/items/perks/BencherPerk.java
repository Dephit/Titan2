package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;

public class BencherPerk extends PerkItem {

    @Override
    public boolean canBeBought(Player player) {
        System.out.println(SquaterPerk.class.getName());
        return !player.inventoryManager.hasPerk(SquaterPerk.class.getName()) && !player.inventoryManager.hasPerk(DeadliftPerk.class.getName());
    }

    public BencherPerk() {
        title = "BencherPerk";
        styleName = "perks/bench_first_perk";
        description = "BencherPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
    }
}

