package ru.myapp.SellerTryOne.dbServices;


import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import ru.myapp.SellerTryOne.OrmLiteUtilities.DatabaseHelper;
import ru.myapp.SellerTryOne.Products.Product;

public class ServiceOrmLiteDB implements ServiceDB<Product, Integer> {
    private Dao<Product, Integer> productDao;

    public ServiceOrmLiteDB(final Context connectionSource) throws SQLException {
        DatabaseHelper dbHelper = OpenHelperManager.getHelper(connectionSource, DatabaseHelper.class);
        this.productDao = dbHelper.getProductDao();
    }

    //Create a new row in the db from an item.
    @Override
    public void add(final Product item) throws SQLException {
        productDao.create(item);
    }


    //returns the object that has the ID field which equals id or null if no matches.
    @Override
    public Product get(final Integer id) throws SQLException {
        return productDao.queryForId(id);
    }

    //deletes item with corresponding id
    @Override
    public void delete(final Integer id) throws SQLException {
        productDao.deleteById(id);
    }

    //store the fields from an object to the db row corresponding to the id from the item parameter
    @Override
    public void update(final Product Item) throws SQLException{
        productDao.update(Item);
    }
}
