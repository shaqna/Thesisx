package com.ngedev.thesisx.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ItemBookBinding
import com.ngedev.thesisx.domain.model.Thesis
import com.ngedev.thesisx.ui.detail.thesis_detail.DetailActivity
import com.ngedev.thesisx.utils.Category
import com.ngedev.thesisx.utils.ExtraName

class ThesisAdapter(private val context: Context) :
    RecyclerView.Adapter<ThesisAdapter.ThesisViewHolder>() {

    private val theses = arrayListOf<Thesis>()

    fun setItems(items: List<Thesis>) {
        this.theses.clear()
        this.theses.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThesisViewHolder {
        val itemBinding =
            ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ThesisViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ThesisViewHolder, position: Int) {
        holder.bind(theses[position])
    }

    override fun getItemCount(): Int {
        return theses.size
    }

    inner class ThesisViewHolder(private val binding: ItemBookBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(thesis: Thesis) {

            binding.tvBookTitle.text = thesis.title
            binding.tvNameYear.text = "${thesis.year}, ${thesis.author}"
            when (thesis.category) {
                Category.CONTROL -> {
                    Glide.with(itemView.context).load(R.drawable.icon_kontrol)
                        .into(binding.imageBook)
                }

                Category.POWER -> {
                    Glide.with(itemView.context).load(R.drawable.icon_power).into(binding.imageBook)
                }

                Category.ELECTRONICA -> {
                    Glide.with(itemView.context).load(R.drawable.icon_elektronika)
                        .into(binding.imageBook)
                }

                Category.TELKOM_MEDIA -> {
                    Glide.with(itemView.context).load(R.drawable.icon_telekomunikasi)
                        .into(binding.imageBook)
                }

                else -> {}
            }

            itemView.setOnClickListener {
                context.startActivity(
                    Intent(itemView.context, DetailActivity::class.java).putExtra(
                        ExtraName.ID, thesis.uid
                    )
                )
            }
        }
    }
}
