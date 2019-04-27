//ĐẠT: CẤP QUYỀN TRUY CẬP ỨNG DỤNG Ở LẦN CHẠY ĐẦU TIÊN
//link tham khảo nếu ko hiểu code: https://developer.android.com/training/permissions/requesting.html
package com.example.quang.studenthousing;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.Manifest;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PermissionActivity extends Activity
{
    //ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE là một giá trị int dùng để định danh mỗi khi yêu cầu cấp quyền.
    // Khi nhận được kết quả, hàm callback sẽ trả về cùng ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE này để ta có thể xác định và xử lý kết quả.
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE= 5469;

    //chạy hàm này đầu tiên
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //kiểm tra phiên bản sdk
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //yêu cầu người dùng chấp nhận cho phép đọc và ghi dữ liệu trong bộ nhớ thiết bị
            requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },0);

            //kiểm tra xem ứng dụng đã dc cấp quyền chưa
            checkPermission();
        }
    }

    //kết quả sau khi được cấp quyền, nếu người dùng cho phép ứng dụng truy cập bộ nhớ thì chạy màn hình giao diện khởi động
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Intent intent = new Intent(this,SplashActivity.class);
        startActivity(intent);
        //trả về dữ liệu
        finish();
    }

    //kết quả sau khi thực hiện permission activity,
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Kiểm tra requestCode có trùng với ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE vừa dùng
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            //nếu user ko cho ứng dụng vẽ lên trên ứng dụng khác thì chạy hàm yêu cầu cấp quyền
            if (!Settings.canDrawOverlays(this)) {
                checkPermission();
            }
        }
    }

    //hàm yêu cầu cấp quyền
    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }
}