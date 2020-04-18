package kz.yeltayev.aqms.module.searchplaces

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_access_code.view.*
import kz.yeltayev.aqms.R
import kz.yeltayev.aqms.api.ApiServiceModule
import kz.yeltayev.aqms.module.live.widget.PlaceUiModel
import kz.yeltayev.aqms.utils.ResourceProvider
import timber.log.Timber


class SearchPlacesViewModel(
    private val res: ResourceProvider
) : ViewModel() {

    lateinit var navController: NavController

    val isLoading = ObservableBoolean()
    val filteredPlaces = ObservableField<List<PlaceUiModel>>()

    private val serviceModule = ApiServiceModule()
    private val disposable = CompositeDisposable()
    private val allPlaces = ObservableField<List<PlaceUiModel>>()

    private lateinit var fragment: SearchPlacesFragment

    init {
        fetchPlaces()
    }

    private fun fetchPlaces() {
        isLoading.set(true)
        val placeService = serviceModule.getPlaceService()
        disposable.add(
            placeService.fetchPlaces()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { response ->
                    val placeUiList = mutableListOf<PlaceUiModel>()

                    response.body()?.filter { place ->
                        place.accessCode.isEmpty()
                    }?.forEach { item ->
                        placeUiList.add(PlaceUiModel(item))
                    }

                    allPlaces.set(placeUiList)
                    filteredPlaces.set(placeUiList)

                    isLoading.set(false)
                }
                .doOnError { error ->
                    isLoading.set(false)
                    Timber.d("yeltayev22 $error")
                }
                .subscribe()
        )
    }

    fun search(query: String) {
        val allPlaces = allPlaces.get() ?: return
        val filtered = allPlaces.filter { item ->
            item.place.city.contains(query, true) || item.place.country.contains(query, true)
        }

        filteredPlaces.set(filtered)
    }

    fun onPlaceSelected(item: PlaceUiModel) {
        val bundle = bundleOf("placeUiModel" to item)
        navController.navigate(R.id.action_search_places_dest_to_place_dest, bundle)
    }

    fun onKeyClicked() {
        val context = fragment.context ?: return

        val dialogBuilder = AlertDialog.Builder(context)
        val dialogContainer =
            LayoutInflater.from(context).inflate(R.layout.dialog_access_code, null)
        val editText = dialogContainer.edit_text

        dialogBuilder
            .setTitle(res.getString(R.string.label_access_code))
            .setMessage(res.getString(R.string.message_enter_access_code))
            .setNegativeButton(res.getString(R.string.label_cancel), null)
            .setPositiveButton(res.getString(R.string.label_find)) { dialog, which ->
                val accessCode = editText.text.toString()
                findPlaceByAccessCode(accessCode)
            }
            .setView(dialogContainer)
            .setCancelable(false)
            .show()
    }

    @SuppressLint("ShowToast")
    private fun findPlaceByAccessCode(accessCode: String) {
        if (accessCode.isEmpty()) {
            return
        }

        val placeService = serviceModule.getPlaceService()
        disposable.add(placeService.fetchPlaceByAccessCode(accessCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess { response ->
                val place = response.body()
                if (place != null) {
                    val bundle = bundleOf("placeUiModel" to PlaceUiModel(place))
                    navController.navigate(R.id.action_search_places_dest_to_place_dest, bundle)
                } else {
                    Toast.makeText(
                        fragment.context,
                        res.getString(R.string.message_no_place),
                        Toast.LENGTH_SHORT
                    )
                }
            }
            .doOnError { error ->
                Timber.d("yeltayev22 $error")
            }
            .subscribe()
        )
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun setFragment(fragment: SearchPlacesFragment) {
        this.fragment = fragment
    }
}