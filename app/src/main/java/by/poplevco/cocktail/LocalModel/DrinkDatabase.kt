package by.poplevco.cocktail.LocalModel

import androidx.room.Database
import androidx.room.RoomDatabase
import by.poplevco.cocktail.RemoteModel.Drink

@Database (entities = [Drink::class],version = 1)
abstract class DrinkDatabase: RoomDatabase() {
    abstract fun drinkDao(): DrinkDao
}