package kg.geektech.easyrecipe.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        private var retrofit: Retrofit? = null
        val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }
    }
}