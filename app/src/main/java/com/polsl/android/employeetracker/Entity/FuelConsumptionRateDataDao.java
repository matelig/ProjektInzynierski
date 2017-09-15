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
 * DAO for table "FUEL_CONSUMPTION_RATE_DATA".
*/
public class FuelConsumptionRateDataDao extends AbstractDao<FuelConsumptionRateData, Void> {

    public static final String TABLENAME = "FUEL_CONSUMPTION_RATE_DATA";

    /**
     * Properties of entity FuelConsumptionRateData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property RouteId = new Property(0, Long.class, "routeId", false, "ROUTE_ID");
        public final static Property Value = new Property(1, float.class, "value", false, "VALUE");
        public final static Property Timestamp = new Property(2, Long.class, "timestamp", false, "TIMESTAMP");
    }

    private Query<FuelConsumptionRateData> routeData_FuelConsumptionRateDataListQuery;

    public FuelConsumptionRateDataDao(DaoConfig config) {
        super(config);
    }
    
    public FuelConsumptionRateDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"FUEL_CONSUMPTION_RATE_DATA\" (" + //
                "\"ROUTE_ID\" INTEGER," + // 0: routeId
                "\"VALUE\" REAL NOT NULL ," + // 1: value
                "\"TIMESTAMP\" INTEGER);"); // 2: timestamp
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"FUEL_CONSUMPTION_RATE_DATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, FuelConsumptionRateData entity) {
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
    protected final void bindValues(SQLiteStatement stmt, FuelConsumptionRateData entity) {
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
    public FuelConsumptionRateData readEntity(Cursor cursor, int offset) {
        FuelConsumptionRateData entity = new FuelConsumptionRateData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // routeId
            cursor.getFloat(offset + 1), // value
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2) // timestamp
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, FuelConsumptionRateData entity, int offset) {
        entity.setRouteId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setValue(cursor.getFloat(offset + 1));
        entity.setTimestamp(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(FuelConsumptionRateData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(FuelConsumptionRateData entity) {
        return null;
    }

    @Override
    public boolean hasKey(FuelConsumptionRateData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "fuelConsumptionRateDataList" to-many relationship of RouteData. */
    public List<FuelConsumptionRateData> _queryRouteData_FuelConsumptionRateDataList(Long routeId) {
        synchronized (this) {
            if (routeData_FuelConsumptionRateDataListQuery == null) {
                QueryBuilder<FuelConsumptionRateData> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RouteId.eq(null));
                queryBuilder.orderRaw("T.'TIMESTAMP' ASC");
                routeData_FuelConsumptionRateDataListQuery = queryBuilder.build();
            }
        }
        Query<FuelConsumptionRateData> query = routeData_FuelConsumptionRateDataListQuery.forCurrentThread();
        query.setParameter(0, routeId);
        return query.list();
    }

}
