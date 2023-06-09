package com.htetarkarzaw.hanatest.persentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.htetarkarzaw.hanatest.databinding.ViewHolderUserBinding
import com.htetarkarzaw.hanatest.domain.vo.UserVO

class UserAdapter(
    private val onClickDetail: (Int) -> Unit
) :
    ListAdapter<UserVO, RecyclerView.ViewHolder>(userDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ViewHolderUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(getItem(position))
    }

    companion object {
        val userDiffUtil = object : DiffUtil.ItemCallback<UserVO>() {
            override fun areItemsTheSame(oldItem: UserVO, newItem: UserVO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: UserVO, newItem: UserVO): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun getClickItem(position: Int): UserVO = getItem(position)

    inner class UserViewHolder(private val binding: ViewHolderUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserVO?) {
            binding.tvName.text = item?.name
            binding.tvMail.text = item?.email ?: ""
            itemView.setOnClickListener {
                onClickDetail(adapterPosition)
            }
        }
    }
}