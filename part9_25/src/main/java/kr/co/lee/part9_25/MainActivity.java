package kr.co.lee.part9_25;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
/**
 * Created by kkang
 * 깡샘의 안드로이드 프로그래밍 - 루비페이퍼
 * 위의 교재에 담겨져 있는 코드로 설명 및 활용 방법은 교제를 확인해 주세요.
 */
public class MainActivity extends AppCompatActivity {
    TextView titleView;
    TextView dateView;
    TextView contentView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleView=findViewById(R.id.lab1_title);
        dateView=findViewById(R.id.lab1_date);
        contentView=findViewById(R.id.lab1_content);
        imageView=findViewById(R.id.lab1_image);


        HashMap<String, String> map=new HashMap<>();
        map.put("name","kkang");

        HttpRequester httpRequester=new HttpRequester();
        httpRequester.request("http://~~~~:8000/files/test.json", map, httpCallback);
        

    }

    HttpCallback httpCallback=new HttpCallback() {
        @Override
        public void onResult(String result) {

            try{
                JSONObject root=new JSONObject(result);
                titleView.setText(root.getString("title"));
                dateView.setText(root.getString("date"));
                contentView.setText(root.getString("content"));

                String imageFile=root.getString("file");
                if(imageFile!=null && !imageFile.equals("")){
                    HttpImageRequester imageRequester=new HttpImageRequester();
                    imageRequester.request("http://~~~~:8000/files/"+imageFile, null, imageCallback);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    };

    HttpImageCallback imageCallback=new HttpImageCallback() {
        @Override
        public void onResult(Bitmap d) {

            imageView.setImageBitmap(d);
            
        }
    };

}
