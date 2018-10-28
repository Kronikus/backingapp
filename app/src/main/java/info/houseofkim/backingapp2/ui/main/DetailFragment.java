package info.houseofkim.backingapp2.ui.main;


import android.app.ActionBar;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.adapter.IngredientAdapter;
import info.houseofkim.backingapp2.adapter.RecipeAdapter;
import info.houseofkim.backingapp2.adapter.StepAdapter;
import info.houseofkim.backingapp2.data.Recipe;

public class DetailFragment extends Fragment {
    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    private RecipeAdapter recadapter;
    private StepAdapter stepAdapter;
    private IngredientAdapter ingredientAdapter;

    private MainViewModel detailModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getActivity() != null) {
            setHasOptionsMenu(true);
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
            }
            detailModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
            // ingredientAdapter.setmIngredients(detailModel.current.getIngridients());
            // stepAdapter.setmSteps(detailModel.current.getSteps());
            detailModel.getSelected().observe(this, new Observer<Recipe>() {
                @Override
                public void onChanged(@Nullable Recipe recipe) {
                    if (recipe != null) {
                        stepAdapter.setmSteps(recipe.getSteps());
                        ingredientAdapter.setmIngredients(recipe.getIngridients());
                        //   Log.e("ViewModel", String.valueOf(recipe.getIngridients()));
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.getActivity() != null) {
            ActionBar actionBar = getActivity().getActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
            } else {

                //    getActivity().setActionBar();
                Log.d("DetailFragment", "no action bar");
            }


        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);
        Log.d("DetailFragment", "start");

        RecyclerView recyclerViewIngredient = view.findViewById(R.id.detail_recyclerview_ingredient);
        ingredientAdapter = new IngredientAdapter(this.getContext());
        recyclerViewIngredient.setAdapter(ingredientAdapter);
        recyclerViewIngredient.setLayoutManager(new LinearLayoutManager(this.getContext()));


        RecyclerView recyclerViewStep = view.findViewById(R.id.detail_recyclerview_step);
        stepAdapter = new StepAdapter(this.getContext());
        recyclerViewStep.setAdapter(stepAdapter);
        recyclerViewStep.setLayoutManager(new LinearLayoutManager(this.getContext()));

        stepAdapter.setOnItemClickListener(new StepAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                detailModel.setSelectedStep(position);
                detailModel.setCurrentStep(position);
                if (view.findViewById(R.id.exo_playerView) == null) {
                    Log.d("DetailFragment", "Detail step click " + String.valueOf(detailModel.current));
                    PlayerFragment playerFragment = PlayerFragment.newInstance();
                    //playerFragment.setmStepCurrent(detailModel.getCurrentStep());
                    playerFragment.setVideoUrl(detailModel.getCurrentStep().getVideoURL());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction transaction = Objects.requireNonNull(fragmentManager).beginTransaction();
                    transaction.replace(R.id.container, playerFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });
        Log.d("DetailFragment", String.valueOf("activity"));


        Log.d("DetailFragment", "end");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {

        }
    }
}
