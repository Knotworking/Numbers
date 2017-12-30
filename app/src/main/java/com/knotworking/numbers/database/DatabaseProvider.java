package com.knotworking.numbers.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by BRL on 13/04/17.
 */

public class DatabaseProvider extends ContentProvider {

    private static final int COUNTER_LIST = 1;
    private static final int COUNTER_ID = 2;
    private static final int EXCHANGE_RATE_LIST = 3;
    private static final int EXCHANGE_RATE_ID = 4;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(DatabaseContract.AUTHORITY,
                "counters",
                COUNTER_LIST);
        URI_MATCHER.addURI(DatabaseContract.AUTHORITY,
                "counters/*",
                COUNTER_ID);

        URI_MATCHER.addURI(DatabaseContract.AUTHORITY,
                "exchange_rates",
                EXCHANGE_RATE_LIST);
        URI_MATCHER.addURI(DatabaseContract.AUTHORITY,
                "exchange_rates/*",
                EXCHANGE_RATE_ID);
    }

    private DatabaseOpenHelper helper;

    @Override
    public boolean onCreate() {
        helper = new DatabaseOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = helper.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        switch (URI_MATCHER.match(uri)) {
            case COUNTER_LIST:
                builder.setTables(DatabaseContract.Counters.TABLE);
                break;
            case COUNTER_ID:
                builder.setTables(DatabaseContract.Counters.TABLE);
                selection = DatabaseContract.Counters._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            case EXCHANGE_RATE_LIST:
                builder.setTables(DatabaseContract.ExchangeRates.TABLE);
                break;
            case EXCHANGE_RATE_ID:
                builder.setTables(DatabaseContract.ExchangeRates.TABLE);
                selection = DatabaseContract.Counters._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                throw new IllegalArgumentException("Failed to query URI: " + uri);
        }

        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case COUNTER_LIST:
                return DatabaseContract.Counters.CONTENT_TYPE;
            case COUNTER_ID:
                return DatabaseContract.Counters.CONTENT_ITEM_TYPE;
            case EXCHANGE_RATE_LIST:
                return DatabaseContract.ExchangeRates.CONTENT_TYPE;
            case EXCHANGE_RATE_ID:
                return DatabaseContract.ExchangeRates.CONTENT_ITEM_TYPE;
            default:
                throw null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        long id;
        switch (URI_MATCHER.match(uri)) {
            case COUNTER_LIST:
                id = db.insertWithOnConflict(DatabaseContract.Counters.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;
            case EXCHANGE_RATE_LIST:
                id = db.insertWithOnConflict(DatabaseContract.ExchangeRates.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                break;
            default:
                throw new IllegalArgumentException("Failed to insert into URI: " + uri);
        }
        return getUriForId(id, uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int deleted;

        switch (URI_MATCHER.match(uri)) {
            case COUNTER_LIST:
                deleted = db.delete(DatabaseContract.Counters.TABLE, selection, selectionArgs);
                break;
            case COUNTER_ID:
                selection = DatabaseContract.Counters._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                deleted = db.delete(DatabaseContract.Counters.TABLE, selection, selectionArgs);
                break;
            case EXCHANGE_RATE_LIST:
                deleted = db.delete(DatabaseContract.ExchangeRates.TABLE, selection, selectionArgs);
                break;
            case EXCHANGE_RATE_ID:
                selection = DatabaseContract.ExchangeRates._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                deleted = db.delete(DatabaseContract.ExchangeRates.TABLE, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed to delete from URI: " + uri);
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = helper.getReadableDatabase();
        int count;

        switch (URI_MATCHER.match(uri)) {
            case COUNTER_LIST:
                count = db.update(DatabaseContract.Counters.TABLE, values, selection, selectionArgs);
                break;
            case COUNTER_ID:
                selection = DatabaseContract.Counters.TABLE + "." + DatabaseContract.Counters._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                count = db.update(DatabaseContract.Counters.TABLE, values, selection, selectionArgs);
                break;
            case EXCHANGE_RATE_LIST:
                count = db.update(DatabaseContract.ExchangeRates.TABLE, values, selection, selectionArgs);
                break;
            case EXCHANGE_RATE_ID:
                selection = DatabaseContract.ExchangeRates.TABLE + "." + DatabaseContract.Counters._ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                count = db.update(DatabaseContract.ExchangeRates.TABLE, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Failed to update URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(itemUri, null);
            return itemUri;
        } else if (id == -1) {
            //happens when insertOnConflict IGNORE
            return uri;
        }
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }
}
