package com.mygdx.game.model.items.supplements;

import com.mygdx.game.model.ContiniousItem;
import com.mygdx.game.model.ItemType;

public class SupplementItem extends ContiniousItem {

    public Long timeInUseLeft = 0L;
    public Long timeWillBeLast = 10L;

    public SupplementItem() {
        type = ItemType.SUPPLEMENT;
    }



}

