package com.derar.libya.todoapp.fragment.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.Priority
import com.derar.libya.todoapp.data.models.Priority.*
import com.derar.libya.todoapp.databinding.FragmentUpdateBinding
import com.derar.libya.todoapp.fragment.SharedViewModel


class UpdateFragment : Fragment() {

    //Args that passed from ListAdapter in ListFragment recycleView
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var binding: FragmentUpdateBinding


    private val mSharedViewModel: SharedViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        binding= FragmentUpdateBinding.bind(view)

        //Set title , description and spinner
        // information from passed argumnet
        val toDoData=args.currentItem 
        binding.currentTitleEt.setText(toDoData.title)
        binding.currentDescriptionEt.setText(toDoData.description)
        binding.currentPrioritiesSpinner.setSelection(parsePriority(toDoData.priority))

        //Set spinner onItemSelectedListener to be
        // change color listener inside SharedViewModel class
        binding.currentPrioritiesSpinner.onItemSelectedListener= mSharedViewModel.listener


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.update_fragment_menu,menu)
    }


    private fun parsePriority(priority: Priority):Int = when(priority){
        HIGH -> 0
        MEDIUM -> 1
        LOW -> 2
    }


}