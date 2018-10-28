package info.houseofkim.backingapp2.adapter;

import android.content.Context;
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.data.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> implements ListAdapter {
    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes = Arrays.asList(new Recipe[0]);

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public RecipeListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
//        delegate = (ListAdapter) context;

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view, -1);
    }


    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (mRecipes != null) {
            Recipe current = mRecipes.get(position);
            holder.recipeName.setText(current.getName());
            holder.recipeServings.setText(String.format("Servings: %s", current.getServings()));

        } else {
            holder.recipeName.setText(R.string.text_no_recipe);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    public void setRecipes(List<Recipe> recipes) {
        if (recipes != null) {
            mRecipes = recipes;
            notifyDataSetChanged();
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        RecipeListAdapter.mListener = clickListener;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView recipeName;
        final TextView recipeServings;
        private final ImageView recipeImage;
        int position;

        private RecipeViewHolder(View itemView, int pos) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeServings = itemView.findViewById(R.id.recipe_servings);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            position = pos;
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            Log.e("Adapter", String.valueOf(getAdapterPosition()));
            if (getAdapterPosition() < 0) {
                mListener.onItemClickListener(position);

            } else {
                mListener.onItemClickListener(getAdapterPosition());
            }
        }
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
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
    public Recipe getItem(int i) {
        return mRecipes.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RecipeViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.recipe_item, viewGroup, false);

            viewHolder = new RecipeViewHolder(view, i);
            view.setTag(viewHolder);
        } else {
            viewHolder = (RecipeViewHolder) view.getTag();
        }
        if (mRecipes != null) {
            Recipe current = mRecipes.get(i);
            viewHolder.recipeName.setText(current.getName());
            viewHolder.recipeServings.setText(String.format("Servings: %s", current.getServings()));

        } else {
            viewHolder.recipeName.setText(R.string.text_no_recipe);
        }
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

}
