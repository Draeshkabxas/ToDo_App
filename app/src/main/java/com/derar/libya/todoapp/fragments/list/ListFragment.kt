package com.derar.libya.todoapp.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentListBinding
import com.derar.libya.todoapp.fragments.SharedViewModel


class ListFragment : Fragment() {

    //Recycle view adapter
    private val adapter : ListAdapter by lazy {
       ListAdapter()
    }

    private val mToDoViewModel: ToDoViewModel by viewModels()

    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Debug:findNav","Star Fragment")

        //Tell the fragment that it have menu or Set Menu
        setHasOptionsMenu(true)

        //DataBinding Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)

        //Set lifecycleOwner binding to be this
        binding.lifecycleOwner= this

        //Set binding variable mSharedViewModel  to be this mSharedViewModel
        binding.mSharedViewModel=mSharedViewModel

        //Setup recycleView
        setupRecyclerView()

        //Observe LiveData to Get the data from database and set it in adapter
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {data->
            //Call check if data base empty for show no data image and text to user
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //Whenever emptyDatabase change value of it call showEmptyDatabaseViews
        // for show no data image and text to user if there is no data



        return binding.root
    }

    private fun setupRecyclerView() {
        //The recycle view
        val recyclerView=binding.recyclerView

        //Set recycle view adapter to be ListAdapter
        recyclerView.adapter =adapter

        //Set recycle view layout manager to be linear layout
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
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


    /**
     * Set _binding to null for avoid memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}