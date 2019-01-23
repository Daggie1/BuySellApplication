package com.example.anonymous.tomatoesapp.UI.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.fragments.AddProductActivity;
import com.example.anonymous.tomatoesapp.UI.fragments.UpdateProductActivity;
import com.example.anonymous.tomatoesapp.adapters.fragmentActivityLinker;
import com.example.anonymous.tomatoesapp.model.TomatoesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Add_Update_Product_Activity extends fragmentActivityLinker {
    private Uri photopath;
    private static final int PICK_PHOTO_REQUEST_CODE = 0700;
    Button addproductBtn;
    TomatoesModel tomatoesModel;
    EditText productnameedittxt,productDesctxt,productPricetxt;
    ImageView addpicImageView;
    Uri downloadUrl;
    StorageReference photoref;
    DatabaseReference dbreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__update__product_);
addFragment(new AddProductActivity(),R.id.add_updateContainer);
        //addFragment(new UpdateProductActivity(),R.id.add_updateContainer);
    }

}
