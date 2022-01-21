package com.example.loginfinale.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.loginfinale.fragment.BooksFragment
import com.example.loginfinale.fragment.CartFragment
import com.example.loginfinale.fragment.ProfileFragment

class ViewPagerFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BooksFragment()
            1 -> CartFragment()
            2 -> ProfileFragment()
            else -> BooksFragment()
        }
    }


}