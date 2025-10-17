package com.example.summitapp.ui
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.summitapp.Constants
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.summitapp.R
import com.example.summitapp.databinding.ActivityMainBinding
import com.example.summitapp.fragments.CategoryListFragment
import com.example.summitapp.fragments.ProfileFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.iconView.animate()
                .alpha(0f)
                .setDuration(500)
                .withEndAction {
                    splashScreenView.remove()
                    proceedWithNextSteps()
                }
        }

    }

    private fun proceedWithNextSteps() {
        val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
        val hasLoggedIn = pref.getBoolean(Constants.LOGGED_IN,false)
        if (!hasLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        } else {

            enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.dark(
                    getColor(R.color.colorPrimary)
                )
            )
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            initView()
            openFragment(CategoryListFragment())
        }
    }

    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_outline_menu_24)
        }

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.nav_home -> openFragment(CategoryListFragment())
                R.id.nav_profile -> openFragment(ProfileFragment())
            }
            menuItem.isChecked = false

            binding.drawerLayout.closeDrawers()
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    binding.drawerLayout.openDrawer(GravityCompat.START)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
