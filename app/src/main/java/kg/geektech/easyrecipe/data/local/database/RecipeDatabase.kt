package kg.geektech.easyrecipe.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kg.geektech.easyrecipe.data.entities.conerters.CategoryListConverter
import kg.geektech.easyrecipe.data.entities.conerters.MealListConverter
import kg.geektech.easyrecipe.data.entities.model.*
import kg.geektech.easyrecipe.data.local.dao.RecipeDao

@Database(entities = [Recipes::class,CategoryItems::class,Category::class, Meal::class, MealsItems::class],version = 1,exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    companion object{

        private var recipesDatabase:RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if (recipesDatabase == null){
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao():RecipeDao
}