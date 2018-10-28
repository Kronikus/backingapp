package info.houseofkim.backingapp2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import info.houseofkim.backingapp2.data.Recipe;
import info.houseofkim.backingapp2.data.Step;
import info.houseofkim.backingapp2.ui.main.MainFragment;
import info.houseofkim.backingapp2.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mModel;
    private Recipe currentRecipe;
    private Step currentStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mModel.getSelected().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                currentRecipe = recipe;
            }
        });
        mModel.getSelectedStep().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(@Nullable Step step) {
                currentStep = step;
            }
        });
        mModel.getmAllRecipes().observe(this, (Observer<Recipe[]>) new Observer<Recipe[]>() {
            @Override
            public void onChanged(@Nullable Recipe[] recipes) {

                if (recipes != null) {

                    Log.e("ViewModel", String.valueOf(recipes.length));
                }
            }
        });

        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    public void buttonNext(View view) {
        if (currentRecipe.getSteps() != null) {
            int stepNo = currentRecipe.getSteps().indexOf(currentStep) + 1;
            Log.d("butPrev", String.valueOf(stepNo));

            if (currentRecipe.getSteps().size() > stepNo) {
                mModel.setSelectedStep(stepNo);
            } else {
                Toast.makeText(this, R.string.no_more_steps, Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("ButtonNextClick", "No current recipe");
        }
    }

    public void buttonPrev(View view) {


        if (currentRecipe.getSteps() != null) {
            int stepNo = currentRecipe.getSteps().indexOf(currentStep) - 1;

            if (stepNo >= 0) {

                Log.d("butPrev", String.valueOf(stepNo));
                Log.d("butPrev", String.valueOf(currentStep.getStep_num()));

                mModel.setSelectedStep(stepNo);
            } else {
                Toast.makeText(this, R.string.no_more_steps, Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.e("ButtonPrevClick", "No current recipe");
        }

    }
}
