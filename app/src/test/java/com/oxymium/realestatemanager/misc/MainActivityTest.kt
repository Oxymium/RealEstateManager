package com.oxymium.realestatemanager.misc

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.oxymium.realestatemanager.R
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class MainActivityTest {

    @Test
    fun textViewInitTest() {
        // GIVEN
        val mockView = mockk<TextView>()
        every { mockView.visibility = any() } just Runs

        val mockActivity = mockk<MainActivity>()
        every { mockActivity.findViewById<TextView>(R.id.activity_main_activity_text_view_main) } returns mockView

        // WHEN
        mockActivity.onCreate(Bundle())

        // THEN
        verify { mockActivity.findViewById<TextView>(R.id.activity_main_activity_text_view_main) }
        verify { mockView.visibility = View.VISIBLE }
    }

}
