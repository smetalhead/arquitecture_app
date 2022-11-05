package com.uniremington.arquitecture_app.presenter;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.sql.SQLException;

public interface IFirstFragmentPresenter {

    void getDataSpinners();

    void fillLocalArraySpinner(int spinnerId, int arrayOptions);

    void showToastCreate(String msg, int time);

    void save(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) throws UnirestException, JSONException;

    void fillFields(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state);

    void findById(int appId) throws SQLException, UnirestException, JSONException;

    void deleteById(int appId) throws UnirestException;

    void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) throws UnirestException, JSONException;
}
