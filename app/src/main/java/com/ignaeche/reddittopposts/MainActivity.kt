package com.ignaeche.reddittopposts

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import com.ignaeche.reddittopposts.ui.PostListFragment
import com.ignaeche.reddittopposts.viewmodel.PostsViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var factory: ViewModelProvider.Factory

    var mLandscape: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container_left?.also { mLandscape = true }

        if (mLandscape) {
            supportFragmentManager.beginTransaction()
                    .replace(container_left.id, PostListFragment())
                    .commit()
        } else {
            supportFragmentManager.beginTransaction()
                    .replace(container.id, PostListFragment())
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_dismiss_all -> {
                ViewModelProviders.of(this, factory).get(PostsViewModel::class.java).removeAllPosts()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
