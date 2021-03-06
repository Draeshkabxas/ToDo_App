package com.derar.libya.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentAddBinding
import com.derar.libya.todoapp.fragments.SharedViewModel


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Set menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        binding = FragmentAddBinding.bind(view)

        //Set spinner onItemSelectedListener to be
        // change color listener inside SharedViewModel class
        binding.prioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return view
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                insertDataToDatabase()
            }
        }


        return super.onOptionsItemSelected(item)
    }

    /**
     * This function insert to do data  to room database
     * First check if all information not empty
     * then insert the new data to the database
     * then show toast to the user to show them what's happened
     * Finally navigate back to listFragment
     * If any information missing it's show the user toast
     */
    private fun insertDataToDatabase() {
        val title = binding.titleEt.text.toString()
        val priority = binding.prioritiesSpinner.selectedItem.toString()
        val description = binding.descriptionEt.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            //Insert data to database
            val newData = ToDoData(
                0,
                title,
                description,
                mSharedViewModel.parsePriority(priority)
            )


            mToDoViewModel.insertData(newData)
            Toast.makeText(
                requireContext(),
                "successfully added!",
                Toast.LENGTH_SHORT
            ).show()

            //Navigate back to ListFragment
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        } else {
            Toast.makeText(
                requireContext(),
                "Please fill out all fields.",
                Toast.LENGTH_SHORT
            ).show()
        }

    }



}