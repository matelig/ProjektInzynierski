package com.polsl.android.employeetracker.Entity;

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
 * DAO for table "FUEL_LEVEL_DATA".
*/
public class FuelLevelDataDao extends AbstractDao<FuelLevelData, Void> {

    public static final String TABLENAME = "FUEL_LEVEL_DATA";

    /**
     * Properties of entity FuelLevelData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property RouteId = new Property(0, Long.class, "routeId", false, "ROUTE_ID");
        public final static Property Value = new Property(1, float.class, "value", false, "VALUE");
        public final static Property Timestamp = new Property(2, Long.class, "timestamp", false, "TIMESTAMP");
    }

    private Query<FuelLevelData> routeData_FuelLevelDataListQuery;

    public FuelLevelDataDao(DaoConfig config) {
        super(config);
    }
    
    public FuelLevelDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FUEL_LEVEL_DATA\" (" + //
                "\"ROUTE_ID\" INTEGER," + // 0: routeId
                "\"VALUE\" REAL NOT NULL ," + // 1: value
                "\"TIMESTAMP\" INTEGER);"); // 2: timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FUEL_LEVEL_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FuelLevelData entity) {
        stmt.clearBindings();
 
        Long routeId = entity.getRouteId();
        if (routeId != null) {
            stmt.bindLong(1, routeId);
        }
        stmt.bindDouble(2, entity.getValue());
 
        Long timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(3, timestamp);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, FuelLevelData entity) {
        stmt.clearBindings();
 
        Long routeId = entity.getRouteId();
        if (routeId != null) {
            stmt.bindLong(1, routeId);
        }
        stmt.bindDouble(2, entity.getValue());
 
        Long timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(3, timestamp);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public FuelLevelData readEntity(Cursor cursor, int offset) {
        FuelLevelData entity = new FuelLevelData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // routeId
            cursor.getFloat(offset + 1), // value
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // timestamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FuelLevelData entity, int offset) {
        entity.setRouteId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setValue(cursor.getFloat(offset + 1));
        entity.setTimestamp(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(FuelLevelData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(FuelLevelData entity) {
        return null;
    }

    @Override
    public boolean hasKey(FuelLevelData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "fuelLevelDataList" to-many relationship of RouteData. */
    public List<FuelLevelData> _queryRouteData_FuelLevelDataList(Long routeId) {
        synchronized (this) {
            if (routeData_FuelLevelDataListQuery == null) {
                QueryBuilder<FuelLevelData> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RouteId.eq(null));
                queryBuilder.orderRaw("T.'TIMESTAMP' ASC");
                routeData_FuelLevelDataListQuery = queryBuilder.build();
            }
        }
        Query<FuelLevelData> query = routeData_FuelLevelDataListQuery.forCurrentThread();
        query.setParameter(0, routeId);
        return query.list();
    }

}