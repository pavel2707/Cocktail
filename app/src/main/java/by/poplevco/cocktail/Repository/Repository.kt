package by.poplevco.cocktail.Repository

import by.poplevco.cocktail.LocalModel.LocalModel
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.RemoteModel.RemoteModel
import javax.inject.Inject

class Repository @Inject constructor(val remoteModel: RemoteModel, val localModel: LocalModel) {

    suspend fun getData(): MutableList<Drink>{
        val drinks = localModel.getAllDrinks()

         if (drinks.isEmpty()){
            val allList = remoteModel.getRemNotAlcCocktails()
            for (elem in allList){
                val drink = remoteModel.getFullDetails(elem.idDrink)
                localModel.insertOneDrink(drink[0])
            }
            val allList2 = remoteModel.getRemAlcCocktails()
            for (elem in allList2){
                val drink = remoteModel.getFullDetails(elem.idDrink)
                localModel.insertOneDrink(drink[0])
            }
            val allList3 = remoteModel.getRemoteCocktails()
            for (elem in allList3){
                val drink = remoteModel.getFullDetails(elem.idDrink)
                localModel.insertOneDrink(drink[0])
            }
            return localModel.getAllDrinks()
        }else{
            return drinks
        }
    }

    suspend fun searchByIngredient(searchIngred: String): MutableList<Drink>{
        return localModel.searchByIngredient(searchIngred)
    }
    suspend fun selectMaxId(): Drink{
        return localModel.selectMaxId()
    }
    suspend fun insertOneDrink(drink: Drink){
        localModel.insertOneDrink(drink)
    }
    suspend fun deleteOneDrink(drink: Drink){
        localModel.deleteOneDrink(drink)
    }
    suspend fun getAllDetails(id: Int): Drink{
        return localModel.getAllDetails(id)
    }
    suspend fun clearAll(){
        localModel.clearAll()
    }
    suspend fun updateDrink(drink: Drink){
        localModel.updateDrink(drink)
    }
    suspend fun getNotAlcDrinks(): MutableList<Drink>{
        return localModel.getNotAlcDrinks()
    }
    suspend fun getAlcDrinks(): MutableList<Drink>{
        return localModel.getAlcDrinks()
    }
    suspend fun getLikeDrinks(): MutableList<Drink>{
        return localModel.getLikeDrinks()
    }
     suspend fun searchDatabase(searchQuery: String): MutableList<Drink>{
        return localModel.searchDatabase(searchQuery)
    }
    suspend fun getDrinksCategory(category: String): MutableList<Drink>{
        return localModel.getDrinksCategory(category)
    }

}