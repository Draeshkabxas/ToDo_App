package com.derar.libya.todoapp.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.ToDoDatabase
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentListBinding
import com.derar.libya.todoapp.fragment.SharedViewModel


class ListFragment : Fragment() {

    //Recycle view adapter
    private val adapter : ListAdapter by lazy {
       ListAdapter()
    }

    private val mToDoViewModel: ToDoViewModel by viewModels()

    private val mSharedViewModel: SharedViewModel by viewModels()

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Debug:findNav","Star Fragment")

        //Tell the fragment that it have menu or Set Menu
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        binding = FragmentListBinding.bind(view)

        //The recycle view
        val recyclerView=binding.recyclerView

        //Set recycle view adapter to be ListAdapter
        recyclerView.adapter =adapter

        //Set recycle view layout manager to be linear layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //Get the data from database and set it in adapter
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {data->
            //Call check if data base empty for show no data image and text to user
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //Whenever emptyDatabase change value of it call showEmptyDatabaseViews
        // for show no data image and text to user if there is no data
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseViews(it)
        })

        //Change the fragment to aadFragment
        // when user click on add floating button
        binding.floatingActionButton.setOnClickListener { view ->
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
            Log.d("Debug:findNav","$view")
        }
        return view
    }

    /**
     * This function change the visibility of no data image and no data text
     * IF passed pram is ture to set visibility to visible
     * IF passed pram is false to set visibility to invisible
     * @param emptyDatabase the situation of the database is it empty or not
     */
    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase){
            binding.noDataImage.visibility = View.VISIBLE
            binding.noDataText.visibility = View.VISIBLE
        }else{
            binding.noDataImage.visibility = View.INVISIBLE
            binding.noDataText.visibility = View.INVISIBLE
        }

    }


    /**
     * Create menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }


    /**
     * This function call when menu item clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //check which item click by the id of it
        when (item.itemId){
            //when delete all clicked call this function
            R.id.menu_delete_all-> confirmRemoval()
        }


        return super.onOptionsItemSelected(item)
    }


    /**
     * This function show alert dialog to confirm removal all item from database table
     * if user click on yes then all item will be remove from database
     * and successful toast will show
     * if user click no nothing happened
     */
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())

        //Set Yes button
        builder.setPositiveButton("Yes"){_,_->

            //Call todoViewModel to delete everything in the database
            mToDoViewModel.deleteAll()

            //Show successful toast to user
            Toast.makeText(
                requireContext(),
                "Successfully Removed Everything!",
                Toast.LENGTH_SHORT
            ).show()
        }
        //Set negative button to do nothing
        builder.setNegativeButton("No"){_,_->}

        //Set dialog title
        builder.setTitle("Delete Everything?")

        //Set dialog message
        builder.setMessage("Are you sure you want to remove everything?")

        //Create and show the dialog
        builder.create().show()
    }


}