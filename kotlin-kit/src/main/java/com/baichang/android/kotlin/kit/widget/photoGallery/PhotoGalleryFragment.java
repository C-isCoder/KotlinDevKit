package com.baichang.android.kotlin.kit.widget.photoGallery;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.baichang.android.kotlin.kit.R;
import com.baichang.android.kotlin.kit.config.Configuration;
import com.baichang.android.kotlin.kit.widget.photoView.PhotoView;
import com.baichang.android.kotlin.kit.widget.photoView.PhotoViewAttacher;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoGalleryFragment extends Fragment
    implements PhotoViewAttacher.OnViewTapListener, OnLongClickListener {

    private static final String PHOTO_PARAM = "photo_param";
    private static final String PHOTO_IS_FULL_PATH = "photo_is_full_path";
    private PhotoView mPhoto;
    private String imageUrl;
    private boolean isFullPath; // 是否是全路径，本地 or url

    public PhotoGalleryFragment() {
    }

    public static PhotoGalleryFragment newInstance(String param, boolean isFullPath) {
        PhotoGalleryFragment fragment = new PhotoGalleryFragment();
        Bundle args = new Bundle();
        args.putString(PHOTO_PARAM, param);
        args.putBoolean(PHOTO_IS_FULL_PATH, isFullPath);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageUrl = getArguments().getString(PHOTO_PARAM);
            isFullPath = getArguments().getBoolean(PHOTO_IS_FULL_PATH);
        }
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return createView(inflater, container);
    }

    private View createView(LayoutInflater inflater, ViewGroup container) {
        View view =
            inflater.inflate(R.layout.fragment_photo_gallery_image_banner, container, false);
        mPhoto = view.findViewById(R.id.fragment_photo_gallery_image_banner_image);
        mPhoto.setOnViewTapListener(this);
        mPhoto.setOnLongClickListener(this);
        Glide.with(getActivity())
            .asBitmap()
            .load(
                isFullPath ? imageUrl : Configuration.Companion.getApiLoadImage() + imageUrl)
            .into(new SimpleTarget<Bitmap>() {
                @Override public void onResourceReady(@NonNull Bitmap resource,
                    @Nullable Transition<? super Bitmap> transition) {
                    mPhoto.setImageBitmap(resource);
                }
            });
        return view;
    }

    @Override public void onViewTap(View view, float x, float y) {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override public boolean onLongClick(final View v) {
        Context context = getContext();
        if (context != null) {
            new Builder(context).setMessage("保存图片")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        BitmapDrawable drawable = (BitmapDrawable) ((ImageView) v).getDrawable();
                        if (drawable != null) {
                            saveBitmap(drawable.getBitmap());
                        }
                    }
                })
                .setNegativeButton("取消", null)
                .create()
                .show();
        }
        return true;
    }

    private void saveBitmap(Bitmap bitmap) {
        Context context = getContext();
        if (context != null) {
            String path = saveImageToGallery(context, bitmap);
            if (TextUtils.isEmpty(path)) {
                Toast.makeText(getActivity(), "保存失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "保存成功,请到相册查看 " + path, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 保存图片到指定目录，并且更新图库
     *
     * @param context 上下文
     * @param bmp 要保存的Bitmap
     * @return 文件路径
     */
    private String saveImageToGallery(Context context, Bitmap bmp) {
        PackageManager manager = context.getPackageManager();
        ApplicationInfo info = null;
        try {
            info = manager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String appName = (String) manager.getApplicationLabel(info);
        // 首先保存图片
        File appDir = context.getExternalFilesDir(appName);
        if (appDir != null && !appDir.exists()) {
            boolean b = appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                file.getAbsolutePath(),
                fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        String path = file.getAbsolutePath();
        context.sendBroadcast(
            new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        return path;
    }
}
