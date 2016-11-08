package com.duongtung.musicbox;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.duongtung.musicbox.fragment.FragmentListMusic;
import com.duongtung.musicbox.fragment.FragmentListVideo;
import com.duongtung.musicbox.utils.CommonUtils;
import com.duongtung.musicbox.utils.SharePreferenceUtils;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSIONALL = 1;
    private String gmail, name;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private ImageView avatar;
    private final int PICK_IMAGE = 1;
    private final int OVERLAY_REQUEST = 2;
    private final String[] permission = new String[]{Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    FragmentManager fm;
    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasPermissions(this, permission)) {
            ActivityCompat.requestPermissions(this, permission, PERMISSIONALL);
        }
        Pattern gmailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(this).getAccounts();
        for (Account account : accounts) {
            if (gmailPattern.matcher(account.name).matches()) {
                gmail = account.name;
                name = gmail.substring(0, gmail.lastIndexOf("@"));
                break;
            }
        }
        setContentView(R.layout.activity_main);
        addSupportToolBar();
        if (savedInstanceState == null) {
            nvDrawer.getMenu().performIdentifierAction(R.id.list_music, 0);
        }

        requestSystemAlertPermission(this, null, OVERLAY_REQUEST);
    }

    private void addSupportToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                ((TextView) nvDrawer.findViewById(R.id.name)).setText(name);
                ((TextView) nvDrawer.findViewById(R.id.gmail)).setText(gmail);
                String path = SharePreferenceUtils.getSharedPreferences(CommonUtils.IMAGE_AVA);
                avatar = (ImageView) nvDrawer.findViewById(R.id.avatar);
                if (TextUtils.isEmpty(path)) {
                    //TODO
                } else {
                    decodeUri(Uri.parse(path));
                }
                avatar.setOnClickListener((View v) -> {
                    openGallery();
                });
            }
        };
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        nvDrawer.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        setCheckedMenuItem(nvDrawer.getMenu(), item.getItemId());
        switch (item.getItemId()) {
            case R.id.my_music:
                break;
            case R.id.favorite_music:
                break;
            case R.id.list_music:
                fragment = FragmentListMusic.getInstance();
                break;
            case R.id.list_video:
                fragment = FragmentListVideo.getInstance();
                break;
            case R.id.my_video:
                break;
            case R.id.favorite_video:
                break;
            default:
                fragment = FragmentListMusic.getInstance();
                break;
        }
        if (fragment == null) {
            fragment = FragmentListMusic.getInstance();
        }
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.flContent, fragment);
        ft.commit();
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            SharePreferenceUtils.setSharedPreferences(CommonUtils.IMAGE_AVA, data.getData().toString());
            decodeUri(data.getData());
        }
        if (requestCode == OVERLAY_REQUEST) {
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void decodeUri(Uri uri) {
        ParcelFileDescriptor parcelFD = null;
        try {
            parcelFD = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor imageSource = parcelFD.getFileDescriptor();
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(imageSource, null, o);
            final int REQUIRED_SIZE = 1024;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            circlBitmap(scaleDown(bitmap, metrics.DENSITY_280, true));
        } catch (FileNotFoundException e) {
            // handle errors
        } catch (IOException e) {
            // handle errors
        } finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                }
        }
    }

    private Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                             boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        if (newBitmap.getWidth() >= newBitmap.getHeight()) {
            newBitmap = Bitmap.createBitmap(
                    newBitmap,
                    newBitmap.getWidth() / 2 - newBitmap.getHeight() / 2,
                    0,
                    newBitmap.getHeight(),
                    newBitmap.getHeight()
            );

        } else {
            newBitmap = Bitmap.createBitmap(
                    newBitmap,
                    0,
                    newBitmap.getHeight() / 2 - newBitmap.getWidth() / 2,
                    newBitmap.getWidth(),
                    newBitmap.getWidth()
            );
        }
        return newBitmap;
    }

    private void circlBitmap(Bitmap bitmap) {
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        avatar.setImageBitmap(circleBitmap);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setCheckedMenuItem(Menu menu, int idChecked) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getSubMenu() != null && menu.getItem(i).getSubMenu().size() > 0) {
                Menu submenu = menu.getItem(i).getSubMenu();
                for (int j = 0; j < submenu.size(); j++) {
                    if (submenu.getItem(j).getItemId() == idChecked) {
                        submenu.getItem(j).setChecked(true);
                    } else {
                        submenu.getItem(j).setChecked(false);
                    }
                }
            }
        }
    }

    public boolean isServiceRunning(@NonNull Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void requestSystemAlertPermission(Activity context, Fragment fragment, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        final String packageName = context == null ? fragment.getActivity().getPackageName() : context.getPackageName();
        final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + packageName));
        if (fragment != null && !Settings.canDrawOverlays(MainActivity.this))
            fragment.startActivityForResult(intent, requestCode);
        else if (!Settings.canDrawOverlays(MainActivity.this)) {
            context.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onResume() {
        fragment.onResume();
        super.onResume();
    }
}
