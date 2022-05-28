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

import com.univalle.javiermurguia.proyectotelefericoturistico2.Models.GLView;
import com.univalle.javiermurguia.proyectotelefericoturistico2.R;

import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.VideoPlayer;

public class ARActivity extends Activity
{

    private static String key = "dEbIJnBV0DpoM4wRBF7Jg3bkAUKUeLLbDUTgXUR0/g1wZPgQRGnvXQsl+BVccukYRG76P1Zq+hZdKfgQXCW3XVxm6AtUddAaSE7/XQs2t11dbvgaX3T+DBM9wAQTZe4RVWv+NlV0uUVqWrddR2bpFlBp7wwTPcBdUmj2EkRp8gtIJcZTE3f3HkVh9A1cdLlFaiXsFl9j9AhCJbddXGb4XWwruRJeY+4TVHS5RWol6BpfdP5ReGr6GFRT6R5SbPIRViW3XUJi9QxUKdgTXnL/LVRk9Bhfbu8WXmm5UxN0/hFCYrUtVGT0DVVu9RgTK7kMVGnoGh9I+RVUZO8rQ2b4FFhp/F0dJegaX3T+UWJy6RlQZP4rQ2b4FFhp/F0dJegaX3T+UWJ3+g1CYsgPUHPyHl1K+g8TK7kMVGnoGh9K9AtYaPUrQ2b4FFhp/F0dJegaX3T+UXVi9QxUVOseRW76E3xm610dJegaX3T+UXJG3ytDZvgUWGn8XWwruRpJd/INVFPyElRU7x5cd7lFX3L3Ex0l8gx9aPgeXSWhGVBr6BpMK+BdU3L1G11i0htCJaEkE2T0Eh9y9RZHZvcTVCnxHkdu/g1ccukYRG76UUF19AZUZO8QRWL3GVR18hxec+4NWHTvFlJouSIdJe0eQ276EUV0uUVqJfgQXGruEVhz4l1sK7kPXWbvGV519gwTPcBdUGn/DV5u/11sK7kSXmPuE1R0uUVqJegaX3T+UXhq+hhUU+keUmzyEVYlt11CYvUMVCnYE15y/y1UZPQYX27vFl5puVMTdP4RQmK1LVRk9A1VbvUYEyu5DFRp6BofSPkVVGTvK0Nm+BRYafxdHSXoGl90/lFicukZUGT+K0Nm+BRYafxdHSXoGl90/lFid/oNQmLID1Bz8h5dSvoPEyu5DFRp6BofSvQLWGj1K0Nm+BRYafxdHSXoGl90/lF1YvUMVFTrHkVu+hN8ZutdHSXoGl90/lFyRt8rQ2b4FFhp/F1sK7kaSXfyDVRT8hJUVO8eXHe5RV9y9xMdJfIMfWj4Hl0loRlQa+gaTCvgXVNy9RtdYtIbQiWhJBMlxlMTcfoNWGb1C0IloSQTZPQSXHL1FkV+uSIdJesTUHP9EENq6F0LXLkWXnS5Ih0l9hBVcvcaQiWhJBN0/hFCYrU2XGb8GmV1+hxabvUYEyu5DFRp6BofRPcQRGPJGlJo/BFYc/IQXyW3XUJi9QxUKckaUmjpG1hp/F0dJegaX3T+UX5l8RpSc88NUGTwFl9guVMTdP4RQmK1LER1/R5SYs8NUGTwFl9guVMTdP4RQmK1LEFm6QxUVOseRW76E3xm610dJegaX3T+UXxo7xZeac8NUGTwFl9guVMTdP4RQmK1O1Rp6Bpid/oLWGb3MlB3uVMTdP4RQmK1PHBDzw1QZPAWX2C5Ih0l/gdBbukaZW72GmJz+hJBJaERRGv3UxNu6DNeZPoTEz39Hl10/gJseqGQcH/AzR/maTBLUFbjR3IYne7H+JW3m9A9Dvk6CFBE7eUotu/vlJ+ggOjEHuwKt8TqeeBYF/pdptKjK8uakUrPNTbSqMQKe+HVP0XTk/aJ/+AfmLV0ie4jcbZZPKBxKyKgd+IKGlbCROPlQw/c9r+hanpIv4vByPOiTMzkWk2wW2a8eD3z6wLm1jBPQWqGegFf+HbSzgJEMmbXsFaUwJFueY04EHxgzdg8zTEF++z3lBCs+ElgCqdxfLlPo8qcqaJJRr/D16s4uZy7hAoYhnF+YAmnITi4AMB4SnSTCAhqe4nfFtSLK0h1WKvCfQciZl8cx9RX+vIb8FW6ozEHm38=";
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
