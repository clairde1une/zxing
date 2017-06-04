package ru.myapp.SellerTryOne.OrmLiteUtilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ru.myapp.SellerTryOne.Products.Product;
import ru.myapp.SellerTryOne.R;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    private Dao<Product, Integer> productDao = null;
    private RuntimeExceptionDao<Product, Integer> productRuntimeDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Product.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try {
            TableUtils.dropTable(connectionSource, Product.class,  true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<Product, Integer> getProductRuntimeDao() {
        if(productRuntimeDao == null){
            productRuntimeDao = getRuntimeExceptionDao(Product.class);
        }
        return productRuntimeDao;
    }

    public Dao<Product, Integer> getProductDao() throws SQLException{
        if (productDao == null){
            productDao = getDao(Product.class);
        }
        return productDao;

    }
}
