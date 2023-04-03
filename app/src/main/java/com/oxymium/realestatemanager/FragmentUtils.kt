package com.oxymium.realestatemanager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun replaceFragment(fragmentActivity: FragmentActivity, fragmentIdToLoad: Int, fragmentDestination: Fragment){
    val transaction = fragmentActivity.supportFragmentManager.beginTransaction()
    transaction.replace(fragmentIdToLoad, fragmentDestination, "")
    transaction.commit()
}

