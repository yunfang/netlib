package com.cheers.okhttplibrary.utils.okhttp.download.okgo.db;

import android.content.ContentValues;
import android.database.Cursor;


import com.cheers.okhttplibrary.utils.okhttp.download.okgo.model.CollectionModel;

import java.util.List;

/**
 * ===============================耀世星辉传媒=============================
 * 作      者 ： big-方（周云方）    Github地址： https://github.com/yunfang
 * 电      话 ： 18501134764       QQ:  1052692333
 * 创 建 日 期 ： 2017/9/22
 * 描      述 ：
 * =======================================================================
 */

public class CollectionManager extends BaseDao<CollectionModel> {


    private CollectionManager() {
        super(new DBHelper());
    }

    public static CollectionManager getInstance() {
        return CollectionManager.CollectionManagerHolder.instance;
    }

    private static class CollectionManagerHolder {
        private static final CollectionManager instance = new CollectionManager();
    }

    @Override
    public String getTableName() {
        return DBHelper.TABLE_COLLECTION;
    }

    @Override
    public void unInit() {

    }

    @Override
    public CollectionModel parseCursorToBean(Cursor cursor) {
        return CollectionModel.parseCursorToBean(cursor);
    }

    @Override
    public ContentValues getContentValues(CollectionModel videoBean) {
        return CollectionModel.buildContentValues(videoBean);
    }

    /**
     * 根据video_id获取数据
     * @param video_id
     * @return
     */
    public CollectionModel get(String video_id) {
        return queryOne(CollectionModel.VIDEO_ID + "=?", new String[]{video_id});
    }

    /**
     * 根据video_id 删除信息
     * @param video_id
     */
    public void delete(String video_id) {
        delete(CollectionModel.VIDEO_ID + "=?", new String[]{video_id});
    }

    /** 获取所有下载信息 */
    public List<CollectionModel> getAll() {
        return query(null, null, null, null, null, CollectionModel.DATE + " ASC", null);
    }


}
