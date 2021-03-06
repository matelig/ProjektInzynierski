package com.polsl.android.employeetracker.entity;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LOCATION_DATA".
*/
public class LocationDataDao extends AbstractDao<LocationData, Void> {

    public static final String TABLENAME = "LOCATION_DATA";

    /**
     * Properties of entity LocationData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Timestamp = new Property(0, Long.class, "timestamp", false, "TIMESTAMP");
        public final static Property Latitude = new Property(1, double.class, "latitude", false, "LATITUDE");
        public final static Property Longitude = new Property(2, double.class, "longitude", false, "LONGITUDE");
        public final static Property RouteId = new Property(3, Long.class, "routeId", false, "ROUTE_ID");
    }

    private Query<LocationData> routeData_LocationDataListQuery;

    public LocationDataDao(DaoConfig config) {
        super(config);
    }
    
    public LocationDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LOCATION_DATA\" (" + //
                "\"TIMESTAMP\" INTEGER," + // 0: timestamp
                "\"LATITUDE\" REAL NOT NULL ," + // 1: latitude
                "\"LONGITUDE\" REAL NOT NULL ," + // 2: longitude
                "\"ROUTE_ID\" INTEGER);"); // 3: routeId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LOCATION_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LocationData entity) {
        stmt.clearBindings();
 
        Long timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(1, timestamp);
        }
        stmt.bindDouble(2, entity.getLatitude());
        stmt.bindDouble(3, entity.getLongitude());
 
        Long routeId = entity.getRouteId();
        if (routeId != null) {
            stmt.bindLong(4, routeId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LocationData entity) {
        stmt.clearBindings();
 
        Long timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(1, timestamp);
        }
        stmt.bindDouble(2, entity.getLatitude());
        stmt.bindDouble(3, entity.getLongitude());
 
        Long routeId = entity.getRouteId();
        if (routeId != null) {
            stmt.bindLong(4, routeId);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public LocationData readEntity(Cursor cursor, int offset) {
        LocationData entity = new LocationData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // timestamp
            cursor.getDouble(offset + 1), // latitude
            cursor.getDouble(offset + 2), // longitude
            cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3) // routeId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LocationData entity, int offset) {
        entity.setTimestamp(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLatitude(cursor.getDouble(offset + 1));
        entity.setLongitude(cursor.getDouble(offset + 2));
        entity.setRouteId(cursor.isNull(offset + 3) ? null : cursor.getLong(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(LocationData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(LocationData entity) {
        return null;
    }

    @Override
    public boolean hasKey(LocationData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "locationDataList" to-many relationship of RouteData. */
    public List<LocationData> _queryRouteData_LocationDataList(Long routeId) {
        synchronized (this) {
            if (routeData_LocationDataListQuery == null) {
                QueryBuilder<LocationData> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RouteId.eq(null));
                queryBuilder.orderRaw("T.'TIMESTAMP' ASC");
                routeData_LocationDataListQuery = queryBuilder.build();
            }
        }
        Query<LocationData> query = routeData_LocationDataListQuery.forCurrentThread();
        query.setParameter(0, routeId);
        return query.list();
    }

}
