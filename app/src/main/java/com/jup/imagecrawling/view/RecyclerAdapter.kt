package com.jup.imagecrawling.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jup.imagecrawling.R
import com.jup.imagecrawling.model.GettyImageModel
import java.util.*


/**
 * @author: JiMinLee
 * @description: Recycler 어댑터
 **/
class RecyclerAdapter(var items: ArrayList<GettyImageModel>?, private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val gtImage:ImageView? = itemView.findViewById(R.id.image_imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.gtImage?.let{
            Glide.with(context)
                .load(items?.get(position)?.imageUri)
                .into(it)
        }
    }
}