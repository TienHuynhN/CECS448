/**
 * CECS 453 Mobile Application Development
 * Professor Fahim
 * @author: Tien Huynh, Howard Chen
 * Final Project : Quiz Taker
 * Due: Aug 15, 2019
 * Purpose: This app is an android quiz taker app which challenges the user to multiple categories.
 * The app shows a good implementation of most of the features we learning in our CECS 453 Mobile
 * Application Development app. The app stores a sqlite database for the username/password and saved questions.
 */

package com.example.quiztaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.example.quiztaker.QuizContract.*;

/**
 * A class which uses SQLite to create account database for quiz taker game
 */
public class QuizTakerDatabase extends SQLiteOpenHelper
{
    //declare and initialize variables for database tables and columns
    public static final String DATABASE_NAME = "QuizTaker.db";
    public static final int DATABASE_VERSION = 1;

    //attributes for account table
    private static final String TABLE_ACCOUNT = "accounts";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_IN_GAME_NAME = "ingamename";

    //attributes for leaderboard table
    private static final String TABLE_LEADERBOARD = "leaderboards";
    public static final String COL_SCORES = "scores";

    //attributes for current account table
    private static final String TABLE_CURRENT_ACCOUNT = "current_account";

    //query to create account table
    private final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ACCOUNT + "(" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD + " TEXT, " + COL_EMAIL + " TEXT, " + COL_IN_GAME_NAME + " TEXT);";
    //query to create question table
    private final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE IF NOT EXISTS " + QuestionsTable.TABLE_QUESTION + "(" + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QuestionsTable.COLUMN_QUESTION + " TEXT, " + QuestionsTable.COLUMN_CATEGORY+ " TEXT, " + QuestionsTable.COLUMN_ChoiceA + " TEXT, " + QuestionsTable.COLUMN_ChoiceB+ " TEXT, " + QuestionsTable.COLUMN_ChoiceC + " TEXT, "  + QuestionsTable.COLUMN_ChoiceD + " TEXT, " + QuestionsTable.COLUMN_ANSWER_NR + " TEXT " + ");";
    //query to create leaderboard table
    private final String SQL_CREATE_LEADERBOARD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LEADERBOARD + "(" + COL_IN_GAME_NAME + " TEXT PRIMARY KEY, " + COL_SCORES + " INT);";
    //query to create current account table
    private final String SQL_CREATE_CURRENT_ACCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CURRENT_ACCOUNT + "(" + COL_USERNAME + " TEXT PRIMARY KEY);";

    private SQLiteDatabase db;

    /**
     * Create database through constructor
     */
    public QuizTakerDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Create tables in database
     */
    public void onCreate(SQLiteDatabase db)
    {
        this.db = db;
        //create accounts table
        db.execSQL(SQL_CREATE_ACCOUNT_TABLE);

        //create questions table
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();

        //create leaderboard table
        db.execSQL(SQL_CREATE_LEADERBOARD_TABLE);

        //create current account table
        db.execSQL(SQL_CREATE_CURRENT_ACCOUNT_TABLE);
    }

