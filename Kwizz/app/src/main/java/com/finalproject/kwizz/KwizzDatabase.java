package com.finalproject.kwizz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KwizzDatabase extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "Kwizz.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_ACCOUNT = "accounts";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_NAME = "name";

    public static final String TABLE_QUIZ_CATEGORY = "categories";
    public static final String COL_CATEGORY_NAME = "catname";
    public static final String COL_ICON = "icon";


    public static final String TABLE_PUBLIC_QUESTION = "publicquestions";
    public static final String TABLE_PRIVATE_QUESTION = "privatequestions";
    public static final String COL_ID = "id";
    public static final String COL_QUESTION = "question";
    public static final String COL_OPTION_A = "optionA";
    public static final String COL_OPTION_B = "optionB";
    public static final String COL_OPTION_C = "optionC";
    public static final String COL_OPTION_D = "optionD";
    public static final String COL_ANSWER = "answer";

    private final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + "(" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD + " TEXT, " + COL_EMAIL + " TEXT, " + COL_NAME + " TEXT);";
    private final String SQL_CREATE_QUIZ_CATEGORY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_QUIZ_CATEGORY + "(" + COL_CATEGORY_NAME + " TEXT PRIMARY KEY, " + COL_ICON + " INTEGER);";
    private final String SQL_CREATE_PUBLIC_QUESTION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PUBLIC_QUESTION + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_CATEGORY_NAME + " TEXT, " + COL_QUESTION + " TEXT, " + COL_OPTION_A + " TEXT, " + COL_OPTION_B + " TEXT, " + COL_OPTION_C + " TEXT, " + COL_OPTION_D + " TEXT, " + COL_ANSWER + " TEXT);";

    private SQLiteDatabase db;

    public KwizzDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;

        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);

        db.execSQL(SQL_CREATE_QUIZ_CATEGORY_TABLE);
        fillQuizCategoryTable();

        db.execSQL(SQL_CREATE_PUBLIC_QUESTION_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_CATEGORY);
        onCreate(db);
    }

    public void insertAccount(String username, String password, String email, String name)
    {
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_EMAIL, email);
        values.put(COL_NAME, name);

        getWritableDatabase().insert(TABLE_ACCOUNT, null, values);
    }

    public Cursor getSpecificAccount(String column, String value)
    {
        String query = "";

        if(column.equals(COL_USERNAME))
        {
            query = "SELECT * FROM " + TABLE_ACCOUNT + " where " + COL_USERNAME + " = '" + value + "';";
        }
        else if(column.equals(COL_NAME))
        {
            query = "SELECT " + COL_NAME + " from " + TABLE_ACCOUNT + " where " + COL_NAME + " = '" + value + "';";
        }

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        cursor.moveToNext();

        return cursor;
    }

    public Cursor getQuizCategory()
    {
        String query = "SELECT * FROM " + TABLE_QUIZ_CATEGORY;

        Cursor cursor = getReadableDatabase().rawQuery(query, null);
        cursor.moveToNext();

        return cursor;
    }

    public void fillQuizCategoryTable()
    {
        addQuizCategory("Math", R.drawable.maths_icon);
        addQuizCategory("Astronomy", R.drawable.astronomy_icon);
        addQuizCategory("Computer Science", R.drawable.cs_icon);
        addQuizCategory("History", R.drawable.history_icon);
        addQuizCategory("Biology", R.drawable.biology_icon);
        addQuizCategory("Physics", R.drawable.physics_icon);
        addQuizCategory("Philosophy", R.drawable.philosophy_icon);
        addQuizCategory("Psychology", R.drawable.psychology_icon);
    }

    public void fillPublicQuestionTable()
    {
        addPublicQuestion("Math", "The average of first 50 natural numbers is ___", "25.30", "25.5", "25.00", " 12.25", "25.5");
        addPublicQuestion("Math", "The number of 3-digit numbers divisible by 6 is ___", "149", "166", "150", "151", "150");
        addPublicQuestion("Math", "A clock strikes once at 1 o'clock, twice at 2 o'clock, thrice at 3 o'clock and so on.  How many times will it strike in 24 hours?", "78", "136", "156", "196", "156");
        addPublicQuestion("Math", "106 x 106 - 94 x 94 = ?", "2004", "2400", "1904", "1906", "2400");
        addPublicQuestion("Math", "Which of the following numbers gives 240 when added to its own square?", "15", "16", "18", "20", "15");
        addPublicQuestion("Math", "The simplest form of 1.5 : 2.5 is ___", "6 : 10", "15 : 25", "0.75 : 1.25", "3 : 5", "3 : 5");
        addPublicQuestion("Math", "If 16 = 11, 25 = 12, 36 = 15, then 49 = ?", "14", "20", "19", "17", "20");
        addPublicQuestion("Math", "If 6 is 50% of a number, what is the number?", "10", "11", "12", "13", "12");
        addPublicQuestion("Math", "Andy read 4/5 th of a story book which has 100 pages.  How many pages of the book is not yet read by Andy?", "40", "60", "80", "20", "20");
        addPublicQuestion("Math", "Which number is missing?\n1, 9, 25, 49, (?)", "121", "81", "16", "169", "81");
    }

    public void addQuizCategory(String category, int icon)
    {
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, category);
        values.put(COL_ICON, icon);

        db.insert(TABLE_QUIZ_CATEGORY, null, values);
    }

    public void addPublicQuestion(String category, String question, String optionA, String optionB, String optionC, String optionD, String answer)
    {
        ContentValues values = new ContentValues();
        values.put(COL_CATEGORY_NAME, category);
        values.put(COL_QUESTION, question);
        values.put(COL_OPTION_A, optionA);
        values.put(COL_OPTION_B, optionB);
        values.put(COL_OPTION_C, optionC);
        values.put(COL_OPTION_D, optionD);
        values.put(COL_ANSWER, answer);

        db.insert(TABLE_PUBLIC_QUESTION,null, values);
    }
}
