package com.ticket.digitick;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    RadioButton rs10;
    RadioButton rs50;
    RadioButton rs100;
    EditText amount;
    RadioGroup moneyGroup;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_payment, container, false);
        moneyGroup = (RadioGroup)root.findViewById(R.id.money_group);
        rs10 = (RadioButton)root.findViewById(R.id.rs_10);
        rs50 = (RadioButton)root.findViewById(R.id.rs_50);
        rs100 = (RadioButton)root.findViewById(R.id.rs_100);
        amount = (EditText)root.findViewById(R.id.amount);
        rs10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText("10");
            }
        });
        rs50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText("50");
            }
        });
        rs100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount.setText("100");
            }
        });
        return root;
    }

}