    /**
     * Drop all the tables and recreate them to make any changes
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.COLUMN_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEADERBOARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_ACCOUNT);
        //call onCreate method to recreate tables
        onCreate(db);
    }

    /**
     *This method is used to add new account to database
     */
    public void insertAccount(String username, String password, String email, String inGameName)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //adding new account in accounts table
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        values.put(COL_EMAIL, email);
        values.put(COL_IN_GAME_NAME, inGameName);
        db.insert(TABLE_ACCOUNT, null, values);
    }

    /**
     * This method is used to add new question to database
     */
    public void insertQuestion(String category, String question, String answer1, String answer2, String answer3, String answer4, String correct_answer)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //adding new question in questions table
        ContentValues values = new ContentValues();
        values.put(QuestionsTable.COLUMN_CATEGORY, category);
        values.put(QuestionsTable.COLUMN_QUESTION, question);
        values.put(QuestionsTable.COLUMN_ChoiceA, answer1);
        values.put(QuestionsTable.COLUMN_ChoiceB, answer2);
        values.put(QuestionsTable.COLUMN_ChoiceC, answer3);
        values.put(QuestionsTable.COLUMN_ChoiceD, answer4);
        values.put(QuestionsTable.COLUMN_ANSWER_NR, correct_answer);
        db.insert(QuestionsTable.TABLE_QUESTION, null, values);
    }

    /**
     * This method is used to insert new player to the leaderboard table
     * @param in_game_name new player's name
     * @param scores new score that player gets
     */
    public void insertScore(String in_game_name, int scores)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //adding new scores in leaderboard table
        ContentValues values = new ContentValues();
        values.put(COL_IN_GAME_NAME, in_game_name);
        values.put(COL_SCORES, scores);
        db.insert(TABLE_LEADERBOARD, null, values);
    }

    /**
     *This method is used to add current account to database
     */
    public void insertCurrentAccount(String username)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //adding new account in accounts table
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);

        db.insert(TABLE_CURRENT_ACCOUNT, null, values);
    }

    /**
     * Delete the current account
     */
    public void deleteCurrentAccount()
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //delete the current user
        db.delete(TABLE_CURRENT_ACCOUNT, null, null);
    }

    /**
     * Update the score after each game
     */
    public void updateScore(String in_game_name, int scores)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //update data in accounts table
        ContentValues newValue = new ContentValues();
        newValue.put(COL_SCORES, scores);
        db.update(TABLE_LEADERBOARD, newValue, COL_IN_GAME_NAME + "=?", new String[] {in_game_name});
    }

    /**
     * This method is used to either update in-game name or password
     * @param column column in the table
     * @param value new value that will be updated
     */
    public void updateAccount(String column, String value, String key, String keyValue)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        //update data in accounts table
        ContentValues newValue = new ContentValues();
        newValue.put(column, value);
        db.update(TABLE_ACCOUNT, newValue, key + "=?", new String[] {keyValue});
    }

    /**
     * Get current account to automatically login
     */
    public Cursor getCurrentAccount()
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_CURRENT_ACCOUNT + ";";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return cursor;
    }

    /**
     * Get all score to display on leaderboard
     */
    public Cursor getAllScores()
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_LEADERBOARD + " ORDER BY " + COL_SCORES + " DESC;";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return cursor;
    }

    /**
     * Get specific score
     */
    public Cursor getSpecificScores(String in_game_name)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_LEADERBOARD + " WHERE " + COL_IN_GAME_NAME + " = '" + in_game_name + "';";

        //getting results in cursor which allow us to read result from the database
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return cursor;
    }

    /**
     * Find an account by matching username
     * @param value unique username or in-game name that is being searched for
     * @return cursor object which will be used to retrieve data in database
     */
    public Cursor getSpecificAccountInfo(String value, String type)
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "";

        if(type.equals("username"))
        {
            //a query that will get the username and password that match the where clause
            query = "SELECT * FROM " + TABLE_ACCOUNT + " where " + COL_USERNAME + " = '" + value + "';";
        }
        else if(type.equals("in_game_name"))
        {
            //a query that will get the in-game name that match the where clause
            query = "SELECT " + COL_IN_GAME_NAME + " from " + TABLE_ACCOUNT + " where " + COL_IN_GAME_NAME + " = '" + value + "';";
        }

        //getting results in cursor which allow us to read result from the database
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        return cursor;
    }

    /**
     * Get list of questions of the same category
     */
    public List<Question> getSpecificCategoryQuestions(String specificCategory) {

        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        //use query to select the question of the same category
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_QUESTION +
                " WHERE category = " + "'" + specificCategory + "'", null);

        //store the question
        if(c.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY)));
                question.setChoiceA(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ChoiceA)));
                question.setChoiceB(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ChoiceB)));
                question.setChoiceC(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ChoiceC)));
                question.setChoiceD(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ChoiceD)));
                question.setAnswer(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);

            }while(c.moveToNext()); //continue till cursor is at end of db
        }
        c.close(); //closes cursor
        return questionList;
    }

    /**
     * Ge the category
     */
    public Cursor getQuestionCategory()
    {
        //get an database object that is used to write data to the database
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT DISTINCT " + QuestionsTable.COLUMN_CATEGORY + " FROM " + QuestionsTable.TABLE_QUESTION + ";";

        //getting results in cursor which allow us to read result from the database
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();

        return cursor;
    }

    /**
     * Fill the question table with some questions
     */
    private void fillQuestionsTable(){

        //Creates New question Objects
        Question q1 = new Question("History", "World War I began in which year?",
                "1923", "1938", "1917", "1914", "1914");
        addQuestion(q1); //adds a Question to database

        Question q2 = new Question("History","Adolf Hitler was born in which " +
                "country?", "France", "Germany",  "Austria",
                "Hungary", "Austria");
        addQuestion(q2); //adds a Question to database

        Question q3 = new Question("History","John F. Kennedy was assassinated in: ",
                "New York", "Austin", "Dallas", "Miami",
                "Dallas");
        addQuestion(q3); //adds a Question to database
        Question q4 = new Question("History","Who fought in the war of 1812?",
                "Andrew Jackson", "Arthur Wellsley", "Otto von Bismarck",
                "Napoleon", "Andrew Jackson");
        addQuestion(q4); //adds a Question to database

        Question q5 = new Question("History","Which general famously stated 'I shall return'?",
                "Bull Halsey", "George Patton", "Douglas MacArthur",
                "Omar Bradley", "Douglas MacArthur");
        addQuestion(q5); //adds a Question to database

        Question q6 = new Question("History","American involvement in the Korean " +
                "war took place in which decade?", "1970s", "1950s", "1920s",
                "1960s", "1950s");
        addQuestion(q6); //adds a Question to database

        Question q7 = new Question("History","Battle of Hastings in 1066 was " +
                "fought in which country?", "France", "Russia", "England",
                "Norway", "England");
        addQuestion(q7); //adds a Question to database

        Question q8 = new Question("History","The Magna Carta was published by the " +
                "King of which country?", "France", "Austria", "Italy",
                "England", "England");
        addQuestion(q8); //adds a Question to database

        Question q9 = new Question("History","The first successful printing press " +
                "was developed by _____.", "Johannes Gutenburg", "Benjamin Franklin",
                "Sir Issac Newton", "Martin Luther", "Johannes Gutenburg");
        addQuestion(q9); //adds a Question to database


        Question q10 = new Question("History","The disease that ravaged and killed " +
                "a third of Europe's population in the 14th century is known as:",
                "The White Death", "The Black Plaque", "Smallpox",
                "The Bubonic Plaque", "The Bubonic Plaque");
        addQuestion(q10); //adds a Question to database

        Question q11 = new Question("Astronomy","At any time we may describe " +
                "the position of an inferior planet by the angle it makes with the sun as seen " +
                "from the earth. This angle is called the:", "ecliptic (pron: eh-klip-tik)",
                "epicycle", "elongation", "proxima",
                "elongation");
        addQuestion(q11); //adds a Question to database

        Question q12 = new Question("Astronomy","Which of the following planets" +
                "has the greatest eccentricity?", "Pluto", "Jupiter", "Mars",
                "Mercury", "Pluto");
        addQuestion(q12); //adds a Question to database

        Question q13 = new Question("Astronomy","The largest moon in our solar " +
                "system has an atmosphere that is denser than the atmosphere of Mars. The name of " +
                "this moon is:", "Titan", "Ganymede", "Triton",
                "Io (pron: I - O)", "Titan");
        addQuestion(q13); //adds a Question to database

        Question q14 = new Question("Astronomy","On which of the following " +
                "planets would the sun rise in the west?", "Saturn", "Pluto",
                "Mercury", "Venus", "Venus");
        addQuestion(q14); //adds a Question to database

        Question q15 = new Question("Astronomy","Which planet seems to be turned " +
                "on its side with an axis tilt of 98 degrees?", "Uranus",
                "Pluto", "Neptune", "Saturn",
                "Uranus");
        addQuestion(q15); //adds a Question to database

        Question q16 = new Question("Astronomy","The angle that the full moon " +
                "takes up in the night sky is equal to which of the following values?",
                "1/8 degree", "1/2 degree", "1 degree",
                "2 degrees", "1/2 degree");
        addQuestion(q16); //adds a Question to database

        Question q17 = new Question("Astronomy","The period from one full moon to the next is:",
                "33 days", "30 days", "29.5 days",
                "28 days", "29.5 days");
        addQuestion(q17); //adds a Question to database

        Question q18 = new Question("Astronomy","When a superior planet is at " +
                "opposition it is making an angle of how many degrees with the sun?",
                "0 degrees", "45 degrees", "90 degrees",
                "180 degrees", "180 degrees");
        addQuestion(q18); //adds a Question to database

        Question q19 = new Question("Astronomy","The word Albedo " +
                "(pron: al-BEE-doe) refers to which of the following?", "The wobbling " +
                "motion of a planet", "The amount of light a planet reflects",
                "The phase changes of a planet", "The brightness of a star",
                "The amount of light a planet reflects");
        addQuestion(q19); //adds a Question to database

        Question q20 = new Question("Astronomy","Galileo discovered something " +
                "about Venus with his telescope that shook the old theories. Which of the following " +
                "was Galileo's discovery?",
                "Venus was covered in clouds", "Venus had phases like the moon",
                "Venus' surface was similar to the earth's", "Venus had retrograde" +
                " motion", "Venus had phases like the moon");
        addQuestion(q20); //adds a Question to database

        Question q21 = new Question("Biology","During photosynthesis:",
                "light reactions produce sugar, while the Calvin cycle produces O2.",
                "light reactions produce NADPH and ATP, while the Calvin cycle produces sugar.",
                "light reactions photophosphorylate ADP, while the Calvin cycle produces ATP.",
                "the Calvin cycle produces both sugar and O2.", "light reactions produce NADPH and ATP, while the Calvin cycle produces sugar.");
        addQuestion(q21); //adds a Question to database

        Question q22 = new Question("Biology","The adult human of average age " +
                "and size has approximately how many quarts of blood?", "4", "6",
                "8", "10", "6");
        addQuestion(q22); //adds a Question to database

        Question q23 = new Question("Biology","Once the erythrocytes enter the " +
                "blood in humans, it is estimated that they have an average lifetime of how many " +
                "days. Is it:", "10 days", "120 days", "200 days",
                "360 days", "120 days");
        addQuestion(q23); //adds a Question to database

        Question q24 = new Question("Biology","Of the following, which " +
                "mechanisms are important in the death of erythrocytes (pron: eh-rith-reh-sites) " +
                "in human blood? Is it",
                "phagocytosis (pron: fag-eh-seh-toe-sis)", "hemolysis",
                "mechanical damage",
                "all of the above", "all of the above");
        addQuestion(q24); //adds a Question to database

        Question q25 = new Question("Biology","Surplus red blood cells, needed to " +
                "meet an emergency, are MAINLY stored in what organ of the human body? Is it the:",
                "pancreas", "spleen", "liver",
                "kidneys", "spleen");
        addQuestion(q25); //adds a Question to database

        Question q26 = new Question("Biology","When a human donor gives a pint " +
                "of blood, it usually requires how many weeks for the body RESERVE of red " +
                "corpuscles to be replaced? Is it:", "1 week", "3 weeks", "7 weeks",
                "21 weeks", "7 weeks");
        addQuestion(q26); //adds a Question to database

        Question q27 = new Question("Biology","The several types of white blood " +
                "cells are sometime collectively referred to as:",
                "erythrocytes (pron: eh-rith-row-cites)",
                "leukocytes (pron: lew-kah-cites)",
                "erythroblasts (pron: eh-rith-rah-blast)",
                "thrombocytes (pron: throm-bow-cites)", "leukocytes (pron: lew-kah-cites)");
        addQuestion(q27); //adds a Question to database

        Question q28 = new Question("Biology","The condition in which there is " +
                "a DECREASE in the number of white blood cells in humans is known as:",
                "leukocytosis (pron: lew-kO-sigh-toe-sis)",
                "leukopenia (pron: lew-kO-pea-nee-ah)",
                "leukemia (pron: lew-kee-me-ah)",
                "leukohyperia (pron: lew-kO-high-per-e-ah)", "leukopenia (pron: lew-kO-pea-nee-ah");
        addQuestion(q8); //adds a Question to database

        Question q29 = new Question("Biology","The smallest of the FORMED " +
                "elements of the blood are the:",
                "white cells", "red cells", "platelets",
                "erythrocytes", "platelets");
        addQuestion(q29); //adds a Question to database

        Question q30 = new Question("Biology","Which of the following statements " +
                "concerning platelets is INCORRECT. Platelets:",
                "contain DNA", "are roughly disk-shaped",
                "have little ability to synthesize proteins",
                "are between 1/2 and 1/3 the diameter of the red cell\n", "contain DNA");
        addQuestion(q30); //adds a Question to database

        Question q31 = new Question("Computer Science","Where does the execution " +
                "of the program starts?", "user-defined function",
                "main function",  "void function", "none of the mentioned",
                "main function");
        addQuestion(q31); //adds a Question to database

        Question q32 = new Question("Computer Science","What are mandatory parts " +
                "in the function declaration?", "return type, function name",
                "return type, function name, parameters",  "parameters, function name",
                "none of the mentioned",
                "return type, function name");
        addQuestion(q32); //adds a Question to database

        Question q33 = new Question("Computer Science","What is the header file " +
                "for the string class?", "#include<ios>",
                "#include<str>",  "#include<string>", "none of the mentioned",
                "#include<string>");
        addQuestion(q33); //adds a Question to database

        Question q34 = new Question("Computer Science","Which of the following " +
                "permits function overloading on c++?", "type",
                "number of arguments",  "type & number of arguments",
                "none of the mentioned",
                "type & number of arguments");
        addQuestion(q34); //adds a Question to database

        Question q35 = new Question("Computer Science","In which of the following " +
                "we cannot overload the function?", "return function",
                "caller",  "called function", "none of the mentioned",
                "return function");
        addQuestion(q35); //adds a Question to database

        Question q36 = new Question("Computer Science","Overloaded functions are",
                "Very long functions that can hardly run",
                "One function containing another one or more functions inside it",
                "Two or more functions with the same name but different number of " +
                        "parameters or type", "none of the mentioned",
                "Two or more functions with the same name but different number of " +
                        "parameters or type");
        addQuestion(q36); //adds a Question to database

        Question q37 = new Question("Computer Science","What will happen while " +
                "using pass by reference", "The values of those variables are passed to " +
                "the function so that it can manipulate them",
                "The location of variable in memory is passed to the function so that " +
                        "it can use the same memory area for its processing",  "The " +
                "function declaration should contain ampersand (& in its type declaration)",
                "none of the mentioned",
                "The location of variable in memory is passed to the function so that" +
                        " it can use the same memory area for its processing");
        addQuestion(q37); //adds a Question to database

        Question q38 = new Question("Computer Science","What should be passed " +
                "in parameters when function does not require any parameters?",
                "void",
                "blank space",  "both void & blank space",
                "none of the mentioned",
                "blank space");
        addQuestion(q38); //adds a Question to database

        Question q39 = new Question("Computer Science","What are the advantages " +
                "of passing arguments by reference?", "Changes to parameter values within " +
                "the function also affect the original arguments", "There is need to copy " +
                "parameter values (i.e. less memory used)",  "There is no need to call " +
                "constructors for parameters (i.e. faster)", "All of the mentioned",
                "All of the mentioned");
        addQuestion(q39); //adds a Question to database

        Question q40 = new Question("Computer Science","Which operator works only " +
                "with integer variables?", "increment", "decrement",
                "both increment & decrement", "none of the mentioned",
                "both increment & decrement");
        addQuestion(q40); //adds a Question to database

        Question q41 = new Question("Physics","Which of the following is a " +
                "physical quantity that has a magnitude but no direction?", "Vector", "Frame of reference",
                "Resultant", "Scalar",
                "Scalar");
        addQuestion(q41); //adds a Question to database

        Question q42 = new Question("Physics","A man presses more weight on " +
                "earth at :", "Sitting position", "Standing Position",
                "Lying Position", "None of these",
                "Standing Position");
        addQuestion(q42); //adds a Question to database

        Question q43 = new Question("Physics","A piece of ice is dropped in a " +
                "vesel containing kerosene. When ice melts, the level of kerosene will", "Rise", "Fall",
                "Remain Same", "None of these",
                "Fall");
        addQuestion(q43); //adds a Question to database

        Question q44 = new Question("Physics","Young's modulus is the property of",
                "Gas only", "Both Solid and Liquid",
                "Liquid only", "Solid only",
                "Solid only");
        addQuestion(q44); //adds a Question to database

        Question q45 = new Question("Physics","An artificial Satellite revolves " +
                "round the Earth in circular orbit, which quantity remains constant?",
                "Angular Momentum", "Linear Velocity",
                "Angular Displacement", "None of these",
                "Angular Momentum");
        addQuestion(q45); //adds a Question to database

        Question q46 = new Question("Physics","Product of Force and Velocity " +
                "is called:", "Work", "Power",
                "Energy", "Momentum",
                "Power");
        addQuestion(q46); //adds a Question to database

        Question q47 = new Question("Physics","If electrical conductivity " +
                "increases with the increase of temperature of a substance, then it is a:",
                "Conductor", "Semiconductor",
                "Insulator", "Carborator",
                "Semiconductor");
        addQuestion(q47); //adds a Question to database

        Question q48 = new Question("Physics","Which one of the following has the " +
                "highest value of specific heat?", "Alcohol", "Methane",
                "Kerosene", "Water",
                "Water");
        addQuestion(q48); //adds a Question to database

        Question q49 = new Question("Physics","Electronegativity is the measure of:",
                "Metallic character", "Non-metallic character",
                "Basic Character", "None of these",
                "Non-metallic character");
        addQuestion(q49); //adds a Question to database

        Question q50 = new Question("Physics","The rotational effect of a force " +
                "on a body about an axis of rotation is described in terms of the: ",
                "Centre of gravity", "Centripetal force",
                "Centrifugal force", "Moment of force",
                "Moment of force");
        addQuestion(q50); //adds a Question to database
    }

    /**
     * this method is used to add all questions to the database
     */
    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_CATEGORY, question.getCategory());
        cv.put(QuestionsTable.COLUMN_ChoiceA, question.getChoiceA());
        cv.put(QuestionsTable.COLUMN_ChoiceB, question.getChoiceB());
        cv.put(QuestionsTable.COLUMN_ChoiceC, question.getChoiceC());
        cv.put(QuestionsTable.COLUMN_ChoiceD, question.getChoiceD());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswer());
        db.insert(QuestionsTable.TABLE_QUESTION, null, cv);
    }
}
