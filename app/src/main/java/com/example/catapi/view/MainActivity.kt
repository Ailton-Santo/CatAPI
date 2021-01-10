package com.example.catapi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catapi.R
import com.example.catapi.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ImageViewModel
    private val catsAdapter = ImageAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(ImageViewModel::class.java)

        setListeners()
        setAdapter()
        observeViewModel()

        viewModel.refresh()
    }

    fun setListeners() {
        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = false
            viewModel.refresh()
        }
        radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            viewModel.typeIndex.value = i
            viewModel.refresh()
        }
    }

    fun setAdapter() {
        findViewById<RecyclerView>(R.id.imageList).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = catsAdapter
        }
    }

    fun observeViewModel() {

        viewModel.images.observe(this, Observer { images ->
            images?.let {
                catsAdapter.updateCats(it)
            }
        })
    }
}