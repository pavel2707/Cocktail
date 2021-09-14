package by.poplevco.cocktail.UI

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.poplevco.cocktail.R
import by.poplevco.cocktail.UI.dialogs.DialogDelOne
import by.poplevco.cocktail.UI.dialogs.DialogInfoMeasure
import by.poplevco.cocktail.ViewModel.DrinkViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_review.*


class ReviewFragment : Fragment(){
    lateinit var navController: NavController
    lateinit var viewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        viewModel.drink.observe(viewLifecycleOwner,  {

            if (viewModel.drink.value == null) {
                progressBar.visibility = View.VISIBLE
                revLayout.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                revLayout.visibility = View.VISIBLE

                detailsNameTV.text = viewModel.drink.value?.strDrink
                Glide.with(detailsImageView)
                    .load(if (viewModel.drink.value?.strDrinkThumb == null|| viewModel.drink.value!!.strDrinkThumb!!.contains("null"))
                        viewModel.drink.value?.strBespokeImage else viewModel.drink.value?.strDrinkThumb )
                    .optionalCircleCrop()
                    .into(detailsImageView)
                detailsCategoryTV.text = viewModel.drink.value?.strCategory
                detailsAlcoholicTV.text = viewModel.drink.value?.strAlcoholic
                detailsInstrTV.text = viewModel.drink.value?.strInstructions

                detailsIngredients1TV.text = viewModel.drink.value?.strIngredient1 ?: ""
                detailsMeasure1TV.text = viewModel.drink.value?.strMeasure1 ?: ""

                detailsIngredients2TV.text = viewModel.drink.value?.strIngredient2 ?: ""
                detailsMeasure2TV.text = viewModel.drink.value?.strMeasure2 ?: ""

                detailsIngredients3TV.text = viewModel.drink.value?.strIngredient3 ?: ""
                detailsMeasure3TV.text = viewModel.drink.value?.strMeasure3 ?: ""

                detailsIngredients4TV.text = viewModel.drink.value?.strIngredient4 ?: ""
                detailsMeasure4TV.text = viewModel.drink.value?.strMeasure4 ?: ""

                detailsIngredients5TV.text = viewModel.drink.value?.strIngredient5 ?: ""
                detailsMeasure5TV.text = viewModel.drink.value?.strMeasure5 ?: ""

                detailsIngredients6TV.text = viewModel.drink.value?.strIngredient6 ?: ""
                detailsMeasure6TV.text = viewModel.drink.value?.strMeasure6 ?: ""

                detailsIngredients7TV.text = viewModel.drink.value?.strIngredient7 ?: ""
                detailsMeasure7TV.text = viewModel.drink.value?.strMeasure7 ?: ""

                detailsIngredients8TV.text = viewModel.drink.value?.strIngredient8 ?: ""
                detailsMeasure8TV.text = viewModel.drink.value?.strMeasure8 ?: ""

                detailsIngredients9TV.text = viewModel.drink.value?.strIngredient9 ?: ""
                detailsMeasure9TV.text = viewModel.drink.value?.strMeasure9 ?: ""

                detailsIngredients10TV.text = viewModel.drink.value?.strIngredient10 ?: ""
                detailsMeasure10TV.text = viewModel.drink.value?.strMeasure10 ?: ""

                detailsIngredients11TV.text = viewModel.drink.value?.strIngredient11 ?: ""
                detailsMeasure11TV.text = viewModel.drink.value?.strMeasure11 ?: ""
                detailsIngredients12TV.text = viewModel.drink.value?.strIngredient12 ?: ""
                detailsMeasure12TV.text = viewModel.drink.value?.strMeasure12 ?: ""
                detailsIngredients13TV.text = viewModel.drink.value?.strIngredient13 ?: ""
                detailsMeasure13TV.text = viewModel.drink.value?.strMeasure13 ?: ""
                detailsIngredients14TV.text = viewModel.drink.value?.strIngredient14 ?: ""
                detailsMeasure14TV.text = viewModel.drink.value?.strMeasure14 ?: ""
                detailsIngredients15TV.text = viewModel.drink.value?.strIngredient15 ?: ""
                detailsMeasure15TV.text = viewModel.drink.value?.strMeasure15 ?: ""

                img_favorite_false.isVisible = viewModel.drink.value!!.favorite == false
                img_favorite_true.isVisible = viewModel.drink.value!!.favorite == true
            }
        })

        img_favorite_false.setOnClickListener {
            falseOnClickImage()
        }
        img_favorite_true.setOnClickListener {
            trueOnclickImage()
        }
    }

    private fun falseOnClickImage() {
        if (viewModel.drink.value?.favorite == false) {

            viewModel.drink.value?.favorite = true
            viewModel.drink.value = viewModel.drink.value

            viewModel.updateDrink(viewModel.drink.value!!)
            Toast.makeText(
                activity as StartActivity,
                "Добавлено в избранное: ${viewModel.drink.value?.strDrink}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    private fun trueOnclickImage() {
        if (viewModel.drink.value?.favorite == true) {

            viewModel.drink.value?.favorite = false
            viewModel.drink.value = viewModel.drink.value

            viewModel.updateDrink(viewModel.drink.value!!)
            Toast.makeText(
                activity as StartActivity,
                "Удалено из избранного: ${viewModel.drink.value?.strDrink}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.search_item).setVisible(isHidden)
        menu.findItem(R.id.selAlc).setVisible(isHidden)
        menu.findItem(R.id.selNotAlc).setVisible(isHidden)
        menu.findItem(R.id.selRandom).setVisible(isHidden)
        menu.findItem(R.id.selClearAll).setVisible(isHidden)
        menu.findItem(R.id.done_item).setVisible(isHidden)
        menu.findItem(R.id.spinner_item).setVisible(isHidden)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.selAddFavorite -> {
                falseOnClickImage()
                return true
            }
            R.id.selDelFavorite -> {
                trueOnclickImage()
                return true
            }
            R.id.selDelete ->{
                startDialog()
                return true
            }
            R.id.info_in_review ->{
                dialogInfoMeasure()
                return true
            }
            R.id.selEditDrink ->{
                navController.navigate(R.id.editFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun startDialog(){
        val dialog = DialogDelOne()
        dialog.show(parentFragmentManager,"del")
    }
    private fun dialogInfoMeasure(){
        val dialog = DialogInfoMeasure()
        dialog.show(parentFragmentManager,"info")
    }

}

