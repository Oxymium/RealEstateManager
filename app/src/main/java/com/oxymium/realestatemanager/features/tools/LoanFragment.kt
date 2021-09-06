package com.oxymium.realestatemanager.features.tools

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.oxymium.realestatemanager.R
import com.oxymium.realestatemanager.databinding.FragmentLoanBinding
import com.oxymium.realestatemanager.viewmodel.LoanViewModel
import com.github.mikephil.charting.components.Legend





// ------------
// LaonFragment
// ------------

class LoanFragment: Fragment() {

    private val fragmentTAG = javaClass.simpleName

    // DataBinding
    private lateinit var fragmentLoanBinding: FragmentLoanBinding
    private val binding get() = fragmentLoanBinding

    // LoanViewModel
    private val loanViewModel: LoanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(fragmentTAG, "onCreate: ")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentLoanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_loan, container, false)
        fragmentLoanBinding.loanViewModel = loanViewModel

        fragmentLoanBinding.fragmentLoanBorrowedAmountInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        loanViewModel.updateBorrowedAmount(s.toString().toFloat())
                    }else{

                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        fragmentLoanBinding.fragmentLoanDepositInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        loanViewModel.updateDeposit(s.toString().toFloat())
                    }else{

                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        fragmentLoanBinding.fragmentLoanDurationInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        loanViewModel.updateDuration(s.toString().toInt())
                    }else{

                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        fragmentLoanBinding.fragmentLoanInterestRateInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) {
                        loanViewModel.updateInterestRate(s.toString().toFloat())
                    }else{

                    }
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        displayLoanChartData()


        return binding.root
    }

    private fun displayLoanChartData(){

        loanViewModel.loan.observe(viewLifecycleOwner, {

            loan ->

            println(loan)

            val entries = listOf(
                PieEntry(loan.borrowedAmount, "Borrowed"),
                PieEntry(loan.deposit, "Deposit"),
                PieEntry(calculateInterestsTotalAmount(loan.borrowedAmount, loan.interestRate), "Interests")
            )
            // Ready set & data
            val set = PieDataSet(entries, "Cost: $" + calculateTotalEstateCost(loan.borrowedAmount, loan.interestRate, loan.deposit).toString())
            val data = PieData(set)

            // Visuals & flavours
            // ColorTemplate.PASTEL_COLORS
            set.colors = mutableListOf(
                Color.rgb(64, 89, 128), Color.rgb(149, 165, 124), Color.rgb(217, 184, 162),
                Color.rgb(191, 134, 134), Color.rgb(179, 48, 80))
            set.valueTextColor = ContextCompat.getColor(requireActivity(), R.color.white)
            set.valueTextSize = 14f;

            provideDataToPieChart(data, loan.duration, calculateMonthlyCost(loan.borrowedAmount, loan.interestRate, loan.duration))

        })

    }

    private fun provideDataToPieChart(data: PieData, loanDuration: Int, monthlyCost: Float){

        val decimalRounded = String.format("%.2f", monthlyCost)

        fragmentLoanBinding.fragmentLoanPieChart.data = data
        fragmentLoanBinding.fragmentLoanPieChart.setHoleColor(ContextCompat.getColor(requireActivity(), R.color.transparent))
        fragmentLoanBinding.fragmentLoanPieChart.legend.isEnabled = true
        fragmentLoanBinding.fragmentLoanPieChart.legend.textColor = ContextCompat.getColor(requireActivity(), R.color.white)
        fragmentLoanBinding.fragmentLoanPieChart.legend.textSize = 13f
        fragmentLoanBinding.fragmentLoanPieChart.legend.horizontalAlignment
        fragmentLoanBinding.fragmentLoanPieChart.description.isEnabled = false
        fragmentLoanBinding.fragmentLoanPieChart.centerText = "$decimalRounded/m - $loanDuration years"
        fragmentLoanBinding.fragmentLoanPieChart.setCenterTextSize(14f);
        fragmentLoanBinding.fragmentLoanPieChart.setCenterTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
        fragmentLoanBinding.fragmentLoanPieChart.invalidate()
        fragmentLoanBinding.fragmentLoanPieChart.notifyDataSetChanged()

    }

    private fun calculateInterestsTotalAmount(borrowedAmount: Float, interestRate: Float): Float {
        return borrowedAmount * (1 + interestRate / 100) - borrowedAmount
    }

    private fun calculateMonthlyCost(borrowedAmount: Float, interestRate: Float, duration: Int): Float {
       return ((calculateInterestsTotalAmount(borrowedAmount, interestRate) + borrowedAmount) / (duration * 12))
    }

    private  fun calculateTotalEstateCost(borrowedAmount: Float, interestRate: Float, deposit: Float): Float{
        return calculateInterestsTotalAmount(borrowedAmount, interestRate) + deposit + borrowedAmount
    }


}