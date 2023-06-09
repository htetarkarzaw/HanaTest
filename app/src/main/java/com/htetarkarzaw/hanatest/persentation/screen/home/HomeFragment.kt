package com.htetarkarzaw.hanatest.persentation.screen.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.databinding.FragmentHomeBinding
import com.htetarkarzaw.hanatest.persentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    override fun observe() {
        lifecycleScope.launch {
            viewModel.users.collectLatest {
                when (it) {
                    is Resource.Error -> {
                        binding.textviewFirst.text = it.message
                    }

                    is Resource.Loading -> {
                        binding.textviewFirst.text = "Loading..."
                    }

                    is Resource.Nothing -> {
                        binding.textviewFirst.text = "Starting"
                    }

                    is Resource.Success -> {
                        binding.textviewFirst.text = it.data!!.size.toString()
                    }
                }
            }
        }
    }

    override fun initUi() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUser()
    }

}