package com.delarosa.prueba.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.delarosa.prueba.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when (NavHostFragment.findNavController(navHostFragment).currentDestination?.id) {
            R.id.iceCreamFragment, R.id.billFragment -> finish()
            else -> super.onBackPressed()
        }
    }
}

