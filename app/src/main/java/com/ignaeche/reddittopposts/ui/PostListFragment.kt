package com.ignaeche.reddittopposts.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

import com.ignaeche.reddittopposts.R
import com.ignaeche.reddittopposts.adapters.ClickCallback
import com.ignaeche.reddittopposts.di.Injectable
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.repository.Resource
import com.ignaeche.reddittopposts.viewmodel.PostsViewModel
import javax.inject.Inject

import kotlinx.android.synthetic.main.fragment_post_list.*

class PostListFragment : Fragment(), Injectable, ClickCallback<PostData> {

    @Inject lateinit var factory: ViewModelProvider.Factory

    lateinit var viewModel: PostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        post_list.layoutManager = layoutManager

        post_list.adapter = PostListAdapter(this)
        post_list.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))

        viewModel = ViewModelProviders.of(this, factory).get(PostsViewModel::class.java)
        viewModel.getPosts().observe(this, Observer<Resource<List<PostData>>> {
            (post_list.adapter as PostListAdapter).replace(it?.data)
        })
    }

    override fun onClick(view: View, t: PostData) {
        // This can be done much better but it's quick
        activity?.also {
            val container = it.findViewById<FrameLayout>(R.id.container)
            it.supportFragmentManager.beginTransaction()
                    .replace(container.id, PostFragment.newInstance(t.id))
                    .addToBackStack(null)
                    .commit()
        }
    }
}
