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
import com.example.camiotest.R
import com.example.camiotest.adapters.MoviesAdapter
import com.example.camiotest.data.DBHelper
import com.example.camiotest.data.MoviePojo
import com.example.camiotest.data.MoviesPojo
import com.example.camiotest.interfaces.APIService
import com.example.camiotest.callbacks.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
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
                val hits = checkRecord()
                uiThread {
                    if(hits.isNotEmpty()) {
                        movieList=hits
                        initTemplate(movieList)
                    }else{
                        showErrorDialog()
                    }
                }
        }
    }

    private fun checkRecord(): List<MoviePojo> {
        val dbHandler = DBHelper(mContext, null)
        val cursor = dbHandler.getAllHit()
        cursor!!.moveToFirst()
        var returnList:MutableList<MoviePojo> = mutableListOf(MoviePojo())
        while (cursor.moveToNext()) {
//            var pojo:MoviePojo = MoviePojo()
//            pojo.author=((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_AUTHOR))))
//            pojo.url=((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_URL))))
//            pojo.created_at=((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_CREATED))))
//            pojo.title=((cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_TITLE))))
//            returnList.add(pojo)
        }
        cursor.close()
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
                    call = getRetrofit().create(APIService::class.java).getPopularMovies().execute()
                else if(url.equals("upcoming"))
                    call = getRetrofit().create(APIService::class.java).getUpcomingMovies().execute()
                else if(url.equals("top_rated"))
                    call = getRetrofit().create(APIService::class.java).getTopRatedMovies().execute()

                val movies = call.body() as MoviesPojo
                uiThread {
                    if(movies.movies.isNotEmpty()) {
                        movieList=movies.movies
                        initTemplate(movieList)
                    }else{
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
        dbHandler.deleteAllHit()
        for(hit in movieList){
            dbHandler.addHit(hit)
        }
    }

    private fun initTemplate(movieList: List<MoviePojo>) {
        if(movieList.isNotEmpty()) {
            adapter = MoviesAdapter(movieList) { hit ->
                val url: String = "if (hit.url != null) hit.url else hit.story_url"
                if (url == null)
                    Toast.makeText(activity, getString(R.string.notUrl), Toast.LENGTH_LONG).show()
                else {
                    if(checkInternet()) {
                        val fragment: Fragment = WebviewFragment.newInstance(url)
                        activity?.supportFragmentManager
                            ?.beginTransaction()
                            ?.addToBackStack(url)
                            ?.add(R.id.fragment, fragment, url)
                            ?.commit()
                    }else{
                        Toast.makeText(mContext,getString(R.string.internet),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            rvHits.setHasFixedSize(true)
            rvHits.layoutManager = LinearLayoutManager(mContext)
            rvHits.adapter = adapter

//            enableSwipeToDelete()

            if(swipeRefreshLayout.isRefreshing)swipeRefreshLayout.isRefreshing = false

            swipeRefreshLayout.setOnRefreshListener {
                if(checkInternet()){
                    searchMovies(url)
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

    private fun enableSwipeToDelete() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()!!) {
            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {


                val position = viewHolder.adapterPosition
                val item = adapter!!.getData().get(position)

                adapter!!.removeItem(position)

                val dbHandler = DBHelper(mContext, null)
                dbHandler.addDeleteHit(item)

                val snack = Snackbar.make(constraintLayout, getString(R.string.removeHit), Snackbar.LENGTH_LONG)


                snack.setActionTextColor(Color.YELLOW)
                snack.show()

            }
        }

        val itemTouchhelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchhelper.attachToRecyclerView(rvHits)
    }



}