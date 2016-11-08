package com.duongtung.musicbox.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.duongtung.musicbox.utils.DatabaseStatic;

public class DatabaseHandler extends SQLiteOpenHelper {
	Context context;

	public DatabaseHandler(Context context) {
		super(context, DatabaseStatic.databaseName, null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseStatic.createFavorite);
		db.execSQL(DatabaseStatic.createTable);
//		insertDefault(db);
	}

	public void insertDefault(SQLiteDatabase db) {
//		ArrayList<EqualizerObject> arrayList = new ArrayList<EqualizerObject>();
//		EqualizerObject normal = new EqualizerObject(
//				"Normal",
//				0,
//				"[{indext:0,level:600,band:0},{indext:1,level:0,band:1},{indext:2,level:0,band:2},{indext:3,level:0,band:3},{indext:4,level:600,band:4}]");
//		arrayList.add(normal);
//		EqualizerObject classical = new EqualizerObject(
//				"Classical",
//				0,
//				"[{indext:0,level:1000,band:0},{indext:1,level:600,band:1},{indext:2,level:-400,band:2},{indext:3,level:800,band:3},{indext:4,level:800,band:4}]");
//		arrayList.add(classical);
//		EqualizerObject dance = new EqualizerObject("Dance", 0,
//				"[{indext:0,level:1100,band:0}," + "{indext:1,level:0,band:1},"
//						+ "{indext:2,level:400,band:2},"
//						+ "{indext:3,level:800,band:3}"
//						+ ",{indext:4,level:200,band:4}]");
//		arrayList.add(dance);
//		EqualizerObject flat = new EqualizerObject("Flat", 0,
//				"[{indext:0,level:0,band:0}," + "{indext:1,level:0,band:1},"
//						+ "{indext:2,level:0,band:2},"
//						+ "{indext:3,level:0,band:3}"
//						+ ",{indext:4,level:0,band:4}]");
//		arrayList.add(flat);
//		EqualizerObject folk = new EqualizerObject("Folk", 0,
//				"[{indext:0,level:600,band:0}," + "{indext:1,level:0,band:1},"
//						+ "{indext:2,level:0,band:2},"
//						+ "{indext:3,level:400,band:3}"
//						+ ",{indext:4,level:-200,band:4}]");
//		arrayList.add(folk);
//		EqualizerObject heavymetal = new EqualizerObject("Heavy Metal", 0,
//				"[{indext:0,level:800,band:0},"
//						+ "{indext:1,level:200,band:1},"
//						+ "{indext:2,level:1500,band:2},"
//						+ "{indext:3,level:600,band:3}"
//						+ ",{indext:4,level:0,band:4}]");
//		arrayList.add(heavymetal);
//		EqualizerObject hiphop = new EqualizerObject("Hip hop", 0,
//				"[{indext:0,level:1000,band:0},"
//						+ "{indext:1,level:600,band:1},"
//						+ "{indext:2,level:0,band:2},"
//						+ "{indext:3,level:200,band:3}"
//						+ ",{indext:4,level:600,band:4}]");
//		arrayList.add(hiphop);
//		EqualizerObject jazz = new EqualizerObject("Jazz", 0,
//				"[{indext:0,level:800,band:0},"
//						+ "{indext:1,level:400,band:1},"
//						+ "{indext:2,level:-400,band:2},"
//						+ "{indext:3,level:400,band:3}"
//						+ ",{indext:4,level:1000,band:4}]");
//		arrayList.add(jazz);
//		EqualizerObject pop = new EqualizerObject("Pop", 0,
//				"[{indext:0,level:-200,band:0},"
//						+ "{indext:1,level:400,band:1},"
//						+ "{indext:2,level:1000,band:2},"
//						+ "{indext:3,level:200,band:3}"
//						+ ",{indext:4,level:400,band:4}]");
//		arrayList.add(pop);
//		EqualizerObject rock = new EqualizerObject("Rock", 0,
//				"[{indext:0,level:1000,band:0},"
//						+ "{indext:1,level:600,band:1},"
//						+ "{indext:2,level:-200,band:2},"
//						+ "{indext:3,level:600,band:3}"
//						+ ",{indext:4,level:1000,band:4}]");
//		arrayList.add(rock);
//		EqualizerObject latin = new EqualizerObject("Latin", 0,
//				"[{indext:0,level:1200,band:0},"
//						+ "{indext:1,level:400,band:1},"
//						+ "{indext:2,level:-600,band:2},"
//						+ "{indext:3,level:200,band:3}"
//						+ ",{indext:4,level:1400,band:4}]");
//		arrayList.add(latin);
//		for (int i = 0; i < arrayList.size(); i++) {
//			InsertEqualizerDefault(arrayList.get(i), db);
//
//		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DatabaseStatic.dropTable);
		onCreate(db);
	}

//	private long InsertEqualizerDefault(EqualizerObject data, SQLiteDatabase db) {
//		ContentValues content = new ContentValues();
//		content.put(DatabaseStatic.name, data.getName());
//		content.put(DatabaseStatic.type, data.getType());
//		content.put(DatabaseStatic.data, data.getData());
//		return db.insert(DatabaseStatic.tableName, null, content);
//
//	}
}
