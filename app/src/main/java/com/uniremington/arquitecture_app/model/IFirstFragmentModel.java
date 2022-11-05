package com.uniremington.arquitecture_app.model;

import java.sql.SQLException;

public interface IFirstFragmentModel {

    void fillLocalArraySpinner(int spinnerId, int arrayOptions);

    void getDataSpinners();

    void getEstados();

    void getEntorno();

    void save(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state);

    void findById(int appId) throws SQLException;

    void deleteById(int appId);

    void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state);
}
