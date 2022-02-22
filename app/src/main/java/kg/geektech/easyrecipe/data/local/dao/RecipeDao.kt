package kg.geektech.easyrecipe.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kg.geektech.easyrecipe.data.entities.model.CategoryItems
import kg.geektech.easyrecipe.data.entities.model.MealsItems
import kg.geektech.easyrecipe.data.entities.model.Recipes

@Dao
interface RecipeDao {

    @Query("SELECT * FROM categoryItems ORDER BY id DESC")
    suspend fun getAllCategory(): List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: CategoryItems?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: MealsItems?)

    @Query("DELETE FROM categoryItems")
    suspend fun clearDb()

    @Query("SELECT * FROM MealItems WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName: String): List<MealsItems>

}