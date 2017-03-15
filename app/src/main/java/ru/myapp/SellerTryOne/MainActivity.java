package ru.myapp.SellerTryOne;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import java.sql.SQLException;
import java.util.List;

import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;
import ru.myapp.SellerTryOne.OrmLiteUtilities.DatabaseHelper;
import ru.myapp.SellerTryOne.Products.Product;

public class MainActivity extends AppCompatActivity {

    private static final String RESULT = "result";

    private TextView textViewResult;
    private Button scanButton;
    private Button showDataBaseButton;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = (TextView) findViewById(R.id.result);
        scanButton = (Button) findViewById(R.id.btn_scan);
        scanButton.setOnClickListener(getOnClickListener());

        showDataBaseButton = (Button) findViewById(R.id.showDataBase);
        showDataBaseButton.setOnClickListener(showDataBaseActivity());

        dbHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);

    }

    @NonNull
    private View.OnClickListener getOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ZxingOrient(MainActivity.this).initiateScan();
            }
        };
    }

    private View.OnClickListener showDataBaseActivity() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowDataBaseActivity.class));
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        ZxingOrientResult scanResult = ZxingOrient.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            String resultOfScanning = scanResult.getContents();
            textViewResult.setText(resultOfScanning);
            try {
                Dao<Product, Integer> productDao = dbHelper.getProductDao();
                QueryBuilder<Product, Integer> queryBuilder = productDao.queryBuilder();
                queryBuilder.where().eq("barcode", resultOfScanning);
                List<Product> queryResult = queryBuilder.query();
                Integer a = queryResult.size();
                Log.d("Demo", a.toString());
                if(queryResult.size() == 0) {
                    productDao.create(new Product(resultOfScanning, 1));
                }
                else{
                    UpdateBuilder<Product, Integer> updateBuilder = productDao.updateBuilder();
                    updateBuilder.updateColumnValue("amount", "amount+1");
                    updateBuilder.where().eq("barcode", resultOfScanning);
                    updateBuilder.update();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(RESULT, (String) textViewResult.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textViewResult.setText(savedInstanceState.getString(RESULT));
    }
}
