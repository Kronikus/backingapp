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
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
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
    }

    public void buttonNext(View view) {
        int stepNo = currentStep.getStep_num() + 1;
        Log.d("butPrev", String.valueOf(stepNo));
        if (currentRecipe.getSteps() != null && currentRecipe.getSteps().size() > stepNo) {
            mModel.setSelectedStep(stepNo);
        } else {
            Toast.makeText(this,R.string.no_more_steps,Toast.LENGTH_LONG).show();
        }
    }

    public void buttonPrev(View view) {
        int stepNo = currentStep.getStep_num() - 1;
        Log.d("butPrev", String.valueOf(stepNo));
        Log.d("butPrev", String.valueOf(currentStep.getStep_num()));

        if (currentRecipe.getSteps() != null && stepNo >= 0) {
            mModel.setSelectedStep(stepNo);
        }
        else {
            Toast.makeText(this,R.string.no_more_steps,Toast.LENGTH_LONG).show();
        }
    }
}
