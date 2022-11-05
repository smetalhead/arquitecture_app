package com.uniremington.arquitecture_app.presenter;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.uniremington.arquitecture_app.model.FirstFragmentModelImpl;
import com.uniremington.arquitecture_app.model.IFirstFragmentModel;
import com.uniremington.arquitecture_app.view.FirstFragment;
import com.uniremington.arquitecture_app.view.IFirstFragmentView;

import java.sql.SQLException;

public class FirstFragmentPresenterImpl implements IFirstFragmentPresenter {
    private IFirstFragmentView fragmentView;
    private IFirstFragmentModel fragmentModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FirstFragmentPresenterImpl(FirstFragment firstFragment) {
        this.fragmentView = firstFragment;
        this.fragmentModel = new FirstFragmentModelImpl(this);
    }


    @Override
    public void deleteById(int appId) {
        fragmentModel.deleteById(appId);
    }

    @Override
    public void updateById(int appId, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) {
        if (fragmentView != null) {
            fragmentModel.updateById(appId,nameApp,appUrl,phone,routingPoint,environment,state);
        }
    }

    @Override
    public void findById(int appId) throws SQLException {
        fragmentModel.findById(appId);
    }

    @Override
    public void fillFields(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) {
        fragmentView.fillFields(idAppUrl, nameApp, appUrl, phone, routingPoint, environment, state);
    }

    @Override
    public void save(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) {
        if (fragmentView != null) {
            fragmentModel.save(idAppUrl, nameApp, appUrl, phone, routingPoint, environment, state);
        }
    }

    @Override
    public void fillLocalArraySpinner(int spinnerId, int arrayOptions) {
        if (fragmentView != null) {
            fragmentView.fillLocalArraySpinner(spinnerId, arrayOptions);
        }
    }

    @Override
    public void showToastCreate(String msg, int time) {
        if (fragmentView != null) {
            fragmentView.showToastCreate(msg, time);
        }
    }

    @Override
    public void getDataSpinners() {
        if (fragmentView != null) {
            fragmentModel.getDataSpinners();
        }
    }

}
