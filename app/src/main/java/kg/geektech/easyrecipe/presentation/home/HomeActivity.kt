package kg.geektech.easyrecipe.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kg.geektech.easyrecipe.R
import kg.geektech.easyrecipe.data.entities.model.CategoryItems
import kg.geektech.easyrecipe.data.entities.model.MealsItems
import kg.geektech.easyrecipe.data.local.database.RecipeDatabase
import kg.geektech.easyrecipe.presentation.adapters.MainCategoryAdapter
import kg.geektech.easyrecipe.presentation.adapters.SubCategoryAdapter
import kg.geektech.easyrecipe.presentation.base.BaseActivity
import kg.geektech.easyrecipe.presentation.detail.DetailActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    private var arrMainCategory = ArrayList<CategoryItems>()
    private var arrSubCategory = ArrayList<MealsItems>()
    private var mainCategoryAdapter = MainCategoryAdapter()
    private var subCategoryAdapter = SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        getDataFromDb()
        mainCategoryAdapter.setClickListener(onCLicked)
        subCategoryAdapter.setClickListener(onCLickedSubItem)
    }

    private val onCLicked = object : MainCategoryAdapter.OnItemClickListener {
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }

    private val onCLickedSubItem = object : SubCategoryAdapter.OnItemClickListener {
        override fun onClicked(id: String) {
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun getDataFromDb() {
        launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()

                getMealDataFromDb(arrMainCategory[0].strcategory)
                mainCategoryAdapter.setData(arrMainCategory)
                rv_main_category.layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_main_category.adapter = mainCategoryAdapter
            }


        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMealDataFromDb(categoryName: String) {
        tvCategory.text = "$categoryName Category"
        launch {
            this.let {
                val cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao()
                    .getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                rv_sub_category.layoutManager =
                    LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rv_sub_category.adapter = subCategoryAdapter
            }
        }
    }
}