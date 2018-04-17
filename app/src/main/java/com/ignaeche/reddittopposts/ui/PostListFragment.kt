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
import android.widget.Toast

import com.ignaeche.reddittopposts.R
import com.ignaeche.reddittopposts.adapters.ClickCallback
import com.ignaeche.reddittopposts.di.Injectable
import com.ignaeche.reddittopposts.model.PostData
import com.ignaeche.reddittopposts.repository.Resource
import com.ignaeche.reddittopposts.repository.Status
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

        swipe_refresh.setOnRefreshListener { viewModel.refreshPosts() }
        viewModel.getPosts().observe(this, Observer<Resource<List<PostData>>> {
            when (it?.status) {
                Status.LOADING -> {
                    swipe_refresh.isRefreshing = true
                }
                Status.SUCCESS -> {
                    swipe_refresh.isRefreshing = false
                }
                Status.ERROR -> {
                    swipe_refresh.isRefreshing = false
                    Toast.makeText(context, R.string.network_error, Toast.LENGTH_SHORT).show()
                }
            }
            (post_list.adapter as PostListAdapter).replace(it?.data)
        })
    }

    override fun onClick(view: View, t: PostData) {
        when (view.id) {
            R.id.item_layout -> {
                viewModel.markAsRead(t.id)
                // This can be done much better but it's quick
                activity?.also {
                    val container = it.findViewById<FrameLayout>(R.id.container)?.id ?: it.findViewById<FrameLayout>(R.id.container_right).id
                    it.supportFragmentManager.beginTransaction()
                            .replace(container, PostFragment.newInstance(t.id))
                            .addToBackStack(null)
                            .commit()
                }
            }
            R.id.dismiss_button -> {
                viewModel.removePost(t)
            }
        }
    }
}
