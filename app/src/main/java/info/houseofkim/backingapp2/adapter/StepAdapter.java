package info.houseofkim.backingapp2.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.data.Recipe;
import info.houseofkim.backingapp2.data.Step;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private final LayoutInflater mInflater;
    private List<Step> mSteps = Arrays.asList(new Step[0]);

    private static OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public StepAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      //  Log.e("RecipeAdapter", "bind start");


        View view = mInflater.inflate(R.layout.step_item,parent,false);
        //  private static myClickListener onItemClickListener;
        return new StepViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        //Log.e("RecipeAdapter", "bind start");


        if (mSteps != null) {
            Step current = mSteps.get(position);
            holder.recipeName.setText(String.format("%s. %s", String.valueOf(current.getStep_num()), String.valueOf(current.getShortDescription())));
           // holder.recipeServings.setText(String.format("Servings: %s", current.getServings()));

        } else {holder.recipeName.setText("No recipe");}
    }
    @Override
    public int getItemCount() {
        return mSteps.size();
    }
    public void setmSteps(List<Step> steps) {
        mSteps = steps;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        StepAdapter.mListener = clickListener;
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView recipeName;
//        private final TextView recipeServings;
//        private final ImageView recipeImage;


        public StepViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.step_item_text);
            //recipeName = itemView.findViewById(R.id.step_item_text);
            //recipeServings = itemView.findViewById(R.id.recipe_servings);
            //recipeImage = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }
    }
}
