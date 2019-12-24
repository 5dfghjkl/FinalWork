package com.footprint.footprint.db.green;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.footprint.footprint.dbbean.DongtaiPhotoShow;
import com.footprint.footprint.dbbean.DongtaiShow;
import com.footprint.footprint.dbbean.locationData;
import com.footprint.footprint.dbbean.UserInfo;

import com.footprint.footprint.db.green.DongtaiPhotoShowDao;
import com.footprint.footprint.db.green.DongtaiShowDao;
import com.footprint.footprint.db.green.locationDataDao;
import com.footprint.footprint.db.green.UserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dongtaiPhotoShowDaoConfig;
    private final DaoConfig dongtaiShowDaoConfig;
    private final DaoConfig locationDataDaoConfig;
    private final DaoConfig userInfoDaoConfig;

    private final DongtaiPhotoShowDao dongtaiPhotoShowDao;
    private final DongtaiShowDao dongtaiShowDao;
    private final locationDataDao locationDataDao;
    private final UserInfoDao userInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dongtaiPhotoShowDaoConfig = daoConfigMap.get(DongtaiPhotoShowDao.class).clone();
        dongtaiPhotoShowDaoConfig.initIdentityScope(type);

        dongtaiShowDaoConfig = daoConfigMap.get(DongtaiShowDao.class).clone();
        dongtaiShowDaoConfig.initIdentityScope(type);

        locationDataDaoConfig = daoConfigMap.get(locationDataDao.class).clone();
        locationDataDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        dongtaiPhotoShowDao = new DongtaiPhotoShowDao(dongtaiPhotoShowDaoConfig, this);
        dongtaiShowDao = new DongtaiShowDao(dongtaiShowDaoConfig, this);
        locationDataDao = new locationDataDao(locationDataDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);

        registerDao(DongtaiPhotoShow.class, dongtaiPhotoShowDao);
        registerDao(DongtaiShow.class, dongtaiShowDao);
        registerDao(locationData.class, locationDataDao);
        registerDao(UserInfo.class, userInfoDao);
    }
    
    public void clear() {
        dongtaiPhotoShowDaoConfig.clearIdentityScope();
        dongtaiShowDaoConfig.clearIdentityScope();
        locationDataDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
    }

    public DongtaiPhotoShowDao getDongtaiPhotoShowDao() {
        return dongtaiPhotoShowDao;
    }

    public DongtaiShowDao getDongtaiShowDao() {
        return dongtaiShowDao;
    }

    public locationDataDao getLocationDataDao() {
        return locationDataDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}
