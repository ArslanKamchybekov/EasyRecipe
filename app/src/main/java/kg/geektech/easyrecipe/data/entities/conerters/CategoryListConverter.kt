package kg.geektech.easyrecipe.data.entities.conerters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kg.geektech.easyrecipe.data.entities.model.CategoryItems

class CategoryListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<CategoryItems>): String? {
        return run {
            val gson = Gson()
            val type = object : TypeToken<CategoryItems>() {

            }.type
            gson.toJson(category, type)
        }
    }

    @TypeConverter
    fun toCategoryList(categoryString: String): List<CategoryItems>? {
        return run {
            val gson = Gson()
            val type = object : TypeToken<CategoryItems>() {

            }.type
            gson.fromJson(categoryString, type)
        }
    }
}