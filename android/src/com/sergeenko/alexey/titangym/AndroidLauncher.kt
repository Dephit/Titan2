package com.sergeenko.alexey.titangym

import android.annotation.SuppressLint
import com.badlogic.gdx.backends.android.AndroidApplication
import com.mygdx.game.IActivityRequestHandler
import android.widget.RelativeLayout
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import android.os.Bundle
import android.view.LayoutInflater
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import android.view.WindowManager
import com.mygdx.game.MyGdxGame
import android.widget.Toast
import com.mygdx.game.model.CompetitionOpponent
import com.mikepenz.fastadapter.IItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.updateLayoutParams
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.PlayerItem
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import com.sergeenko.alexey.titangym.databinding.PlayerListBinding
import java.util.ArrayList

@SuppressLint("Registered")
class AndroidLauncher : AndroidApplication(), IActivityRequestHandler {
    private var gameLayout: RelativeLayout? = null
    var binding: MainActivityBinding? = null
    private val data: Map<String?, Any?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = MainActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding!!.root)
        val config = AndroidApplicationConfiguration()
        gameLayout = createGameLayout(config)
        launchGame()
    }

    private fun launchGame() {
        binding!!.container.addView(gameLayout)
    }

    private fun createGameLayout(config: AndroidApplicationConfiguration): RelativeLayout {
        val layout = RelativeLayout(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
        val gameView = initializeForView(MyGdxGame(this), config)
        layout.addView(gameView)
        return layout
    }

    override fun showAds(show: Boolean) {}
    override fun loadData(): Map<String?, Any?> {
        return data!!
    }

    override fun showToast(s: String) {
        runOnUiThread {
            Toast.makeText(
                this@AndroidLauncher.applicationContext,
                s,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun showPlayers(
        playerList: MutableList<CompetitionOpponent>,
        status: Comp,
        runnable: OnCLickCallback?
    ) {
        val listBinding = PlayerListBinding.inflate(LayoutInflater.from(this))
        binding!!.root.post {
            val itemAdapter = ItemAdapter<IItem<*>>()
            val adapter = FastAdapter.with(itemAdapter)
            adapter.setHasStableIds(true)
            listBinding.rv.layoutManager = LinearLayoutManager(this)
            listBinding.rv.adapter = adapter
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val list = ArrayList<IItem<*>>()
                list.add(TableHeaderItem())
                list.addAll(
                    playerList.sortedByDescending { it.getCurrentSet(status.attempt) }.mapIndexed {  index, competitionOpponent ->
                        PlayerItem(
                            index + 1,
                            status,
                            competitionOpponent
                        )

                    }
                )
                itemAdapter.setNewList(list, false)
            }
            listBinding.cont.y = binding!!.root.y + 48
            listBinding.cont.x = binding!!.root.x
            listBinding.cont.updateLayoutParams {
                height = binding!!.root.height - 48
                width = binding!!.root.width - 48
            }
            listBinding.root.setOnClickListener { v: View? -> }
            listBinding.button.setOnClickListener {
                listBinding.cont.callOnClick()
                runnable?.call(null)
            }
            listBinding.cont.setOnClickListener { v: View? ->
                binding!!.viewContainer.removeAllViews()
                binding!!.viewContainer.visibility = View.GONE
            }
            binding!!.viewContainer.addView(listBinding.root)
            binding!!.viewContainer.visibility = View.VISIBLE
        }
    }

    override fun isAdShown(): Boolean {
        return false //adView.isShown();
    }

    override fun signOut() {}
    override fun saveData(value: Map<*, *>?) {}
}