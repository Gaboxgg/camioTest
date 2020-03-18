package com.example.camiotest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.camiotest.fragments.MoviesFragment
import com.example.camiotest.fragments.WebviewFragment

class MainActivity : AppCompatActivity(){
    lateinit var fragment: Fragment
    lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectFragment("")
    }

    fun selectFragment(url:String){

        if(url.isEmpty()) {
            fragment = MoviesFragment.newInstance()
            name = "movieList"
        }else {
            fragment = WebviewFragment.newInstance(url)
            name = url
        }
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(name)
            .add(R.id.fragment, fragment, name)
            .commit()
    }
}
