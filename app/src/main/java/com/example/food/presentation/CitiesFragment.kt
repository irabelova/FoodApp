package com.example.food.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.food.databinding.CitiesFragmentBinding

class CitiesFragment : Fragment() {

    private val list = listOf("Москва", "Санкт-Петербург", "Казань")

    private lateinit var binding: CitiesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CitiesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = CityAdapter(list) {
            parentFragmentManager.setFragmentResult(CITY_REQUEST_KEY, bundleOf(
                CITY_BUNDLE_KEY to it
            ))
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.cityRecycler.adapter = adapter
    }

    companion object {
        const val CITY_REQUEST_KEY = "CITY_REQUEST_KEY"
        const val CITY_BUNDLE_KEY = "CITY_BUNDLE_KEY"
    }
}