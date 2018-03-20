package com.example.daomodule;

import com.example.daomodule.greendao.DaoManager;
import com.example.daomodule.greendao.gen.UserDao;

/**
 * Created by caixingcun on 2018/3/12.
 * 实体类 数据库操作管理类
 */

public class EntityManager {
    private static EntityManager mEntityManager;

    public static EntityManager getEntityManager() {
        if (mEntityManager == null) {
            mEntityManager = new EntityManager();
        }
        return mEntityManager;
    }

    public UserDao mUserDao;
    public UserDao getUserDao() {
        mUserDao  = DaoManager.getInstance().getDaoSession().getUserDao();
        return mUserDao;
    }

}
