package com.example.summitapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.summitapp.databinding.FragmentCategoryLystBinding

class CategoryListFragment : Fragment() {


    private lateinit var binding : FragmentCategoryLystBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryLystBinding.inflate(inflater, container, false)
       return binding.root
    }
}