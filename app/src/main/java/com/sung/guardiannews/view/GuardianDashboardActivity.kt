package com.sung.guardiannews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sung.guardiannews.R
import com.sung.guardiannews.databinding.ActivityGuardianDashboardBinding
import com.sung.guardiannews.view.widget.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuardianDashboardActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityGuardianDashboardBinding>(
            this,
            R.layout.activity_guardian_dashboard
        )
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.dashboardBottomNavigationView)
        val navGraphIds = listOf(R.navigation.nav_guardian_news, R.navigation.nav_guardian_news_live)

        val controller: LiveData<NavController> = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host,
            intent = intent
        )
        controller.observe(this, { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    /**
     * In order to prevent the crashes from happening due to large amount of data transfer between fragments through Intent
     * The following link is the resolution to this issue.
     *
     * https://stackoverflow.com/questions/56411933/androidruntime-fatal-exception-main-process-data-parcel-size-1245464-bytes-err
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }
}