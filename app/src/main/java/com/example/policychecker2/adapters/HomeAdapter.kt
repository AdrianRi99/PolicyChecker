package com.example.policychecker2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.policychecker2.R
import com.example.policychecker2.db.models.Files

class HomeAdapter(
    private val fileItemClickInterface: FileItemClickInterface,
    private val fileOnLongClickInterface: FileOnLongClickInterface
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileTitle: TextView = itemView.findViewById(R.id.tvFileTitle)
        val timeStamp: TextView = itemView.findViewById(R.id.tvCreatedAt)
//        val deleteIcon = itemView.findViewById<ImageView>(R.id.imageButtonDeleteFile)
    }

    //Differ for better performance
    private val diffCallback = object : DiffUtil.ItemCallback<Files>() {
        override fun areItemsTheSame(oldItem: Files, newItem: Files): Boolean {
            return oldItem.id == newItem.id  //here the bitmap or avg speed could be different, but it is generally the same
        }

        override fun areContentsTheSame(oldItem: Files, newItem: Files): Boolean {
            return oldItem.hashCode() == newItem.hashCode()  //here everything has to be same
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun updateListOfFiles(list: List<Files>) = differ.submitList(list)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.row_item_file,
            parent, false
        )
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val file = differ.currentList[position]

        //maybe it is possible  to work with viewbinding
        holder.fileTitle.text = file.fileTitle
        holder.timeStamp.text = file.timeStamp
        holder.itemView.setOnClickListener {
            fileItemClickInterface.onNoteClick(file)
        }
//        holder.deleteIcon.setOnClickListener {
//            fileClickDeleteInterface.onDeleteIconClick(file)
//        }

        holder.itemView.setOnLongClickListener {
            fileOnLongClickInterface.onLongClick(holder.itemView, file)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}

interface FileItemClickInterface {
    //creating a method for click action on recycler view item for updating it.
    fun onNoteClick(file: Files)
}

//interface FileClickDeleteInterface {
//    //creating a method for click action on delete image view.
//    fun onDeleteIconClick(file: Files)
//}


interface FileOnLongClickInterface {
    //creating a method for click action on delete image view.
    fun onLongClick(view: View, file: Files)
}
