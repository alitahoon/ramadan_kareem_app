package com.example.ramadan_kareem.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.example.ramadan_kareem.R
import com.example.ramadan_kareem.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Main : AppCompatActivity() {
    private val TAG:String?="Main"
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideActionBar()
        setBottomBarIcons()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        binding.bottomNavigationBar.setTabSelectedListener(object :
            BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                Log.i(TAG,position.toString())
            }
            override fun onTabUnselected(position: Int) {

            }
            override fun onTabReselected(position: Int) {

            }
        })
    }

    private fun hideActionBar() {
        if (getSupportActionBar() != null) {
            try {
                this.supportActionBar!!.hide()
            } // catch block to handle NullPointerException
            catch (e: NullPointerException) {
                Log.e(TAG,e.message.toString())
            }
        }
    }

    private fun setBottomBarIcons() {
        binding.bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
        binding.bottomNavigationBar
            .addItem(BottomNavigationItem(R.drawable.home, "الرئيسيه")).setActiveColor(R.color.homeCardViewIconColorDark)
            .addItem(BottomNavigationItem(R.drawable.baseline_search_24, "البحث")).setActiveColor(R.color.homeCardViewIconColorDark)
            .addItem(BottomNavigationItem(R.drawable.favourite, "المفضله")).setActiveColor(R.color.homeCardViewIconColorDark)
            .addItem(BottomNavigationItem(R.drawable.calender, "التقويم")).setActiveColor(R.color.homeCardViewIconColorDark)
            .setFirstSelectedPosition(0)
            .initialise()
    }
//    private fun setupSmoothBottomMenu() {
//        binding.bottomBar.setupWithNavController(navController)
//    }

    //set an active fragment programmatically
//    fun setSelectedItem(pos:Int){
//        binding.bottomBar.setSelectedItem(pos)
//    }
//    //set badge indicator
//    fun setBadge(pos:Int){
//        binding.bottomBar.setBadge(pos)
//    }
//    //remove badge indicator
//    fun removeBadge(pos:Int){
//        binding.bottomBar.removeBadge(pos)
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}