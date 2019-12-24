package com.footprint.footprint.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.footprint.footprint.R;
import com.footprint.footprint.base.BaseActivity;
import com.footprint.footprint.base.TheApplication;
import com.footprint.footprint.db.green.locationDataDao;
import com.footprint.footprint.dbbean.User;
import com.footprint.footprint.dbbean.locationData;
import com.footprint.footprint.utils.ToastUtils;
import com.footprint.footprint.view.TitleWidget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JiLuActivity extends BaseActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private TitleWidget title;
    private locationDataDao locationDao;
    private List<LatLng> latLngs=new ArrayList<>();
    private List<locationData> locationDataList=new ArrayList<>();
    private String data;
    private AppCompatTextView actv_desc,actv_time,actv_juli,actv_sudu;
    private  Polyline mPolyline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.actiivty_jilu);
        locationDao= TheApplication.instance.getDaoSession().getLocationDataDao();

        mMapView =findViewById(R.id.mapview);
        actv_desc=findViewById(R.id.actv_desc);
        title=findViewById(R.id.title);
        title.setBackVisibility(View.GONE);
        actv_time=findViewById(R.id.actv_time);
        actv_sudu=findViewById(R.id.actv_sudu);
        actv_juli=findViewById(R.id.actv_juli);
        data= DateFormat.format("yyy-MM-dd", new Date()).toString();

        mBaiduMap = mMapView.getMap();
        // 设置是否打开交通图层
        mBaiduMap.setTrafficEnabled(false);
        mBaiduMap.setBaiduHeatMapEnabled(false);
        mBaiduMap.setMyLocationEnabled(false);
        // 设置普通视图  MAP_TYPE_NONE  MAP_TYPE_NORMAL
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setBuildingsEnabled(false);
        mBaiduMap.showMapPoi(true);
        title.setSubmitListener(new TitleWidget.onSubmitListener() {
            @Override
            public void onSubmit(View paramView) {
                final Calendar c = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(JiLuActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year, monthOfYear, dayOfMonth);
                        data= DateFormat.format("yyy-MM-dd", c).toString();

                        getData();
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }

    private void getData() {
        locationDataList=   locationDao.queryBuilder().where(locationDataDao.Properties.Name.eq(User.getUserName(JiLuActivity.this)
        ),locationDataDao.Properties.Time.eq(data)).build().list();
  if (locationDataList.size()<1){
      actv_desc.setVisibility(View.VISIBLE);
      actv_juli.setText("");
      actv_time.setText("");
      mBaiduMap.clear();
  }else{
      actv_desc.setVisibility(View.GONE);

      for ( locationData locationData1: locationDataList) {
          LatLng latLng1=new LatLng(locationData1.getJd(),locationData1.getWd());
          latLngs.add(latLng1);
      }

      MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLngs.get(latLngs.size()/2));
      mBaiduMap.setMapStatus(u);



      OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(latLngs);
      //在地图上画出线条图层，mPolyline：线条图层
      mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
      mPolyline.setZIndex(3);
      mBaiduMap.setMapStatus(MapStatusUpdateFactory
              .newMapStatus(new MapStatus.Builder().zoom(17).build()));

      actv_time.setText("TIME："+latLngs.size()*2+"s");
      double mmm = 0;
      if (latLngs.size()>0){
          LatLng latLng=latLngs.get(0);
          mmm=0;
          for (int i = 0; i <latLngs.size() ; i++) {
              mmm+= DistanceUtil.getDistance(latLngs.get(i),latLng);
              latLng=latLngs.get(i);
          }
          actv_juli.setText("Hostory: "+mmm+"M");
      }

      actv_sudu.setText("Speed: "+mmm/latLngs.size()*2);
  }

        }
    }

