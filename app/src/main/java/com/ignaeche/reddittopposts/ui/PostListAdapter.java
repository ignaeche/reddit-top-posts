package com.ignaeche.reddittopposts.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import com.ignaeche.reddittopposts.R;
import com.ignaeche.reddittopposts.adapters.ClickCallback;
import com.ignaeche.reddittopposts.adapters.DataBoundListAdapter;
import com.ignaeche.reddittopposts.databinding.FragmentPostListItemBinding;
import com.ignaeche.reddittopposts.model.PostData;
import com.squareup.picasso.Picasso;

public class PostListAdapter extends DataBoundListAdapter<PostData, FragmentPostListItemBinding> {

    private ClickCallback<PostData> callback;

    public PostListAdapter(ClickCallback<PostData> callback) {
        this.callback = callback;
    }

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
        binding.setCallback(callback);
        if (URLUtil.isValidUrl(item.getThumbnail())) {
            Picasso.get().load(item.getThumbnail())
                    .resizeDimen(R.dimen.reddit_image, R.dimen.reddit_image)
                    .centerCrop()
                    .into(binding.itemThumbnail);
        } else {
            binding.itemThumbnail.setImageBitmap(null);
        }
    }

    @Override
    protected boolean areItemsTheSame(PostData oldItem, PostData newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(PostData oldItem, PostData newItem) {
        return oldItem.equals(newItem);
    }
}
