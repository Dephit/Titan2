package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.unit.*
import com.mygdx.game.*
import com.mygdx.game.model.items.perks.*
import com.sergeenko.alexey.titangym.*
import com.sergeenko.alexey.titangym.fragments.*

@Composable
fun DrawPerkMenu(
    player: Player,
    state: ComposeState.ShowPerkMenu,
    onClose: () -> Unit
) {
    val allPerks: List<PerkItem> = PerksMenu().items.map { it as PerkItem }

    return Box(
        modifier = dialogModifier()
    ) {
        Column(
            modifier = Modifier.padding(start = 30.dp, end = 30.dp)
        ) {
            CloseButton {
                onClose()
            }
            Row {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    allPerks.forEachIndexed { _, item ->
                        val bgColor = player.getPerkColor(item)
                        item {
                            LazyRow {
                                item {
                                    PerkItem(
                                        item,
                                        bgColor,
                                        state.onItemClick
                                    )

                                }
                                item.childPerk.forEach {
                                    //TODO make it as item
                                    val bgColor = player.getPerkColor(it)
                                    item {
                                        Row(
                                            modifier = Modifier.height(40.dp)
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(width = 30.dp, height = 2.dp)
                                                    .align(CenterVertically)
                                                    .background(color = bgColor)
                                            )
                                        }
                                    }
                                    item {
                                        PerkItem(it, bgColor, state.onItemClick)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                        }
                    }
                }
            }
        }
    }
}

private fun Player.getPerkColor(item: PerkItem): Color {
    return if (inventoryManager?.hasPerk(item) == true) Color.Gray else if (item.canBeBought(this)) if (item.isRequirementSatisfied(
            this
        )
    ) Color.Green else Color.Yellow else Color.Red
}

@Composable
private fun PerkItem(perk: PerkItem, bgColor: Color, onItemClick: (PerkItem) -> Unit) {
    val assetManager = LocalContext.current.assets

    Box(modifier = Modifier) {
        Column(
            Modifier
                .background(bgColor)
                .padding(1.dp)
        ) {
            assetManager.getItemImage(perk) {
                onItemClick(it as PerkItem)
            }
        }
    }
}
