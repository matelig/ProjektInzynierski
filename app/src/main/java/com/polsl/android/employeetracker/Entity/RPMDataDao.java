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
 * DAO for table "RPMDATA".
*/
public class RPMDataDao extends AbstractDao<RPMData, Void> {

    public static final String TABLENAME = "RPMDATA";

    /**
     * Properties of entity RPMData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property RouteId = new Property(0, Long.class, "routeId", false, "ROUTE_ID");
        public final static Property Timestamp = new Property(1, Long.class, "timestamp", false, "TIMESTAMP");
        public final static Property Value = new Property(2, int.class, "value", false, "VALUE");
    }

    private Query<RPMData> routeData_RpmDataListQuery;

    public RPMDataDao(DaoConfig config) {
        super(config);
    }
    
    public RPMDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RPMDATA\" (" + //
                "\"ROUTE_ID\" INTEGER," + // 0: routeId
                "\"TIMESTAMP\" INTEGER," + // 1: timestamp
                "\"VALUE\" INTEGER NOT NULL );"); // 2: value
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RPMDATA\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RPMData entity) {
        stmt.clearBindings();
 
        Long routeId = entity.getRouteId();
        if (routeId != null) {
            stmt.bindLong(1, routeId);
        }
 
        Long timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(2, timestamp);
        }
        stmt.bindLong(3, entity.getValue());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RPMData entity) {
        stmt.clearBindings();
 
        Long routeId = entity.getRouteId();
        if (routeId != null) {
            stmt.bindLong(1, routeId);
        }
 
        Long timestamp = entity.getTimestamp();
        if (timestamp != null) {
            stmt.bindLong(2, timestamp);
        }
        stmt.bindLong(3, entity.getValue());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public RPMData readEntity(Cursor cursor, int offset) {
        RPMData entity = new RPMData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // routeId
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // timestamp
            cursor.getInt(offset + 2) // value
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RPMData entity, int offset) {
        entity.setRouteId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTimestamp(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setValue(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(RPMData entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(RPMData entity) {
        return null;
    }

    @Override
    public boolean hasKey(RPMData entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    /** Internal query to resolve the "rpmDataList" to-many relationship of RouteData. */
    public List<RPMData> _queryRouteData_RpmDataList(Long routeId) {
        synchronized (this) {
            if (routeData_RpmDataListQuery == null) {
                QueryBuilder<RPMData> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RouteId.eq(null));
                queryBuilder.orderRaw("T.'TIMESTAMP' ASC");
                routeData_RpmDataListQuery = queryBuilder.build();
            }
        }
        Query<RPMData> query = routeData_RpmDataListQuery.forCurrentThread();
        query.setParameter(0, routeId);
        return query.list();
    }

}
