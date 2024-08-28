package com.example.shopclothes.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.shopclothes.databinding.AuthorisationLayoutBinding
import com.example.shopclothes.utils.mask.PhoneTextWatcher
import com.example.shopclothes.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorisationActivity : AppCompatActivity() {

    enum class REGISTRATION_STATUS{
        SUCCESS_USER,
        LATE_USER,
        NONE
    }

    val authViewModel by viewModel<AuthViewModel>()

    private lateinit var binding: AuthorisationLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AuthorisationLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.phone.observe(this) {
            binding.regBtn.visibility = if(authViewModel.isValidPhone()) View.VISIBLE else View.INVISIBLE
        }

        binding.numberField.addTextChangedListener(PhoneTextWatcher())
        binding.numberField.addTextChangedListener {
            authViewModel.insertPhone(binding.numberField.text.toString())
        }

        binding.toRegBtn.setOnClickListener {
            authViewModel.changeType(AuthViewModel.Type.Registration)
        }

        binding.changeBtn.setOnClickListener{
            when(authViewModel.type.value!!){
                AuthViewModel.Type.None -> TODO()
                AuthViewModel.Type.Registration -> {
                    authViewModel.changeType(AuthViewModel.Type.Authorisation)
                }
                AuthViewModel.Type.Authorisation -> {
                    authViewModel.changeType(AuthViewModel.Type.Registration)
                }
            }
        }

        authViewModel.type.observe(this){
            when(authViewModel.type.value!!){
                AuthViewModel.Type.None -> {
                    binding.defaultGroup.visibility = View.VISIBLE
                    binding.authGroup.visibility = View.INVISIBLE
                    binding.numberField.setText("")
                }
                AuthViewModel.Type.Registration -> {
                    binding.defaultGroup.visibility = View.INVISIBLE
                    binding.authGroup.visibility = View.VISIBLE
                    binding.numberField.setText("")
                    binding.changeBtn.text = "Авторизоваться"
                    binding.authLabel.text = "Регистрация"
                    binding.regBtn.text = "Зарегистрироваться"
                }
                AuthViewModel.Type.Authorisation -> {
                    binding.defaultGroup.visibility = View.INVISIBLE
                    binding.authGroup.visibility = View.VISIBLE
                    binding.numberField.setText("")
                    binding.changeBtn.text = "Зарегистироваться"
                    binding.authLabel.text = "Авторизация"
                    binding.regBtn.text = "Авторизоваться"
                }
            }
        }

        binding.regBtn.setOnClickListener{
            if(authViewModel.isValidPhone()){
                authViewModel.updateUser{ token ->
                    setResult(
                        RESULT_OK,
                        Intent().apply {
                            putExtra("result", REGISTRATION_STATUS.SUCCESS_USER)
                            putExtra("token", token.token)
                        }
                    )
                    finish()
                }
            }
        }

        binding.lateBtn.setOnClickListener {
            setResult(
                RESULT_OK,
                Intent().apply {
                    putExtra("result", REGISTRATION_STATUS.LATE_USER)
                }
            )
            finish()
        }
    }

}