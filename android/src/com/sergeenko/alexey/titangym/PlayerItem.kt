package com.sergeenko.alexey.titangym

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.mikepenz.fastadapter.items.AbstractItem
import com.mygdx.game.model.CompetitionOpponent
import com.mygdx.game.model.enums.Comp
import com.sergeenko.alexey.titangym.databinding.PlayerItemBinding
import com.sergeenko.alexey.titangym.items.BaseViewHolder

public class PlayerItem(
    val index: Int,
    val status: Comp,
    val player: CompetitionOpponent,
) : AbstractItem<PlayerItem.ViewHolder>() {

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    override val type = R.id.playerItem

    override val layoutRes = R.layout.player_item

    class ViewHolder(view: View) : BaseViewHolder<PlayerItemBinding, PlayerItem>(view) {

        override fun bindView(item: PlayerItem, payloads: List<Any>) {
            with(binding){
                rnkView.text = item.index.toString()
                nameVIew.text = item.player.name
                val status = item.status
                sqt1VIew.setAttempt(item.player.squat.firstAttempt, status, 1)
                sqt2VIew.setAttempt(item.player.squat.secondAttempt, status, 2)
                sqt3VIew.setAttempt(item.player.squat.thirdAttempt, status, 3)

                bp1VIew.setAttempt(item.player.bench.firstAttempt, status , 4)
                bp2VIew.setAttempt(item.player.bench.secondAttempt, status, 5)
                bp3VIew.setAttempt(item.player.bench.thirdAttempt, status, 6)

                dl1VIew.setAttempt(item.player.deadlift.firstAttempt, status, 7)
                dl2VIew.setAttempt(item.player.deadlift.secondAttempt, status, 8)
                dl3VIew.setAttempt(item.player.deadlift.thirdAttempt, status, 9)

                totalView.text = item.player.getCurrentSet(status.attempt).toString()
            }
        }


        override fun unbindView(item: PlayerItem) {
            super.unbindView(item)
        }

        override fun bind() = PlayerItemBinding.bind(itemView.rootView)
    }
}

private fun TextView.setAttempt(firstAttempt: CompetitionOpponent.Attempt?, b: Comp, i: Int) {
    text = firstAttempt?.weight.toString()
    val color = when {
        i == b.attempt -> {
            //setVisible()
            R.color.color_white
        }
        i > b.attempt -> {
            //setGone()
            text = "-"
            R.color.current_attempt
        }
        firstAttempt?.isGood == true -> {
            //setVisible()
            R.color.good_attempt
        }
        firstAttempt?.isGood == false -> {
            //setVisible()
            R.color.bad_attempt
        }
        else -> R.color.current_attempt
    }
    setVisible()

    setBackgroundColor(ContextCompat.getColor(context, color))
}

public fun View.setVisible() {
    visibility = View.VISIBLE
}

public fun View.setGone() {
    visibility = View.GONE
}

