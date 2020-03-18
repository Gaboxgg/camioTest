package com.example.camiotest.adapters

import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.camiotest.R
import com.example.camiotest.data.MoviePojo
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*


class MoviesAdapter (var movies: List<MoviePojo>, val listener: (MoviePojo) -> Unit) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: MoviePojo) {
            itemView.text.text="${movie.title}  \n \n  ${movie.overview} \n \n ${movie.release_date}"
            Glide.with(itemView)  //2
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}?api_key=0bda8a0480d0c29189aa581a42c8000d") //3
                .into(itemView.image) //8
            itemView.text.movementMethod = ScrollingMovementMethod()
            itemView.text.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    itemView.text.getParent().requestDisallowInterceptTouchEvent(true)
                    return false;
                }
            })
            itemView.setOnClickListener { listener(movie) }
        }

        //method to format the lower text in each row compose by date - author
        private fun formatText(createdAt: String, author: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX")
            val strDate: Date = sdf.parse(createdAt)
            val diff: Long = System.currentTimeMillis() - strDate.getTime()
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            var date: String = ""
            if (diff < 1000) {
                date = "1s"
            } else if (diff > 1000 && minutes < 1) {
                date = seconds.toString() + "s"
            } else if (minutes in 1..59) {
                date = minutes.toString() + "m"
            } else if (hours >= 1) {
                var real_minutes:Long = minutes%(hours*60)
                var minu: Char = (real_minutes*100/60).toString().elementAt(0)

                if(minu.equals('0'))
                    date = "$hours"+'h'
                else
                    date = "$hours.$minu"+"h"
            }
            if (hours >= 24){
                date = "yesterday"
        }

            return "$author - $date"
        }
    }

    public fun removeItem(position: Int) {
        val list = movies.toMutableList()
        list.removeAt(position)
        movies = list
        notifyItemRemoved(position)
    }

    fun getData(): List<MoviePojo> {
        return movies
    }
}