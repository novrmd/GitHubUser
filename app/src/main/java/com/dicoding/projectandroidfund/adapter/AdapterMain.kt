package com.dicoding.projectandroidfund.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dicoding.projectandroidfund.data.model.User
import com.dicoding.projectandroidfund.databinding.ItemUserBinding

class AdapterMain : RecyclerView.Adapter<AdapterMain.UserViewHolder>() {
    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback?=null
    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(newList: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffCallback(list, newList))
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var user: User
        init {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
        }
        fun bind(user: User){
            this.user = user
            binding.apply{
                Glide.with(itemView).load(user.avatar_url).transition(DrawableTransitionOptions.withCrossFade()).centerCrop().into(ivUser)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data:User)
    }

    private inner class UserDiffCallback(private val oldList: List<User>, private val newList: List<User>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

