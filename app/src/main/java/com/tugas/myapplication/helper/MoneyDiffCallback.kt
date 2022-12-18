package com.tugas.myapplication.helper

import androidx.recyclerview.widget.DiffUtil
import com.tugas.myapplication.database.Money

class MoneyDiffCallback(
    private val mOldMoneyItem: List<Money>,
    private val mNewMoneyItem: List<Money>
) : DiffUtil.Callback() {
    override fun getOldListSize() = mOldMoneyItem.size

    override fun getNewListSize() = mNewMoneyItem.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldMoneyItem[oldItemPosition].id == mNewMoneyItem[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newEmployee = mNewMoneyItem[newItemPosition]
        val oldEmployee = mOldMoneyItem[oldItemPosition]

        return oldEmployee.transaction == newEmployee.transaction
    }
}