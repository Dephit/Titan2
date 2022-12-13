package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.items.PlayerComposeItem
import com.sergeenko.alexey.titangym.items.TableHeaderComposeItem

@Composable
fun PlayerList(
    playerList: MutableList<CompetitionOpponent>,
    status: Comp,
    runnable: OnClickCallback?
) {
    val playersList = playerList.sortedByDescending { it.getCurrentSet(status.attempt) }

    return DialogBox {
        Column(
            modifier = dialogModifier()
            ) {
                CloseButton(runnable)
                LazyColumn(content = {
                    item { TableHeaderComposeItem() }
                    playersList.forEachIndexed { index, competitionOpponent ->
                        item { PlayerComposeItem(index = index, status = status, player = competitionOpponent) }
                    }
                })
            }
        }
    }
