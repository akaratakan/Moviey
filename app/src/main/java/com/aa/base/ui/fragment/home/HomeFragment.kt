package com.aa.base.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.aa.base.R
import com.aa.base.databinding.FragmentHomeBinding
import com.aa.base.ui.root.BaseFragment
import com.aa.base.ui.root.viewBinding
import com.aa.model.generic.mapData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribe()
        initListener()
    }

    private fun subscribe() {
        viewModel.fetchMovieLD.observe(viewLifecycleOwner) {
            submit(it)
            it?.mapData {
                binding.detailText.text = it.search.joinToString { it.title }
            }
        }
    }

    private fun initListener() {
        binding.movieText.addTextChangedListener {
            binding.searchButton.isEnabled = it.toString().length > 2
        }
        binding.searchButton.setOnClickListener {
            viewModel.searchMovie(binding.movieText.text.toString())
        }
    }

}