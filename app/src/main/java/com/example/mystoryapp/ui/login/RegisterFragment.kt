package com.example.mystoryapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mystoryapp.R
import com.example.mystoryapp.data.remote.Result
import com.example.mystoryapp.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_register -> {
                val name = binding.edtName.text.toString()
                val email = binding.edtEmail.text.toString()
                val password = binding.edtPassword.text.toString()
                registerViewModel.register(name, email, password)
                    .observe(viewLifecycleOwner) { result ->
                        if (result != null) {
                            when (result) {
                                is Result.Loading -> {
                                    isLoading(true)
                                }

                                is Result.Success -> {
                                    isLoading(false)
                                    Toast.makeText(
                                        requireContext(),
                                        getString(R.string.register_success),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                is Result.Error -> {
                                    isLoading(false)
                                    Toast.makeText(
                                        requireContext(),
                                        result.error,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    }
            }
        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}