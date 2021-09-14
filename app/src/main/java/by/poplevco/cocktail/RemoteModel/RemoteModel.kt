package by.poplevco.cocktail.RemoteModel

import javax.inject.Inject


class RemoteModel @Inject constructor() {
    val apiService = ApiService.create()

    suspend fun getRemoteCocktails(): MutableList<Drink>{
        return try {
            val drinkList = apiService.getCocktails()
            val myList = drinkList.drinks
            myList
        }catch (e:Exception){ mutableListOf()}
    }
    suspend fun getRemAlcCocktails(): MutableList<Drink>{
        return try {
            val listAlcDrink = apiService.getAlcCocktails()
            val myListAlc = listAlcDrink.drinks
            myListAlc
        }catch (e:Exception){ mutableListOf()}
    }
    suspend fun getRemNotAlcCocktails(): MutableList<Drink>{
        return try {
            val listNotAlcDrink = apiService.getNotAlcCocktails()
            val myListNotAlc = listNotAlcDrink.drinks
            myListNotAlc
        }catch (e:Exception){mutableListOf()}
    }
    suspend fun getFullDetails(i: String): MutableList<Drink>{
        return try {
            val detail = apiService.getFullDetails(i)
            detail.drinks
        }catch (e:Exception){mutableListOf()}
    }

}