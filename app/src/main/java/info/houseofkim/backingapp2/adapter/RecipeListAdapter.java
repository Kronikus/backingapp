package info.houseofkim.backingapp2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.data.Recipe;

 public  class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {
    private final LayoutInflater mInflater;
    private List<Recipe> mRecipes = Arrays.asList(new Recipe[0]);

     private static OnItemClickListener mListener;
     public interface OnItemClickListener {
         void onItemClick(int position);
     }
    public RecipeListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item,parent,false);
        //  private static myClickListener onItemClickListener;
        return new RecipeViewHolder(view);
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
    public int getItemCount() {
        return mRecipes.size();
    }
    public void setRecipes(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        RecipeListAdapter.mListener = clickListener;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView recipeName;
        private final TextView recipeServings;
        private final ImageView recipeImage;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipe_name);
            recipeServings = itemView.findViewById(R.id.recipe_servings);
            recipeImage = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }
    }
}
