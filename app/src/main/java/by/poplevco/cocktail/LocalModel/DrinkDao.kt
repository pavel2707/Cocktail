package by.poplevco.cocktail.LocalModel

import androidx.room.*
import by.poplevco.cocktail.RemoteModel.Drink
import java.util.concurrent.Flow

@Dao
interface DrinkDao {

    @Query("SELECT * FROM drinks_table WHERE strIngredient1 LIKE :searchIngred OR strIngredient2 LIKE :searchIngred OR strIngredient3 LIKE:searchIngred OR strIngredient4 LIKE:searchIngred OR strIngredient5 LIKE:searchIngred OR strIngredient6 LIKE:searchIngred OR strIngredient7 LIKE:searchIngred OR strIngredient8 LIKE:searchIngred OR strIngredient9 LIKE:searchIngred OR strIngredient10 LIKE:searchIngred")
    suspend fun searchByIngredient(searchIngred: String): MutableList<Drink>

    @Query("SELECT * FROM drinks_table WHERE id = (SELECT MAX(id) FROM drinks_table)")
    suspend fun selectMaxId(): Drink

    @Query("SELECT * FROM drinks_table WHERE strDrink LIKE :searchQuery ")
    suspend fun searchDatabase(searchQuery: String): MutableList<Drink>

    @Query("SELECT * FROM drinks_table")
    suspend fun getAllDrinks(): MutableList<Drink>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOneDrink(drink: Drink)

    @Delete
    suspend fun deleteOneDrink(drink: Drink)

    @Query("SELECT * FROM drinks_table WHERE id = :id")
    suspend fun getAllDetails(id: Int): Drink

    @Query("DELETE FROM drinks_table")
    suspend fun clearAll()

    @Update
    suspend fun updateDrink(drink: Drink)

    @Query("SELECT * FROM drinks_table WHERE strAlcoholic = 'Non alcoholic' ")
    suspend fun getNotAlcDrinks(): MutableList<Drink>
    @Query("SELECT * FROM drinks_table WHERE strAlcoholic = 'Alcoholic'")
    suspend fun getAlcDrinks(): MutableList<Drink>
    @Query("SELECT * FROM drinks_table WHERE favorite = '1'")
    suspend fun getLikeDrinks(): MutableList<Drink>

    @Query("SELECT * FROM drinks_table WHERE strCategory = :category")
    suspend fun getDrinksCategory(category: String): MutableList<Drink>

}