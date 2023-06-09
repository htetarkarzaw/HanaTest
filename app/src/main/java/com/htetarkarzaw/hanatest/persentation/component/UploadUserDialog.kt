package com.htetarkarzaw.hanatest.persentation.component

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.htetarkarzaw.hanatest.databinding.DialogAddUserBinding
import com.htetarkarzaw.hanatest.persentation.screen.MainViewModel

class UploadUserDialog(
    context: Context, viewModel: MainViewModel
) : AlertDialog(context) {
    private var _binding: DialogAddUserBinding? = null
    val binding get() = _binding!!

    init {
        _binding = DialogAddUserBinding.inflate(LayoutInflater.from(context))
        setView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(true)
        setCanceledOnTouchOutside(true)

        binding.btnUpload.setOnClickListener {
            val userId = binding.etUserId.text.toString().trim()
            val title = binding.etTitle.text.toString().trim()
            val body = binding.etBody.text.toString().trim()
            if (userId.isEmpty()) {
                binding.tilUserId.error = "Please enter userId!"
                return@setOnClickListener
            } else {
                binding.tilUserId.error = null
            }
            if (title.isEmpty()) {
                binding.tilUserId.error = "Please enter userId!"
                return@setOnClickListener
            } else {
                binding.tilUserId.error = null
            }
            if (body.isEmpty()) {
                binding.tilUserId.error = "Please enter userId!"
                return@setOnClickListener
            } else {
                binding.tilUserId.error = null
            }
            viewModel.uploadUser(userId = userId, title = title, body = body)
            dismiss()
        }
    }
}