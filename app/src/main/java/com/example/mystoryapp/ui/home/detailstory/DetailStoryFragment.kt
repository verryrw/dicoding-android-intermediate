package com.example.mystoryapp.ui.home.detailstory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mystoryapp.R
import com.example.mystoryapp.databinding.FragmentDetailStoryBinding

class DetailStoryFragment : Fragment(), View.OnClickListener {
    private val args: DetailStoryFragmentArgs by navArgs()
    private var _binding: FragmentDetailStoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupAction()
    }

    private fun setupData() {
        Glide.with(requireView()).load(args.imageUrl).into(binding.imageView3)
        binding.apply {
            tvTitle.text = args.title
            tvDescription.text = args.description
        }
    }

    private fun setupAction() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}