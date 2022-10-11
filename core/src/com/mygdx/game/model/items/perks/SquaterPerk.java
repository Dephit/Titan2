package com.mygdx.game.model.items.perks;


import com.mygdx.game.Player;

public class SquaterPerk extends PerkItem {

    @Override
    public boolean canBeBought(Player player) {
        System.out.println(SquaterPerk.class.getName());
        return !player.inventoryManager.hasPerk(DeadliftPerk.class.getName()) && !player.inventoryManager.hasPerk(BencherPerk.class.getName());
    }

    public SquaterPerk() {
        title = "SquaterPerk";
        styleName = "perks/squat_first_perk";
        description = "SquaterPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
    }
}


