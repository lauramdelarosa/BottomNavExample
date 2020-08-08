package com.delarosa.detailsurprise.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.delarosa.detailsurprise.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when (NavHostFragment.findNavController(navHostFragment).currentDestination?.id) {
            R.id.authFragment, R.id.messageFragment, R.id.infoFragment-> finish()
            else -> super.onBackPressed()
        }
    }
}

