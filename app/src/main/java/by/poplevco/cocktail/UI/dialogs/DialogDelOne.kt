package by.poplevco.cocktail.UI.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.poplevco.cocktail.UI.MainActivity
import by.poplevco.cocktail.R
import by.poplevco.cocktail.UI.StartActivity
import by.poplevco.cocktail.ViewModel.DrinkViewModel


class DialogDelOne : DialogFragment() {
    private lateinit var viewModel: DrinkViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
            val builder = AlertDialog.Builder(context)
            builder.setMessage(R.string.delete_question)
                .setPositiveButton(R.string.delete_yes) { _,_ ->
                    viewModel.deleteOneDrink(viewModel.drink.value!!)
                        Toast.makeText(
                            activity as StartActivity,
                            "Удаленно",
                            Toast.LENGTH_SHORT
                        ).show()
                    findNavController().popBackStack()
                    }
                .setNegativeButton(R.string.delete_no) { dialog, _ ->
                    dialog.dismiss()
                }
            return builder.create()
    }
}