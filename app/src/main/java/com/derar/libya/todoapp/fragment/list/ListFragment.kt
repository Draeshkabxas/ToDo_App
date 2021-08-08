package com.derar.libya.todoapp.fragment.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentListBinding


class ListFragment : Fragment() {

    //Recycle view adapter
    private val adapter : ListAdapter by lazy {
       ListAdapter()
    }

    private val mToDoViewModel: ToDoViewModel by viewModels()

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
            adapter.setData(data)
        })

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


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)
    }


}