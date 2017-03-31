package ru.myapp.SellerTryOne.OrmLiteUtilities;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.IOException;
import java.sql.SQLException;

import ru.myapp.SellerTryOne.Products.Product;


public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private final static Class<?> classes[] = new Class[] {Product.class};
    public static void main(String[] args) throws SQLException, IOException {
        writeConfigFile("ormlite_config.txt", classes);
    }
}