package com.sung.guardiannews.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sung.guardiannews.R
import com.sung.guardiannews.databinding.ActivityGuardianDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuardianDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityGuardianDashboardBinding>(
            this,
            R.layout.activity_guardian_dashboard
        )
    }
}