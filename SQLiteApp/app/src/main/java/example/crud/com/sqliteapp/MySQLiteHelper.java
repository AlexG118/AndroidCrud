package example.crud.com.sqliteapp;

import example.crud.com.sqliteapp.Juego;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySQLiteHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "JuegoDB";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, "+
                "author TEXT )";

        // create books table
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS books");

        // create fresh books table
        this.onCreate(db);
    }

    // Books table name
    private static final String TABLE_GAMES = "juegos";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "titulo";
    private static final String KEY_GENRE  = "genero";
    private static final String KEY_VALUE  = "valor";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_GENRE,KEY_VALUE};

    public void addBook(Juego juego){
        //for logging
        Log.d("addJuego", juego.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, juego.getTitulo()); // get title
        values.put(KEY_GENRE, juego.getGenero()); // get author
        values.put(KEY_VALUE, juego.getValor());
        // 3. insert
        db.insert(TABLE_GAMES, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }






    public Juego getJuego(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_GAMES, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Juego juego = new Juego();
        juego.setId(Integer.parseInt(cursor.getString(0)));
        juego.setTitulo(cursor.getString(1));
        juego.setGenero(cursor.getString(2));
        juego.setValor(Integer.parseInt(cursor.getString(4)));
        Log.d("getJuego("+id+")", juego.toString());

        // 5. return book
        return juego;
    }

    // Get All Books
    public List<Juego> getAllJuegos() {
        List<Juego> juegos = new LinkedList<Juego>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_GAMES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Juego juego = null;
        if (cursor.moveToFirst()) {
            do {
                juego = new Juego();
                juego.setId(Integer.parseInt(cursor.getString(0)));
                juego.setTitulo(cursor.getString(1));
                juego.setGenero(cursor.getString(2));
                juego.setValor(Integer.parseInt(cursor.getString(4)));

                // Add book to books
                juegos.add(juego);
            } while (cursor.moveToNext());
        }

        Log.d("getAllJuegos()", juegos.toString());

        // return books
        return juegos;
    }

    // Updating single book
    public int updateJuego(Juego juego) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("titulo", juego.getTitulo()); // get title
        values.put("genero", juego.getGenero()); // get author
        values.put("valor", juego.getValor()); // get author
        // 3. updating row
        int i = db.update(TABLE_GAMES, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(juego.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single book
    public void deleteBook(Juego juego) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_GAMES,
                KEY_ID+" = ?",
                new String[] { String.valueOf(juego.getId()) });

        // 3. close
        db.close();

        Log.d("deleteJuego", juego.toString());

    }
}