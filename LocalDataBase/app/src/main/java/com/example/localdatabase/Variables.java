package com.example.localdatabase;

public class Variables {
    public static final String NAME_DB = "db_users";
    public static final String NAME_TABLE = "users";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NAME = "name";
    public static final String CAMPO_PHONE = "phone";
    public static final String CREATE_TABLE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
            NAME_TABLE, CAMPO_ID, CAMPO_NAME, CAMPO_PHONE
    );
    public static final String DELETE_TABLE = String.format("DROP TABLE IF EXIST %s", NAME_TABLE);
}
