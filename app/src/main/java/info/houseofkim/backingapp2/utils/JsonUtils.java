package info.houseofkim.backingapp2.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.houseofkim.backingapp2.data.Ingredient;
import info.houseofkim.backingapp2.data.Recipe;
import info.houseofkim.backingapp2.data.Step;

import static android.content.ContentValues.TAG;

public class JsonUtils {

    public static Ingredient parseIngridientJSON(String json, int recipeId, int id) {

        Ingredient result = null;
        if (json != null) {
            try {
                JSONObject c = new JSONObject(json);
                // int recipeid = recipeId;
                String quant = c.getString("quantity");
                String measure = c.getString("measure");
                String ingredient = c.getString("ingredient");

                result = new Ingredient(id, recipeId, quant, measure, ingredient);


                //  result = new Recipe(movieId, movieName, movieReleaseDate, movieImage, movieDescription, movieDuration, movieRating, moviePopularity);

            } catch (final JSONException e) {
                Log.e(TAG, "Ingridient item Json parsing error: " + e.getMessage());
            }

        } else {
            Log.e(TAG, "Ingridient item Couldn't get json.");

        }

        return result;
    }

    public static Step parseStepJSON(String json, int recipeId, int id) {

        Step result = null;
        if (json != null) {
            try {
                JSONObject c = new JSONObject(json);
                int step_num = c.getInt("id");
                String shortDescr = c.getString("shortDescription");
                String descr = c.getString("description");
                String videoURL = c.getString("videoURL");
                String thumbnailURL = c.getString("thumbnailURL");

                result = new Step(id ,recipeId, step_num, shortDescr, descr, videoURL, thumbnailURL);


                //  result = new Recipe(movieId, movieName, movieReleaseDate, movieImage, movieDescription, movieDuration, movieRating, moviePopularity);

            } catch (final JSONException e) {
                Log.e(TAG, "Step item Json parsing error: " + e.getMessage());
            }

        } else {
            Log.e(TAG, "Step item Couldn't get json.");

        }

        return result;
    }


    public static Recipe parseRecipeJSON(String json, int num) {

        Recipe result = null;
        List<Step> stepsList = new ArrayList<>();
        List<Ingredient> ingredientsList = new ArrayList<>();

        if (json != null) {
            try {
                JSONObject c = new JSONObject(json);
                int id = c.getInt("id");
                String name = c.getString("name");
                String servings = c.getString("servings");
                String image = c.getString("image");
                JSONArray ingredients = c.getJSONArray("ingredients");
                for (int i = 0; i < ingredients.length(); i++) {
                    Ingredient ingr = parseIngridientJSON(String.valueOf(ingredients.get(i)), id ,num *100 +i);
                    ingredientsList.add(ingr);
                }

                JSONArray steps = c.getJSONArray("steps");
                for (int i = 0; i < steps.length(); i++) {
                    Step stp = parseStepJSON(String.valueOf(steps.get(i)), id, num *100 +i);
                    stepsList.add(stp);
                }
                result = new Recipe(id, name, servings, image,ingredientsList,stepsList);

                //  result = new Recipe(movieId, movieName, movieReleaseDate, movieImage, movieDescription, movieDuration, movieRating, moviePopularity);

            } catch (final JSONException e) {
                Log.e(TAG, "Recipe item Json parsing error: " + e.getMessage());
            }

        } else {
            Log.e(TAG, "Recipe item Couldn't get json.");

        }

        return result;
    }

    public static Recipe[] parseRecipesJSON(String json) {

        Recipe[] result = null;
        Recipe recipe;
        // Log.e("jsonArray",json.toString());
        if (json != null) {
            try {
                //   Log.e("jsonArray", json.toString());

                //JSONObject c = new JSONObject(json);
                // Log.e("jsonArray",c.toString());
                JSONArray b = new JSONArray(json);
                //Log.e("jsonArray",b.toString());
                result = new Recipe[b.length()];
                // Log.e("Array",result.toString());
                for (int i = 0; i < b.length(); i++) {
                    //  Log.e("index", String.valueOf(i) + " "+ String.valueOf(b.length()));

                    //       Log.e("Array",String.valueOf(b.getJSONObject(i)));
                    recipe = parseRecipeJSON(String.valueOf(b.getJSONObject(i)), i);
                    result[i] = recipe;
                    //      Log.e("Array",String.valueOf(result[i]));

                }


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }

        } else {
            Log.e(TAG, "Couldn't get json.");

        }


        return result;
    }
}
