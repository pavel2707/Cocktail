package by.poplevco.cocktail.UI.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import by.poplevco.cocktail.UI.MainActivity
import by.poplevco.cocktail.R
import by.poplevco.cocktail.UI.StartActivity
import by.poplevco.cocktail.ViewModel.DrinkViewModel

class DialogDelAll: DialogFragment() {
    private lateinit var viewModel: DrinkViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel = ViewModelProvider(activity as StartActivity).get(DrinkViewModel::class.java)
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.delete_question_all)
            .setPositiveButton(R.string.delete_yes) { _,_ ->
                viewModel.clearAll()
                Toast.makeText(
                    activity as StartActivity,
                    "Удаленно",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(R.string.delete_no) { dialog, _ ->
                dialog.dismiss()
            }
        return builder.create()
    }
}