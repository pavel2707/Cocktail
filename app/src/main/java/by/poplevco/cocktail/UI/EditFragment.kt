package by.poplevco.cocktail.UI

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import by.poplevco.cocktail.R
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.ViewModel.DrinkViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_edit.*
import java.io.ByteArrayOutputStream


class EditFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModel: DrinkViewModel
    lateinit var editCategory: String
    lateinit var editAlcoholic: String
    lateinit var strThumb: String
    lateinit var strIdDrink: String
    var editBespoke = byteArrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data: Intent? = result.data
                    val bmp = data?.extras?.get("data") as Bitmap
                    val stream = ByteArrayOutputStream()
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArray = stream.toByteArray()
                    editBespoke = byteArray

                    val bmpFact = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    editDrinkThumb.setImageBitmap(bmpFact)
                }
            }
        doImg.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }
        editDrinkNameET.text = editDrinkNameET.text.append(viewModel.drink.value?.strDrink)
        Glide.with(editDrinkThumb)
            .load(if (viewModel.drink.value?.strDrinkThumb == null||
                viewModel.drink.value!!.strDrinkThumb!!.contains("null"))viewModel.drink.value?.strBespokeImage else viewModel.drink.value?.strDrinkThumb )
            .circleCrop()
            .into(editDrinkThumb)

        editInstructionsET.text = editInstructionsET.text.append(viewModel.drink.value?.strInstructions)

        //<----------------start spinner for category ---------->
        ArrayAdapter.createFromResource(
            activity as StartActivity,
            R.array.ru_category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editSpinnerCategory.adapter = adapter
        }
        editSpinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                editCategory = editSpinnerCategory.selectedItem.toString()
                when{
                    editCategory.contains("Напитки для вечеринки") -> editCategory = "Punch / Party Drink"
                    editCategory.contains("Шоты") -> editCategory = "Shot"
                    editCategory.contains("Коктейль") ->editCategory = "Cocktail"
                    editCategory.contains("Мягкие напитки") -> editCategory = "Soft Drink / Soda"
                    editCategory.contains("Обычный напиток") -> editCategory = "Ordinary Drink"
                    editCategory.contains("Пиво") -> editCategory = "Beer"
                    editCategory.contains("Домашний ликер") -> editCategory = "Homemade Liqueur"
                    editCategory.contains("Молочный коктейль") -> editCategory = "Milk / Float / Shake"
                    editCategory.contains("Какао") -> editCategory = "Cocoa"
                    editCategory.contains("Кофе чай") -> editCategory = "Coffee / Tea"
                    editCategory.contains("Разное") -> editCategory = "Other/Unknown"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        //<----------------end spinner for category ---------->

        //<----------------start spinner for alcoholic ---------->
        ArrayAdapter.createFromResource(
            activity as StartActivity,
            R.array.ru_alcoholic_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            editSpinnerAlcoholic.adapter = adapter
        }
        editSpinnerAlcoholic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                editAlcoholic = editSpinnerAlcoholic.selectedItem.toString()
                when{
                    editAlcoholic.contains("Безалкогольный") -> editAlcoholic = "Non alcoholic"
                    editAlcoholic.contains("Алкогольный") -> editAlcoholic = "Alcoholic"
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //<----------------end spinner for alcoholic ---------->
        fillAllFields()
    }
    private fun fillAllFields(){
        if (viewModel.drink.value?.strIngredient1 != null) edit_ingredient1.text =
            edit_ingredient1.text.append(viewModel.drink.value?.strIngredient1) else edit_ingredient1.text =
            edit_ingredient1.text
        if (viewModel.drink.value?.strMeasure1 != null) edit_measure1.text =
            edit_measure1.text.append(viewModel.drink.value?.strMeasure1) else edit_measure1.text =
            edit_measure1.text
        if (viewModel.drink.value?.strIngredient2 != null) edit_ingredient2.text =
            edit_ingredient2.text.append(viewModel.drink.value?.strIngredient2) else edit_ingredient2.text =
            edit_ingredient2.text
        if (viewModel.drink.value?.strMeasure2 != null) edit_measure2.text =
            edit_measure2.text.append(viewModel.drink.value?.strMeasure2) else edit_measure2.text =
            edit_measure2.text
        if (viewModel.drink.value?.strIngredient3 != null) edit_ingredient3.text =
            edit_ingredient3.text.append(viewModel.drink.value?.strIngredient3) else edit_ingredient3.text =
            edit_ingredient3.text
        if (viewModel.drink.value?.strMeasure3 != null) edit_measure3.text =
            edit_measure3.text.append(viewModel.drink.value?.strMeasure3) else edit_measure3.text =
            edit_measure3.text
        if (viewModel.drink.value?.strIngredient4 != null) edit_ingredient4.text =
            edit_ingredient4.text.append(viewModel.drink.value?.strIngredient4) else edit_ingredient4.text =
            edit_ingredient4.text
        if (viewModel.drink.value?.strMeasure4 != null) edit_measure4.text =
            edit_measure4.text.append(viewModel.drink.value?.strMeasure4) else edit_measure4.text =
            edit_measure4.text
        if (viewModel.drink.value?.strIngredient5 != null) edit_ingredient5.text =
            edit_ingredient5.text.append(viewModel.drink.value?.strIngredient5) else edit_ingredient5.text =
            edit_ingredient5.text
        if (viewModel.drink.value?.strMeasure5 != null) edit_measure5.text =
            edit_measure5.text.append(viewModel.drink.value?.strMeasure5) else edit_measure5.text =
            edit_measure5.text
        if (viewModel.drink.value?.strIngredient6 != null) edit_ingredient6.text =
            edit_ingredient6.text.append(viewModel.drink.value?.strIngredient6) else edit_ingredient6.text =
            edit_ingredient6.text
        if (viewModel.drink.value?.strMeasure6 != null) edit_measure6.text =
            edit_measure6.text.append(viewModel.drink.value?.strMeasure6) else edit_measure6.text =
            edit_measure6.text
        if (viewModel.drink.value?.strIngredient7 != null) edit_ingredient7.text =
            edit_ingredient7.text.append(viewModel.drink.value?.strIngredient7) else edit_ingredient7.text =
            edit_ingredient7.text
        if (viewModel.drink.value?.strMeasure7 != null) edit_measure7.text =
            edit_measure7.text.append(viewModel.drink.value?.strMeasure7) else edit_measure7.text =
            edit_measure7.text
        if (viewModel.drink.value?.strIngredient8 != null) edit_ingredient8.text =
            edit_ingredient8.text.append(viewModel.drink.value?.strIngredient8) else edit_ingredient8.text =
            edit_ingredient8.text
        if (viewModel.drink.value?.strMeasure8 != null) edit_measure8.text =
            edit_measure8.text.append(viewModel.drink.value?.strMeasure8) else edit_measure8.text =
            edit_measure8.text
        if (viewModel.drink.value?.strIngredient9 != null) edit_ingredient9.text =
            edit_ingredient9.text.append(viewModel.drink.value?.strIngredient9) else edit_ingredient9.text =
            edit_ingredient9.text
        if (viewModel.drink.value?.strMeasure9 != null) edit_measure9.text =
            edit_measure9.text.append(viewModel.drink.value?.strMeasure9) else edit_measure9.text =
            edit_measure9.text
        if (viewModel.drink.value?.strIngredient10 != null) edit_ingredient10.text =
            edit_ingredient10.text.append(viewModel.drink.value?.strIngredient10) else edit_ingredient10.text =
            edit_ingredient10.text
        if (viewModel.drink.value?.strMeasure10 != null) edit_measure10.text =
            edit_measure10.text.append(viewModel.drink.value?.strMeasure10) else edit_measure10.text =
            edit_measure10.text
        if (viewModel.drink.value?.strIngredient11 != null) edit_ingredient11.text =
            edit_ingredient11.text.append(viewModel.drink.value?.strIngredient11) else edit_ingredient11.text =
            edit_ingredient11.text
        if (viewModel.drink.value?.strMeasure11 != null) edit_measure11.text =
            edit_measure11.text.append(viewModel.drink.value?.strMeasure11) else edit_measure11.text =
            edit_measure11.text
        if (viewModel.drink.value?.strIngredient12 != null) edit_ingredient12.text =
            edit_ingredient12.text.append(viewModel.drink.value?.strIngredient12) else edit_ingredient12.text =
            edit_ingredient12.text
        if (viewModel.drink.value?.strMeasure12 != null) edit_measure12.text =
            edit_measure12.text.append(viewModel.drink.value?.strMeasure12) else edit_measure12.text =
            edit_measure12.text
        if (viewModel.drink.value?.strIngredient13 != null) edit_ingredient13.text =
            edit_ingredient13.text.append(viewModel.drink.value?.strIngredient13) else edit_ingredient13.text =
            edit_ingredient13.text
        if (viewModel.drink.value?.strMeasure13 != null) edit_measure13.text =
            edit_measure13.text.append(viewModel.drink.value?.strMeasure13) else edit_measure13.text =
            edit_measure13.text
        if (viewModel.drink.value?.strIngredient14 != null) edit_ingredient14.text =
            edit_ingredient14.text.append(viewModel.drink.value?.strIngredient14) else edit_ingredient14.text =
            edit_ingredient14.text
        if (viewModel.drink.value?.strMeasure14 != null) edit_measure14.text =
            edit_measure14.text.append(viewModel.drink.value?.strMeasure14) else edit_measure14.text =
            edit_measure14.text
        if (viewModel.drink.value?.strIngredient15 != null) edit_ingredient15.text =
            edit_ingredient15.text.append(viewModel.drink.value?.strIngredient15) else edit_ingredient15.text =
            edit_ingredient15.text
        if (viewModel.drink.value?.strMeasure15 != null) edit_measure15.text =
            edit_measure15.text.append(viewModel.drink.value?.strMeasure15) else edit_measure15.text =
            edit_measure15.text
    }

    private fun editDrink() {
        val idDr = viewModel.drink.value?.id
        strIdDrink = viewModel.drink.value?.idDrink.toString()
        if (editBespoke.isNotEmpty()){
            strThumb = null.toString()
        }else{
            if (viewModel.drink.value?.strBespokeImage==null){
                strThumb = viewModel.drink.value?.strDrinkThumb.toString()
            }else{
                editBespoke = viewModel.drink.value?.strBespokeImage!!
                strThumb = viewModel.drink.value?.strDrinkThumb.toString()
            }
        }
        val editDrink = Drink(
            editDrinkNameET.text.toString(),
            strThumb,
            editBespoke,
            strIdDrink,
            editCategory,
            editAlcoholic,
            editInstructionsET.text.toString(),
            edit_ingredient1.text.toString(),
            edit_ingredient2.text.toString(),
            edit_ingredient3.text.toString(),
            edit_ingredient4.text.toString(),
            edit_ingredient5.text.toString(),
            edit_ingredient6.text.toString(),
            edit_ingredient7.text.toString(),
            edit_ingredient8.text.toString(),
            edit_ingredient9.text.toString(),
            edit_ingredient10.text.toString(),
            edit_ingredient11.text.toString(),
            edit_ingredient12.text.toString(),
            edit_ingredient13.text.toString(),
            edit_ingredient14.text.toString(),
            edit_ingredient15.text.toString(),
            edit_measure1.text.toString(),
            edit_measure2.text.toString(),
            edit_measure3.text.toString(),
            edit_measure4.text.toString(),
            edit_measure5.text.toString(),
            edit_measure6.text.toString(),
            edit_measure7.text.toString(),
            edit_measure8.text.toString(),
            edit_measure9.text.toString(),
            edit_measure10.text.toString(),
            edit_measure11.text.toString(),
            edit_measure12.text.toString(),
            edit_measure13.text.toString(),
            edit_measure14.text.toString(),
            edit_measure15.text.toString(),
            id = idDr!!
        )
        viewModel.editDrink(editDrink)
        Toast.makeText(requireContext(),"Коктейль ${editDrink.strDrink} изменён ", Toast.LENGTH_LONG).show()
        navController.popBackStack()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.search_item).setVisible(isHidden)
        menu.findItem(R.id.filter_item_menu).setVisible(isHidden)
        menu.findItem(R.id.info_in_review).setVisible(isHidden)
        menu.findItem(R.id.spinner_item).setVisible(isHidden)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.done_item -> {
                editDrink()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}