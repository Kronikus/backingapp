package info.houseofkim.backingapp2.adapter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.data.Recipe;
import info.houseofkim.backingapp2.data.Step;
import info.houseofkim.backingapp2.ui.main.MainViewModel;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeDetailViewHolder> {

    private Recipe mRecipe;
    private final LayoutInflater mInflater;

    public RecipeAdapter(Context context, FragmentActivity activity) {
        this.mInflater = LayoutInflater.from(context);
        MainViewModel model = ViewModelProviders.of(activity).get(MainViewModel.class);
        mRecipe = model.getCurrent();

    }


    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("RecipeAdapter", "create");

        View view = mInflater.inflate(R.layout.step_item, parent, false);
        return new RecipeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder holder, int position) {
        Log.e("RecipeAdapter", "bind start");

        if (mRecipe != null) {
            Step current = mRecipe.getSteps().get(position);
            holder.recipeSteps.setText(current.getShortDescription());
            //    holder.recipeServings.setText(String.format("Servings: %s", current.getServings()));
            Log.e("RecipeAdapter", "bind");
        } else {
            holder.recipeSteps.setText("No recipe");
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public Recipe getmRecipe() {
        return mRecipe;
    }

    public void setmRecipe(Recipe mRecipe) {
        this.mRecipe = mRecipe;
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder {


        // private final TextView recipeIngredients;
        private final TextView recipeSteps;
        // private final ImageView recipeImage;

        public RecipeDetailViewHolder(View itemView) {
            super(itemView);
            //recipeIngredients = itemView.findViewById(R.id.recipe_detail_ingredients);
            recipeSteps = itemView.findViewById(R.id.step_item_text);
            // recipeImage = itemView.findViewById(R.id.recipe_image);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//
//        }
        }
    }
}
