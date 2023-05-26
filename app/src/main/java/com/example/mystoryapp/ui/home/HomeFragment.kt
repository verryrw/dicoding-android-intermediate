package com.example.mystoryapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystoryapp.R
import com.example.mystoryapp.adapter.LoadingStateAdapter
import com.example.mystoryapp.adapter.StoryListAdapter
import com.example.mystoryapp.data.local.sharedpreference.AuthPreferences
import com.example.mystoryapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupAction()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        binding.rvStories.layoutManager = layoutManager
        binding.rvStories.addItemDecoration(dividerItemDecoration)

        val adapter = StoryListAdapter()
        binding.rvStories.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        val token = AuthPreferences(requireContext().applicationContext).getToken()!!
        homeViewModel.getAllStories("Bearer $token").observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    private fun setupAction() {
        binding.fabAddStory.setOnClickListener(this)
        binding.btnMaps.setOnClickListener(this)
        binding.btnLogout.setOnClickListener(this)
    }

    private fun clearSessionAndToken() {
        val authPreferences = AuthPreferences(requireContext())
        authPreferences.setSessionAndToken(false, "")
    }

    private fun logout() {
        clearSessionAndToken()
        Toast.makeText(requireContext(), "Logged Out", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.navigateToLoginFragment)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fab_add_story -> findNavController().navigate(R.id.navigateToAddStoryFragment)
            R.id.btn_maps -> findNavController().navigate(R.id.navigateToMapsActivity)
            R.id.btn_logout -> logout()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}