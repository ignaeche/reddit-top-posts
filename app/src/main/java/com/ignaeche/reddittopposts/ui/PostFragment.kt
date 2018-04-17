package com.ignaeche.reddittopposts.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import com.ignaeche.reddittopposts.R

import com.ignaeche.reddittopposts.databinding.FragmentPostBinding
import com.ignaeche.reddittopposts.di.Injectable
import com.ignaeche.reddittopposts.viewmodel.PostsViewModel
import com.squareup.picasso.Picasso
import javax.inject.Inject

class PostFragment : Fragment(), Injectable {
    private lateinit var postId: String

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postId = it.getString(POST_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val binding = view?.let { DataBindingUtil.getBinding<FragmentPostBinding>(it) }

        val viewModel = ViewModelProviders.of(this, factory).get(PostsViewModel::class.java)

        viewModel.postId.value = postId
        viewModel.getPost().observe(this, Observer { postData ->
            binding?.also {
                it.data = postData
                // Set image
                if (URLUtil.isValidUrl(postData?.thumbnail)) {
                    Picasso.get()
                            .load(postData?.thumbnail)
                            .resizeDimen(R.dimen.reddit_big_image, R.dimen.reddit_big_image)
                            .centerInside()
                            .into(binding.itemThumbnail)
                } else {
                    binding.itemThumbnail.setImageBitmap(null)
                }
            }
        })
    }

    companion object {
        private const val POST_ID = "POST_ID"
        @JvmStatic
        fun newInstance(postId: String) =
                PostFragment().apply {
                    arguments = Bundle().apply {
                        putString(POST_ID, postId)
                    }
                }
    }
}
