package com.htetarkarzaw.hanatest.persentation.component

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.htetarkarzaw.hanatest.R
import com.htetarkarzaw.hanatest.databinding.DialogErrorBinding

class ErrorDialog(
    context: Context
) : AlertDialog(context) {

    private var _binding: DialogErrorBinding? = null
    val binding get() = _binding!!

    init {
        _binding = DialogErrorBinding.inflate(LayoutInflater.from(context))
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }

    fun setUpDialog(message: String, isRetry: Boolean, onClickRetry: () -> Unit) {
        binding.tvError.text = message
        if (isRetry) {
            binding.btnRetry.text = context.getString(R.string.retry)
            binding.btnCancel.visibility = View.VISIBLE
        } else {
            binding.btnRetry.text = context.getString(R.string.okay)
            binding.btnCancel.visibility = View.GONE
        }
        binding.btnRetry.setOnClickListener {
            onClickRetry()
        }
        show()
    }

}