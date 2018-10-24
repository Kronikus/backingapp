package info.houseofkim.backingapp2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.data.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients = Arrays.asList(new Ingredient[0]);

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public IngredientAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  Log.e("IngredientAdapter", "bind start");


        View view = mInflater.inflate(R.layout.ingredient_item, parent, false);
        //  private static myClickListener onItemClickListener;
        return new IngredientViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
       // Log.e("IngredientAdapter", "bind start");


        if (mIngredients != null) {
            Ingredient current = mIngredients.get(position);
            holder.ingredient.setText(current.getIngridient());
            holder.quantity.setText(current.getQuantity());
            holder.measure.setText(current.getMeasure());
            // holder.recipeServings.setText(String.format("Servings: %s", current.getServings()));

        } else {
            holder.ingredient.setText("No recipe");
        }
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public void setmIngredients(List<Ingredient> Ingredients) {
        mIngredients = Ingredients;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        IngredientAdapter.mListener = clickListener;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder  {
        private final TextView ingredient;
        private final TextView quantity;
        private final TextView measure;


        public IngredientViewHolder(View itemView) {
            super(itemView);
            ingredient = itemView.findViewById(R.id.recipe_detail_ingredient_text);
            measure = itemView.findViewById(R.id.ingredient_measure);
            quantity = itemView.findViewById(R.id.ingredient_quantity);
        }
    }
}
