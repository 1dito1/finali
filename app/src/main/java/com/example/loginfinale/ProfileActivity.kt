package com.example.loginfinale

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.loginfinale.adapter.ViewPagerFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerTabs: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        viewPager = findViewById(R.id.viewPager)
        viewPagerTabs = findViewById(R.id.viewPagerTabs)

        viewPager.adapter = ViewPagerFragmentAdapter(this)

        TabLayoutMediator(viewPagerTabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setIcon(R.drawable.ic_book)
                1 -> tab.setIcon(R.drawable.ic_cart)
                2 -> tab.setIcon(R.drawable.ic_profile)
            }
        }.attach()
    }
}