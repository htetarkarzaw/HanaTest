package com.htetarkarzaw.hanatest.persentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.htetarkarzaw.hanatest.persentation.component.ErrorDialog
import com.htetarkarzaw.hanatest.persentation.component.LoadingDialog
import com.htetarkarzaw.hanatest.utils.InflateActivity

abstract class BaseActivity<VB: ViewBinding>(private val inflate: InflateActivity<VB>) :
    AppCompatActivity() {

    private var _binding: VB? = null
    val binding get() = _binding!!
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog(this) }
    val errorDialog: ErrorDialog by lazy { ErrorDialog(this) }
    abstract fun initUi()
    abstract fun observe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        setContentView(binding.root)
        initUi()
        observe()
    }

    fun showLoadingDialog(text: String) {
        loadingDialog.apply {
            setMessage(text)
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            show()
        }
    }

    fun hideLoadingDialog() {
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }
}