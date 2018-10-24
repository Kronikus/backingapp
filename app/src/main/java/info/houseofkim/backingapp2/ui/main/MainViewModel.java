package info.houseofkim.backingapp2.ui.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import info.houseofkim.backingapp2.data.Recipe;
import info.houseofkim.backingapp2.data.Repository;
import info.houseofkim.backingapp2.data.Step;

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<Recipe> selected = new MutableLiveData<Recipe>();

    private MutableLiveData<Step> selectedStep = new MutableLiveData<Step>();




    private  Step currentStep;
    public Recipe current ;
    private Repository mRepository;
    private LiveData<Recipe[]> mAllRecipes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllRecipes = mRepository.getmRecipes();
        Log.d("ViewModel","starter");
    }
    LiveData<Recipe[]> getmAllRecipes(){return mAllRecipes;}

    public MutableLiveData<Recipe> getSelected() {
        return selected;
    }

    public void setSelected(int position) {
        Recipe[] recipes = mAllRecipes.getValue();
        if (recipes != null) {
          //  Log.e("MainViewModel", "onclick "+ String.valueOf(recipes[position]));
            selected.setValue(recipes[position]);
        }
    }
    public Recipe getCurrent() {
        return current;
    }

    public void setCurrent(int position) {
        Recipe[] recipes = mAllRecipes.getValue();
        if (recipes != null) {
            this.current = recipes[position];
            Log.e("MainViewModel", "onclick "+ String.valueOf(current));

        }

    }

    public Step getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(int position) {
        Log.e("Model",current.getName());
        this.currentStep = current.getSteps().get(position);
    }

    public MutableLiveData<Step> getSelectedStep() {
        return selectedStep;
    }

    public void setSelectedStep(int position) {
        if (selected.getValue() != null) {
        this.selectedStep.setValue(selected.getValue().getSteps().get(position));
    }
    }

}
