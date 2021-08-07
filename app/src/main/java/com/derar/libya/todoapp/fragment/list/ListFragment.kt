package com.derar.libya.todoapp.fragment.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {


    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Debug:findNav","Star Fragment")
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)

        //Change the fragment to aadFragment
        // when user click on add floating button
        binding.floatingActionButton.setOnClickListener { view ->
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            Log.d("Debug:findNav","${view}")
        }

        /**
         * Change the fragment to updateFragment
         * when user click on listFragment
         */
        binding.listLayout.setOnClickListener {
          findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }


        return view
    }


}