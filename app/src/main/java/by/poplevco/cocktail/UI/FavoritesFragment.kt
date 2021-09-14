package by.poplevco.cocktail.UI

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.poplevco.cocktail.R
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.UI.adapters.DrinkFavAdapter
import by.poplevco.cocktail.ViewModel.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModel: DrinkViewModel
    var favList = mutableListOf<Drink>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =view.findNavController()

        startViewFavorite()
        viewModel.favoriteLiveData.observe(viewLifecycleOwner,  {
            favList.clear()
            favList.addAll(it)
            recyclerFavList.adapter?.notifyDataSetChanged()
        })

        recyclerFavList.layoutManager = LinearLayoutManager(activity as StartActivity)
        val adapter = DrinkFavAdapter(favList,this)
        recyclerFavList.adapter = adapter
    }
    fun showFavDetails(position: Int){
        val drink = favList[position]
        viewModel.getAllDetails(drink.id)
        navController.navigate(R.id.reviewFragment)
    }

    private fun startViewFavorite(){
        viewModel.getLikeDrinks()
    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_item).setVisible(isHidden)
        menu.findItem(R.id.filter_item_menu).setVisible(isHidden)
        menu.findItem(R.id.info_in_review).setVisible(isHidden)
        menu.findItem(R.id.done_item).setVisible(isHidden)
        menu.findItem(R.id.spinner_item).setVisible(isHidden)
        super.onPrepareOptionsMenu(menu)
    }

}