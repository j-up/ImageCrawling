package com.jup.imagecrawling.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.jup.imagecrawling.R
import com.jup.imagecrawling.databinding.ActivityMainBinding
import com.jup.imagecrawling.repository.ImageRepository
import com.jup.imagecrawling.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
* @author: JiMinLee
* @description: 3개씩 gettyImage를 보여주는 뷰
**/

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val imageRepository = ImageRepository()
        val mainViewModel = MainViewModel(imageRepository)
        val context = applicationContext
        val gridLayoutManager = GridLayoutManager(context,3)
        val recyclerAdapter = RecyclerAdapter(null,context)


        mainViewModel.onImageLoad.observe(this, Observer {
            recyclerAdapter.items = it

            image_recycler_view.apply {
                setHasFixedSize(true)
                layoutManager = gridLayoutManager
                adapter = recyclerAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            }

            swipe_refresh_layout.isRefreshing = false
        })

        mainViewModel.onReFresh.observe(this, Observer {
            swipe_refresh_layout.isRefreshing = true
        })

        mainViewModel.onError.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
            if (swipe_refresh_layout.isRefreshing) swipe_refresh_layout.isRefreshing=false
        })

        binding.mainViewModel=mainViewModel
        binding.lifecycleOwner=this@MainActivity
    }
}
