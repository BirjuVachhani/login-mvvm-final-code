package com.example.loginmvvm.login.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.loginmvvm.BR

abstract class BaseActivity<VM : BaseVM, VDB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {

    abstract val viewModel: VM
    private lateinit var binding: VDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
        binding.setVariable(BR.viewModel, viewModel)
    }

}