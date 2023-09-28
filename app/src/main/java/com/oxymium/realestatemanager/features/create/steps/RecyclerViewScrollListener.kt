package com.oxymium.realestatemanager.features.create.steps

import androidx.recyclerview.widget.RecyclerView

class RecyclerViewScrollListener(
    private val onScrollToStart: (() -> Unit)?,
    private val onScrollToEnd: (() -> Unit)?,
    private val onScrollToTop: (() -> Unit)?,
    private val onScrollToBottom: (() -> Unit)?
    ) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        // Check if scrolling towards the start or end
        if (dx < 0) {
            // Scrolling towards the start
            if (!recyclerView.canScrollHorizontally(-1)) {
                // Reached the beginning of the RecyclerView
                onScrollToStart?.invoke()
            }
        } else if (dx > 0) {
            // Scrolling towards the end
            if (!recyclerView.canScrollHorizontally(1)) {
                // Reached the end of the RecyclerView
                onScrollToEnd?.invoke()
            }
        }

        if (dy < 0) {
            // Scrolling towards the top
            if (!recyclerView.canScrollVertically(-1)) {
                // Reached the top of the RecyclerView
                onScrollToTop?.invoke()
            }
        } else if (dy > 0) {
            // Scrolling towards the bottom
            if (!recyclerView.canScrollVertically(1)) {
                // Reached the bottom of the RecyclerView
                onScrollToBottom?.invoke()
            }
        }
    }
}