package by.poplevco.cocktail.UI

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.poplevco.cocktail.R
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.UI.adapters.DrinksAdapter
import by.poplevco.cocktail.UI.dialogs.DialogDelAll
import by.poplevco.cocktail.ViewModel.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModel: DrinkViewModel
    lateinit var categoryMenu: String
    var drink = mutableListOf<Drink>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel.drinkLiveData.observe(viewLifecycleOwner, {
            drink.clear()
            drink.addAll(it)
            recyclerListDrinks.adapter?.notifyDataSetChanged()

            if (drink.isEmpty()){
                progressBarMain.visibility = View.VISIBLE
                recyclerListDrinks.visibility = View.GONE
            }else{
                progressBarMain.visibility = View.GONE
                recyclerListDrinks.visibility = View.VISIBLE
            }
        })

        recyclerListDrinks.layoutManager = GridLayoutManager(activity as StartActivity, 2)
        val adapter = DrinksAdapter(drink, this)
        recyclerListDrinks.adapter = adapter

        floatingActionButton.setOnClickListener {
            navController.navigate(R.id.addFragment)
        }
        flActButOpen.setOnClickListener {
            blockSearchByIngredient.visibility = View.VISIBLE
            flActButClose.visibility = View.VISIBLE
            flActButOpen.visibility = View.GONE
        }
        flActButClose.setOnClickListener {
            blockSearchByIngredient.visibility = View.GONE
            flActButOpen.visibility = View.VISIBLE
            flActButClose.visibility = View.GONE
        }
        clickSearchIngred.setOnClickListener {
            val stringIngred = searchByIngredient.text.toString()
            val _stringIngred = replaceFirstChar(stringIngred," "," ")
            if (!_stringIngred.isEmpty()){
                viewModel.searchByIngredient(_stringIngred)
                viewModel.drinkLiveData.value = viewModel.drinkLiveData.value
            }else{
                Toast.makeText(requireContext(),"Введите ингредиент!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val mSpin = menu.findItem(R.id.spinner_item)
        val mSpinView = mSpin.actionView as Spinner

        //<----------------start spinner for category ---------->
        ArrayAdapter.createFromResource(
            activity as StartActivity,
            R.array.ru_menu_filter_category,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.spinner_drop_down_item)
            mSpinView.adapter = adapter
        }
        mSpinView.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                categoryMenu = mSpinView.selectedItem.toString()
                                    when {
                                        categoryMenu.contains("Все напитки") -> {
                                            viewModel.getData()
                                        }
                                        categoryMenu.contains("Напитки для вечеринки") -> {
                                            viewModel.getDrinksCategory("Punch / Party Drink")
                                        }
                                        categoryMenu.contains("Шоты") -> {
                                            viewModel.getDrinksCategory("Shot")
                                        }
                                        categoryMenu.contains("Коктейль") -> {
                                            viewModel.getDrinksCategory("Cocktail")
                                        }
                                        categoryMenu.contains("Мягкие напитки") -> {
                                            viewModel.getDrinksCategory("Soft Drink / Soda")
                                        }
                                        categoryMenu.contains("Обычный напиток") -> {
                                            viewModel.getDrinksCategory("Ordinary Drink")
                                        }
                                        categoryMenu.contains("Пиво") -> {
                                            viewModel.getDrinksCategory("Beer")
                                        }
                                        categoryMenu.contains("Домашний ликер") -> {
                                            viewModel.getDrinksCategory("Homemade Liqueur")
                                        }
                                        categoryMenu.contains("Молочный коктейль") -> {
                                            viewModel.getDrinksCategory("Milk / Float / Shake")
                                        }
                                        categoryMenu.contains("Какао") -> {
                                            viewModel.getDrinksCategory("Cocoa")
                                        }
                                        categoryMenu.contains("Кофе чай") -> {
                                            viewModel.getDrinksCategory("Coffee / Tea")
                                        }
                                        categoryMenu.contains("Разное") -> {
                                            viewModel.getDrinksCategory("Other/Unknown")
                                        }
                                    }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        //<----------------end spinner for category ---------->
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.selAddFavorite).isVisible = isHidden
        menu.findItem(R.id.selDelFavorite).isVisible = isHidden
        menu.findItem(R.id.selDelete).isVisible = isHidden
        menu.findItem(R.id.info_in_review).isVisible = isHidden
        menu.findItem(R.id.done_item).isVisible = isHidden
        menu.findItem(R.id.selEditDrink).isVisible = isHidden

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.selAlc -> {
                viewModel.getAlcDrinks()
                true
            }
            R.id.selNotAlc -> {
               viewModel.getNotAlcDrinks()
                true
            }
            R.id.selRandom -> {
                viewModel.randomDrink()
                true
            }
            R.id.selClearAll -> {
                startDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showDetails(position: Int) {
        val one = viewModel.drinkLiveData.value?.get(position)
        one?.let { viewModel.getAllDetails(it.id) }
        navController.navigate(R.id.action_mainFragment_to_reviewFragment)
    }

    private fun startDialog(){
        val dialog = DialogDelAll()
        dialog.show(parentFragmentManager,"clear")
    }
    private fun replaceFirstChar(string: String, delimiter: String = " ", separator: String = " "): String{
        return string.split(delimiter).joinToString(separator=separator){
            it.lowercase().replaceFirstChar { char->char.titlecase() }
        }
    }

}
