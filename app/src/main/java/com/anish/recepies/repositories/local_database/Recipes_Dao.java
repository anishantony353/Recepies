package com.anish.recepies.repositories.local_database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.anish.recepies.models.Recipe;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface Recipes_Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipe> list);


    @Query("SELECT * FROM Recipe")
    Single<List<Recipe>> getRecipes();



    @Query("DELETE FROM Recipe")
    void deleteRecipes();

}
