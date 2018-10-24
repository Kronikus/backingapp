package info.houseofkim.backingapp2.ui.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.Toast;

import java.util.Arrays;

import info.houseofkim.backingapp2.R;
import info.houseofkim.backingapp2.adapter.RecipeListAdapter;
import info.houseofkim.backingapp2.data.Recipe;


public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private RecipeListAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        adapter = new RecipeListAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter.setOnItemClickListener(new RecipeListAdapter.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(int position) {
                                               mViewModel.setSelected(position);
                                               mViewModel.setCurrent(position);

                                               Log.d("MainFragment", "onclick "+ String.valueOf(mViewModel.current));
                                               Toast.makeText(getContext(), "click "+ String.valueOf(position), Toast.LENGTH_SHORT).show();
                                               DetailFragment detailFragment = DetailFragment.newInstance();
                                               FragmentManager fragmentManager = getFragmentManager();
                                               FragmentTransaction transaction = fragmentManager.beginTransaction();
                                               transaction.replace(R.id.container, detailFragment);
                                               transaction.addToBackStack(null);
                                               transaction.commit();
                                           }
                                       }
        );
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null) {
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.getmAllRecipes().observe(this, new Observer<Recipe[]>() {
            @Override
            public void onChanged(@Nullable Recipe[] recipes) {

                adapter.setRecipes(Arrays.asList(recipes));

            }
        });
        }
    }

}
