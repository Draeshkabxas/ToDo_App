package com.derar.libya.todoapp.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.Priority
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding

    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Set menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        binding = FragmentAddBinding.bind(view)


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

        val validation = verifyDataFromUser(title, description)
        if (validation) {
            //Insert data to database
            val newData = ToDoData(
                0,
                title,
                description,
                parsePriority(priority)
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

    /**
     * This function covert spinner selected item to priority
     */
    private fun parsePriority(priority: String): Priority {
        val choices:Array<String> =resources.getStringArray(R.array.priorities)
    return when (priority)
    {
        choices[0] -> Priority.HIGH

        choices[1] -> Priority.MEDIUM

        choices[2] -> Priority.LOW

        else -> Priority.LOW
    }
}

    /**
     * This function check if title and description not empty
     * @return true  if title and description not empty
     * @return false if title or description is empty
     */
    private fun verifyDataFromUser(title: String, description: String):Boolean{
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty() || description.isEmpty())
    }

}