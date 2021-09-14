package com.oxymium.realestatemanager.database

import android.content.ClipData
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.ClipData.Item
import com.oxymium.realestatemanager.model.Estate
import android.content.ContentUris
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.coroutineContext


class EstateContentProvider : ContentProvider() {

    val applicationScope = CoroutineScope(SupervisorJob())

    // FOR DATA
    val AUTHORITY = "com.oxymium.realestatemanager"
    val TABLE_NAME = Estate::class.java.simpleName
    val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        if (context != null) {
            val estateId = ContentUris.parseId(uri)
            val cursor: Cursor? = EstateRoomDatabase.getDatabase(context!!, applicationScope).estateDao().getEstateWithCursor(estateId)
            cursor!!.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri " + uri);
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_NAME;
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }


    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        return 0
    }
}