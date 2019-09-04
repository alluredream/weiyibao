package com.android.test.makestart2;

import android.Manifest;
 import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.test.makestart2.JPush.ExampleUtil;
import com.android.test.makestart2.Utils.HttpsUtil;
import com.android.test.makestart2.android.CaptureActivity;
import com.android.test.makestart2.application.MainApplication;
import com.android.test.makestart2.settingactivity.SystemSetting;
import com.android.test.makestart2.settingactivity.fabAvtivity;
import com.android.test.makestart2.settingactivity.linechartActivity;
import com.bumptech.glide.Glide;
import android.net.*;


import org.json.JSONException;

import q.rorbin.badgeview.QBadgeView;

import static com.android.test.makestart2.R.id.nav_camera;

/**
 * 主页面
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private long downloadId;
    DownloadManager downloadManager;
    private SharedPreferences sp;
    private static final int REQUEST_CODE_SCAN = 0x0000;
    private ImageView btn_scan;
    private TextView textView;
    private  MenuItem menuItem=null;
    private TextView tv_nav_user;
    private ImageView bac;
    private Menu menu;
    private View headerLayout;
    private ImageView headimageView;
    private GridLayout gridLayout;
    public static boolean isForeground = false;

      @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
          sp = this.getSharedPreferences("version", Context.MODE_PRIVATE);

         //实现网络监测
         findViewById(R.id.make_09).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 getNetInfor();
             }
         });
          //申明一个权限
          if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
              ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
          }
         btn_scan = (ImageView) findViewById(R.id.btn_scan);
         btn_scan.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                 // startActivity(intent);
                 startActivityForResult(intent, REQUEST_CODE_SCAN);
             }
         });
         //强制横屏
         setRequestedOrientation(ActivityInfo
                 .SCREEN_ORIENTATION_LANDSCAPE);

          registerMessageReceiver();  // used for receive msg

         //打开系统设置
         opensetting();
         //打开统计分析页面
         openlinechart();
         //打开搜索页面
         opensearch();
         //打开公告页面
         opennotask();
         //打开待办事项页面
         opennotices();
         //实现更新
       changeupdate();
         //主页右下角显示信息
         FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
//                 Snackbar.make(view, "提示信息", Snackbar.LENGTH_LONG)
//                         .setAction("Action", null).show();
                 Intent intent = new Intent(MainActivity.this,fabAvtivity.class);
                 startActivity(intent);

             }
         });
         //左侧状态栏
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                 this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
         drawer.addDrawerListener(toggle);
         toggle.syncState();

         //主页右上角设置
         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         navigationView.setNavigationItemSelectedListener(this);

          navigationView.setNavigationItemSelectedListener(this);
         //添加角标
         addbadge();
         //登录名显示
          test11();
          //设置高度
         sethight();
     }
    /**
     * 显示侧边栏信息
     */
    public void test11(){
         NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
         String response = MainApplication.getInstance().stringHashMap.get("i");
         JSONObject jsonObject1 = JSON.parseObject(response);
         String str1 = jsonObject1.getString("resultData");
         JSONObject jsonObject2 = JSON.parseObject(str1);
         String str2 = jsonObject2.getString("fileDataBean");
         JSONObject jsonObject3 = JSON.parseObject(str2);
         String str3 = jsonObject3.getString("fileUrl");
         String str4 = jsonObject2.getString("user");
         JSONObject jsonObject4 = JSON.parseObject(str4);
         String strUser = jsonObject4.getString("createUser");
         String strDate = jsonObject4.getString("updateDate");
         String strSex = jsonObject4.getString("sex");
         if(strSex.equals("1")){
             strSex="男";
         }else{
             strSex="女";
          }
        //显示内容
         headerLayout  = navigationView.inflateHeaderView(R.layout.nav_header_main);
         tv_nav_user = (TextView)headerLayout.findViewById(R.id.userName);
         headimageView = (ImageView) headerLayout.findViewById(R.id.imageView);

          tv_nav_user.setText(strUser);
         navigationView.getMenu().getItem(0).setTitle(strSex);
         navigationView.getMenu().getItem(1).setTitle(strDate);
        try {
            Uri headUrl = Uri.parse(str3);
            Glide.with(MainActivity.this).load(headUrl).into(headimageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
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
            Intent intent = new Intent(MainActivity.this,SystemSetting.class);
            startActivity(intent);
        }
        if(id==R.id.action_scan){
            Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == nav_camera) {


        } else if (id == R.id.nav_gallery) {


        } else if (id == R.id.nav_exit) {
//            Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
//            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(logoutIntent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /**
     * 跳转到扫码界面扫码
     */
    private void goScan(){
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
         startActivityForResult(intent, REQUEST_CODE_SCAN);
        //startActivity(intent);
    }

    /**
     * 实现网络监测
     */
    public void getNetInfor() {
        //首先是获取网络连接管理者
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        //网络状态存在并且是已连接状态
        if (info != null && info.isConnected()) {
            Toast.makeText(MainActivity.this, "网络已连接", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            //如果没有网络，弹出提示框，并可跳转到设置页面
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("请检查网络连接")
                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (android.os.Build.VERSION.SDK_INT > 10) {
                                //安卓系统3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
                                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                            } else {
                                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }
                    })
                    .show();
        }
    }

    /**
     * 询问相机权限
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //goScan();
//                } else {
//                    Toast.makeText(this, "你拒绝了权限申请，可能无法打开相机扫码哟！", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//        }
//    }

    /**
     * 打开系统设置
     */
    public void opensetting(){
        findViewById(R.id.systemtsetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SystemSetting.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 打开搜索页面
     */
    public void opensearch(){
        findViewById(R.id.opensearch).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 打开待办事项
     */
    public void opennotices(){
        findViewById(R.id.opennotices).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,noticesActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 打开公告页面
     */
    public void opennotask(){
        findViewById(R.id.opentask).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,taskInfoActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 打开统计分析页面
     */
    public void openlinechart(){
        findViewById(R.id.linechart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,linechartActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * 检查是否需要更新
     */
        public void changeupdate(){
            findViewById(R.id.changeupdate).setOnClickListener(new View.OnClickListener() {
                 @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            String result = HttpsUtil.testUrl("http://221.193.236.253:8888/wyb/version/getNewVersion.jhtml");
                             if (result == null){
                                return;
                            }
                             try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(result);
                                String url = jsonObject.optString("fileUrl");
                                String version = jsonObject.optString("verNo");
                                String oldverstr =  sp.getString("oldversion","");
                                 //对比
                                int newver= Integer.parseInt(version.replaceAll("\\.",""));
                                int oldver= Integer.parseInt(oldverstr.replaceAll("\\.",""));
                                if(newver>oldver){
                                    Toast.makeText(MainActivity.this,"检测到新版本，开始下载....",Toast.LENGTH_SHORT).show();
                                    Uri uri = Uri.parse(url);
                                    DownloadManager.Request request = new DownloadManager.Request(uri);
                                    request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "astbbs"+ version + ".apk");
                                    //设置Notification的标题和描述
                                    request.setTitle("版本更新");
                                    request.setDescription(version);
                                    //设置Notification的显示，和隐藏。
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                    downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                                    downloadId = downloadManager.enqueue(request);
                                    //  Log.i(MainActivity.class.getSimpleName(), "" + downloadId);
                                    getApplicationContext().registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                                }else{
                                    Toast.makeText(MainActivity.this,"已是最新版本！",Toast.LENGTH_SHORT).show();
                                }
                               Looper.loop();// 进入loop中的循环，查看消息队列
                             } catch (JSONException e) {
                                Log.i(MainActivity.class.getSimpleName(), e.getMessage(), e);
                            }
                        }

                        private BroadcastReceiver receiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                checkStatus();
                            }
                        };
                    }){
                    }.start();
                }
            });
        }
    /**
     * 查看下载状态
     */
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()){
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status){
                case DownloadManager.STATUS_SUCCESSFUL:
                   // installAPK();
                    Toast.makeText(getApplicationContext(), "下载完成！", Toast.LENGTH_SHORT).show();
                    break;
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(getApplicationContext(), "下载失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
    /**
     * 安装
     */
    private void installAPK() {
        Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadId);
        if (downloadFileUri != null) {
            Intent intent= new Intent(Intent.ACTION_VIEW);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
                // 由于没有在Activity环境下启动Activity,设置下面的标签
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    /**
     * 添加角标数据
     */
    public void addbadge(){
        ImageView opennoticesimg = (ImageView)findViewById(R.id.opennotices);
        ImageView opentask = (ImageView) findViewById(R.id.opentask);

        String info = MainApplication.getInstance().stringHashMap.get("fornumber");
        JSONObject jsonObject = JSONObject.parseObject(info);
        String str1 = jsonObject.getString("resultData");
        JSONObject jsonObject2 = JSON.parseObject(str1);
        String str2 = jsonObject2.getString("task");
        JSONObject jsonObject3 = JSON.parseObject(str2);
        String str3 = jsonObject3.getString("count");
        int number = Integer.parseInt(str3);
        // new QBadgeView(MainActivity.this).bindTarget(opennoticesimg).setBadgeNumber(5);
        new QBadgeView(MainActivity.this).bindTarget(opentask).setBadgeNumber(number);
    }

    /**
     * 设置高度
     */
    public void sethight(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;
        int a = screenWidth/2;
        gridLayout = (GridLayout)findViewById(R.id.testgridLayout);
        gridLayout.setTop(a);
    }
    //重写home键功能
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String title = intent.getStringExtra(KEY_TITLE);
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_TITLE + " : " + title + "\n");
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    //setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

//    private void setCostomMsg(String msg){
//        if (null != msgText) {
//            msgText.setText(msg);
//            msgText.setVisibility(android.view.View.VISIBLE);
//        }
//    }
}
