package com.uniremington.arquitecture_app.model.data;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnection {
    Connection connection = null;

    public Connection conexionBD() {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://10.0.2.2:5432/ivrApp", "userapp", "admin");
        } catch (Exception er) {
            System.err.println("Error Conexion" + er.toString());
        }
        return connection;
    }

    protected void cerrar_conexion(Connection con) throws Exception {
        con.close();
    }
}