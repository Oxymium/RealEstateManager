package com.oxymium.realestatemanager.features.estates

import com.oxymium.realestatemanager.model.databaseitems.Picture

class PictureCommentListener(val pictureClickListener: (picture: Picture) -> Unit) {
    fun onClickPictureCommentButton(picture: Picture) = pictureClickListener(picture)
}