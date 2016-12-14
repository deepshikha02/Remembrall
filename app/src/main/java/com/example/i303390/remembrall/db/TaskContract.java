package com.example.i303390.remembrall.db;

/**
 * Created by I303390 on 12/4/2016.
 */

import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example.i303390.remembrall.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns{
        public static final String TABLE = "tasks";

        public static final String COL_TASK_TITLE = "title";
        public static final String COL_TASK_PRIO = "priority";
//        public static final String COL_TASK_LOCATION = "location";

    }
}
