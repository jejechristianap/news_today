package com.jejec.newstoday.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jejec.newstoday.R
import com.jejec.newstoday.adapter.NewsAdapter
import com.jejec.newstoday.ui.NewsActivity
import com.jejec.newstoday.ui.NewsViewModel
import com.jejec.newstoday.util.Constant
import com.jejec.newstoday.util.Resource
import kotlinx.android.synthetic.main.fragment_search_rx_java.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchRxJavaFragment : Fragment(R.layout.fragment_search_rx_java) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        initSearchBox()
        initRecyclerView()
        observeSearch()
    }

    private fun initSearchBox() {
        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(Constant.SEARCH_TIME_DELAY)
                editable?.let{
                    if (editable.toString().isNotEmpty()) {
                        viewModel.makeApiCall(editable.toString())
                    }
                }
            }
        }

    }

    private fun observeSearch() {
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e("BreakingNewsFragment", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter()
        rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}