package com.task.dd.greenbox.database;

/**定义纲要Schema
 * Created by dd on 2017/3/20.
 */

public class DBSchema {
    public static final class Table{
        public static final String NAME="USER";

        public static final  class Cols{
            public static final String UUID="uuid";
            public static final String PHONE="phone";
            public static final String PASSWORD="password";
            public static final String USERNAME="username";
            public static final String SIGNATURE="signature";


        }

    }
}
