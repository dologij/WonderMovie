package com.brunix.wondermovie.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.brunix.wondermovie.R
import com.brunix.wondermovie.ui.common.startActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_btn.setOnClickListener {
            startActivity<MovieListActivity> {}
        }
    }
}
