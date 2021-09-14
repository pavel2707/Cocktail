package by.poplevco.cocktail.UI

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.fragment_add.*
import java.io.ByteArrayOutputStream


class AddFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var viewModel: DrinkViewModel
    lateinit var category: String
    lateinit var alcoholic: String
    var bespoke = byteArrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
        return inflater.inflate(R.layout.fragment_add, container, false)
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
                    bespoke = byteArray

                    val wh = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    addStrDrinkThumb.setImageBitmap(wh)
                }
            }
        addStrDrinkThumb.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            resultLauncher.launch(cameraIntent)
        }

        //<----------------start spinner for category ---------->
        ArrayAdapter.createFromResource(
            activity as StartActivity,
            R.array.ru_category_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategory.adapter = adapter
        }
        spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                category = spinnerCategory.selectedItem.toString()
                    when{
                        category.contains("Напитки для вечеринки") -> category = "Punch / Party Drink"
                        category.contains("Шоты") -> category = "Shot"
                        category.contains("Коктейль") ->category = "Cocktail"
                        category.contains("Мягкие напитки") -> category = "Soft Drink / Soda"
                        category.contains("Обычный напиток") -> category = "Ordinary Drink"
                        category.contains("Пиво") -> category = "Beer"
                        category.contains("Домашний ликер") -> category = "Homemade Liqueur"
                        category.contains("Молочный коктейль") -> category = "Milk / Float / Shake"
                        category.contains("Какао") -> category = "Cocoa"
                        category.contains("Кофе чай") -> category = "Coffee / Tea"
                        category.contains("Разное") -> category = "Other/Unknown"
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
            spinnerAlcoholic.adapter = adapter
        }
        spinnerAlcoholic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                alcoholic = spinnerAlcoholic.selectedItem.toString()
                when{
                    alcoholic.contains("Безалкогольный") -> alcoholic = "Non alcoholic"
                    alcoholic.contains("Алкогольный") -> alcoholic = "Alcoholic"
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //<----------------end spinner for alcoholic ---------->
        //<----------------start block ingredient 1 ---------->
        add_ingredient1_editT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btn_add_ingredients1.isEnabled = s.toString().isNotEmpty()
            }
        })
        btn_add_ingredients1.setOnClickListener {
            ingredient_measure_layout2.visibility = View.VISIBLE
        }
        //<----------------end block ingredient 1 ---------->
        //<----------------start block ingredient 2 ---------->
        add_ingredient2_editT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btn_add_ingredients2.isEnabled = s.toString().isNotEmpty()
            }
        })
        btn_add_ingredients2.setOnClickListener {
            ingredient_measure_layout3.visibility = View.VISIBLE
        }
        //<----------------end block ingredient 2 ---------->
        //<----------------start block ingredient 3 ---------->
        add_ingredient3_editT.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                btn_add_ingredients3.isEnabled = s.toString().isNotEmpty()
            }
        })
        btn_add_ingredients3.setOnClickListener {
            ingredient_measure_layoutAll.visibility = View.VISIBLE
        }
        //<----------------end block ingredient 3 ---------->

    }

    private fun insertToDatabase() {
        val idDrink = ""
        val nameDrink = strDrinkNameET.text.toString()
        val instructions = strInstructionsET.text.toString()
        val ingredient1 = add_ingredient1_editT.text.toString()

        if (inputAdd(nameDrink,instructions,ingredient1)){
            val newDrink = Drink(
                nameDrink,
                null,
                bespoke,
                idDrink,
                category,
                alcoholic,
                instructions,
                ingredient1,
                add_ingredient2_editT.text.toString(),
                add_ingredient3_editT.text.toString(),
                add_ingredient4_editT.text.toString(),
                add_ingredient5_editT.text.toString(),
                add_ingredient6_editT.text.toString(),
                add_ingredient7_editT.text.toString(),
                add_ingredient8_editT.text.toString(),
                add_ingredient9_editT.text.toString(),
                add_ingredient10_editT.text.toString(),
                add_ingredient11_editT.text.toString(),
                add_ingredient12_editT.text.toString(),
                add_ingredient13_editT.text.toString(),
                add_ingredient14_editT.text.toString(),
                add_ingredient15_editT.text.toString(),
                add_measure1_editT.text.toString(),
                add_measure2_editT.text.toString(),
                add_measure3_editT.text.toString(),
                add_measure4_editT.text.toString(),
                add_measure5_editT.text.toString(),
                add_measure6_editT.text.toString(),
                add_measure7_editT.text.toString(),
                add_measure8_editT.text.toString(),
                add_measure9_editT.text.toString(),
                add_measure10_editT.text.toString(),
                add_measure11_editT.text.toString(),
                add_measure12_editT.text.toString(),
                add_measure13_editT.text.toString(),
                add_measure14_editT.text.toString(),
                add_measure15_editT.text.toString()
            )
            viewModel.insertOneDrink(newDrink)
            navController.popBackStack()
            Toast.makeText(requireContext(),"Коктейль ${newDrink.strDrink} добавлен ", Toast.LENGTH_LONG).show()
        }else{
            strDrinkNameET.setHintTextColor(Color.RED)
            strInstructionsET.setHintTextColor(Color.RED)
            add_ingredient1_editT.setHintTextColor(Color.RED)
            Toast.makeText(requireContext(),"Пожалуйста,заполните все поля!", Toast.LENGTH_LONG).show()
        }
    }
    private fun inputAdd(nameDrink: String,instructions: String,ingredient1: String): Boolean{
        return (!TextUtils.isEmpty(nameDrink) && !TextUtils.isEmpty(instructions) && !TextUtils.isEmpty(ingredient1))
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.filter_item_menu).setVisible(isHidden)
        menu.findItem(R.id.search_item).setVisible(isHidden)
        menu.findItem(R.id.info_in_review).setVisible(isHidden)
        menu.findItem(R.id.spinner_item).setVisible(isHidden)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.done_item -> {
                insertToDatabase()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}