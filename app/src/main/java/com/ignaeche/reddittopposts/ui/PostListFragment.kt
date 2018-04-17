package com.ignaeche.reddittopposts.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ignaeche.reddittopposts.R
import com.ignaeche.reddittopposts.di.Injectable
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.repository.Resource
import com.ignaeche.reddittopposts.viewmodel.PostsViewModel
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_post_list.*

class PostListFragment : Fragment(), Injectable {

    @Inject lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        post_list.layoutManager = LinearLayoutManager(context)
        post_list.adapter = PostListAdapter()

        val viewModel = ViewModelProviders.of(this, factory).get(PostsViewModel::class.java)
        viewModel.getPosts().observe(this, Observer<Resource<List<PostData>>> {
            (post_list.adapter as PostListAdapter).replace(it?.data)
        })
    }
}
