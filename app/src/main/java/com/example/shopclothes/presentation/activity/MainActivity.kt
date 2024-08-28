package com.example.shopclothes.presentation.activity

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.shopclothes.InternetCheckReceiver
import com.example.shopclothes.R
import com.example.shopclothes.databinding.ActivityMainBinding
import com.example.shopclothes.presentation.viewmodel.MainContextViewModel
import com.google.android.material.navigation.NavigationBarView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var internetReceiver: InternetCheckReceiver

    var registrationContractCallback: ((AuthorisationActivity.REGISTRATION_STATUS) -> Unit)? = null

    val registration = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        val data = result.data!!
        val token = data.getStringExtra("token")
        val status = data.getSerializableExtra("result") as AuthorisationActivity.REGISTRATION_STATUS
        when(status){
            AuthorisationActivity.REGISTRATION_STATUS.SUCCESS_USER -> {
                model.saveToken(token!!)
                model.checkAndUpdateUser{}
            }
            AuthorisationActivity.REGISTRATION_STATUS.LATE_USER -> {

            }
            AuthorisationActivity.REGISTRATION_STATUS.NONE -> {

            }
        }
        registrationContractCallback?.invoke(status)
    }

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        internetReceiver = InternetCheckReceiver{ status ->
            if(status){
                binding.internetContainer.visibility = View.GONE
            }
            else{
                binding.internetContainer.visibility = View.VISIBLE
            }
        }
        registerReceiver(
            internetReceiver,
            IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION
            )
        )
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(internetReceiver)
    }

    fun checkUserAndUpdate(callback: (AuthorisationActivity.REGISTRATION_STATUS) -> Unit){
        model.checkAndUpdateUser{
            val user = model.user.value
            if(user == null){
                registration.launch(
                    Intent(
                        this@MainActivity,
                        AuthorisationActivity::class.java
                    )
                )
                registrationContractCallback = { status ->
                    callback(status)
                }
            }
            else{
                callback.invoke(AuthorisationActivity.REGISTRATION_STATUS.SUCCESS_USER)
            }
        }
    }

    val model by viewModel<MainContextViewModel>()

    lateinit var navController: NavController

    enum class Type {
        HOME,
        CATALOG,
        CART,
        USER
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = binding.navFragmentView.getFragment<NavHostFragment>().navController
        (binding.bottomNavigationView as NavigationBarView).setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home_item -> {
                    menuCallback(Type.HOME)
                }
                R.id.catalog_item -> {
                    menuCallback(Type.CATALOG)
                }
                R.id.cart_item -> {
                    menuCallback(Type.CART)
                }
                R.id.user_item -> {
                    menuCallback(Type.USER)
                }
            }
            return@setOnItemSelectedListener true
        }

        onBackPressedDispatcher.addCallback(this){
            navController.popBackStack()
        }

        checkUserAndUpdate{}
    }

    fun menuCallback(type: Type){
        when(type){
            Type.HOME -> navController.navigate(R.id.catalogRootFragment)
            Type.CATALOG -> navController.navigate(R.id.catalogRootFragment)
            Type.CART -> navController.navigate(R.id.orderFragment)
            Type.USER -> navController.navigate(R.id.profileRootFragment)
        }
    }
}