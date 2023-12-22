package com.example.suitmediaapp.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.suitmediaapp.data.response.DataItem
import com.example.suitmediaapp.databinding.ItemUserBinding

class UserAdapter: ListAdapter<DataItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)

        // fungsi ketika sebuah item ditkan, maka akan berpindah screen dengan mengirim username
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ScreenTwoActivity::class.java)
            val username = "${user.firstName} ${user.lastName}"
            intent.putExtra(ScreenTwoActivity.USERNAME_DATA, username)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: DataItem){
            binding.txtFirstname.text = user.firstName
            binding.txtLastname.text = user.lastName
            binding.txtEmail.text = user.email
            Glide.with(binding.ivUserPhoto)
                .load(user.avatar)
                .apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(binding.ivUserPhoto)
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>(){
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}