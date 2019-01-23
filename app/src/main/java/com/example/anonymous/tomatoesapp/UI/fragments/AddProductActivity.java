package com.example.anonymous.tomatoesapp.UI.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.model.TomatoesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductActivity extends Fragment {

    private static final int PICK_PHOTO_REQUEST_CODE =1212 ;
    Uri photopath,downloadurl;
   Button maddproductBtn;
    ImageView mproductPhoto;
    Boolean uploadStatus=false;
AutoCompleteTextView mproductPrice,mproductName,mproductDesc;
String mcurrentLat,mCurrentLong;
    StorageReference photoref;
    DatabaseReference dbreference;
    String senderlat,sendeerLang,marker;
    public AddProductActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        senderlat=String.valueOf(-0.395541);
        sendeerLang=String.valueOf(36.962156);
        marker="Dedan Kimathi University of Technology";
        photoref= FirebaseStorage.getInstance().getReference();
        dbreference= FirebaseDatabase.getInstance().getReference("PostedProducts");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_product, container, false);
        maddproductBtn=(Button) view.findViewById(R.id.addProductBtn);
        mproductDesc=(AutoCompleteTextView) view.findViewById(R.id.addproducDesctxt);
        mproductName=(AutoCompleteTextView) view.findViewById(R.id.addproductnametxt);
        mproductPrice=(AutoCompleteTextView) view.findViewById(R.id.addproductPricetxt);
mproductPhoto=(ImageView) view.findViewById(R.id.addProductImageview);
mproductPhoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        choosePhoto();
    }
});
maddproductBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

       uploadPhoto();
    }
});
        return view;
    }
    public void addProduct(){
        Toast.makeText(getActivity(), uploadStatus.toString() +"with" +downloadurl.toString(), Toast.LENGTH_LONG).show();
      if(uploadStatus){
          }
    }
    public void choosePhoto() {
        Intent photointent=new Intent();
        photointent.setType("image/*");
        photointent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photointent,"Choose a product Photo"),PICK_PHOTO_REQUEST_CODE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==PICK_PHOTO_REQUEST_CODE && resultCode==RESULT_OK && data!=null&& data.getData()!=null){
             photopath  =data.getData();
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),photopath);
                mproductPhoto.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void uploadPhoto(){
        if(photopath!=null) {
            StorageReference riversRef = photoref.child("images/"+ new Date().getTime() +".jpg");

            riversRef.putFile(photopath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            downloadurl= taskSnapshot.getDownloadUrl();
                            //downloadurl = taskSnapshot.getUploadSessionUri();
                            Toast.makeText(getActivity(), "adding...", Toast.LENGTH_LONG).show();
                            uploadStatus = true;
                            String productname=mproductName.getText().toString().trim();
                            String productPrice=mproductPrice.getText().toString().trim();
                            String productDesc=mproductDesc.getText().toString().trim();
                            String picurl=downloadurl.toString();
                            String customerLong=sendeerLang;
                            String customerlat=senderlat;
                            if(!TextUtils.isEmpty(productname)){
                                String id=dbreference.push().getKey();
                                String sellerId= FirebaseAuth.getInstance().getCurrentUser().getUid();

                                TomatoesModel newproduct=new TomatoesModel(productname,productDesc,sellerId,id,customerlat,customerLong,marker,productPrice,picurl);
                                dbreference.child(id).setValue(newproduct);
                                Toast.makeText(getActivity(),"added successfully",Toast.LENGTH_LONG).show();


                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getActivity(), "image not uploading correctly.make sure you are connected to internet and try again", Toast.LENGTH_LONG).show();
                            uploadStatus = false;
                        }
                    });
        }else{
            uploadStatus = false;
            Toast.makeText(getActivity(),"EmptyFilepath please select a photo..",Toast.LENGTH_LONG).show();
        }

    }
}
