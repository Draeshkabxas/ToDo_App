package com.derar.libya.todoapp.fragment.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentUpdateBinding
import com.derar.libya.todoapp.fragment.SharedViewModel


class UpdateFragment : Fragment() {

    //Args that passed from ListAdapter in ListFragment recycleView
    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var binding: FragmentUpdateBinding


    private val mSharedViewModel: SharedViewModel by viewModels()

    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        binding = FragmentUpdateBinding.bind(view)

        //Set title , description and spinner
        // information from passed argument
        val toDoData = args.currentItem
        binding.currentTitleEt.setText(toDoData.title)
        binding.currentDescriptionEt.setText(toDoData.description)
        binding.currentPrioritiesSpinner.setSelection(mSharedViewModel.parsePriorityToInt(toDoData.priority))

        //Set spinner onItemSelectedListener to be
        // change color listener inside SharedViewModel class
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener


        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                updateItem()
            }
            R.id.menu_delete -> {

            }
        }


        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        //Get information from user
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        val priority = binding.currentPrioritiesSpinner.selectedItem.toString()

        //Check if the title and description are valid
        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            //Update current item
            val updatedItem = ToDoData(
                args.currentItem.id,
                title,
                description,
                mSharedViewModel.parsePriority(priority)
            )

            //Update the item in the database
            mToDoViewModel.updateData(updatedItem)

            //Show successfully updated toast to the user
            Toast.makeText(
                requireContext(),
                "Successfully updated!",
                Toast.LENGTH_SHORT
            ).show()

            //Navigate back to ListFragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            //Show successfully updated toast to the user
            Toast.makeText(
                requireContext(),
                "Please fill out all fields.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}