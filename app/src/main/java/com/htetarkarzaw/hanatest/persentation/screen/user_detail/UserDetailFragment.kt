package com.htetarkarzaw.hanatest.persentation.screen.user_detail

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.htetarkarzaw.hanatest.databinding.FragmentUserDetailBinding
import com.htetarkarzaw.hanatest.domain.vo.UserVO
import com.htetarkarzaw.hanatest.persentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : BaseFragment<FragmentUserDetailBinding>(FragmentUserDetailBinding::inflate) {
    private val args: UserDetailFragmentArgs by navArgs()
    private val viewModel: UserDetailViewModel by viewModels()
    private var userId = 0
    override fun observe() {
        lifecycleScope.launch {
            viewModel.dbUser.collectLatest {
                if (it.id != 0) {
                    bindUserData(it)
                }
            }
        }
    }

    private fun bindUserData(it: UserVO) {
        binding.tvUserId.text = it.id.toString()
        binding.tvName.text = it.name
        binding.tvEmail.text = it.email
        binding.tvWebsite.text = it.website
        binding.tvPhone.text = it.phone
        binding.tvAddress.text = it.address
        binding.tvCompany.text = it.company
    }

    override fun initUi() {
        userId = args.userId
        viewModel.retrieveUserById(userId)
    }


}