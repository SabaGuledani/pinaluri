package com.saba.spark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.navigation.NavigationBarView
import com.saba.spark.databinding.ActivityMainBinding
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewPager2Adapter: ViewPagerAdapter
    private val NavigationItemSelectedListener = NavigationBarView.OnItemSelectedListener {
        when (it.itemId){
            R.id.badHabits ->{
                viewPager2.currentItem = 0
                return@OnItemSelectedListener true
            }
            R.id.GoodHabits ->{
                viewPager2.currentItem = 1
                return@OnItemSelectedListener true
            }
            R.id.UglyHabit ->{
                viewPager2.currentItem = 2
                return@OnItemSelectedListener true
            }
        }
        false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewPager2 = findViewById(R.id.view_pager)
        var bottomNavigationView = binding.navView
        viewPager2Adapter = ViewPagerAdapter(this)
        viewPager2.adapter = viewPager2Adapter
        bottomNavigationView.setOnItemSelectedListener (NavigationItemSelectedListener)
        viewPager2.registerOnPageChangeCallback(object :OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position){
                    0->bottomNavigationView.menu.findItem(R.id.badHabits).isChecked=true
                    1->bottomNavigationView.menu.findItem(R.id.GoodHabits).isChecked=true
                    2->bottomNavigationView.menu.findItem(R.id.UglyHabit).isChecked=true
                }
            }
        })


        }
    override fun onBackPressed() {
        if (viewPager2.currentItem == 0){
            super.onBackPressed()
        }else{
            viewPager2.currentItem = viewPager2.currentItem - 1
        }

    }


    }
