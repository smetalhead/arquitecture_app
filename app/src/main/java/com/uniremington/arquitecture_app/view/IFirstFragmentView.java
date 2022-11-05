package com.uniremington.arquitecture_app.view;

public interface IFirstFragmentView {

    void fillLocalArraySpinner(int spinnerId, int arrayOptions);

    void showToastCreate(String msg, int time);

    void fillFields(int idAppUrl, String nameApp, String appUrl,String phone, String routingPoint, String environment, char state);
}
