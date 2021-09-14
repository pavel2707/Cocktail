package by.poplevco.cocktail.UI.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import by.poplevco.cocktail.R
import by.poplevco.cocktail.RemoteModel.Drink
import by.poplevco.cocktail.UI.MainFragment
import com.bumptech.glide.Glide


class DrinksAdapter(val list: MutableList<Drink>, val fragment: MainFragment) :
    RecyclerView.Adapter<DrinksAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var im: ImageView = itemView.findViewById(R.id.imageItem)
        var namDrink: TextView = itemView.findViewById(R.id.nameDrinkTextView)
        var imIndic: ImageView = itemView.findViewById(R.id.imageFavIndication)
        fun bind(drink: Drink) {
            namDrink.setText(drink.strDrink)
            Glide.with(im)
                .load(if (drink.strDrinkThumb == null||drink.strDrinkThumb.contains("null"))drink.strBespokeImage else drink.strDrinkThumb )
                .circleCrop()
                .into(im)
            if (drink.favorite){
                imIndic.visibility = View.VISIBLE
            }else{
                imIndic.visibility = View.GONE
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_layout_adapt, parent, false)
        val holder = ViewHolder(itemView)
        holder.itemView.setOnClickListener {
            fragment.showDetails(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
    override fun getItemCount(): Int {
        return list.size
    }
}