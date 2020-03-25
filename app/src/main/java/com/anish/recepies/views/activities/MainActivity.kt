package com.anish.recepies.views.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.anish.recepies.R
import com.anish.recepies.databinding.ActivityMainBinding
import com.anish.recepies.utilities.Utility
import com.anish.recepies.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    lateinit var binding:ActivityMainBinding
    lateinit var viewmodel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.log(TAG, "onCreate()")
        setupBindings(savedInstanceState)
        setUpObservers()
    }

    private fun setupBindings(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            viewmodel.init()
        }
        binding.viewmodel = viewmodel

    }

    private fun setUpObservers() {
        viewmodel.observeToastMsg().observe(
            this,
            Observer {Utility.showToast(it,Toast.LENGTH_SHORT,applicationContext) }
        )

    }
}
