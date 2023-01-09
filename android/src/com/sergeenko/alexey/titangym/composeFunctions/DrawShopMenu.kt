package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mygdx.game.model.Container
import com.mygdx.game.model.enums.InventoryType
import com.mygdx.game.model.items.Item
import com.mygdx.game.model.items.OnItemClick
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.blockModifier

@Composable
fun DrawShopMenu(
    type: InventoryType,
    inventoryContainer: Container,
    shopContainer: Container,
    onBuyRunnable: (Item) -> Unit,
    onCancel: Runnable
) {
    val titleText = remember {
        when(type){
            InventoryType.INVENTORY -> R.string.inventory_shop_title
            InventoryType.REFRIGERATOR -> R.string.food_shop_title
            InventoryType.EQUIPMENT -> R.string.equipment_shop_title
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = blockModifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 30.dp),
                text = stringResource(id = titleText),
                textAlign = TextAlign.Center,
                style = shadow
            )

            CloseButton{ onCancel.run() }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ShopPartScreen(
                titleText = R.string.your_inventory_title,
                container = inventoryContainer
            )
            ShopPartScreen(
                titleText = R.string.shops_goods,
                container = shopContainer
            ){
                onBuyRunnable(it)
            }
        }

    }
}

@Composable
private fun RowScope.ShopPartScreen(
    titleText: Int,
    container: Container,
    onBuyRunnable: (Item) -> Unit = { },
) {
    Column(blockModifier.weight(1f)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp, horizontal = 30.dp),
            text = stringResource(id = titleText),
            textAlign = TextAlign.Center,
            style = shadow
        )
        DrawInventory(
            container = container,
            showCloseBotton = false,
            onItemClick = {
                onBuyRunnable(it)
            },
            onClose = {

            },
            widthModifier = Modifier
        )
    }
}
