package com.example.scrollingimagegallery

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.scrollingimagegallery.recyclerview.ImageGalleryAdapter
import com.example.scrollingimagegallery.utils.ImageHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ImageGalleryAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var imageCountEditText: EditText

    @Inject
    lateinit var imageHandler: ImageHandler

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.toolbar_title)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        recyclerView = findViewById(R.id.recyclerView)
        imageCountEditText = findViewById(R.id.imageCountEditText)
        val applyButton: Button = findViewById(R.id.applyButton)
        applyButton.setOnClickListener {
            it.hideKeyboard()
            val countText = imageCountEditText.text.toString()
            val count = countText.toIntOrNull()
            if (count != null && count > 0) {
                viewModel.setImagesPerPage(count)
            } else {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
        adapter = ImageGalleryAdapter(imageHandler)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadImages()
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1) && viewModel.uiState.value !is UiState.Loading) {
                    viewModel.loadImages()
                }
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UiState.Loading -> swipeRefreshLayout.isRefreshing = true
                        is UiState.Success -> {
                            swipeRefreshLayout.isRefreshing = false
                            adapter.submitList(uiState.list)
                        }

                        is UiState.Error -> {
                            swipeRefreshLayout.isRefreshing = false
                            if (adapter.currentList.isEmpty()) {
                                showErrorDialog()
                            } else {
                                Toast.makeText(this@MainActivity, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error_title))
            .setMessage(getString(R.string.error_message))
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

}