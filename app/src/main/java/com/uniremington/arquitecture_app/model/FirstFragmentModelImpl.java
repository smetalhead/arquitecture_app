package com.uniremington.arquitecture_app.model;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.uniremington.arquitecture_app.R;
import com.uniremington.arquitecture_app.model.data.JDBCConnection;
import com.uniremington.arquitecture_app.presenter.FirstFragmentPresenterImpl;
import com.uniremington.arquitecture_app.presenter.IFirstFragmentPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


@RequiresApi(api = Build.VERSION_CODES.O)
public class FirstFragmentModelImpl extends AppCompatActivity implements IFirstFragmentModel {
    private IFirstFragmentPresenter fragmentPresenter;
    private static JDBCConnection db = new JDBCConnection();


    public FirstFragmentModelImpl(FirstFragmentPresenterImpl fragmentPresenter) {
        this.fragmentPresenter = fragmentPresenter;
    }

    @Override
    public void save(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) throws UnirestException, JSONException {
        JSONObject jsonBodyObj = new JSONObject();
        jsonBodyObj.put("nameApp", nameApp);
        jsonBodyObj.put("appUrl", appUrl);
        jsonBodyObj.put("phone", phone);
        jsonBodyObj.put("routingPoint", routingPoint);
        jsonBodyObj.put("environment", environment);
        jsonBodyObj.put("state", state);

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
        } catch (Exception e) {
            System.out.println("EXCP --- > " + e.getMessage());
            fragmentPresenter.showToastCreate(e.getMessage(), 0);
            fragmentPresenter.showToastCreate(postRequest("http://10.0.2.2:8080/uniremington-uat/api/app/save", jsonBodyObj.toString()), 3);
        }
    }


    @Override
    public void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) throws UnirestException, JSONException {
        JSONObject jsonBodyObj = new JSONObject();
        jsonBodyObj.put("idAppUrl", appId);
        jsonBodyObj.put("nameApp", nameApp);
        jsonBodyObj.put("appUrl", appUrl);
        jsonBodyObj.put("phone", phone);
        jsonBodyObj.put("routingPoint", routingPoint);
        jsonBodyObj.put("environment", environment);
        jsonBodyObj.put("state", state);
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
        } catch (Exception e) {
            System.out.println("EXCP --- > " + e.getMessage());
            fragmentPresenter.showToastCreate(postRequest("http://10.0.2.2:8080/uniremington-uat/api/app/save", jsonBodyObj.toString()), 3);

        }
    }

    @Override
    public void findById(int appId) throws UnirestException, SQLException, JSONException {
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
        } catch (Exception e) {
            fragmentPresenter.showToastCreate(e.getMessage(), 0);
            System.out.println("Exception --> " + e.getMessage());

            if (!getRequest("http://10.0.2.2:8080/uniremington-uat/api/app/" + appId).equals("null")) {
                JSONObject jsonObject = new JSONObject(getRequest("http://10.0.2.2:8080/uniremington-uat/api/app/" + appId));
                fragmentPresenter.fillFields(Integer.parseInt(jsonObject.getString("idAppUrl")), jsonObject.getString("nameApp"), jsonObject.getString("appUrl"),
                        jsonObject.getString("phone"), jsonObject.getString("routingPoint"),
                        jsonObject.getString("environment"), jsonObject.getString("state").charAt(0));
            } else {
                fragmentPresenter.fillFields(0,
                        "N/A", "N/A",
                        "N/A", "N/A",
                        "N/A", 'N');
            }

        }

    }

    @Override
    public void deleteById(int appId) throws UnirestException {
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
        } catch (Exception e) {
            System.out.println("EXCP --- > " + e.getMessage());
            System.out.println("EEEEEE ---> " + deleteRequest("http://10.0.2.2:8080/uniremington-uat/api/app/delete/" + appId));
            fragmentPresenter.showToastCreate(deleteRequest("http://10.0.2.2:8080/uniremington-uat/api/app/delete/" + appId), 0);
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


    public String getRequest(String url) throws UnirestException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    public String postRequest(String url, String bodyJSON) throws UnirestException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, bodyJSON);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();


        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String deleteRequest(String url) throws UnirestException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url(url)
                .method("DELETE", body)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

}

