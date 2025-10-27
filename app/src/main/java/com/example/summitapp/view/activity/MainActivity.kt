package com.example.summitapp.view.activity
import android.R.attr.scaleType
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.summitapp.Constants
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.summitapp.R
import com.example.summitapp.databinding.ActivityMainBinding
import com.example.summitapp.databinding.NavHeaderBinding
import com.example.summitapp.view.fragment.CartFragment
import com.example.summitapp.view.fragment.CategoryListFragment
import com.example.summitapp.view.fragment.OrdersFragment
import com.example.summitapp.view.fragment.ProfileFragment
import androidx.core.content.edit
import androidx.core.view.WindowInsetsControllerCompat


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove()
        }

        proceedWithNextSteps()
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

    @SuppressLint("SetTextI18n")
    private fun initView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_outline_menu_24)
        }

        val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
        val headerBinding = NavHeaderBinding.bind(binding.navView.getHeaderView(0))
        val fullName = pref.getString(Constants.FULL_NAME, "")
        headerBinding.tvGreeting.text = "Hello, $fullName"
        headerBinding.tvEmail.text = pref.getString(Constants.EMAIL_ID,"")
        headerBinding.tvPhone.text = pref.getString(Constants.MOBILE_NO,"")

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            when (menuItem.itemId) {
                R.id.nav_home -> openFragment(CategoryListFragment())
                R.id.nav_profile -> openFragment(ProfileFragment())
                R.id.nav_cart -> openFragment(CartFragment())
                R.id.nav_orders -> openFragment(OrdersFragment())
                R.id.nav_logout -> {
                    logout()
                }
            }
            menuItem.isChecked = false

            binding.drawerLayout.closeDrawers()
            true
        }
    }

    private fun logout() {
        val pref = getSharedPreferences(Constants.SETTING, MODE_PRIVATE)
        pref.edit { clear() }
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
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
