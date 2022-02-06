package com.sergeenko.alexey.titangym

import android.view.View
import com.mikepenz.fastadapter.items.AbstractItem
import com.mygdx.game.Player
import com.mygdx.game.model.CompetitionOpponent
import com.sergeenko.alexey.titangym.databinding.PlayerItemBinding
import com.sergeenko.alexey.titangym.items.BaseViewHolder

public class PlayerItem(
    val player: CompetitionOpponent,
) : AbstractItem<PlayerItem.ViewHolder>() {

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    override val type = R.id.playerItem

    override val layoutRes = R.layout.player_item

    class ViewHolder(view: View) : BaseViewHolder<PlayerItemBinding, PlayerItem>(view) {

        override fun bindView(item: PlayerItem, payloads: List<Any>) {
            with(binding){
                textView2.text = item.player.name
            }
        }


        override fun unbindView(item: PlayerItem) {
            super.unbindView(item)
        }

        override fun bind() = PlayerItemBinding.bind(itemView.rootView)
    }
}