package com.uniremington.arquitecture_app.presenter;

import java.sql.SQLException;

public interface IFirstFragmentPresenter {

    void getDataSpinners();

    void fillLocalArraySpinner(int spinnerId, int arrayOptions);

    void showToastCreate(String msg, int time);

    void save(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state);

    void fillFields(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state);

    void findById(int appId) throws SQLException;

    void deleteById(int appId);

    void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state);
}
