package com.derar.libya.todoapp.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentUpdateBinding
import com.derar.libya.todoapp.fragments.SharedViewModel


class UpdateFragment : Fragment() {

    //Args that passed from ListAdapter in ListFragment recycleView
    private val args by navArgs<UpdateFragmentArgs>()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val mSharedViewModel: SharedViewModel by viewModels()

    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set menu
        setHasOptionsMenu(true)

        //Data binding
       _binding=FragmentUpdateBinding.inflate(inflater, container, false)
        binding.args = args.currentItem



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
            R.id.menu_save -> updateItem()

            R.id.menu_delete -> confirmItemRemoval()
        }


        return super.onOptionsItemSelected(item)
    }



    /**
     * This function update currentItem with what user type
     * in updateFragment
     */
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


    /**
     * This function show alert dialog to confirm item removal
     * if user click on yes the item will be remove from database
     * and successful toast will show and the user will navigate back
     * to ListFragment
     * if user click no nothing happened
     */
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())

        //Set Yes button
        builder.setPositiveButton("Yes"){_,_->
            mToDoViewModel.deleteItem(args.currentItem)

            //Show successful toast to user
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()

            //Navigate back to ListFragment
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        //Set negative button to do nothing
        builder.setNegativeButton("No"){_,_->}

        //Set dialog title
        builder.setTitle("Delete '${args.currentItem.title}'?")

        //Set dialog message
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")

        //Create and show the dialog
        builder.create().show()
    }

    /**
     * Set _binding to null for avoid memory leaks
     */
    override fun onDestroy() {
        super.onDestroy()
        _binding =null
    }


}