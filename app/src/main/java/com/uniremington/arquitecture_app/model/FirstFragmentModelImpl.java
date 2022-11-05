package com.uniremington.arquitecture_app.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.uniremington.arquitecture_app.R;
import com.uniremington.arquitecture_app.model.data.JDBCConnection;
import com.uniremington.arquitecture_app.presenter.FirstFragmentPresenterImpl;
import com.uniremington.arquitecture_app.presenter.IFirstFragmentPresenter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;


@RequiresApi(api = Build.VERSION_CODES.O)
public class FirstFragmentModelImpl extends AppCompatActivity implements IFirstFragmentModel {
    private IFirstFragmentPresenter fragmentPresenter;
    private static JDBCConnection db = new JDBCConnection();

    
    public FirstFragmentModelImpl(FirstFragmentPresenterImpl fragmentPresenter) {
        this.fragmentPresenter = fragmentPresenter;
    }

    @Override
    public void save(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) {
        try {
            PreparedStatement statement = db.conexionBD().prepareStatement("INSERT INTO public.apps(" + "\n" +
                    "didivr, nombreivr, puntoruteo, urlivr, entorno, estado)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?);");
            statement.setString(1, phone);
            statement.setString(2, nameApp);
            statement.setString(3, routingPoint);
            statement.setString(4, appUrl);
            statement.setString(5, environment);
            statement.setString(6, String.valueOf(state));

            int resultSet = statement.executeUpdate();
            if (resultSet > 0) {
                fragmentPresenter.showToastCreate("Registro Guardado", 0);
                System.out.println("UPDATED --> " + resultSet);
            } else {
                fragmentPresenter.showToastCreate("No se puede guardar registro", 0);
                fragmentPresenter.fillFields(0,
                        "N/A", "N/A",
                        "N/A", "N/A",
                        "N/A", 'N');
            }
        } catch (SQLException e) {
            System.out.println("EXCP --- > " + e.getMessage());
            fragmentPresenter.showToastCreate(e.getMessage(), 0);
            fragmentPresenter.fillFields(0,
                    "N/A", "N/A",
                    "N/A", "N/A",
                    "N/A", 'N');
        }
    }


    @Override
    public void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) {
        try {
            PreparedStatement statement = db.conexionBD().prepareStatement("UPDATE apps" + "\n" +
                    "SET didivr=?, nombreivr=?, puntoruteo=?, urlivr=?, entorno=?, estado=?" + "\n" +
                    "WHERE idivrurl= ?;");
            statement.setString(1, phone);
            statement.setString(2, nameApp);
            statement.setString(3, routingPoint);
            statement.setString(4, appUrl);
            statement.setString(5, environment);
            statement.setString(6, String.valueOf(state));
            statement.setInt(7, appId);

            int resultSet = statement.executeUpdate();
            if (resultSet > 0) {
                fragmentPresenter.showToastCreate("Registro Actualizado", 0);
                System.out.println("UPDATED --> " + resultSet);
            } else {
                fragmentPresenter.showToastCreate("Id App no existe", 0);
                fragmentPresenter.fillFields(0,
                        "N/A", "N/A",
                        "N/A", "N/A",
                        "N/A", 'N');
            }
        } catch (SQLException e) {
            System.out.println("EXCP --- > " + e.getMessage());
            fragmentPresenter.showToastCreate(e.getMessage(), 0);
            fragmentPresenter.fillFields(0,
                    "N/A", "N/A",
                    "N/A", "N/A",
                    "N/A", 'N');
        }
    }

    @Override
    public void findById(int appId) {
        try {
            PreparedStatement statement = db.conexionBD().prepareStatement("SELECT idivrurl, didivr, nombreivr, puntoruteo, urlivr, entorno, fechacreacion, estado" + "\n" +
                    "FROM apps where idivrurl = ?");
            statement.setInt(1, appId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    fragmentPresenter.fillFields(resultSet.getInt("idivrurl"),
                            resultSet.getString("nombreivr"), resultSet.getString("urlivr"),
                            resultSet.getString("didivr"), resultSet.getString("puntoruteo"),
                            resultSet.getString("entorno"), resultSet.getString("estado").charAt(0));
                }
            } else {
                fragmentPresenter.showToastCreate("App no Existe", 0);
                fragmentPresenter.fillFields(0,
                        "N/A", "N/A",
                        "N/A", "N/A",
                        "N/A", 'N');
            }
        } catch (SQLException e) {
            fragmentPresenter.showToastCreate(e.getMessage(), 0);
            fragmentPresenter.fillFields(0,
                    "N/A", "N/A",
                    "N/A", "N/A",
                    "N/A", 'N');
        }

    }

    @Override
    public void deleteById(int appId) {
        try {
            PreparedStatement statement = db.conexionBD().prepareStatement("DELETE FROM apps" + "\n" +
                    "WHERE idivrurl = ?");
            statement.setInt(1, appId);
            int resultSet = statement.executeUpdate();
            if (resultSet > 0) {
                fragmentPresenter.showToastCreate("Registro Borrado", 0);
                System.out.println("DELETED --> " + resultSet);
            } else {
                fragmentPresenter.showToastCreate("App no existe", 0);
                fragmentPresenter.fillFields(0,
                        "N/A", "N/A",
                        "N/A", "N/A",
                        "N/A", 'N');
            }
        } catch (SQLException e) {
            System.out.println("EXCP --- > " + e.getMessage());
            fragmentPresenter.showToastCreate(e.getMessage(), 0);
            fragmentPresenter.fillFields(0,
                    "N/A", "N/A",
                    "N/A", "N/A",
                    "N/A", 'N');
        }
    }

    @Override
    public void getDataSpinners() {
        getEstados();
        getEntorno();
    }

    @Override
    public void fillLocalArraySpinner(int spinnerId, int arrayOptions) {
        fragmentPresenter.fillLocalArraySpinner(spinnerId, arrayOptions);
    }

    @Override
    public void getEstados() {
        fillLocalArraySpinner(R.id.spStatus, R.array.estado_array);
    }

    @Override
    public void getEntorno() {
        fillLocalArraySpinner(R.id.spEntorno, R.array.entorno_array);
    }
}

