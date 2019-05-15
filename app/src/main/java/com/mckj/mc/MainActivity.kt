package com.mckj.mc

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mckj.mc.home.HomepageActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun goHome(view: View) {
        HomepageActivity.goToPage(this)
    }




}
