package com.mygdx.game.model.items.perks;

import com.mygdx.game.Player;

public class DeadliftPerk extends PerkItem {

    @Override
    public boolean canBeBought(Player player) {
        System.out.println(DeadliftPerk.class.getName());
        return !player.inventoryManager.hasPerk(SquaterPerk.class.getName()) && !player.inventoryManager.hasPerk(BencherPerk.class.getName());
    }

    public DeadliftPerk() {
        title = "DeadliftPerk";
        styleName = "perks/deadlift_first_perk";
        description = "DeadliftPerk";
        menuStyleName = "potatoMenu";
        cost = 5;
    }
}
