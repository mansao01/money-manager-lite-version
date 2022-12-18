package com.tugas.myapplication.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tugas.myapplication.database.Money
import com.tugas.myapplication.databinding.ListMoneyItemBinding
import com.tugas.myapplication.helper.MoneyDiffCallback
import com.tugas.myapplication.ui.AddUpdateMoneyActivity

class ListMoneyAdapter : RecyclerView.Adapter<ListMoneyAdapter.ListMoneyViewHolder>() {
    private val listMoney = ArrayList<Money>()

    fun setListMoney(listMoney: List<Money>) {
        val diffCallback = MoneyDiffCallback(this.listMoney, listMoney)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listMoney.clear()
        this.listMoney.addAll(listMoney)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMoneyViewHolder {
        val binding =
            ListMoneyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListMoneyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMoneyViewHolder, position: Int) {
        holder.bind(listMoney[position])
    }

    override fun getItemCount() = listMoney.size

    inner class ListMoneyViewHolder(private val binding: ListMoneyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(money: Money) {
            binding.apply {
                tvTitle.text  =money.transaction
                tvIncome.text = money.income
                tvOutcome.text = money.outcome
                cardView.setOnClickListener {
                    val intent = Intent(itemView.context, AddUpdateMoneyActivity::class.java)
                    intent.putExtra(AddUpdateMoneyActivity.EXTRA_DATA, money)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }
}