package client.solder.com.qrcode;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import client.solder.com.qrcodelibrary.QRCodeUtil;

/**
 * Created by snail on 2018/8/22.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int TAKE_ALBUM = 1;
    private TextView tv_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button album = (Button) findViewById(R.id.album);
        tv_code = (TextView) findViewById(R.id.tv_code);
        album.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.album:
                Intent albumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                albumIntent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(albumIntent, TAKE_ALBUM);

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_ALBUM && resultCode == RESULT_OK) {
            if (data == null) {
                return;
            }
            ContentResolver resolver = getContentResolver();
            // 照片的原始资源地址
            Uri originalUri = data.getData();
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 开始对图像资源解码
            String result = QRCodeUtil.decodeFromPhoto(photo);
            if (TextUtils.isEmpty(result)) {
                Toast.makeText(getApplicationContext(), "请选择带有二维码信息的图片！", Toast.LENGTH_SHORT).show();
                tv_code.setText("请选择带有二维码信息的图片！");
                return;
            }
            tv_code.setText(result);
        }
    }
}
