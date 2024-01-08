package com.dicoding.projectandroidfund.ui.detailUser

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.projectandroidfund.R
import com.dicoding.projectandroidfund.databinding.FragmentFollsBinding
import com.dicoding.projectandroidfund.adapter.AdapterMain

class FragmentFollowers: Fragment (R.layout. fragment_folls) {
    private var _binding: FragmentFollsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModelFollowers
    private lateinit var adapter: AdapterMain
    private lateinit var username: String

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollsBinding.bind(view)


        val args = arguments
        username = args?.getString(DetailUserActivity.EXTRA_USERNAME).toString()


        adapter = AdapterMain()
        adapter.notifyDataSetChanged()

        binding.apply{
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager=LinearLayoutManager(activity)
            rvUser.adapter=adapter
        }
        showLoading(true)


        viewModel=ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ViewModelFollowers::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner){
            if(it!=null){
                adapter.setList(it)
                showLoading(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}