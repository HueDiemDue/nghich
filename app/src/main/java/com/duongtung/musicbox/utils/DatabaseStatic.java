package com.duongtung.musicbox.utils;


import android.net.Uri;

public class DatabaseStatic {
	public final static  String id="id";
	public final static String name="name";
	public final static String data="data";
	public final static String type="type";
	public final static String albumArtUri="albumArtUri";
	public final static String fileUri="fileUri";
	public final static String databaseName = "mymusic.db";
	public final static String tableName = "equalizer";
	public final static String createTable ="CREATE TABLE " + tableName + "("
			+ id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ name + " varchar[500],"
			+type+" integer,"
			+ data + " varchar[500]);";
	public final static String dropTable = "DROP TABLE IF EXISTS " +tableName ;
	public final static String FAVORITE = "favorite";
	public final static String createFavorite ="CREATE TABLE " + FAVORITE + "("
			+ id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ name + " varchar[500],"
			+ fileUri +"  varchar[500]),"
			+ albumArtUri +"  varchar[500]),"
			+ type +" integer,"
			+ data + " varchar[500]);";
	public final static String  dropFavorite= "DROP TABLE IF EXISTS " +FAVORITE;
}
