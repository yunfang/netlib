package com.cheers.okhttplibrary.utils.okhttp.download.okgo.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.io.Serializable;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/22
 * 描      述 ：
 * =======================================================================
 */

public class CollectionModel implements Serializable {


    public static String ID = "_id";
    public static String VIDEO_ID = "video_id";
    public static String DATE = "date";
    public static String JSON = "json";


    public String _id;
    public String video_id;
    public long date;
    public String json;


    public CollectionModel(){
        date = System.currentTimeMillis();
    }

    /**
     * 组合
     * @param videoBean
     * @return
     */
    public static ContentValues buildContentValues(CollectionModel videoBean) {
        ContentValues values = new ContentValues();
        values.put(ID, videoBean._id);
        values.put(VIDEO_ID, videoBean.video_id);
        values.put(DATE, videoBean.date);
        values.put(JSON,videoBean.json);
        return values;
    }

    /**
     * 查询
     * @param cursor
     * @return
     */
    public static CollectionModel parseCursorToBean(Cursor cursor) {
        CollectionModel videoBean = new CollectionModel();
        videoBean._id = cursor.getString(cursor.getColumnIndex(videoBean.ID));
        videoBean.video_id = cursor.getString(cursor.getColumnIndex(videoBean.VIDEO_ID));
        videoBean.date = cursor.getLong(cursor.getColumnIndex(videoBean.DATE));
        videoBean.json = cursor.getString(cursor.getColumnIndex(videoBean.JSON));
        return videoBean;
    }



}
