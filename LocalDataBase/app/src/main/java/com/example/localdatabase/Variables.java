package com.example.localdatabase;

public class Variables {
    public static final String NAME_DB = "db_users";
    public static final String NAME_TABLE = "users";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NAME = "name";
    public static final String CAMPO_LASTNAME = "lastname";
    public static final String CAMPO_PHONE = "phone";
    public static final String CAMPO_AGE = "age";
    public static final String CAMPO_SEX = "sex";
    public static final String CAMPO_BIRTHDAY = "birthday";
    public static final String CAMPO_HEIGHT = "height";
    public static final String CREATE_TABLE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s TEXT, %s DOUBLE)",
            NAME_TABLE, CAMPO_ID, CAMPO_NAME, CAMPO_LASTNAME, CAMPO_PHONE, CAMPO_AGE, CAMPO_SEX, CAMPO_BIRTHDAY, CAMPO_HEIGHT
    );
    public static final String DELETE_TABLE = String.format("DROP TABLE IF EXISTS %s", NAME_TABLE);
}
