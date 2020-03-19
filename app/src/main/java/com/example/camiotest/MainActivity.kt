package com.example.camiotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.camiotest.fragments.MoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){
    lateinit var fragment: Fragment
    lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectFragment("popular")

        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_popular -> {
                    selectFragment("popular")
                    true
                }
                R.id.action_top -> {
                    selectFragment("top_rated")
                    true
                }
                R.id.action_upcoming -> {
                    selectFragment("upcoming")
                    true
                }
                else -> false
            }
        }
    }

    fun selectFragment(url:String){
        fragment = MoviesFragment.newInstance(url)
        name = "movieList"
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, fragment, name)
            .commit()
    }

    fun changeNavVisibility(view:Int){
        val bottom: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        bottom.visibility=view
    }
}
