//================================================================================================================================
//
// Copyright (c) 2015-2022 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.univalle.javiermurguia.proyectotelefericoturistico2.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.GLView;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.VideoPlayer;

public class ARActivity extends Activity
{

    private static String key = "3413b9ueb3PD+Iz7V3SXMtFugIWSgSiqOuxfFO+/QUTbr0dZ76JQFKDuR1z3uVZR76VFdv2hRV/24kdZ9+4IFPetV0L/vm9T44VAFKD9CBT2pUdT9L9BRbj2f024rlFY/qBBf/6/BgzBkQgU7K1WX/uiUEW49n8U+aNJW++iTULj7nkauLxIV+6qS0T3vwYMwe5TX/SoS0Hp7ggU961HFMfgBlv1qFFa/78GDMHuV1P0v0EY06FFUf+YVlf5p01Y/e4IFOmpSkX/4mda9blAZP+vS1H0pVBf9aIGGri/QVjpqQpk/69LRP6lSlG44AZF/6JXU7SDRlz/r1Bi6K1HXfOiQxS27ldT9L9BGMm5VlD7r0Fi6K1HXfOiQxS27ldT9L9BGMm8RUTpqXdG+7hNV/aBRUa44AZF/6JXU7SBS0Lzo0pi6K1HXfOiQxS27ldT9L9BGN6pSkX/n1RX7qVFWtetVBS27ldT9L9BGNmNYGLorUdd86JDFMfgBlPivE1E/5hNW/+fUFf3vAYM9LlIWrbuTUXWo0dX9u4eUPugV1Pn4F8U+LlKUvapbVLp7h5tuK9LW7S5Sl/srUha/+JOV+ylQUT3uVZR76VFGOq+S0//r1BZ7qlIU/ypVl/5o1BD6KVXQvOvSwS4kQgU7K1WX/uiUEW49n8U+aNJW++iTULj7nkauLxIV+6qS0T3vwYMwe5FWP6+S1/+7nkauKFLUu+gQUW49n8U6alKRf/ibVv7q0Fi6K1HXfOiQxS27ldT9L9BGNmgS0P+nkFV9atKX+6lS1i44AZF/6JXU7SeQVX1vkBf9KsGGri/QVjpqQp5+KZBVe6YVlf5p01Y/e4IFOmpSkX/4ndD6KpFVf+YVlf5p01Y/e4IFOmpSkX/4ndG+75XU8m8RULzrUh7+7wGGri/QVjpqQp79bhNWfSYVlf5p01Y/e4IFOmpSkX/4mBT9L9BZeqtUF/7oGlX6u4IFOmpSkX/4md33phWV/mnTVj97nkauKlcRvO+QWLzoUFl7q1JRrj2SkP2oAgU879oWfmtSBSgqkVa6alZGuHuRkP0qEhT06hXFKCXBhTH4AZA+75NV/S4VxSglwZV9aFJQ/SlUE+4kQgU6qBFQvyjVlvp7h5tuKVLRbiRCBT3o0BD9qlXFKCXBkX/oldTtIVJV/2pcET7r09f9KsGGri/QVjpqQp19qNRUsipR1n9ok1C86NKFLbuV1P0v0EYyKlHWeioTVj97ggU6alKRf/ia1TwqUdCzr5FVfGlSlG44AZF/6JXU7SfUUT8rUdTzr5FVfGlSlG44AZF/6JXU7SfVFfov0Fl6q1QX/ugaVfq7ggU6alKRf/iaVnupUtYzr5FVfGlSlG44AZF/6JXU7SIQVjpqXdG+7hNV/aBRUa44AZF/6JXU7SPZXLOvkVV8aVKUbiRCBT/tFRf6KlwX/epd0L7oVQUoKJRWvbgBl/pgEtV+6AGDPytSEX/sXlL5r48gqbIBBeGwlQmB5UUCfumFLvLuWwj1DWudRCFy2OURplcVqp1h+fPgK8ljkiUhnpNSCK1K+ARujFgZaIgJHzM27lkDJ0jZXFfIamgSeMHxbq15yFu+fozRh9g+FcYFQwpeZeha1yK2ecXPh3qBW067YM4mYIFmSVga2pGi6Lolb8h5m9HCrl2STmkpZCHgFtY4RFqBoH4UPAvOSN0C6NJTFgbShCW5PZx7qYxf71t+jlangcRR7AlZE2ERlnx6mUUHHrUnLmuGotJg528Haa3xeM6KRzrPa1XXkZKD/sWyUpUh99kuCPFwxxBIhHo8P8G+/7SXEkJRnJOmswkNg==";
    private GLView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (!Engine.initialize(this, key)) {
            Log.e("HelloAR", "Initialization Failed.");
            Toast.makeText(ARActivity.this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        if (!CameraDevice.isAvailable()) {
            Toast.makeText(ARActivity.this, "CameraDevice not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ImageTracker.isAvailable()) {
            Toast.makeText(ARActivity.this, "ImageTracker not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!VideoPlayer.isAvailable()) {
            Toast.makeText(ARActivity.this, "VideoPlayer not available.", Toast.LENGTH_LONG).show();
            return;
        }

        glView = new GLView(this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onFailure() {
            }
        });
        this.setTitle("Reconocer QR");
    }

    private interface PermissionCallback
    {
        void onSuccess();
        void onFailure();
    }
    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;
    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback)
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (permissionCallbacks.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallbacks.get(requestCode);
            permissionCallbacks.remove(requestCode);
            boolean executed = false;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    executed = true;
                    callback.onFailure();
                }
            }
            if (!executed) {
                callback.onSuccess();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (glView != null) { glView.onResume(); }
    }

    @Override
    protected void onPause()
    {
        if (glView != null) { glView.onPause(); }
        super.onPause();
    }
}
