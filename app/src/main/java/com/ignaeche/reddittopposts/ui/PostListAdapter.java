package com.ignaeche.reddittopposts.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ignaeche.reddittopposts.adapters.DataBoundListAdapter;
import com.ignaeche.reddittopposts.databinding.FragmentPostListItemBinding;
import com.ignaeche.reddittopposts.model.PostData;

public class PostListAdapter extends DataBoundListAdapter<PostData, FragmentPostListItemBinding> {
    @Override
    protected FragmentPostListItemBinding createBinding(ViewGroup parent) {
        return FragmentPostListItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
    }

    @Override
    protected void bind(FragmentPostListItemBinding binding, PostData item) {
        binding.setData(item);
    }

    @Override
    protected boolean areItemsTheSame(PostData oldItem, PostData newItem) {
        return oldItem.getId() == oldItem.getId();
    }

    @Override
    protected boolean areContentsTheSame(PostData oldItem, PostData newItem) {
        return oldItem.equals(newItem);
    }
}
