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