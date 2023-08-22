package com.aeon.exchangeratesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aeon.exchangeratesapp.ui.container.ContainerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            attachInitialFragment()
        }
    }

    private fun attachInitialFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ContainerFragment(), ContainerFragment.TAG)
            .commit()
    }
}
