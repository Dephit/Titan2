package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.mygdx.game.interfaces.OnClickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.featureGameScreen.models.ComposeState
import com.sergeenko.alexey.titangym.items.PlayerComposeItem
import com.sergeenko.alexey.titangym.items.TableHeaderComposeItem

@Composable
fun PlayerList(state: ComposeState.ShowPlayers, onClose: () -> Unit) {
    val status = state.status
    val playersList = state.playerList.sortedByDescending { it.getCurrentSet(status.attempt) }

    return DialogBox {
        Column(
            modifier = dialogModifier()
            ) {
                CloseButton{
                    onClose()
                }
                LazyColumn(content = {
                    item { TableHeaderComposeItem() }
                    playersList.forEachIndexed { index, competitionOpponent ->
                        item { PlayerComposeItem(index = index, status = status, player = competitionOpponent) }
                    }
                })
            }
        }
    }
