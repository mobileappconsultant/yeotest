package com.android.yeophonebook.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    val phoneNumber: String,
    var dateUpdated: Long? = null
) {
    override fun equals(other: Any?): Boolean {
        return (other as? ContactEntity)?.let {
            it.name == this.name && it.phoneNumber == this.phoneNumber
        } ?: super.equals(other)
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + (dateUpdated?.hashCode() ?: 0)
        return result
    }
}
