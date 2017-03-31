package ru.myapp.SellerTryOne;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import ru.myapp.SellerTryOne.OrmLiteUtilities.DatabaseHelper;
import ru.myapp.SellerTryOne.Products.Product;


public class ShowDataBaseActivity extends AppCompatActivity {
    TextView dataBaseItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_base);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showDB();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.mClearDB);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            public boolean onMenuItemClick(MenuItem item) {
                ShowDataBaseActivity.this.clearDBmethod();
                finish();
                startActivity(getIntent());
                return true;
            }
        });
        return true;
    }
    void showDB(){
        DatabaseHelper dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        dataBaseItem = (TextView) findViewById(R.id.dataBaseItem);
        try {
            Dao<Product, Integer> productDao = dbHelper.getProductDao();
            List<Product> products = productDao.queryForAll();

            for (Product i : products) {
                dataBaseItem.append(i.toString() + "\n\n\n");}
        } catch (SQLException e) {e.printStackTrace();}
    }

    boolean  clearDBmethod(){
        try {
            DatabaseHelper dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
            Dao<Product, Integer> productDao = dbHelper.getProductDao();
            TableUtils.clearTable(productDao.getConnectionSource(), Product.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

}

