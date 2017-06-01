package com.example.baselibrary.dao;

/**
 * Created by douliu on 2017/5/24.
 */

public class DaoSupportFactory {

    private static DaoSupportFactory instance = null;
    private IDatabaseGenerator mGenerator;
    private static final String mDbName = "joke.db";

    private DaoSupportFactory(){
        mGenerator = new SdDatabaseGenerator();
    }

    public static DaoSupportFactory getInstance() {
        if (instance == null){
            synchronized (DaoSupportFactory.class) {
                if (instance == null) {
                    instance = new DaoSupportFactory();
                }
            }
        }
        return instance;
    }

    public <T> IDaoSupport<T> getDao(Class<T> clazz) {
        return new DaoSupport<>(mGenerator.get(mDbName), clazz);
    }
}
