package info.houseofkim.backingapp2.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import info.houseofkim.backingapp2.utils.JsonUtils;
import info.houseofkim.backingapp2.utils.NetworkUtil;

public class Repository {
    private static MutableLiveData<Recipe[]> mRecipes = new MutableLiveData<>();
    public LiveData<Recipe[]> getmRecipes() {

        return mRecipes;
    }

    public static void setmRecipes(Recipe[] mRecipes) {
        Repository.mRecipes.setValue(mRecipes);
    }


    public Repository(Application application) {
       // mRecipes.setValue(new Recipe[0]);
        new insertAsyncTask().execute();

    }

    private static class insertAsyncTask extends AsyncTask<Void, Void, Recipe[]> {

        @Override
        protected void onPostExecute(Recipe[] recipes) {
            super.onPostExecute(recipes);
          setmRecipes(recipes);
            Log.e("Repository", "finished loading");
        }

        @Override
        protected Recipe[] doInBackground(Void... voids) {
            Recipe[] result = new Recipe[0];
            String resp = "";
            try {
                resp = NetworkUtil.getResponseFromHttpUrl(NetworkUtil.buildUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Log.e("RecipeDB",s);
            result = JsonUtils.parseRecipesJSON(resp);

            return result;
        }
    }
}
