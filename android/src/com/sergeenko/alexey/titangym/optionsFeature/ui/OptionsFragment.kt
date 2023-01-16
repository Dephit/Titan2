package com.sergeenko.alexey.titangym.optionsFeature.ui

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.fragment.findNavController
import com.sergeenko.alexey.titangym.R
import com.sergeenko.alexey.titangym.composeFunctions.FillSpacer
import com.sergeenko.alexey.titangym.composeFunctions.titleTextSize
import com.sergeenko.alexey.titangym.fragments.BaseComposeFragment
import com.sergeenko.alexey.titangym.optionsFeature.viewModels.OptionsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class OptionsFragment : BaseComposeFragment() {

    private val viewModel by viewModel<OptionsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        drawView()
    }

    private fun drawView(){
        setContent {
            val text = if(viewModel.musicState.value)
                stringResource(id = R.string.turn_off_music)
            else
                stringResource(id = R.string.turn_on_music)

            Box(Modifier) {
                Column {
                    FillSpacer()
                    Button(onClick = {
                        if(findNavController().previousBackStackEntry?.destination?.id == R.id.androidLauncherFragment){
                            findNavController().navigate(R.id.toAndroidLauncher)
                        }else{
                            findNavController().navigateUp()
                        }

                    }) {
                        Text(
                            text = stringResource(id = R.string.back),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Button(onClick = viewModel::changeMusicSettings) {
                        Text(
                            text = text,
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Button(onClick = {
                        navigate(R.id.action_optionsFragment_to_menu)
                    }) {
                        Text(
                            text = stringResource(id = R.string.to_menu),
                            color = Color.White,
                            fontSize = titleTextSize,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    FillSpacer()
                }
            }
        }
    }



}