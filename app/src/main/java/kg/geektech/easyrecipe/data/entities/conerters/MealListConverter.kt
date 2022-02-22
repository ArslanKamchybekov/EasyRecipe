package kg.geektech.easyrecipe.data.entities.conerters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.geektech.easyrecipe.data.entities.model.MealsItems

class MealListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<MealsItems>): String? {
        return run {
            val gson = Gson()
            val type = object : TypeToken<MealsItems>() {

            }.type
            gson.toJson(category, type)
        }
    }

    @TypeConverter
    fun toCategoryList(categoryString: String): List<MealsItems>? {
        return run {
            val gson = Gson()
            val type = object : TypeToken<MealsItems>() {

            }.type
            gson.fromJson(categoryString, type)
        }
    }
}