package com.example.carousels

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val movieListViewModel: MainViewModel by viewModel()
    private lateinit var listAdapter: ListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        movieListViewModel.mainLiveData.observe(this, Observer { json ->
            listAdapter = ListAdapter(json)
            main_recycler_view.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = listAdapter
            }
        })
        movieListViewModel.urlLiveData.observe(this, Observer { url ->
            editTextUrl.setText(url)
            button.setOnClickListener {
                val newUrl=editTextUrl.text.toString()
                movieListViewModel.getConfig(newUrl)
            }
        })
    }
}
