package com.sergeenko.alexey.titangym

import android.view.View
import com.mikepenz.fastadapter.items.AbstractItem
import com.mygdx.game.model.CompetitionOpponent
import com.sergeenko.alexey.titangym.databinding.PlayerItemBinding
import com.sergeenko.alexey.titangym.items.BaseViewHolder

public class PlayerItem(
    val index: Int,
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
                sqtVIew.text = item.player.squatRes.toString()
                bpVIew.text = item.player.benchRes.toString()
                dlVIew.text = item.player.deadliftRes.toString()
                totalView.text = (item.player.squatRes + item.player.benchRes + item.player.deadliftRes).toString()
            }
        }


        override fun unbindView(item: PlayerItem) {
            super.unbindView(item)
        }

        override fun bind() = PlayerItemBinding.bind(itemView.rootView)
    }
}