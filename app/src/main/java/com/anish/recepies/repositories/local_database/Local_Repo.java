package com.anish.recepies.repositories.local_database;

import android.content.Context;

import com.anish.recepies.models.Recipe;

import java.util.List;

import io.reactivex.Single;

public class Local_Repo {

    private String TAG = Local_Repo.class.getSimpleName();

    private static Local_Repo INSTANCE;

    private Recipes_Dao dao;

    private AppDatabase db;

    public Local_Repo(Context context){
        db = AppDatabase.getDataBase(context);
        dao = db.dao();
    }

    public static Local_Repo getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = new Local_Repo(context);
        }

        return INSTANCE;
    }



    public Single<List<Recipe>> get(){
        return dao.getRecipes();
    }


    public void insert(List<Recipe> values){
        dao.insertRecipes(values);
    }


    public void delete(){
        dao.deleteRecipes();
    }





}
