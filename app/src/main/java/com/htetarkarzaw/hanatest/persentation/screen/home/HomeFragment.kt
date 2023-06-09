package com.htetarkarzaw.hanatest.persentation.screen.home

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.htetarkarzaw.hanatest.data.Resource
import com.htetarkarzaw.hanatest.databinding.FragmentHomeBinding
import com.htetarkarzaw.hanatest.persentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()
    private val userAdapter by lazy {
        UserAdapter { clickUser(it) }
    }

    private fun clickUser(position: Int) {
        val userVO = userAdapter.getClickItem(position)
        val navigation = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(userVO.id)
        findNavController().navigate(navigation)
    }

    override fun observe() {
        lifecycleScope.launch {
            viewModel.dbUsers.collectLatest {
                if (it.isNotEmpty()) {
                    userAdapter.submitList(it)
                    binding.tvNoData.visibility = View.GONE
                } else {
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }
        }
        lifecycleScope.launch {
            viewModel.users.collectLatest {
                binding.swipeRefresh.isRefreshing = false
                binding.progressBar.visibility = View.GONE
                when (it) {
                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Resource.Nothing -> {
                    }

                    is Resource.Success -> {
                        Log.d("hakz.home.users", "${it.data}")
                    }
                }
            }
        }
    }

    override fun initUi() {
        userAdapter.apply {
            binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
            binding.rvUser.adapter = this
        }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchUsers()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchUsers()
    }

}