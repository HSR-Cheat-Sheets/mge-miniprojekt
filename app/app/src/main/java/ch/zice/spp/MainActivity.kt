package ch.zice.spp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import ch.zice.spp.fragments.DashboardFragment
import ch.zice.spp.fragments.FriendsFragment
import ch.zice.spp.fragments.PartiesFragment
import ch.zice.spp.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.annotation.NonNull

import com.google.android.material.navigation.NavigationBarView




class MainActivity : AppCompatActivity() {

    private val dashboardFragment = DashboardFragment()
    private val partiesFragment = PartiesFragment()
    private val friendsFragment = FriendsFragment()
    private val profileFragment = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(dashboardFragment)

        Toast.makeText(
            this,
            "Hello. You are 'logged in' ;-)",
            Toast.LENGTH_SHORT
        ).show()


        val bottomNavBar = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavBar.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item ->

            val id = item.itemId

            Toast.makeText(
                this,
                "Switch tab",
                Toast.LENGTH_SHORT
            ).show()

            when (item.itemId) {
                R.id.ic_dashboard -> {
                    replaceFragment(dashboardFragment)
                    true
                }
                R.id.ic_parties -> {
                    replaceFragment(partiesFragment)
                    true
                }
                R.id.ic_friends -> {
                    replaceFragment(friendsFragment)
                    true
                }
                R.id.ic_profile -> {
                    replaceFragment(profileFragment)
                    true
                }
                else -> false
            }
        })




    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()

    }
}