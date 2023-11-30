package com.oxymium.realestatemanager.features.tools

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
import com.oxymium.realestatemanager.model.Loan
import com.oxymium.realestatemanager.viewmodel.LoanViewModel

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
    ): View {

        fragmentLoanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_loan, container, false)
        fragmentLoanBinding.loanViewModel = loanViewModel

        // Observer
        loanViewModel.loan.observe(viewLifecycleOwner){
            displayLoanChartData(it)
        }

        fragmentLoanBinding.loanBorrowInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) loanViewModel.updateBorrowedAmount(s.toString().toFloat())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        fragmentLoanBinding.loanDepositInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) loanViewModel.updateDeposit(s.toString().toFloat())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        fragmentLoanBinding.loanDurationInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) loanViewModel.updateDuration(s.toString().toInt())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })

        fragmentLoanBinding.loanRateInput.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    if (s.isNotEmpty()) loanViewModel.updateInterestRate(s.toString().toFloat())
                }
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }
            })


        return binding.root

    }

    private fun displayLoanChartData(loan: Loan){

        // Monthly cost
        val monthlyPayments = loan.monthlyPayment()
        // Total amount (monthly cost * duration in Years * 12)
        val totalPayments = monthlyPayments * (loan.duration * 12)
        // Sum of all interests (total amount minus sum of all interests)
        val totalInterests = totalPayments - loan.borrowedAmount

        val entries = listOf(
            // Entry: Borrowed Amount
            PieEntry(loan.borrowedAmount, null, ContextCompat.getDrawable(requireActivity(), R.drawable.bank), null),
            // Entry: Deposit
            PieEntry(loan.deposit, null, ContextCompat.getDrawable(requireActivity(), R.drawable.piggy_bank), null),
            // Entry: Interests
            PieEntry(totalInterests, null, ContextCompat.getDrawable(requireActivity(), R.drawable.percent), null)
        )

        // Ready set & data
        val set = PieDataSet(
            entries,
            "Cost: $totalPayments"
        )

        set.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE

        val data = PieData(set)

        // Visuals & flavours
        set.colors = mutableListOf(
            ContextCompat.getColor(requireActivity(), R.color.pie_chart_1),
            ContextCompat.getColor(requireActivity(), R.color.pie_chart_2),
            ContextCompat.getColor(requireActivity(), R.color.pie_chart_3)
        )
        set.valueTextColor = ContextCompat.getColor(requireActivity(), R.color.white)
        set.valueTextSize = 14f

        provideDataToPieChart(
            data,
            loan.duration,
            loan.monthlyPayment()
        )

    }

    private fun provideDataToPieChart(data: PieData, loanDuration: Int, monthlyPayment: Float){

        val decimalRounded = String.format("%.2f", monthlyPayment)
        val yearsToMonths = loanDuration * 12

        fragmentLoanBinding.loanPieChart.apply {
            this.data = data
            setHoleColor(ContextCompat.getColor(requireActivity(), R.color.transparent))
            legend.apply{
                textSize = 13f
                textColor = ContextCompat.getColor(requireActivity(), R.color.white)
                isEnabled = true
                horizontalAlignment
            }
            description.isEnabled = false
            centerText = "$decimalRounded u/m \n over $loanDuration years or $yearsToMonths months"
            setCenterTextSize(14f)
            setCenterTextColor(ContextCompat.getColor(requireActivity(), R.color.white))
            invalidate()
            notifyDataSetChanged()
        }
    }
}