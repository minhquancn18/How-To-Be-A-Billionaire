package com.example.myproject22.Presenter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.RadioGroup;

import java.io.File;
import java.io.IOException;

public interface AddingCategoryInterface {

    //region Set and Create
    public void SetInit();
    public void GetBundleData();
    //Hide Keyboard
    public void HideKeyboard(View view);
    //endregion



    //region Image
    //Load Image from Camera of Gallery
    public void ChooseImage();
    public void TakeImageFromGallery();
    public void TakeImageFromCamera();
    public File createImageFile() throws IOException;
    public void DeleteImage();

    //Check image null
    public Boolean IsNullImage(Bitmap bitmap);
    public void GetNoImage();
    public String GetStringImage();
    //endregion

    //region Condition
    //Radio button Category
    public  void CheckRadioButtonCategory(RadioGroup radioGroup, int idChecked);

    //Is null name category
    public Boolean IsNullName();
    //endregion

    //Saving new category
    public void SavingNewCategory();

}
