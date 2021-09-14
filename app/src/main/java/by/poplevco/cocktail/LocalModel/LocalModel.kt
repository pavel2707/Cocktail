package by.poplevco.cocktail.LocalModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import androidx.room.Room
import by.poplevco.cocktail.RemoteModel.Drink
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalModel @Inject constructor(@ApplicationContext context: Context) {

    private val database: DrinkDatabase = Room.databaseBuilder(
        context,
        DrinkDatabase::class.java, "drinks_Database"
    ).build()

    suspend fun searchByIngredient(searchIngred: String): MutableList<Drink>{
        return database.drinkDao().searchByIngredient(searchIngred)
    }
    suspend fun selectMaxId(): Drink{
        return database.drinkDao().selectMaxId()
    }

    suspend fun getAllDrinks(): MutableList<Drink> {
        return database.drinkDao().getAllDrinks()
    }
    suspend fun insertOneDrink(drink: Drink) {
        database.drinkDao().insertOneDrink(drink)
    }
    suspend fun deleteOneDrink(drink: Drink) {
        database.drinkDao().deleteOneDrink(drink)
    }
    suspend fun getAllDetails(id: Int): Drink {
        return database.drinkDao().getAllDetails(id)
    }
    suspend fun clearAll(){
        database.drinkDao().clearAll()
    }

    suspend fun updateDrink(drink: Drink){
        database.drinkDao().updateDrink(drink)
    }
    suspend fun getNotAlcDrinks(): MutableList<Drink>{
        return database.drinkDao().getNotAlcDrinks()
    }
    suspend fun getAlcDrinks(): MutableList<Drink>{
        return database.drinkDao().getAlcDrinks()
    }
    suspend fun getLikeDrinks(): MutableList<Drink>{
        return database.drinkDao().getLikeDrinks()
    }
     suspend fun searchDatabase(searchQuery: String): MutableList<Drink> {
        return database.drinkDao().searchDatabase(searchQuery)
    }
    suspend fun getDrinksCategory(category: String): MutableList<Drink>{
        return database.drinkDao().getDrinksCategory(category)
    }

}