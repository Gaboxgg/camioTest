package com.example.camiotest.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context,
               factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME,
        factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_MOVIE_POPULAR_TABLE = ("CREATE TABLE " +
                TABLE_NAME_PO + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT , " +
                 COLUMN_POPULARITY + " TEXT , "+
                 COLUMN_VOTE_COUNT + " TEXT , "+
                 COLUMN_VIDEO + " TEXT , "+
                 COLUMN_POSTER_PATH + " TEXT , "+
                 COLUMN_ADULT + " TEXT , "+
                 COLUMN_BACKDROP_PATH + " TEXT , "+
                 COLUMN_ORIGINAL_LANGUAGE + " TEXT , "+
                 COLUMN_ORIGINAL_TITLE + " TEXT , "+
                 COLUMN_VOTE_AVG + " TEXT , "+
                 COLUMN_OVERVIEW + " TEXT , "+
                 COLUMN_RELEASE_DATE + " TEXT "+ ");")
        val CREATE_MOVIE_TOP_TABLE = ("CREATE TABLE " +
                TABLE_NAME_TO + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT , " +
                 COLUMN_POPULARITY + " TEXT , "+
                 COLUMN_VOTE_COUNT + " TEXT , "+
                 COLUMN_VIDEO + " TEXT , "+
                 COLUMN_POSTER_PATH + " TEXT , "+
                 COLUMN_ADULT + " TEXT , "+
                 COLUMN_BACKDROP_PATH + " TEXT , "+
                 COLUMN_ORIGINAL_LANGUAGE + " TEXT , "+
                 COLUMN_ORIGINAL_TITLE + " TEXT , "+
                 COLUMN_VOTE_AVG + " TEXT , "+
                 COLUMN_OVERVIEW + " TEXT , "+
                 COLUMN_RELEASE_DATE + " TEXT "+ ");")
        val CREATE_MOVIE_UPCOMING_TABLE = ("CREATE TABLE " +
                TABLE_NAME_UP + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT , " +
                 COLUMN_POPULARITY + " TEXT , "+
                 COLUMN_VOTE_COUNT + " TEXT , "+
                 COLUMN_VIDEO + " TEXT , "+
                 COLUMN_POSTER_PATH + " TEXT , "+
                 COLUMN_ADULT + " TEXT , "+
                 COLUMN_BACKDROP_PATH + " TEXT , "+
                 COLUMN_ORIGINAL_LANGUAGE + " TEXT , "+
                 COLUMN_ORIGINAL_TITLE + " TEXT , "+
                 COLUMN_VOTE_AVG + " TEXT , "+
                 COLUMN_OVERVIEW + " TEXT , "+
                 COLUMN_RELEASE_DATE + " TEXT "+ ");")
        val CREATE_MOVIE_FAV_TABLE = ("CREATE TABLE " +
                TABLE_NAME_FA + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_TITLE + " TEXT , " +
                COLUMN_POPULARITY + " TEXT , "+
                COLUMN_VOTE_COUNT + " TEXT , "+
                COLUMN_VIDEO + " TEXT , "+
                COLUMN_POSTER_PATH + " TEXT , "+
                COLUMN_ADULT + " TEXT , "+
                COLUMN_BACKDROP_PATH + " TEXT , "+
                COLUMN_ORIGINAL_LANGUAGE + " TEXT , "+
                COLUMN_ORIGINAL_TITLE + " TEXT , "+
                COLUMN_VOTE_AVG + " TEXT , "+
                COLUMN_OVERVIEW + " TEXT , "+
                COLUMN_RELEASE_DATE + " TEXT "+ ");")
        db.execSQL(CREATE_MOVIE_POPULAR_TABLE)
        db.execSQL(CREATE_MOVIE_TOP_TABLE)
        db.execSQL(CREATE_MOVIE_UPCOMING_TABLE)
        db.execSQL(CREATE_MOVIE_FAV_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PO)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TO)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_UP)
        onCreate(db)
    }
    fun addPopularMovie(movie: MoviePojo) {
        try {
            val values = ContentValues()
            values.put(COLUMN_ID, movie.id)
            values.put(COLUMN_TITLE, movie.title)
            values.put(COLUMN_POPULARITY, movie.popularity)
            values.put(COLUMN_VOTE_COUNT, movie.vote_count)
            values.put(COLUMN_VIDEO, movie.video)
            values.put(COLUMN_POSTER_PATH, movie.poster_path)
            values.put(COLUMN_ADULT, movie.adult)
            values.put(COLUMN_BACKDROP_PATH, movie.backdrop_path)
            values.put(COLUMN_ORIGINAL_LANGUAGE, movie.original_language)
            values.put(COLUMN_ORIGINAL_TITLE, movie.original_title)
            values.put(COLUMN_VOTE_AVG, movie.vote_average)
            values.put(COLUMN_OVERVIEW, movie.overview)
            values.put(COLUMN_RELEASE_DATE, movie.release_date)
            val db = this.writableDatabase
            db.insert(TABLE_NAME_PO, null, values)
            db.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun addUpcomingMovie(movie: MoviePojo) {
        try {
            val values = ContentValues()
            values.put(COLUMN_ID, movie.id)
            values.put(COLUMN_TITLE, movie.title)
            values.put(COLUMN_POPULARITY, movie.popularity)
            values.put(COLUMN_VOTE_COUNT, movie.vote_count)
            values.put(COLUMN_VIDEO, movie.video)
            values.put(COLUMN_POSTER_PATH, movie.poster_path)
            values.put(COLUMN_ADULT, movie.adult)
            values.put(COLUMN_BACKDROP_PATH, movie.backdrop_path)
            values.put(COLUMN_ORIGINAL_LANGUAGE, movie.original_language)
            values.put(COLUMN_ORIGINAL_TITLE, movie.original_title)
            values.put(COLUMN_VOTE_AVG, movie.vote_average)
            values.put(COLUMN_OVERVIEW, movie.overview)
            values.put(COLUMN_RELEASE_DATE, movie.release_date)
            val db = this.writableDatabase
            db.insert(TABLE_NAME_UP, null, values)
            db.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun addTopMovie(movie: MoviePojo) {
        try {
            val values = ContentValues()
            values.put(COLUMN_ID, movie.id)
            values.put(COLUMN_TITLE, movie.title)
            values.put(COLUMN_POPULARITY, movie.popularity)
            values.put(COLUMN_VOTE_COUNT, movie.vote_count)
            values.put(COLUMN_VIDEO, movie.video)
            values.put(COLUMN_POSTER_PATH, movie.poster_path)
            values.put(COLUMN_ADULT, movie.adult)
            values.put(COLUMN_BACKDROP_PATH, movie.backdrop_path)
            values.put(COLUMN_ORIGINAL_LANGUAGE, movie.original_language)
            values.put(COLUMN_ORIGINAL_TITLE, movie.original_title)
            values.put(COLUMN_VOTE_AVG, movie.vote_average)
            values.put(COLUMN_OVERVIEW, movie.overview)
            values.put(COLUMN_RELEASE_DATE, movie.release_date)
            val db = this.writableDatabase
            db.insert(TABLE_NAME_TO, null, values)
            db.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    fun addFavMovie(movie: MoviePojo) {
        try {
            val values = ContentValues()
            values.put(COLUMN_ID, movie.id)
            values.put(COLUMN_TITLE, movie.title)
            values.put(COLUMN_POPULARITY, movie.popularity)
            values.put(COLUMN_VOTE_COUNT, movie.vote_count)
            values.put(COLUMN_VIDEO, movie.video)
            values.put(COLUMN_POSTER_PATH, movie.poster_path)
            values.put(COLUMN_ADULT, movie.adult)
            values.put(COLUMN_BACKDROP_PATH, movie.backdrop_path)
            values.put(COLUMN_ORIGINAL_LANGUAGE, movie.original_language)
            values.put(COLUMN_ORIGINAL_TITLE, movie.original_title)
            values.put(COLUMN_VOTE_AVG, movie.vote_average)
            values.put(COLUMN_OVERVIEW, movie.overview)
            values.put(COLUMN_RELEASE_DATE, movie.release_date)
            val db = this.writableDatabase
            db.insert(TABLE_NAME_FA, null, values)
            db.close()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    fun getAllPopularMovies(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_PO", null)
    }

    fun deleteAllPopularMovies(): Int {
        val db = this.writableDatabase
        db.delete("$TABLE_NAME_PO",null,null)
        return 0
    }

    fun getAllFavMovies(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_FA", null)
    }

    fun deleteAllFavMovies(): Int {
        val db = this.writableDatabase
        db.delete("$TABLE_NAME_FA",null,null)
        return 0
    }

    fun getAllTopMovies(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_TO", null)
    }

    fun deleteAllTopMovies(): Int {
        val db = this.writableDatabase
        db.delete("$TABLE_NAME_TO",null,null)
        return 0
    }
    fun getAllUpMovies(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME_UP", null)
    }

    fun deleteAllUpMovies(): Int {
        val db = this.writableDatabase
        db.delete("$TABLE_NAME_UP",null,null)
        return 0
    }

    fun deleteFavMovie(id:Int): Int {
        val db = this.writableDatabase
        db.delete("$TABLE_NAME_FA","_id=$id",null)
        return 0
    }
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "movies.db"
        val TABLE_NAME_PO = "popularMovies"
        val TABLE_NAME_TO = "topMovies"
        val TABLE_NAME_UP = "upMovies"
        val TABLE_NAME_FA = "favMovies"
        val COLUMN_ID = "_id"
        val COLUMN_TITLE = "title"
        val COLUMN_POPULARITY = "popularity"
        val COLUMN_VOTE_COUNT = "voteCount"
        val COLUMN_VIDEO = "video"
        val COLUMN_POSTER_PATH = "poster_path"
        val COLUMN_ADULT = "ADULT"
        val COLUMN_BACKDROP_PATH = "backdrop_path"
        val COLUMN_ORIGINAL_LANGUAGE = "original_language"
        val COLUMN_ORIGINAL_TITLE = "original_title"
        val COLUMN_VOTE_AVG = "vote_avg"
        val COLUMN_OVERVIEW = "overview"
        val COLUMN_RELEASE_DATE = "release_date"
    }
}