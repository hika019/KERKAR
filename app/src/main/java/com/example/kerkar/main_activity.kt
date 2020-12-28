package com.example.kerkar


import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.main_activity.*


class main_activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        this.setToolbar()
        this.setDrawerLayout()
        this.setHomeFragment()





    }

    private fun setToolbar(){
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(false)

    }


    private fun setDrawerLayout(){
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open , R.string.nav_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    private fun setHomeFragment(){
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.nav_host_fragment, Home_fragment())
        ft.commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment : Fragment? = null

        when (item.itemId){
            R.id.nav_home ->{
                fragment = Home_fragment()
                Log.d("main_activity", "select fragment: Home_fragment")
            }
            R.id.nav_timetable -> {
                fragment = Timetable_fragment()
                Log.d("main_activity", "select fragment: Timetable_fragment")
            }
            R.id.nav_assignment_list -> {
                fragment = Assignment_list_fragment()
                Log.d("main_activity", "select fragment: Assignment_list_fragment")
            }
            R.id.nav_setting -> {
                fragment = Setting_fragment()
                Log.d("main_activity", "select fragment: Setting_fragment")
            }
        }

        if (fragment != null){
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.nav_host_fragment, fragment)
            ft.commit()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}