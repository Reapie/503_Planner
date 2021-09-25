/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.htlkaindorf.ahif18.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author PD
 */
public class DataBase {

    private static DataBase instance;

    private String DB_URL;
    private String DB_USERNAME;
    private String DB_PASSWORD;
    private String DB_DRIVER;

    private Connection con;
    private CachedConnection cc;

    public static synchronized DataBase getInstance() throws ClassNotFoundException, SQLException, IOException {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    private DataBase() throws ClassNotFoundException, SQLException, IOException {
        loadProperties();
        Class.forName(DB_DRIVER);
        connect();
    }

    public void connect() throws SQLException {
        con = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        cc = new CachedConnection(con);
    }

    public void disconnect() throws SQLException {
        con.close();
        instance = null;
    }

    private void loadProperties() throws IOException {
        Properties props = new Properties();
        File file = new File("src/main/java/at/htlkaindorf/ahif18/db/database.properties");
        System.out.println(file.getAbsolutePath());
        FileInputStream fis = new FileInputStream(file);

        props.load(fis);
        DB_URL = props.getProperty("DB_URL");
        DB_USERNAME = props.getProperty("DB_USERNAME");
        DB_PASSWORD = props.getProperty("DB_PASSWORD");
        DB_DRIVER = props.getProperty("DB_DRIVER");

        /*
        DB_URL = jdbc:postgresql://localhost/HereGoesTheDB
        DB_USERNAME = kaindorf
        DB_PASSWORD = kaindorf
        DB_DRIVER = org.postgresql.Driver
         */
    }

    public Statement getStatement() throws Exception {
        if (cc != null && con != null) {
            return cc.getStatement();
        }
        throw new Exception("not connected to the DB");
    }

    public void releaseStatement(Statement stat) throws Exception {
        if (cc != null && con != null) {
            cc.releaseStatement(stat);
        } else {
            throw new Exception("not connected to the DB");
        }
    }

    public Connection getConnection() {
        return con;
    }
}
