package com.oxymium.realestatemanager.utils

import com.oxymium.realestatemanager.model.Picture

class PictureCommentListener(val pictureClickListener: (picture: Picture) -> Unit) {
    fun onClickPictureCommentButton(picture: Picture) = pictureClickListener(picture)
}