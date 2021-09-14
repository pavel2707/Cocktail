package by.poplevco.cocktail.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import by.poplevco.cocktail.Repository.Repository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DrinkViewModelFactory @Inject constructor(val repository: Repository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DrinkViewModel(repository) as T
    }
}