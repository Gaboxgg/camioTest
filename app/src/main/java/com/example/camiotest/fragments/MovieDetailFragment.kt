package com.example.camiotest.fragments


import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.camiotest.R
import com.example.camiotest.data.MoviePojo
import kotlinx.android.synthetic.main.fragment_movie_detail.*
import org.jetbrains.anko.*


class MovieDetailFragment : Fragment(){
    lateinit var movie:MoviePojo
    lateinit var fragment_image:ImageView
    lateinit var fragment_info:TextView
    companion object {
        @JvmStatic
        fun newInstance(movie: MoviePojo) = MovieDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("movie", movie)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable("movie")?.let {
            movie = it as MoviePojo
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        fragment_image = view.findViewById(R.id.image)
        fragment_info = view.findViewById(R.id.info)

        initTemplate(movie)
        return view
    }

    fun initTemplate(movie:MoviePojo){
        Glide.with(fragment_image)  //2
            .load("https://image.tmdb.org/t/p/w500${movie.backdrop_path}?api_key=0bda8a0480d0c29189aa581a42c8000d") //3
            .apply( RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(fragment_image) //8

        var str:String = "${movie.original_title} - ${movie.release_date} - ${movie.original_language}" +
                " \n Popularidad: ${movie.popularity} \n" +
                "Voto promedio: ${movie.vote_average}/10 Cantidad de votos: ${movie.vote_count} \n" +
                "Resumen: \n\n ${movie.overview}"
        fragment_info.movementMethod = ScrollingMovementMethod()
        fragment_info.text=str
    }

}