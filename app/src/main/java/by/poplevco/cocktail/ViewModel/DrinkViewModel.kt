package by.poplevco.cocktail.ViewModel

import androidx.lifecycle.*
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.Repository.Repository
import kotlinx.coroutines.*


class DrinkViewModel(val repository: Repository) : ViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO+job)

    val drinkLiveData: MutableLiveData<MutableList<Drink>> = MutableLiveData(mutableListOf())
    val favoriteLiveData: MutableLiveData<MutableList<Drink>> = MutableLiveData(mutableListOf())
    var drink: MutableLiveData<Drink> = MutableLiveData(null)

    fun getData() = scope.launch {
       val list = getDrinksAsync().await()
        withContext(Dispatchers.Main){
            drinkLiveData.postValue(list)
        }
    }
    private suspend fun getDrinksAsync(): Deferred<MutableList<Drink>> = coroutineScope {
        async {
            repository.getData()
        }
    }
    fun insertOneDrink(drink: Drink) {
        scope.launch {
            repository.insertOneDrink(drink)
            val last = repository.selectMaxId()
            drinkLiveData.value?.add(last)
        }
    }

    fun deleteOneDrink(drink: Drink) {
        scope.launch {
            drinkLiveData.value?.removeIf { it.id == drink.id }
            repository.deleteOneDrink(drink)
        }
    }
    fun getNotAlcDrinks() {
        scope.launch {
            val notAlcList = repository.getNotAlcDrinks()
            withContext(Dispatchers.Main){
                drinkLiveData.postValue(notAlcList)
            }
        }
    }
    fun getAlcDrinks() {
        scope.launch {
            val alcList = repository.getAlcDrinks()
            withContext(Dispatchers.Main){
                drinkLiveData.postValue(alcList)
            }
        }
    }
    fun getAllDetails(id: Int) {
        scope.launch {
            drink.postValue(null)
            val revDrink = repository.getAllDetails(id)
            drink.postValue(revDrink)
        }
    }
    fun clearAll() {
        scope.launch {
            repository.clearAll()
        }
    }

    fun updateDrink(drink: Drink) {
        scope.launch {
            findFavorite(drink)
            if (drink.favorite) {
                favoriteLiveData.value?.add(drink)
            } else {
                favoriteLiveData.value?.remove(drink)
            }
            repository.updateDrink(drink)
            drinkLiveData.value?.sortByDescending { it.favorite }
        }
    }

    fun getLikeDrinks() {
        scope.launch {
            val favList = repository.getLikeDrinks()
            favoriteLiveData.postValue(favList)
        }
    }
    fun searchDatabase(searchQuery: String) {
        scope.launch {
            val l = repository.searchDatabase(searchQuery)
            drinkLiveData.value?.clear()
            drinkLiveData.postValue(l)
        }
    }
    fun getDrinksCategory(category: String) {
        scope.launch {
            val categoryList = repository.getDrinksCategory(category)
            withContext(Dispatchers.Main){
                drinkLiveData.postValue(categoryList)
            }
        }
    }
    fun randomDrink() {
        scope.launch {
            val allList = repository.getData()
            val randDrink = allList.random()
            allList.clear()
            allList.add(randDrink)
            drinkLiveData.postValue(allList)
        }
    }

    fun editDrink(edDrink: Drink) {
        scope.launch {
            updateLiveData(edDrink)
            repository.updateDrink(edDrink)
            drink.postValue(null)
            val editDrink = repository.getAllDetails(edDrink.id)
            drink.postValue(editDrink)
        }
    }
    fun searchByIngredient(searchIngred: String){
        scope.launch {
           val listByIngred = repository.searchByIngredient(searchIngred)
            drinkLiveData.postValue(listByIngred)
        }
    }
    private fun updateLiveData(drink: Drink){
        drinkLiveData.value?.removeIf { it.id == drink.id }
        drinkLiveData.value?.add(drink)
    }
    private fun findFavorite(drink: Drink){
        val _drink = drinkLiveData.value?.first { it.id == drink.id }
        _drink?.favorite = !_drink?.favorite!!
        updateLiveData(_drink)
    }

}

