package com.example.anonymous.tomatoesapp.mpesa;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.ButtonCallback;
import com.bdhobare.mpesa.Mpesa;
import com.bdhobare.mpesa.interfaces.AuthListener;
import com.bdhobare.mpesa.interfaces.MpesaListener;
import com.bdhobare.mpesa.models.STKPush;
import com.bdhobare.mpesa.models.STKPush.Builder;
import com.bdhobare.mpesa.utils.Pair;
import com.bumptech.glide.Glide;
import com.example.anonymous.tomatoesapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements AuthListener, MpesaListener {
    //TODO: Replace these values from
    public static final String BUSINESS_SHORT_CODE = "174379";
    public static final String PASSKEY = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
    public static final String CONSUMER_KEY = "YTZZTAUnMdOccHYU6ipgdCBmRHMHPDqA";
    public static final String CONSUMER_SECRET = "NKTmGMpbMCuo1pQc";
    public static final String CALLBACK_URL = "YOUR_CALLBACK_URL";


    public static final String NOTIFICATION = "PushNotification";
    public static final String SHARED_PREFERENCES = "com.bdhobare.mpesa_android_sdk";

    Button pay;
    ProgressDialog dialog;
    EditText phone;
    ImageView productimage;
TextView shipping_product_name,shipping_product_price,shipping_product_desc,shipping_email,shipping_product_location;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private String selectedproduct_name,selectedproduct_price,selectedproduct_picurl,selectedproduct_sellersLocation,selectedproduct_desc,sellersEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedproduct_name=getIntent().getStringExtra("selectedproduct_name");
        selectedproduct_price=getIntent().getStringExtra("selectedproduct_price");
        selectedproduct_desc=getIntent().getStringExtra("selectedproduct_desc");
        selectedproduct_picurl=getIntent().getStringExtra("selectedproduct_picurl");
        selectedproduct_sellersLocation=getIntent().getStringExtra("selectedproduct_sellersloc");
        sellersEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_mpesa);
        //initializing
        shipping_email=(TextView) findViewById(R.id.shipping_emailail);
        shipping_product_name=(TextView) findViewById(R.id.shipping_productname);
        shipping_product_price=(TextView) findViewById(R.id.shipping_productprice);
        shipping_product_desc=(TextView) findViewById(R.id.shipping_productdesc);
        shipping_product_location=(TextView) findViewById(R.id.shipping_location);
        productimage=(ImageView) findViewById(R.id.shippingImageview);
        //setting
        shipping_product_price.setText(selectedproduct_price);
        shipping_product_location.setText(selectedproduct_sellersLocation);
        shipping_product_desc.setText(selectedproduct_desc);
        shipping_email.setText(sellersEmail);
        shipping_product_name.setText(selectedproduct_name);
        Glide.with(MainActivity.this).load(selectedproduct_picurl).into(productimage);

        pay = (Button)findViewById(R.id.pay);
        phone = (EditText)findViewById(R.id.phone);

        Mpesa.with(this, CONSUMER_KEY, CONSUMER_SECRET);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Processing");
        dialog.setIndeterminate(true);

        pay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = phone.getText().toString();
                int a = Integer.valueOf(selectedproduct_price);
                if (p.isEmpty()){
                    phone.setError("Enter phone.");
                    return;
                }
                pay(p, a);
            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(NOTIFICATION)) {
                    String title = intent.getStringExtra("title");
                    String message = intent.getStringExtra("message");
                    int code = intent.getIntExtra("code", 0);
                    showDialog(title, message, code);

                }
            }
        };
    }

    @Override
    public void onAuthError(Pair<Integer, String> result) {
        Log.e("Error", result.message);
    }

    @Override
    public void onAuthSuccess() {

        //TODO make payment
        pay.setEnabled(true);
    }
    private void pay(String phone, int amount){
        dialog.show();
        STKPush.Builder builder = new Builder(BUSINESS_SHORT_CODE, PASSKEY, amount,BUSINESS_SHORT_CODE, phone);

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
        String token = sharedPreferences.getString("InstanceID", "");

        builder.setFirebaseRegID(token);
        STKPush push = builder.build();



        Mpesa.getInstance().pay(this, push);

    }
    private void showDialog(String title, String message, int code){
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title(title)
                .titleGravity(GravityEnum.CENTER)
                .customView(R.layout.success_dialog, true)
                .positiveText("OK")
                .cancelable(false)
                .widgetColorRes(R.color.colorPrimary)
                .callback(new ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
        View view=dialog.getCustomView();
        TextView messageText = (TextView)view.findViewById(R.id.message);
        ImageView imageView = (ImageView)view.findViewById(R.id.success);
        if (code != 0){
            imageView.setVisibility(View.GONE);
        }
        messageText.setText(message);
        dialog.show();
    }

    @Override
    public void onMpesaError(Pair<Integer, String> result) {
        dialog.hide();
        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMpesaSuccess(String MerchantRequestID, String CheckoutRequestID, String CustomerMessage) {
        dialog.hide();
        Toast.makeText(this, CustomerMessage, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(NOTIFICATION));

    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    public void queryproduct(){

    }
}
