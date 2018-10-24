package info.houseofkim.backingapp2.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {


    private int id;
    private String name;

    private String servings;
    private String image;

    private List<Ingredient> ingridients;
    private List<Step> steps;

    public Recipe(int id, String name, String servings, String image, List<Ingredient> ingridients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingridients = ingridients;
        this.steps = steps;
    }

    public List<Ingredient> getIngridients() {
        return ingridients;
    }

    public void setIngridients(List<Ingredient> ingridients) {
        this.ingridients = ingridients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(servings);
        parcel.writeString(image);
        parcel.writeList(ingridients);
        parcel.writeList(steps);
    }
}
