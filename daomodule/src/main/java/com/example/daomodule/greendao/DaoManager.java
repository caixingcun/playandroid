package com.example.daomodule.greendao;

import android.app.Application;

import com.example.daomodule.greendao.gen.DaoMaster;
import com.example.daomodule.greendao.gen.DaoSession;

/**
 * Created by caixingcun on 2018/3/12.
 *  数据库管理类
 *  不需要动 在需要使用的App中进行 DaoManager.init 初始化 之后就可以使用
 */

public class DaoManager {
    private static DaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoManager(Application app) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(app, "my-db", null);
        mDaoMaster= new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public static DaoManager init(Application app) {
        if (mInstance == null) {
            mInstance = new DaoManager(app);
        }
        return mInstance;
    }

    public static DaoManager getInstance() {
        return mInstance;
    }
}
