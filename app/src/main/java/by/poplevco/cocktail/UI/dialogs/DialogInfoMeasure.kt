package by.poplevco.cocktail.UI.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DialogInfoMeasure: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Measure")
        builder.setMessage("1 cl = 10ml\n" +
                "1 tsp = 1 чайная ложка\n" +
                "1 tbsp =1 столовая ложка\n" +
                "1 oz = 30 ml\n" +
                "1 shot = 50 ml\n" +
                "1 qt = 945 ml")
            .setPositiveButton("Ok"){dialog,_ ->
                dialog.dismiss()
            }
        return builder.create()
    }

}