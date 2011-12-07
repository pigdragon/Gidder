package net.antoniy.gidder.db;

import java.sql.SQLException;

import net.antoniy.gidder.db.entity.Permission;
import net.antoniy.gidder.db.entity.Repository;
import net.antoniy.gidder.db.entity.User;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {
	private final static String TAG = DBHelper.class.getSimpleName();
	
	private final static String DB_NAME = "gidder.db";
	private final static int DB_VERSION = 20111127;
	
	private Dao<User, Integer> userDao;
	private Dao<Repository, Integer> repositoryDao;
	private Dao<Permission, Integer> permissionDao;
	
	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
//		
//		Log.i(TAG, "Creating the database...");
//		
//		// Foreign keys are disabled by default in SQLite, so we need to enable them. 
//		// This works since Froyo 2.2 because this feature is in SQLite since version 3.6.19 and Froyo comes with 2.6.22
//		database.execSQL("PRAGMA foreign_keys=ON;");
		
		try {
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Repository.class);
			TableUtils.createTable(connectionSource, Permission.class);
		} catch (SQLException e) {
			Log.e(TAG, "Unable to create datbases", e);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Repository.class, true);
			TableUtils.dropTable(connectionSource, Permission.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			Log.e(TAG, "Unable to upgrade database from version " + oldVersion + " to new " + newVersion, e);
		}
	}
	
	public Dao<User, Integer> getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = getDao(User.class);
		}
		return userDao;
	}
	
	public Dao<Repository, Integer> getRepositoryDao() throws SQLException {
		if (repositoryDao == null) {
			repositoryDao = getDao(Repository.class);
		}
		return repositoryDao;
	}
	
	public Dao<Permission, Integer> getPermissionDao() throws SQLException {
		if (permissionDao == null) {
			permissionDao = getDao(Permission.class);
		}
		return permissionDao;
	}
	
//	@Override
//	public void onCreate(SQLiteDatabase db) {
//		String createUsers = String.format("create table %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", 
//				USERS, USERS_ID, USERS_USERNAME, USERS_PASSWORD, USERS_FULLNAME, USERS_EMAIL);
//		db.execSQL(createUsers);
//		Log.i(TAG, "SQL: " + createUsers);
//		
////		ContentValues values = new ContentValues();
////		values.put(USERS_USERNAME, "test");
////		values.put(USERS_PASSWORD, encodeSha1("123", "test"));
////		values.put(USERS_FULLNAME, "Test Testov");
////		values.put(USERS_EMAIL, "test@testing.com");
////		
////		db.insert(USERS, null, values);
//	}
//
//	@Override
//	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//		db.rawQuery("", null);
//		
//	}
	
//	private String encodeSha1(String input, String keyString) {
//			byte[] bytes = null;
//			try {
//				SecretKeySpec key = new SecretKeySpec((keyString).getBytes("UTF-8"), "HmacSHA1");
//				Mac mac = Mac.getInstance("HmacSHA1");
//				mac.init(key);
//
//				bytes = mac.doFinal(input.getBytes("UTF-8"));
//			} catch (Exception e) {
//				Log.e(TAG, "Unable to encode to SHA-1.", e);
//				return null;
//			}
//
//			return new String(Base64.encode(bytes, Base64.DEFAULT));
//	}

}