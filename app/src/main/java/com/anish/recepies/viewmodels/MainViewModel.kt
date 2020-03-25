package com.anish.recepies.viewmodels

import android.app.Application
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.anish.recepies.models.Recipe
import com.anish.recepies.repositories.local_database.Local_Repo
import com.anish.recepies.repositories.server.Repo_server
import com.anish.recepies.utilities.NetworkChecks
import com.anish.recepies.utilities.Utility
import com.anish.recepies.views.adapters.RecepieAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel(application: Application) : AndroidViewModel(application){
    val TAG = MainViewModel::class.java.simpleName

    var list = ArrayList<Recipe>()
    var adapter = RecepieAdapter(this)

    var observableAdapter = ObservableField<RecepieAdapter>()

    var searchValue = ObservableField<String>()
    var progress_visibility = ObservableInt(View.GONE)
    var mutableLiveData_ToastMsg = MutableLiveData<String>()
    var disposable = CompositeDisposable()

    var checked = ObservableBoolean(false)

    fun init() {
        observableAdapter.set(adapter)

        if(!NetworkChecks.isNetworkConnected(getApplication())){
            mutableLiveData_ToastMsg.value = "Displaying Previously searched Recepies"
            displayFromLocal()
        }

    }

    private fun displayFromLocal() {

        disposable.add(
            Local_Repo.getInstance(getApplication()).get().
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(
                        {

                            list.clear()
                            list.addAll(it)
                            Utility.log(TAG,"size:"+list.size)
                            adapter.setrecepies(list)
                            adapter.notifyDataSetChanged()
                        },
                        {
                            mutableLiveData_ToastMsg.value = it.message
                        }
                    )
        )

    }

    fun getRecepieByPosition(pos:Int):Recipe{
        return list.get(pos)
    }

    fun onClick(view: View){
        Utility.log(TAG,"onClick  ${searchValue.get()}")
        var value = searchValue.get()
        if(value == null || value!!.trim().equals("")){
            mutableLiveData_ToastMsg.value = "Enter Recipe"
            return
        }
        getRecepiesFromServer()
    }

    private fun getRecepiesFromServer() {

        disposable.add(
            Repo_server().apiService.getRecipes("https://recipesapi.herokuapp.com/api/search?&q="+searchValue.get()).
                map(
                        {

                            if(it.count <= 0){
                                Utility.log(TAG,"server no content")
                                throw Exception("Data not found")
                            }else{
                                Local_Repo.getInstance(getApplication()).delete()
                                Local_Repo.getInstance(getApplication()).insert(it.recipes)

                                Utility.log(TAG,"Count from server:${it.recipes.size}")

                                it
                            }
                        }
                    ).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(
                    {
                        Utility.log(TAG,"about to notify new data")
                        list.clear()
                        list.addAll(it.recipes)
                        adapter.setrecepies(list)
                        adapter.notifyDataSetChanged()

                    },
                    {
                        progress_visibility.set(View.GONE)
                        mutableLiveData_ToastMsg.value = it.message
                    },
                    {
                        progress_visibility.set(View.GONE)
                        mutableLiveData_ToastMsg.value = "Data Fetched"
                    },
                    {
                        progress_visibility.set(View.VISIBLE)
                        if(!NetworkChecks.isNetworkConnected(getApplication())){
                            throw Exception("Check Internet")
                        }
                    }
                )

        )

    }

    fun observeToastMsg():LiveData<String> = mutableLiveData_ToastMsg


    var swipeListner = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder, target: ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                // move item in `fromPos` to `toPos` in adapter.
                return true // true if moved, false otherwise
            }

            override fun onSwiped(
                viewHolder: ViewHolder,
                direction: Int
            ) { // remove from adapter
            }
        })



    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}