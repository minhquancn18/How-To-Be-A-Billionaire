package com.example.myproject22.Presenter;

import android.graphics.Bitmap;
import android.widget.RadioGroup;

public class AddingCategoryPresenter {
    private AddingCategoryInterface anInterface;


    public AddingCategoryPresenter(AddingCategoryInterface anInterface) {
        this.anInterface = anInterface;
    }

    public void setInit(){
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }

    public void savingNewCategory(Bitmap bitmap){
        if(anInterface.IsNullName())
            return;

        if(anInterface.IsNullImage(bitmap))
        {
            anInterface.GetNoImage();
            return;
        }

        anInterface.SavingNewCategory();
    }

    public void chooseImage(){
        anInterface.ChooseImage();
    }

    public Boolean isNullImage(Bitmap bitmap){
        return anInterface.IsNullImage(bitmap);
    }

    public void checkRadioButtonCategory(RadioGroup radioGroup, int idChecked){
        anInterface.CheckRadioButtonCategory(radioGroup,idChecked);
    }

}
