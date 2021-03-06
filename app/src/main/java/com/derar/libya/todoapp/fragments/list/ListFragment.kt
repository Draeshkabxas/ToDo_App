package com.derar.libya.todoapp.fragments.list

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.*
import com.derar.libya.todoapp.R
import com.derar.libya.todoapp.data.models.ToDoData
import com.derar.libya.todoapp.data.viewmodel.ToDoViewModel
import com.derar.libya.todoapp.databinding.FragmentListBinding
import com.derar.libya.todoapp.fragments.SharedViewModel
import com.derar.libya.todoapp.fragments.list.adapter.ListAdapter
import com.derar.libya.todoapp.utils.hideKeyBoard
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(),SearchView.OnQueryTextListener {

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
    ): View {
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

        //Hide soft keyboard
        hideKeyBoard(requireActivity())

        //Observe LiveData to Get the data from database and set it in adapter
        mToDoViewModel.getAllData.observe(viewLifecycleOwner,  {data->
            //Call check if data base empty for show no data image and text to user
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        //Whenever emptyDatabase change value of it call showEmptyDatabaseViews
        // for show no data image and text to user if there is no data



        return binding.root
    }

    /**
     * This function setup the recycleView
     */
    private fun setupRecyclerView() {
        //The recycle view
        val recyclerView=binding.recyclerView

        //Set recycle view adapter to be ListAdapter
        recyclerView.adapter =adapter

        //Set recycle view layout manager to be linear layout
        recyclerView.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)

        //Set recycler view animator
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }

        //Set swipe to delete
        swipeToDelete(recyclerView)
    }


    /**
     * this function make recycle view item can swipe to left for delete it's self
     */
     private fun swipeToDelete(recyclerView:RecyclerView){
         val swipeToDeleteCallback = object : SwipeToDelete() {
             override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                 //Get swiped item
                 val deletedItem = adapter.dataList[viewHolder.adapterPosition]

                 //Delete item from database
                 mToDoViewModel.deleteItem(deletedItem)

                 //Notify adapter about the removing item from it
                 adapter.notifyItemRemoved(viewHolder.adapterPosition)

                 //Restore deleted item
                 restoreDeletedData(viewHolder.itemView,deletedItem)
             }
         }

        //Set attach to recycler view to be passed recyclerView
         val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
         itemTouchHelper.attachToRecyclerView(recyclerView)
     }

    /**
     * Show SnackBar to user with undo button when remove item from recycleView
     * When user click on undo the deleted item will
     * insert to database and notify adapter about it
     */
    private fun restoreDeletedData(view:View,deletedItem:ToDoData){
        val  snackBar = Snackbar.make(
            view,"Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction("Undo"){
            mToDoViewModel.insertData(toDoData = deletedItem)
        }

        snackBar.show()
    }




    /**
     * Create menu
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu,menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }


    /**
     * This function call when menu item clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //check which item click by the id of it
        when (item.itemId){
            //when delete all clicked call this function
            R.id.menu_delete_all-> confirmRemoval()

            //Sort todolist by high priority
            R.id.menu_priority_high -> mToDoViewModel.sortByHighPriority.observe(this,{ list->
                adapter.setData(list)
            })

            //Sort todolist by low priority
            R.id.menu_priority_low -> mToDoViewModel.sortByLowPriority.observe(this,{ list->
                adapter.setData(list)
            })
        }


        return super.onOptionsItemSelected(item)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null){
            searchThroughDatabase(query)
        }
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null){
            searchThroughDatabase(query)
        }
        return true
    }

    /**
     * This function gets list of todoData that have passed query in its title
     * and set this list in the adapter
     * @param query the search query that title should have
     */
    private fun searchThroughDatabase(query: String) {
        val searchQuery="%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observe(this,{ list ->
            list?.let{
                adapter.setData(it)
            }
        })
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