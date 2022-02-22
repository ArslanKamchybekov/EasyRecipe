package kg.geektech.easyrecipe.data.retrofit

import kg.geektech.easyrecipe.data.entities.model.Category
import kg.geektech.easyrecipe.data.entities.model.Meal
import kg.geektech.easyrecipe.data.entities.model.MealResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String): Call<MealResponse>

}