package com.example.ai.uilayer.ui.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.ai.R
import com.example.ai.databinding.CoinListItemBinding
import com.example.ai.uilayer.uistatemodel.CoinUIStateModel
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.squareup.picasso.Picasso
import java.math.RoundingMode
import java.text.DecimalFormat


class CoinListAdapter(
    var itemList: List<CoinUIStateModel>
    ): RecyclerView.Adapter<CoinListAdapter.CoinListViewHolder>() {

    class CoinListViewHolder(private val binding: CoinListItemBinding): ViewHolder(binding.root) {

        fun bind(item: CoinUIStateModel) {
            binding.apply {
                textCoinName.text = item.name
                val decimalFormat = DecimalFormat("#.##")
                decimalFormat.roundingMode = RoundingMode.HALF_UP
                "$${decimalFormat.format(item.price)}".also { textPrice.text = it }
                item.iconUrl?.let { setImageToView(view = iconCoin, iconUrl = it) }
                item.isFavorite?.let { isFavorite ->
                    if (isFavorite) {
                        iconBookmark.setBackgroundResource(R.drawable.baseline_bookmark_24)
                    }
                    else {
                        iconBookmark.setBackgroundResource(R.drawable.baseline_bookmark_border_24)
                    }
                }
                root.setOnClickListener {
                    item.clickEvent(item)
                }
                iconBookmark.setOnClickListener {
                    item.clickBookmarkEvent(item.uuId?:"")
                }
            }
        }

        private fun setImageToView(view: View, iconUrl: String) {
            if (iconUrl.contains(".svg")) {
                GlideToVectorYou.init().with(view.context)
                    .load(Uri.parse(iconUrl), view as ImageView)
            }
            else {
                Picasso.get().load(iconUrl).into(view as ImageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val binding = CoinListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}