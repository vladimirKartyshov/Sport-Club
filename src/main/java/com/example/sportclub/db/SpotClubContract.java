package com.example.sportclub.db;

import android.provider.BaseColumns;

public final class SpotClubContract {

    private SpotClubContract() {
    }

    // в этом классе(таблице базы данных)будет содержаться инф-я о членах клуба
    public static final  class MemberEntry implements BaseColumns {

        public static final String TABLE_NAME = "members";

        public static final String _ID = BaseColumns._ID;//удобно иполь-ть при создании неск таблиц
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_GROUP = "group";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
