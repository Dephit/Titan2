package com.sergeenko.alexey.titangym

import android.annotation.SuppressLint
import android.icu.util.ULocale.getLanguage
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
import com.mygdx.game.Player
import com.mygdx.game.interfaces.OnCLickCallback
import com.mygdx.game.model.CompetitionOpponent.Attempt
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.PlayerItem
import com.sergeenko.alexey.titangym.databinding.MainActivityBinding
import com.sergeenko.alexey.titangym.databinding.NextSetViewBinding
import com.sergeenko.alexey.titangym.databinding.PlayerListBinding
import java.util.ArrayList
import kotlin.random.Random

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
        runnable: OnCLickCallback?,
    ) {
        val listBinding = PlayerListBinding.inflate(LayoutInflater.from(this))
        binding!!.root.post {
            binding!!.viewContainer.removeAllViews()
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

    override fun showNextSetMenu(
        player: Player,
        currentSet: Int,
        onFirstClick: OnCLickCallback?,
        onSecondClick: OnCLickCallback?,
        onThirdClick: OnCLickCallback?,
        onFourthClick: OnCLickCallback?
    ) {
        val listBinding = NextSetViewBinding.inflate(LayoutInflater.from(this))
        binding!!.root.post {
            binding!!.viewContainer.removeAllViews()
            listBinding.root.setOnClickListener { v: View? -> }

            val first = getResult(currentSet, player)
            val second = first + 10
            val third = first + 20
            val fourth = first + 30


            listBinding.weight1.text = first.toString()
            listBinding.weight2.text = second.toString()
            listBinding.weight3.text = third.toString()
            listBinding.weight4.text = fourth.toString()

            listBinding.weigth.text = first.toString()
            getPercentages(currentSet, listBinding,onFirstClick, first, second, third, fourth)

            listBinding.scoreSheetButton.setOnClickListener {
                onFirstClick?.call(null)
            }



            listBinding.closeButton.setOnClickListener {
                binding!!.viewContainer.removeAllViews()
                binding!!.viewContainer.visibility = View.GONE
            }
            binding!!.viewContainer.addView(listBinding.root)
            binding!!.viewContainer.visibility = View.VISIBLE
            listBinding.container.y = binding!!.root.y + 48
            listBinding.container.x = binding!!.root.x
            listBinding.container.updateLayoutParams {
                height = binding!!.root.height - 48
                width = binding!!.root.width - 48
            }
        }
    }

    private fun getPercentages(
        compStatus: Int,
        binding: NextSetViewBinding,
        onFirstClick: OnCLickCallback?,
        first: Int,
        second: Int,
        third: Int,
        fourth: Int,
    ) {
        var per1 = 90
        var per2 = 90
        var per3 = 90
        var per4 = 50
        when (compStatus) {
            1 -> {
                per1 = 90
                per2 = 60
                per3 = 30
            }
            2 -> {
                per1 = 60
                per2 = 30
                per3 = 10
            }
            3 ->  {
                per1 = 30
                per2 = 10
                per3 = 3
            }
            4 -> {
                per1 = 90
                per2 = 60
                per3 = 30
            }
            5 -> {
                per1 = 60
                per2 = 30
                per3 = 10
            }
            6 ->  {
                per1 = 30
                per2 = 10
                per3 = 3
            }
            7 -> {
                per1 = 90
                per2 = 60
                per3 = 30
            }
            8 -> {
                per1 = 60
                per2 = 30
                per3 = 10
            }
            9 ->  {
                per1 = 30
                per2 = 10
                per3 = 3
            }
        }
        binding.button1.text = "$per1%"
        binding.button2.text = "$per2%"
        binding.button3.text = "$per3%"
        binding.button4.text = "AD($per4%)"

        binding.button1.setOnClickListener {
            onFirstClick?.call(Attempt(first, (Random.nextInt(100) < per1)))
            binding.closeButton.callOnClick()
        }

        binding.button2.setOnClickListener {
            onFirstClick?.call(Attempt(second, (Random.nextInt(100) < per2)))
            binding.closeButton.callOnClick()
        }

        binding.button3.setOnClickListener {
            onFirstClick?.call(Attempt(third, (Random.nextInt(100) < per3)))
            binding.closeButton.callOnClick()
        }

        binding.button4.setOnClickListener {
            onFirstClick?.call(Attempt(fourth, true))
            binding.closeButton.callOnClick()
        }

    }

    private fun getResult(compStatus: Int, player: Player): Int {
        when (compStatus) {
            1 -> return player.compValue.squat.firstAttempt.weight
            2 -> {
                player.compValue.squat.secondAttempt.weight = player.compValue.squat.firstAttempt.weight +
                        if(player.compValue.squat.firstAttempt.isGood) 5 else 0
                return player.compValue.squat.secondAttempt.weight
            }
            3 ->  {
                player.compValue.squat.thirdAttempt.weight = player.compValue.squat.secondAttempt.weight +
                        if(player.compValue.squat.secondAttempt.isGood) 5 else 0
                return player.compValue.squat.thirdAttempt.weight
            }
            4 -> return player.compValue.bench.firstAttempt.weight
            5 -> {
                player.compValue.bench.secondAttempt.weight = player.compValue.bench.firstAttempt.weight +
                        if(player.compValue.bench.firstAttempt.isGood) 5 else 0
                return player.compValue.bench.secondAttempt.weight
            }
            6 ->  {
                player.compValue.bench.thirdAttempt.weight = player.compValue.bench.secondAttempt.weight +
                        if(player.compValue.bench.secondAttempt.isGood) 5 else 0
                return player.compValue.bench.thirdAttempt.weight
            }
            7 -> return player.compValue.deadlift.firstAttempt.weight
            8 -> {
                player.compValue.deadlift.secondAttempt.weight = player.compValue.deadlift.firstAttempt.weight +
                        if(player.compValue.deadlift.firstAttempt.isGood) 5 else 0
                return player.compValue.deadlift.secondAttempt.weight
            }
            9 ->  {
                player.compValue.deadlift.thirdAttempt.weight = player.compValue.deadlift.secondAttempt.weight +
                        if(player.compValue.deadlift.secondAttempt.isGood) 5 else 0
                return player.compValue.deadlift.thirdAttempt.weight
            }
                10 -> return 0
            }
        return 0
    }

    override fun isAdShown(): Boolean {
        return false //adView.isShown();
    }

    override fun signOut() {}
    override fun saveData(value: Map<*, *>?) {}
}