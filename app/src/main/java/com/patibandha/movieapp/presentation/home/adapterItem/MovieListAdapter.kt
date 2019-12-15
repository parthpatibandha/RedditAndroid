package com.patibandha.movieapp.presentation.home.adapterItem

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.patibandha.movieapp.R
import com.patibandha.movieapp.data.models.RedditPopularItem
import com.patibandha.movieapp.presentation.home.listener.OnMovieItemClickListener
import com.patibandha.movieapp.presentation.utility.loadImage
import kotlinx.android.synthetic.main.cell_movie_item.view.*

class MovieListAdapter(var list: MutableList<RedditPopularItem>, val listener: OnMovieItemClickListener) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    fun updateDataSet(mlist: List<RedditPopularItem>){
        this.list.forEach { item ->
            mlist.find {
                it.id.contentEquals(item.id)
            }.apply {
                if(this != null) {
                    item.isSelected = this.isSelected
                } else {
                    item.isSelected = false
                }
            }
        }
        notifyDataSetChanged()
    }

    fun setData(mlist: List<RedditPopularItem>) {
        val oldSize = this.list.size
        list.clear()
        notifyItemRangeRemoved(0, oldSize)
        list.addAll(mlist)
        notifyItemRangeInserted(0, this.list.size)
    }

    fun addData(mlist: List<RedditPopularItem>) {
        val oldSize = this.list.size
        this.list.addAll(mlist)
        notifyItemRangeInserted(oldSize, this.list.size)
    }

    override fun onCreateViewHolder(container: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.cell_movie_item, container, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = list.get(position)
        holder.ivImage?.loadImage(movie.thumbnail.orEmpty(), R.drawable.placeholder)
        holder.tvTitle?.text = movie.title
        holder.tvPrefixed?.text = movie.subredditNamePrefixed
        holder.tvDomain?.text = movie.domain
        holder.ivFavourite.isSelected = movie.isSelected
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivImage = view.ivImage
        val tvTitle = view.tvTitle
        val tvPrefixed = view.tvPrefixed
        val tvDomain = view.tvDomain
        val ivFavourite = view.ivFavourite
        val ivShare = view.ivShare

        init {
            itemView.setOnClickListener {
                listener.onMovieItemClick(list.get(adapterPosition))
            }

            ivFavourite.setOnClickListener {
                list.get(adapterPosition).isSelected = !list.get(adapterPosition).isSelected
                notifyItemChanged(adapterPosition)
                listener.onMovieItemFavouriteClick(list.get(adapterPosition))
            }

            ivShare.setOnClickListener {
                listener.onMovieItemShareClick(list.get(adapterPosition))
            }
        }
    }
}