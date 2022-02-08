package com.sergeenko.alexey.titangym

import android.view.View
import com.mikepenz.fastadapter.items.AbstractItem
import com.mygdx.game.model.CompetitionOpponent
import com.sergeenko.alexey.titangym.databinding.PlayerItemBinding
import com.sergeenko.alexey.titangym.databinding.TableHeaderItemBinding
import com.sergeenko.alexey.titangym.items.BaseViewHolder

public class TableHeaderItem() : AbstractItem<TableHeaderItem.ViewHolder>() {

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    override val type = R.id.tableHeaderItem

    override val layoutRes = R.layout.table_header_item

    class ViewHolder(view: View) : BaseViewHolder<TableHeaderItemBinding, TableHeaderItem>(view) {

        override fun bindView(item: TableHeaderItem, payloads: List<Any>) {
        }


        override fun unbindView(item: TableHeaderItem) {
            super.unbindView(item)
        }

        override fun bind() = TableHeaderItemBinding.bind(itemView.rootView)
    }
}