package com.example.camiotest.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.camiotest.MainActivity
import com.example.camiotest.R
import com.example.camiotest.adapters.MoviesAdapter
import com.example.camiotest.callbacks.SwipeToDeleteCallback
import com.example.camiotest.data.DBHelper
import com.example.camiotest.data.MoviePojo
import com.example.camiotest.data.MoviesPojo
import com.example.camiotest.interfaces.APIService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesFragment : Fragment() {

    lateinit var movieList:List<MoviePojo>
    lateinit var adapter: MoviesAdapter
    lateinit var mContext: Context
    var url:String="popular"

    companion object {
        @JvmStatic
        fun newInstance(url: String) = MoviesFragment().apply {
            arguments = Bundle().apply {
                putString("url", url)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext= this!!.requireContext()
        arguments?.getString("url")?.let {
            url = it
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_movies, container, false)
        if(checkInternet())searchMovies(url)
        else checkDatabase()
        return view
    }

    private fun checkDatabase() {
        doAsync {
                val movies = checkRecord()
                uiThread {
                    if(movies.isNotEmpty()) {
                        movieList=movies
                        initTemplate(movieList)
                    }else{
                        showErrorDialog()
                    }
                }
        }
    }

    private fun checkRecord(): List<MoviePojo> {
        val dbHandler = DBHelper(mContext, null)
        var returnList: MutableList<MoviePojo> = mutableListOf(MoviePojo())
        if(url.equals("popular")) {
            val cursor = dbHandler.getAllPopularMovies()
            cursor!!.moveToFirst()
            while (cursor.moveToNext()) {
                try {
                    var pojo: MoviePojo = MoviePojo()
                    pojo.id = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID))))
                    pojo.popularity =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_POPULARITY))))
                    pojo.vote_count =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VOTE_COUNT))))
                    pojo.video = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VIDEO))))
                    pojo.poster_path =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_POSTER_PATH))))
                    pojo.adult = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ADULT))))
                    pojo.backdrop_path =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BACKDROP_PATH))))
                    pojo.original_language =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGINAL_LANGUAGE))))
                    pojo.original_title =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGINAL_TITLE))))
                    pojo.vote_average =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VOTE_AVG))))
                    pojo.overview =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_OVERVIEW))))
                    pojo.release_date =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_RELEASE_DATE))))
                    pojo.title = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE))))
                    returnList.add(pojo)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            cursor.close()
        }
        if(url.equals("top_rated")) {
            val cursor = dbHandler.getAllTopMovies()
            cursor!!.moveToFirst()

            while (cursor.moveToNext()) {
                try {
                    var pojo: MoviePojo = MoviePojo()
                    pojo.id = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID))))
                    pojo.popularity =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_POPULARITY))))
                    pojo.vote_count =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VOTE_COUNT))))
                    pojo.video = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VIDEO))))
                    pojo.poster_path =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_POSTER_PATH))))
                    pojo.adult = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ADULT))))
                    pojo.backdrop_path =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BACKDROP_PATH))))
                    pojo.original_language =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGINAL_LANGUAGE))))
                    pojo.original_title =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGINAL_TITLE))))
                    pojo.vote_average =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VOTE_AVG))))
                    pojo.overview =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_OVERVIEW))))
                    pojo.release_date =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_RELEASE_DATE))))
                    pojo.title = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE))))
                    returnList.add(pojo)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            cursor.close()
        }
        if(url.equals("upcoming")) {
            val cursor = dbHandler.getAllUpMovies()
            cursor!!.moveToFirst()

            while (cursor.moveToNext()) {
                try {
                    var pojo: MoviePojo = MoviePojo()
                    pojo.id = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ID))))
                    pojo.popularity =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_POPULARITY))))
                    pojo.vote_count =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VOTE_COUNT))))
                    pojo.video = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VIDEO))))
                    pojo.poster_path =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_POSTER_PATH))))
                    pojo.adult = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ADULT))))
                    pojo.backdrop_path =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_BACKDROP_PATH))))
                    pojo.original_language =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGINAL_LANGUAGE))))
                    pojo.original_title =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ORIGINAL_TITLE))))
                    pojo.vote_average =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_VOTE_AVG))))
                    pojo.overview =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_OVERVIEW))))
                    pojo.release_date =
                        ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_RELEASE_DATE))))
                    pojo.title = ((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE))))
                    returnList.add(pojo)
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
            cursor.close()
        }
        if(returnList.count()==1) {
            return emptyList()
        }
        else{
            return returnList.subList(1,returnList.count()-1)
        }
    }

    @SuppressLint("ServiceCast")
    fun checkInternet():Boolean{
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        return isConnected
    }
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getString(R.string.baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchMovies(url:String) {
        doAsync {
            try {

                    lateinit var call: Response<MoviesPojo>
                    if (url.equals("popular"))
                        call = getRetrofit().create(APIService::class.java).getPopularMovies()
                            .execute()
                    else if (url.equals("upcoming"))
                        call = getRetrofit().create(APIService::class.java).getUpcomingMovies()
                            .execute()
                    else if (url.equals("top_rated"))
                        call = getRetrofit().create(APIService::class.java).getTopRatedMovies()
                            .execute()

                    val movies = call.body() as MoviesPojo
                    uiThread {
                        if (movies.movies.isNotEmpty()) {
                            movieList = movies.movies
                            refreshDatabase(movieList)
                            initTemplate(movieList)
                        } else {
                            showErrorDialog()
                        }
                    }

            }catch (e:Exception){
                e.printStackTrace()
            }


        }
    }


    private fun refreshDatabase(movieList: List<MoviePojo>) {
        val dbHandler = DBHelper(mContext, null)
        if(url.equals("popular")) {
            dbHandler.deleteAllPopularMovies()
            for (movie in movieList) {
                dbHandler.addPopularMovie(movie)
            }
        }
        if(url.equals("top_rated")) {
            dbHandler.deleteAllTopMovies()
            for (movie in movieList) {
                dbHandler.addTopMovie(movie)
            }
        }
        if(url.equals("upcoming")) {
            dbHandler.deleteAllUpMovies()
            for (movie in movieList) {
                dbHandler.addUpcomingMovie(movie)
            }
        }
    }

    private fun initTemplate(movieList: List<MoviePojo>) {
        if(movieList.isNotEmpty()) {
            adapter = MoviesAdapter(movieList) { movie ->

                    val fragment: Fragment = MovieDetailFragment.newInstance(movie)
                    activity?.supportFragmentManager
                        ?.beginTransaction()
                        ?.addToBackStack(movie.id)
                        ?.replace(R.id.fragment, fragment, movie.id)
                        ?.commit()

                }

            rvHits.setHasFixedSize(true)
            rvHits.layoutManager = LinearLayoutManager(mContext)
            rvHits.adapter = adapter

//            enableSwipeToDelete()

            if(swipeRefreshLayout.isRefreshing)swipeRefreshLayout.isRefreshing = false

            swipeRefreshLayout.setOnRefreshListener {
                if(checkInternet()){
                    searchMovies(url)
                    swipeRefreshLayout.isRefreshing = false
                }else{
                    Toast.makeText(mContext,getString(R.string.refreshi),
                        Toast.LENGTH_LONG).show()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }else{
            Toast.makeText(mContext,getString(R.string.noRecord),Toast.LENGTH_LONG).show()
        }
    }

    private fun showErrorDialog() {
        Toast.makeText(mContext, getString(R.string.tryAgain), Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)?.changeNavVisibility(View.VISIBLE)
    }
}