package by.poplevco.cocktail.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.poplevco.cocktail.R
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.UI.FavoritesFragment
import com.bumptech.glide.Glide

class DrinkFavAdapter(val favList: MutableList<Drink>, val fragment: FavoritesFragment) :
    RecyclerView.Adapter<DrinkFavAdapter.ViewFavHolder>() {

    class ViewFavHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var im: ImageView = itemView.findViewById(R.id.imageItemFav)
        var namDrink: TextView = itemView.findViewById(R.id.nameDrinkFav)
        fun bind(drink: Drink) {
            namDrink.setText(drink.strDrink)
            Glide.with(im)
                .load(if (drink.strDrinkThumb == null||drink.strDrinkThumb.contains("null"))drink.strBespokeImage else drink.strDrinkThumb )
                .circleCrop()
                .into(im)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewFavHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_fav_adapt, parent, false)
        val holder = ViewFavHolder(itemView)
        holder.itemView.setOnClickListener {
            fragment.showFavDetails(holder.adapterPosition)
        }
        return holder
    }
    override fun onBindViewHolder(holder: ViewFavHolder, position: Int) {
        holder.bind(favList[position])
    }
    override fun getItemCount(): Int {
        return favList.size
    }
}