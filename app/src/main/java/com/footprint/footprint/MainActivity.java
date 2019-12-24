package com.footprint.footprint;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.footprint.footprint.activity.BodySetActivity;
import com.footprint.footprint.activity.JiLuActivity;
import com.footprint.footprint.base.BaseActivity;
import com.footprint.footprint.base.MultiItemTypeAdapter;
import com.footprint.footprint.base.TheApplication;
import com.footprint.footprint.db.green.DongtaiPhotoShowDao;
import com.footprint.footprint.db.green.DongtaiShowDao;
import com.footprint.footprint.db.green.locationDataDao;
import com.footprint.footprint.dbbean.DongtaiPhotoShow;
import com.footprint.footprint.dbbean.DongtaiShow;
import com.footprint.footprint.dbbean.User;
import com.footprint.footprint.dbbean.locationData;
import com.footprint.footprint.music.MusicActivity;
import com.footprint.footprint.utils.LocationService;
import com.footprint.footprint.view.TitleWidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends BaseActivity {
    private LocationService locationService;
    private String city="昆明";
    private double longitude,latitude;
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private TitleWidget title;
    private locationDataDao locationDao;
    private List<LatLng> latLngs=new ArrayList<>();
   private  Polyline mPolyline;
   private AppCompatButton acbtn_click,actv_search,acbtn_click2;
   private AppCompatEditText search;
    private List<String> fileList=new ArrayList<>();
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private String adress;
    int nowSearchType = -1; // 当前进行的检索，供判断浏览节点时结果使用。
    // 搜索相关
    RoutePlanSearch mSearch1 = null;    // 搜索模块，也可去掉地图模块独立使用
    DrivingRouteResult nowResultdrive = null;
    RouteLine route = null;
   private String uu_id="";
   private DongtaiShowDao dongtaiShowDao;
   private DongtaiPhotoShowDao dongtaiPhotoShowDao;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationService = TheApplication.instance.locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationDao=TheApplication.instance.getDaoSession().getLocationDataDao();
        dongtaiShowDao=TheApplication.instance.getDaoSession().getDongtaiShowDao();
        dongtaiPhotoShowDao=TheApplication.instance.getDaoSession().getDongtaiPhotoShowDao();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inintView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    private void inintView() {


        acbtn_click2=findViewById(R.id.acbtn_click2);

        actv_search=findViewById(R.id.actv_search);
        search=findViewById(R.id.search);
        fileList.add("");
        locationService.start();
        actv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearch.geocode(new GeoCodeOption().city(city).address(search.getText().toString()));
            }
        });
        acbtn_click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DongtaiPhotoShow dongtaiShow=new DongtaiPhotoShow();


                dongtaiShow.setTitle(search.getText().toString().trim());
                String flieName="";
                for (int i = 0; i <fileList.size() ; i++) {
                    flieName=fileList.get(i)+"##";
                }

                dongtaiShow.setBeizhu1(flieName);
                dongtaiPhotoShowDao.insert(dongtaiShow);

            }
        });

        mMapView =findViewById(R.id.mapview);
        title=findViewById(R.id.title);
        acbtn_click=findViewById(R.id.acbtn_click);


        mBaiduMap = mMapView.getMap();
        // 设置是否打开交通图层
        mBaiduMap.setTrafficEnabled(false);
        mBaiduMap.setBaiduHeatMapEnabled(false);
        mBaiduMap.setMyLocationEnabled(false);
        // 设置普通视图  MAP_TYPE_NONE  MAP_TYPE_NORMAL
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setBuildingsEnabled(false);
        mBaiduMap.showMapPoi(true);
        acbtn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationService.isStart()){
                    DongtaiShow dongtaiShow=new DongtaiShow();
                    dongtaiShow.setCode(uu_id);
                    dongtaiShow.setName(User.getUserName(MainActivity.this));
                    dongtaiShow.setStartAdress(adress);
                    dongtaiShow.setEndAdress(endAdress);
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    dongtaiShow.setTime(sdf.format(d));
                    dongtaiShow.setTitle(search.getText().toString().trim());
                    dongtaiShowDao.insert(dongtaiShow);
                    locationService.stop();
                    acbtn_click.setText("Start");
                }else{
                    locationService.start();
                    acbtn_click.setText("Stop");
                }
            }
        });
    }
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                longitude= location.getLatitude();
                latitude= location.getLongitude();
                    LatLng latLng1=new LatLng(longitude,latitude);
                    MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng1);
                    mBaiduMap.setMapStatus(u);
                    // 设置地图的缩放级别
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory
                            .newMapStatus(new MapStatus.Builder().zoom(17).build()));
                    Date d = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    locationData location1=new locationData();
                    location1.setJd(longitude);
                    location1.setWd(latitude);
                    location1.setName(User.getUserName(MainActivity.this));
                    location1.setTime(sdf.format(d));
                    location1.setBeizhu3(uu_id);
                    locationDao.insert(location1);
                    if (TextUtils.isEmpty(adress)){
                      locationService.stop();
                      adress=location.getAddress().address;
                    }
                        city=location.getCity();
                        latLngs.add(latLng1);
                        if (latLngs.size()>2){
                            OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(latLngs);
                            //在地图上画出线条图层，mPolyline：线条图层
                            mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
                            mPolyline.setZIndex(3);
                        }


                }
            }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_1:
                MainActivity.this.startActivity(new Intent(MainActivity.this,BodySetActivity.class));
                break;
            case R.id.menu_2:
                MainActivity.this.startActivity(new Intent(MainActivity.this,JiLuActivity.class));
                break;
            case R.id.menu_3:
                MainActivity.this.startActivity(new Intent(MainActivity.this, MusicActivity.class));
                break;
        }
        return true;
    }
private String endAdress="";

}
