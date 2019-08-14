package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.cheers.okhttplibrary.bean.BaseResult;
import com.cheers.okhttplibrary.net.network.RequestCallback;
import com.cheers.okhttplibrary.utils.JSONUtils;
import com.cheers.okhttplibrary.utils.LogUtils;
import com.cheers.okhttplibrary.utils.cache.CacheUtils;
import com.example.myapplication.net.ParamApi;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button id_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_but = findViewById(R.id.id_but);
        id_but.setOnClickListener(this);

        Button id_cun = findViewById(R.id.id_cun);
        id_cun.setOnClickListener(this);

        Button id_get = findViewById(R.id.id_get);
        id_get.setOnClickListener(this);

        Button id_pic_upload = findViewById(R.id.id_pic_upload);
        id_pic_upload.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_but:
                ParamApi.requestBaidu().execute(new RequestCallback<BaseResult>() {
                    @Override
                    public void onRequestSuccess(BaseResult dataResult, String result) {
                        System.out.println("------------->onRequestSuccess"+result);
                    }

                    @Override
                    public void onRequestFailure(String response, String errorMsg) {
                        System.out.println("------------->onRequestFailure"+response);
                    }
                });
                break;
            case R.id.id_cun:
//                CacheUtilsObject.getObjectCache().add("login","asdfsalfjsaklfjsaldfjslafjsldjfsldkfjskladfjskldfjskldfj");
                CacheUtils.getInstance().putString("login","asdfsalfjsaklfjsaldfjslafjsldjfsldkfjskladfjskldfjskldfj");
                break;
            case R.id.id_get:
//                String login = (String) CacheUtilsObject.getObjectCache().get("login");
                String login = CacheUtils.getInstance().getString("login");
                LogUtils.logW(login);
                break;
            case R.id.id_pic_upload:
                uploadAccountPhoto("",new File("/adfsfsf/adfsaf/asdfsa/pic.jpg"));
                break;
        }

    }

    /**
     * 上次图片方法
     * @param filePath
     * @param file
     */
    private void uploadAccountPhoto(String filePath, File file){
        ParamApi.requestFileuploads(filePath).uploadexecute(new RequestCallback<BaseResult>() {
            @Override
            public void onRequestSuccess(BaseResult dataResult, String result) {

            }

            @Override
            public void onRequestFailure(String response, String errorMsg) {

            }
        },file,"headimage");

    }
}
