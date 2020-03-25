package com.anish.recepies.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Recipe {

    @PrimaryKey
    lateinit var recipe_id:String
    lateinit var image_url:String
    lateinit var social_rank:String
    lateinit var _id:String
    lateinit var publisher:String
    lateinit var source_url:String
    lateinit var publisher_url:String
    lateinit var title:String

}