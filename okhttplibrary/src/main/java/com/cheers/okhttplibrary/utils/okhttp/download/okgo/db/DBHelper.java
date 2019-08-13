/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cheers.okhttplibrary.utils.okhttp.download.okgo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.cheers.okhttplibrary.utils.okhttp.download.okgo.OkGo;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.CollectionModel;
import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.Progress;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/5
 * 描      述 ：
 * =======================================================================
 */
class DBHelper extends SQLiteOpenHelper {

    private static final String DB_CACHE_NAME = "cheers_cache_video.db";
    private static final int DB_CACHE_VERSION = 1;
    static final String TABLE_COLLECTION = "collection";
    static final String TABLE_DOWNLOAD = "download";

    static final Lock lock = new ReentrantLock();

    private TableEntity collectoinTableEntity = new TableEntity(TABLE_COLLECTION);
    private TableEntity downloadTableEntity = new TableEntity(TABLE_DOWNLOAD);

    DBHelper() {
        this(OkGo.getInstance().getContext());
    }

    DBHelper(Context context) {
        super(context, DB_CACHE_NAME, null, DB_CACHE_VERSION);

        collectoinTableEntity.addColumn(new ColumnEntity(CollectionModel.ID, "INTEGER", true, true,true))//
                .addColumn(new ColumnEntity(CollectionModel.VIDEO_ID, "VARCHAR"))//
                .addColumn(new ColumnEntity(CollectionModel.DATE, "INTEGER"))//
                .addColumn(new ColumnEntity(CollectionModel.JSON, "TEXT"));


        downloadTableEntity.addColumn(new ColumnEntity(Progress.TAG, "VARCHAR", true, true))//
                .addColumn(new ColumnEntity(Progress.URL, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.VIDEO_ID, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.DESCRIPTION, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.VIDEO_LENGTH, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.FOLDER, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.FILE_PATH, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.FILE_NAME, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.FRACTION, "VARCHAR"))//
                .addColumn(new ColumnEntity(Progress.TOTAL_SIZE, "INTEGER"))//
                .addColumn(new ColumnEntity(Progress.CURRENT_SIZE, "INTEGER"))//
                .addColumn(new ColumnEntity(Progress.STATUS, "INTEGER"))//
                .addColumn(new ColumnEntity(Progress.PRIORITY, "INTEGER"))//
                .addColumn(new ColumnEntity(Progress.DATE, "INTEGER"))//
                .addColumn(new ColumnEntity(Progress.REQUEST, "BLOB"))//
                .addColumn(new ColumnEntity(Progress.EXTRA1, "BLOB"))//
                .addColumn(new ColumnEntity(Progress.EXTRA2, "BLOB"))//
                .addColumn(new ColumnEntity(Progress.EXTRA3, "BLOB"));

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(collectoinTableEntity.buildTableString());
        db.execSQL(downloadTableEntity.buildTableString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (DBUtils.isNeedUpgradeTable(db, collectoinTableEntity)) db.execSQL("DROP TABLE IF EXISTS " + TABLE_COLLECTION);
        if (DBUtils.isNeedUpgradeTable(db, downloadTableEntity)) db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOWNLOAD);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
