package com.example.myproject22.Presenter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;

public class AddingCategoryPresenter {

    private AddingCategoryInterface anInterface;

    public AddingCategoryPresenter(AddingCategoryInterface anInterface) {
        this.anInterface = anInterface;
    }

    //region Set and Create

    public void setInit(){
        anInterface.SetInit();
    }

    public void getBundleData(){
        anInterface.GetBundleData();
    }

    public void hideKeyboard(View view){
        anInterface.HideKeyboard(view);
    }

    //endregion

    //region Condition and Saving
    public void checkRadioButtonCategory(RadioGroup radioGroup, int idChecked){
        anInterface.CheckRadioButtonCategory(radioGroup,idChecked);
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
    //endregion

    //region Image

    public void chooseImage(){
        anInterface.ChooseImage();
    }

    public Boolean isNullImage(Bitmap bitmap){
        return anInterface.IsNullImage(bitmap);
    }

    public void takeImageFromCamera(){
        anInterface.TakeImageFromCamera();
    }

    public void takeImageFromGallery(){
        anInterface.TakeImageFromGallery();
    }

    public static byte[] encodeTobase64(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //endregion

}
