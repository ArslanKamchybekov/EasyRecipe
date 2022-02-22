package kg.geektech.easyrecipe.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import kg.geektech.easyrecipe.R
import kg.geektech.easyrecipe.data.entities.model.Category
import kg.geektech.easyrecipe.data.entities.model.Meal
import kg.geektech.easyrecipe.data.entities.model.MealsItems
import kg.geektech.easyrecipe.data.local.database.RecipeDatabase
import kg.geektech.easyrecipe.data.retrofit.ApiService
import kg.geektech.easyrecipe.data.retrofit.RetrofitClient
import kg.geektech.easyrecipe.presentation.base.BaseActivity
import kg.geektech.easyrecipe.presentation.home.HomeActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Response


@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks,
    EasyPermissions.PermissionCallbacks {

    private var READ_STORAGE_PERM = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        readStorageTask()
        initListeners()
    }

    private fun initListeners() {
        btnGetStarted.setOnClickListener {
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getCategories() {
        val service = RetrofitClient.retrofitInstance!!.create(ApiService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : retrofit2.Callback<Category> {
            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(
                    this@SplashActivity,
                    "Error occurred \nRestart the app",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onResponse(
                call: Call<Category>,
                response: Response<Category>
            ) {
                for (arr in response.body()!!.categoryItems!!) {
                    getMeal(arr.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }
        })
    }

    fun getMeal(categoryName: String) {
        val service = RetrofitClient.retrofitInstance!!.create(ApiService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : retrofit2.Callback<Meal> {
            override fun onFailure(call: Call<Meal>, t: Throwable) {
                Toast.makeText(
                    this@SplashActivity,
                    "Error occurred \nRestart the app",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

            override fun onResponse(
                call: Call<Meal>,
                response: Response<Meal>
            ) {
                insertMealDataIntoRoomDb(categoryName, response.body())
            }
        })
    }

    fun insertDataIntoRoomDb(category: Category?) {
        launch {
            this.let {

                for (arr in category!!.categoryItems!!) {
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertCategory(arr)
                }
            }
        }
    }

    fun insertMealDataIntoRoomDb(categoryName: String, meal: Meal?) {
        launch {
            this.let {
                for (arr in meal!!.mealsItem!!) {
                    val mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData", arr.toString())
                }

                btnGetStarted.visibility = View.VISIBLE
            }
        }
    }

    private fun clearDataBase() {
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
            }
        }
    }

    private fun hasReadStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {
            clearDataBase()
            getCategories()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage,",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
}