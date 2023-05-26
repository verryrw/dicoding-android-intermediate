package com.example.mystoryapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mystoryapp.R
import com.example.mystoryapp.data.local.sharedpreference.AuthPreferences

class SplashFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navigateToHome()

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun navigateToHome() {
        Handler(Looper.getMainLooper())
            .postDelayed({
                if (AuthPreferences(requireContext()).getSession()) {
                    findNavController().navigate(R.id.navigateToHomeFragment)
                } else {
                    findNavController().navigate(R.id.navigateToViewPagerFragment)
                }
            }, 2000)
    }
}