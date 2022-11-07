package com.ngedev.thesisx.ui.detail.loan_detail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ngedev.thesisx.R
import com.ngedev.thesisx.databinding.ItemBookBinding
import com.ngedev.thesisx.databinding.ItemLoanBinding
import com.ngedev.thesisx.domain.model.Loan
import com.ngedev.thesisx.utils.Category
import com.ngedev.thesisx.utils.DateConverter
import com.ngedev.thesisx.utils.DiffCallBack
import com.ngedev.thesisx.utils.ExtraName

class LoanAdapter(private val context: Context) :
    RecyclerView.Adapter<LoanAdapter.LoanThesisViewHolder>() {

    private val forms = arrayListOf<Loan>()

    var onItemClick: ((Loan) -> Unit)? = null

    fun setItems(items: List<Loan>) {

        this.forms.clear()
        this.forms.addAll(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanThesisViewHolder {
        val itemBinding =
            ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoanThesisViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LoanThesisViewHolder, position: Int) {
        holder.bind(forms[position])
    }

    override fun getItemCount(): Int {
        return forms.size
    }

    inner class LoanThesisViewHolder(private val binding: ItemLoanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(form: Loan) {

            binding.tvBookTitle.text = form.book_title
            binding.tvLoanName.text = form.author
            binding.tvDate.text = DateConverter.convertMillisToString(form.date)

            when (form.category) {
                Category.CONTROL -> {
                    Glide.with(itemView.context).load(R.drawable.icon_kontrol)
                        .into(binding.ivPoster)
                }

                Category.POWER -> {
                    Glide.with(itemView.context).load(R.drawable.icon_power).into(binding.ivPoster)
                }

                Category.ELECTRONICA -> {
                    Glide.with(itemView.context).load(R.drawable.icon_elektronika)
                        .into(binding.ivPoster)
                }

                Category.TELKOM_MEDIA -> {
                    Glide.with(itemView.context).load(R.drawable.icon_telekomunikasi)
                        .into(binding.ivPoster)
                }

                else -> {}
            }

            itemView.setOnClickListener {
                context.startActivity(
                    Intent(itemView.context, LoanDetailActivity::class.java).putExtra(
                        ExtraName.FORM, form
                    )
                )
            }


            binding.btnMenu.setOnClickListener {
                showMenu(it, R.menu.loan_menu, itemView.context, form)

            }
        }

        private fun showMenu(
            view: View,
            optionMenu: Int,
            context: Context,
            loan: Loan,
        ) {
            val popup = PopupMenu(context, view)
            popup.menuInflater.inflate(optionMenu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem: MenuItem ->

                if (menuItem.itemId == R.id.delete_event) {
                    val materialBuilder = MaterialAlertDialogBuilder(itemView.context).create()
                    val inflater: View =
                        LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
                    val btnDelete: Button = inflater.findViewById(R.id.btn_accept)
                    val btnCancel: Button = inflater.findViewById(R.id.btn_cancel)
                    val deleteDescription: TextView = inflater.findViewById(R.id.tv_desc)
                    val dialogTitle: TextView = inflater.findViewById(R.id.tv_dialog_title)

                    dialogTitle.text = "Riwayat Pinjaman"
                    deleteDescription.text =
                        "Apakah ingin menghapus riwayat ini?"

                    btnDelete.setOnClickListener {
                        onItemClick?.invoke(loan)
                        materialBuilder.dismiss()
                    }
                    btnCancel.setOnClickListener {
                        materialBuilder.dismiss()
                    }

                    materialBuilder.setView(inflater)
                    materialBuilder.show()
                }
                true
            }
            popup.setOnDismissListener {}
            popup.show()
        }
    }




}