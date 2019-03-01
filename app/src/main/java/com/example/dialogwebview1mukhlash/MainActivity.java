package com.example.dialogwebview1mukhlash;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    WebView wv;
    EditText edt;
    Button btn;
    LayoutInflater inflater;
    ProgressBar pb;
    private View dialogView;
    AlertDialog.Builder dialog;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fab = findViewById(R.id.fab);
        wv = findViewById(R.id.wv_main);
        edt = findViewById(R.id.edt_input);
        btn = findViewById(R.id.btn_search);
        pb = findViewById(R.id.pb_main);

        pb.setIndeterminate(true);

//        mengatur Client dan membuat inner class nya
        wv.setWebViewClient(new myWebClient());
//        untuk mengatur Javascript nya = true
        wv.getSettings().setJavaScriptEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogShow();
            }
        });

    }

    private void dialogShow() {
        dialog = new AlertDialog.Builder(this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.dialog_layout, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(android.R.mipmap.sym_def_app_icon);
        dialog.setTitle("Login");

        final TextInputEditText edtEmail, edtPassword;
        edtEmail = dialogView.findViewById(R.id.edt_email);
        edtPassword = dialogView.findViewById(R.id.edt_password);

        final Button btnLogin, btnSignup;
        btnLogin = dialogView.findViewById(R.id.btn_login);
        btnSignup = dialogView.findViewById(R.id.btn_signup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
                    edtEmail.setError("This field can not be Blank");
                } else if (edtPassword.getText().toString().trim().equalsIgnoreCase("")) {
                    edtPassword.setError("This field can not be Blank");
                } else {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

//        Mengatur Alamat web yang akan kita buka sesuai dengan url yang kita masukkan di EditText
    public void Search(View view) {
        wv.loadUrl(edt.getText().toString());
    }

    private class myWebClient extends WebViewClient {
        //        Ketika web selesai di load
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            pb.setVisibility(View.INVISIBLE);
            pb.setIndeterminate(false);
        }
        //    Ketika web dimulai
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
            }
            return super.shouldOverrideUrlLoading(view, request);
        }
    }
}
