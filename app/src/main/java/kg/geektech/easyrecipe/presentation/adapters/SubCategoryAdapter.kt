package kg.geektech.easyrecipe.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.geektech.easyrecipe.R
import kg.geektech.easyrecipe.data.entities.model.MealsItems
import kotlinx.android.synthetic.main.item_rv_sub_category.view.*

class SubCategoryAdapter: RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {

    private var listener: OnItemClickListener? = null
    private var ctx :Context? = null
    private var arrSubCategory = ArrayList<MealsItems>()
    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view)

    fun setData(arrData : List<MealsItems>){
        arrSubCategory = arrData as ArrayList<MealsItems>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_sub_category,parent,false))
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    fun setClickListener(listener1: OnItemClickListener){
        listener = listener1
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        holder.itemView.tv_dish_name.text = arrSubCategory[position].strMeal

        Glide.with(ctx!!).load(arrSubCategory[position].strMealThumb).into(holder.itemView.img_dish)

        holder.itemView.rootView.setOnClickListener {
            listener!!.onClicked(arrSubCategory[position].idMeal)
        }
    }

    interface OnItemClickListener{
        fun onClicked(id:String)
    }
}