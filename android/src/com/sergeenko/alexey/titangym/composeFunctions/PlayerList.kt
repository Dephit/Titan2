package com.sergeenko.alexey.titangym.composeFunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.items.PlayerComposeItem
import com.sergeenko.alexey.titangym.items.TableHeaderComposeItem

@Composable
fun PlayerList(
    playerList: MutableList<CompetitionOpponent>,
    status: Comp,
    runnable: OnCLickCallback?
) {
    return DialogBox {
        Column(
            modifier = dialogModifier()
            ) {
                CloseButton(runnable)
                LazyColumn(content = {
                    item { TableHeaderComposeItem() }
                    playerList.sortedByDescending { it.getCurrentSet(status.attempt) }.forEachIndexed { index, competitionOpponent ->
                        item { PlayerComposeItem(index = index, status = status, player = competitionOpponent) }
                    }
                })
            }
        }
    }
