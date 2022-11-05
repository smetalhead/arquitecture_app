package com.uniremington.arquitecture_app.model;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.sql.SQLException;

public interface IFirstFragmentModel {

    void fillLocalArraySpinner(int spinnerId, int arrayOptions);

    void getDataSpinners();

    void getEstados();

    void getEntorno();

    void save(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) throws UnirestException, JSONException;

    void findById(int appId) throws SQLException, UnirestException, JSONException;

    void deleteById(int appId) throws UnirestException;

    void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) throws UnirestException, JSONException;
}
