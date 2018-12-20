package com.araujo.jordan.fractalbeer.ui.beerList

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.araujo.jordan.fractalbeer.R
import com.araujo.jordan.fractalbeer.model.Beer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.listitem_beer.view.*

class BeerAdapter : RecyclerView.Adapter<BeerAdapter.ViewHolder>() {

    private var list: MutableList<Beer> = mutableListOf()
    class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

    private var listCallback: ListCallback? = null

    fun attachCallback(listCallback:ListCallback) {
        this.listCallback = listCallback
    }

    fun addToList(newItems: MutableList<Beer>) {
        list.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.listitem_beer, parent, false) as View
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beer = list[position]
        holder.itemView.setOnClickListener{
            listCallback?.OnRetrieveBeer(beer,holder.itemView)
        }
        holder.itemView.beeritem_title.text = beer.name
        holder.itemView.beeritem_tagline.text = beer.tagline
        Picasso.get()
            .load(beer.image_url)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(holder.itemView.beeritem_img)

    }

    interface ListCallback {
        fun OnRetrieveBeer(beer:Beer,view:View)
    }
}