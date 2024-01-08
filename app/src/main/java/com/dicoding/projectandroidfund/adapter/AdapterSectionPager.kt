@file:Suppress("DEPRECATION")

package com.dicoding.projectandroidfund.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.projectandroidfund.R
import com.dicoding.projectandroidfund.ui.detailUser.FragmentFollowers
import com.dicoding.projectandroidfund.ui.detailUser.FragmentFollowing

class AdapterSectionPager(private val mCtx: Context, fm: FragmentManager, data: Bundle): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    private var fragmentBundle: Bundle
    init{
        fragmentBundle = data
    }


    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)
    }

    override fun getCount(): Int = TAB_TITLES.size


    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0-> fragment = FragmentFollowers()
            1-> fragment = FragmentFollowing()
        }


        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mCtx.resources.getString(TAB_TITLES [position])
    }

}
