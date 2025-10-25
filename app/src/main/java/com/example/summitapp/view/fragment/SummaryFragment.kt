package com.example.summitapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.summitapp.R
import com.example.summitapp.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment(R.layout.fragment_summary) {
    private lateinit var binding: FragmentSummaryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSummaryBinding.bind(view)
    }
}
