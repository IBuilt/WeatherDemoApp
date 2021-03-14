package com.demo.weather.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.demo.lib.remote.FetchedResult
import com.demo.lib.utils.extensions.inflate
import com.demo.weather.R
import com.demo.weather.constants.Constant
import com.demo.weather.data.entities.current.CityInfo
import com.demo.weather.listener.IFragmentCommunicator
import com.demo.weather.ui.map.MapActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    private val cityAndWeatherAdapter = CityAndWeatherAdapter()

    private lateinit var iFragmentCommunicator: IFragmentCommunicator

    private lateinit var activityLauncher: ActivityResultLauncher<Intent>


    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.iFragmentCommunicator = context as IFragmentCommunicator
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setHasOptionsMenu(true)

        registerActivityResult()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_home)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_search)
            showMapActivity()

        return super.onOptionsItemSelected(item)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cityRecyclerView.apply {
            this.adapter = cityAndWeatherAdapter
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel.findAll()
            .observe(this.requireActivity())
            { list -> cityAndWeatherAdapter.submitList(list) }


        handleEvents()
    }


    private fun handleEvents() {

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback =

            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (viewHolder.absoluteAdapterPosition >= 0) {
                        showDeleteCityConfirmationDialog(viewHolder.absoluteAdapterPosition)
                    }
                }
            }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(cityRecyclerView)
    }


    private fun showDeleteCityConfirmationDialog(position: Int) {

        AlertDialog.Builder(this.activity)
            .setTitle(getString(R.string.title_delete))
            .setMessage(getString(R.string.are_you_sure_to_delete))
            .setPositiveButton(getString(R.string.ok_button)) { _, _ ->
                deleteCity(position)
            }
            .setNegativeButton(getString(R.string.cancel_button)) { _, _ ->
                cityAndWeatherAdapter.notifyItemChanged(position)
            }
            .show()
    }


    private fun deleteCity(position: Int) {

        val cityAndWeather = cityAndWeatherAdapter.getCityItem(position)

        cityAndWeather?.let { nsCityAndWeather ->

            lifecycleScope.launch {
                homeViewModel.deleteCity(nsCityAndWeather.city!!)
            }
        }
    }


    private fun doFetchAndAddCityWithWeather(city: CityInfo) {

        homeViewModel.findWeatherByNewCity(city)
            .observe(this.requireActivity(), Observer { fetchedResult ->

                when (fetchedResult.status) {

                    FetchedResult.Status.LOADING -> {
                        progressBar.show()
                    }

                    FetchedResult.Status.SUCCESS -> {
                        progressBar.hide()
                    }

                    FetchedResult.Status.ERROR -> {
                        progressBar.hide()
                        doAfterErrorOnFetchingWeather(fetchedResult.message)
                    }
                }
            })
    }


    private fun doAfterErrorOnFetchingWeather(errorMessage: String?) {

        errorMessage?.let {
            Toast.makeText(this.requireContext(), errorMessage, Toast.LENGTH_LONG).show()
        }
    }


    private fun registerActivityResult() {

        activityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK)

                    result.data?.let { nsIntent ->

                        val newCity = homeViewModel.generateNewCity(
                            nsIntent.getStringExtra(Constant.CityType.CITY_NAME),
                            nsIntent.getDoubleExtra(Constant.CityType.LATITUDE, 0.0),
                            nsIntent.getDoubleExtra(Constant.CityType.LONGITUDE, 0.0)
                        )

                        doFetchAndAddCityWithWeather(newCity)
                    }
            }
    }


    private fun showMapActivity() {

        Intent(this.activity, MapActivity::class.java).apply {
            activityLauncher.launch(this)
        }
    }
}