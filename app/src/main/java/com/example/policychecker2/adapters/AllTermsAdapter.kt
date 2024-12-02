package com.example.policychecker2.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.policychecker2.R
import com.example.policychecker2.db.models.terms.TermsBaseClass

class AllTermsAdapter(

) : RecyclerView.Adapter<AllTermsAdapter.AllTermsViewHolder>() {


    inner class AllTermsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dangerousTerm: TextView = itemView.findViewById(R.id.tvDangerousTerm)
        val termEasyTranslation: TextView = itemView.findViewById(R.id.tvTermEasyTranslation)
        val background: CardView = itemView.findViewById(R.id.row_item_results)
        val expandedLayout: ConstraintLayout = itemView.findViewById(R.id.expandedLayout)
        val icDropDown: ImageView = itemView.findViewById(R.id.icDropDown)
    }

    //Differ for better performance
    private val diffCallback = object : DiffUtil.ItemCallback<TermsBaseClass>() {
        override fun areItemsTheSame(oldItem: TermsBaseClass, newItem: TermsBaseClass): Boolean {
            return oldItem.id == newItem.id  //here the bitmap or avg speed could be different, but it is generally the same
        }

        override fun areContentsTheSame(oldItem: TermsBaseClass, newItem: TermsBaseClass): Boolean {
            return oldItem.hashCode() == newItem.hashCode()  //here everything has to be same
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun updateListOfTerms(list: List<TermsBaseClass>) = differ.submitList(list)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AllTermsViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_all_terms,
            parent, false
        )
        return AllTermsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AllTermsViewHolder, position: Int) {
        val currentTerm = differ.currentList[position]

        holder.dangerousTerm.setText(currentTerm.term)
        holder.termEasyTranslation.setText(currentTerm.termDescription)

        if(currentTerm.type == 1){
            holder.background.setCardBackgroundColor(Color.parseColor("#68BCB9"))
        } else if (currentTerm.type == 2) {
            holder.background.setCardBackgroundColor(Color.parseColor("#F86A80"))
        } else if (currentTerm.type == 3) {
            holder.background.setCardBackgroundColor(Color.parseColor("#FFBB86FC"))
        }

        val isVisible = currentTerm.visibility   //as an Int -> 0 and 1
        if(isVisible == 1) {
            holder.icDropDown.visibility = View.GONE
            holder.expandedLayout.visibility = View.VISIBLE
        } else {
            holder.icDropDown.visibility = View.VISIBLE
            holder.expandedLayout.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            currentTerm.visibility = if(isVisible == 0) 1 else 0
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}
