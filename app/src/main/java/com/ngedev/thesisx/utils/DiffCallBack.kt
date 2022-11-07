package com.ngedev.thesisx.utils

import androidx.recyclerview.widget.DiffUtil
import com.ngedev.thesisx.domain.model.Loan

class DiffCallBack(private val mOldList: List<Loan>, private val mNewList: List<Loan>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldList[oldItemPosition].uid == mNewList[newItemPosition].uid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMoney = mOldList[oldItemPosition]
        val newMoney = mNewList[newItemPosition]
        return oldMoney.book_title == newMoney.book_title && oldMoney.author == newMoney.author
    }
}