package info.houseofkim.backingapp2.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.adapter.RecipeListAdapter.RecipeViewHolder;
import info.houseofkim.backingapp2.data.Recipe;

public class RecipeGridAdapter extends RecyclerView.Adapter<RecipeViewHolder> implements ListAdapter {
    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes = Arrays.asList(new Recipe[0]);


    public RecipeGridAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    public void setRecipes(List<Recipe> recipes) {
        if (recipes != null) {
            mRecipes = recipes;
            notifyDataSetChanged();}
    }


    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootview = mInflater.inflate(R.layout.recipe_item,parent,false);

        return new RecipeViewHolder(rootview,-1);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (mRecipes != null) {
            Recipe current = mRecipes.get(position);
            holder.recipeName.setText(current.getName());
            holder.recipeServings.setText(String.format("Servings: %s", current.getServings()));

        } else {holder.recipeName.setText("No recipe");}
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return mRecipes.size();
    }

    @Override
    public Object getItem(int i) {
        return mRecipes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mRecipes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecipeViewHolder viewHolder = null;

        if (view == null) {
            View rootview = mInflater.inflate(R.layout.recipe_item,viewGroup,false);

            viewHolder = new RecipeViewHolder(rootview,i);
            view.setTag(viewHolder);
            }
            else {
            viewHolder = (RecipeViewHolder) view.getTag();
        }
        if (mRecipes != null) {
            Recipe current = mRecipes.get(i);
            viewHolder.recipeName.setText(current.getName());
            viewHolder.recipeServings.setText(String.format("Servings: %s", current.getServings()));

        } else {viewHolder.recipeName.setText("No recipe");}
        return view;
       }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mRecipes.isEmpty();
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }
}
