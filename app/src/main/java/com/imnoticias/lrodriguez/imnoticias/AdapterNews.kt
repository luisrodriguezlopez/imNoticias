package com.imnoticias.lrodriguez.imnoticias

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.news_item.view.*


class AdapterNews(val data : List<ItemNews>):RecyclerView.Adapter<AdapterNews.NewsHolder>()  {

    override fun getItemCount(): Int {
      return data.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        return NewsHolder(parent?.inflate(R.layout.news_item)!!)
    }




    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        holder?.bindView(data[position])

    }


    class NewsHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        fun bindView(itemNews : ItemNews) {

            with(itemNews) {
               Picasso.get().load("${itemNews.image}").into(itemView.new_image)
                itemView.new_title.text = title
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, WebActivity::class.java)
                    intent.putExtra("webUrl",url)
                    startActivity(itemView.context,intent,null)


                }
            }
        }


    }


}