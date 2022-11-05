package com.uniremington.arquitecture_app.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.uniremington.arquitecture_app.R;
import com.uniremington.arquitecture_app.databinding.FragmentFirstBinding;
import com.uniremington.arquitecture_app.presenter.FirstFragmentPresenterImpl;
import com.uniremington.arquitecture_app.presenter.IFirstFragmentPresenter;

import java.sql.SQLException;

public class FirstFragment extends Fragment implements IFirstFragmentView {

    private FragmentFirstBinding binding;
    private EditText etIdApp, etDid, etRoutingPoint, etNombreApp, etAppURL;
    private TextView tvIdApp, tvDid, tvRoutingPoint, tvNombreApp, tvAppURL, tvEntorno, tvStatus;
    private Spinner spEntorno, spStatus;
    private Button buttonSave, buttonFind, buttonUpdate, buttonDelete;
    private String stEntorno;
    private char chEstado;

    private IFirstFragmentPresenter fragmentPresenter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fragmentPresenter = new FirstFragmentPresenterImpl(this);


        spEntorno = (Spinner) binding.spEntorno;
        spStatus = (Spinner) binding.spStatus;


        etIdApp = (EditText) binding.etIdApp;
        etDid = (EditText) binding.etDid;
        etRoutingPoint = (EditText) binding.etRoutingPoint;
        etNombreApp = (EditText) binding.etNombreApp;
        etAppURL = (EditText) binding.etAppURL;


        tvIdApp = (TextView) binding.tvIdApp;
        tvDid = (TextView) binding.tvDid;
        tvRoutingPoint = (TextView) binding.tvRoutingPoint;
        tvNombreApp = (TextView) binding.tvNombreApp;
        tvAppURL = (TextView) binding.tvAppURL;
        tvEntorno = (TextView) binding.tvEntorno;
        tvStatus = (TextView) binding.tvStatus;

        buttonSave = (Button) binding.buttonSave;
        buttonFind = (Button) binding.buttonFind;
        buttonUpdate = (Button) binding.buttonUpdate;
        buttonDelete = (Button) binding.buttonDelete;


        fragmentPresenter.getDataSpinners();

        return root;

    }

    @Override
    public void fillFields(int idAppUrl, String nameApp, String appUrl, String phone, String routingPoint, String environment, char state) {
        System.out.println(idAppUrl+" --- "+nameApp+" --- "+appUrl+" --- "+phone+" --- "+routingPoint+" --- "+environment+" --- "+state+" --- ");
        tvIdApp.setText(String.valueOf(idAppUrl));
        tvDid.setText(phone);
        tvRoutingPoint.setText(routingPoint);
        tvNombreApp.setText(nameApp);
        tvAppURL.setText(appUrl);
        tvEntorno.setText(environment);
        tvStatus.setText(String.valueOf(state));
    }

    @Override
    public void fillLocalArraySpinner(int spinnerId, int arrayOptions) {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                arrayOptions, R.layout.spinner_list);
        adapter.setDropDownViewResource(R.layout.spinner_list);

        switch (spinnerId) {
            case R.id.spEntorno:
                spEntorno.setAdapter(adapter);
                break;
            case R.id.spStatus:
                spStatus.setAdapter(adapter);
                break;
            default:
                break;
        }


    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etIdApp.getText().toString().equals("") && !etNombreApp.getText().toString().equals("") &&
                        !etAppURL.getText().toString().equals("") && !etDid.getText().toString().equals("") &&
                        !etRoutingPoint.getText().toString().equals("") && !stEntorno.equals("Sel.Entorno") && !Character.isLetter(chEstado)) {

                    fragmentPresenter.save(Integer.parseInt(etIdApp.getText().toString()), etNombreApp.getText().toString(),
                            etAppURL.getText().toString(), etDid.getText().toString(), etRoutingPoint.getText().toString(), stEntorno, chEstado);

                } else {
                    showToastCreate("Llene todos los campos", 0);
                }


            }
        });

        buttonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etIdApp.getText().toString().equals("")) {
                    try {
                        fragmentPresenter.findById(Integer.parseInt(etIdApp.getText().toString()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    showToastCreate("Ingrese un id válido", 0);
                }

            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etIdApp.getText().toString().equals("") && !etNombreApp.getText().toString().equals("") &&
                        !etAppURL.getText().toString().equals("") && !etDid.getText().toString().equals("") &&
                        !etRoutingPoint.getText().toString().equals("") && !stEntorno.equals("Sel.Entorno") && !Character.isLetter(chEstado)) {

                    fragmentPresenter.updateById(Integer.parseInt(etIdApp.getText().toString()), etNombreApp.getText().toString(),
                            etAppURL.getText().toString(), etDid.getText().toString(), etRoutingPoint.getText().toString(), stEntorno, chEstado);

                } else {
                    showToastCreate("Llene todos los campos", 0);
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etIdApp.getText().toString().equals("")) {
                    fragmentPresenter.deleteById(Integer.parseInt(etIdApp.getText().toString()));
                } else {
                    showToastCreate("Llene todos los campos", 0);
                }
            }
        });

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("STATUS VIEW", String.valueOf(((String) parent.getSelectedItem()).charAt(0)));
                chEstado = ((String) parent.getSelectedItem()).charAt(0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                chEstado = ((String) parent.getSelectedItem()).charAt(0);

            }
        });

        spEntorno.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("ENVIRON VIEW", String.valueOf(parent.getSelectedItem()));
                stEntorno = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stEntorno = (String) parent.getItemAtPosition(0);
            }
        });
    }

    public void save() {

    }


    @Override
    public void showToastCreate(String msg, int time) {
        Toast.makeText(getContext(), msg, time).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}