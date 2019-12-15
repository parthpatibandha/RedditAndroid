package com.patibandha.movieapp.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class RedditPopularItemListPRQ(
    @SerializedName("limit") var limit: String,
    @SerializedName("after") var after: String? = ""
)

data class RedditPopularItemListRS(
    @SerializedName("data") var redditPopularItemChildren: RedditPopularItemChildren? = null
)

data class RedditPopularItemChildren(
    @SerializedName("children") var items: List<RedditPopularItemData>? = arrayListOf(),
    @SerializedName("dist") var dist: Int? = 0,
    @SerializedName("before") var before: String? = "",
    @SerializedName("after") var after: String? = ""
)

data class RedditPopularItemData(
    @SerializedName("data") var item: RedditPopularItem? = null
)

@Parcelize
@Entity(tableName = "redditPopularItem")
data class RedditPopularItem(

    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") var id: String,
    @ColumnInfo(name = "title") @SerializedName("title") var title: String? = "",
    @ColumnInfo(name = "subreddit_name_prefixed") @SerializedName("subreddit_name_prefixed") var subredditNamePrefixed: String? = "",
    @ColumnInfo(name = "thumbnail") @SerializedName("thumbnail") var thumbnail: String? = "",
    @ColumnInfo(name = "domain") @SerializedName("domain") var domain: String? = "",
    @ColumnInfo(name = "url") @SerializedName("url") var description: String? = "",
    @ColumnInfo(name = "created_utc") @SerializedName("created_utc") var createdUtc: String? = "",
    @ColumnInfo(name = "imageUrl") @SerializedName("imageUrl") var imageUrl: String? = "",

    @ColumnInfo(name = "isSelected") @SerializedName("isSelected") var isSelected : Boolean = false

) : Parcelable {
    @Ignore
    @SerializedName("preview") var preview: Preview? = null
}

@Parcelize
data class Preview(
    @SerializedName("images") var prviewImages: List<PreviewImage>? = arrayListOf()
) : Parcelable

@Parcelize
data class PreviewImage(
    @SerializedName("source") var source: Source? = null
) : Parcelable


@Parcelize
data class Source(
    @SerializedName("height") var height: Int? = 0,
    @SerializedName("url") var url: String? = "",
    @SerializedName("width") var width: Int? = 0
) : Parcelable





