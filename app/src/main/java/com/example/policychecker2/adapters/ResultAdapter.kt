package com.example.policychecker2.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.policychecker2.R
import com.example.policychecker2.db.models.terms.de.MietvertragDe

class ResultAdapter(
    //var people: List<People>
    private val buttonClickInterface: ButtonClickInterface
) : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    private val dangerousTermsList = mutableListOf<MietvertragDe>()

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dangerousTerm: TextView = itemView.findViewById(R.id.tvDangerousTerm)
        val termEasyTranslation: TextView = itemView.findViewById(R.id.tvTermEasyTranslation)
        val background: CardView = itemView.findViewById(R.id.row_item_results)
        val btnSearchInText: Button = itemView.findViewById(R.id.btnSearchInText)
        val expandedLayout: ConstraintLayout = itemView.findViewById(R.id.expandedLayout)
        val icDropDown: ImageView = itemView.findViewById(R.id.icDropDown)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ResultViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_results,
            parent, false
        )
        return ResultViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        val currentItem = dangerousTermsList[position]


        holder.dangerousTerm.text = currentItem.term
        holder.termEasyTranslation.text = currentItem.termDescription

        if(currentItem.type == 1){
            holder.background.setCardBackgroundColor(Color.parseColor("#68BCB9"))
        } else if (currentItem.type == 2) {
            holder.background.setCardBackgroundColor(Color.parseColor("#F86A80"))
        } else if (currentItem.type == 3) {
            holder.background.setCardBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        holder.btnSearchInText.setOnClickListener {
            buttonClickInterface.onButtonClick(currentItem, position)
        }

        val isVisible = currentItem.visibility   //as an Int -> 0 and 1
        if(isVisible == 1) {
            holder.icDropDown.visibility = View.GONE
            holder.expandedLayout.visibility = View.VISIBLE
        } else {
            holder.icDropDown.visibility = View.VISIBLE
            holder.expandedLayout.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            currentItem.visibility = if(isVisible == 0) 1 else 0
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return dangerousTermsList.size
    }

    fun updateList(newList: List<MietvertragDe>) {
        dangerousTermsList.clear()
        dangerousTermsList.addAll(newList)
        //on below line we are calling notify data change method to notify our adapter.
        notifyDataSetChanged()
    }

}


interface ButtonClickInterface {
    //creating a method for click action on recycler view item for updating it.
    fun onButtonClick(dangerousTerms: MietvertragDe, position: Int)
}

