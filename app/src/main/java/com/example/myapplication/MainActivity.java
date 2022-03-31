package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private RecyclerView companyRV;
    private Button add;
    private Button next;
    private Boolean haveTwoOrMoreCompany = false;
    private Boolean haveCriteria = false;
    private ArrayList<CompanyModel> companyModelArrayList;
    private ArrayList<Criteria> criteriaArrayList;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar:
                showBottomSheetDialog(-1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        companyRV = findViewById(R.id.idRVCompany);
        add = findViewById(R.id.btnAdd);
        next = findViewById(R.id.btnNext);

        companyModelArrayList = new ArrayList<>();
        criteriaArrayList = new ArrayList<>();

        initAdapter();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialogCriteria();
                add.setText("Добавлено критерии | Изменить");
                add.setBackground(getResources().getDrawable(R.drawable.rounded_green));
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setVectorCompany();
                System.out.println("test->>>>>>>>>>>> " + companyModelArrayList.get(0).getVector());

                if(checkFields()){

                }
            }
        });
    }
    private void setVectorCompany(){

        for(int i = 0; i < companyModelArrayList.size(); i++){
            Map<String, Double> vector = new HashMap<>();
            Map<String, Double> val = companyModelArrayList.get(i).getVal();
            vector.put("one", Math.pow(val.get("aa") * val.get("ab") * val.get("ac") * val.get("ad"), 0.25));
            vector.put("two", Math.pow(val.get("ba") * val.get("bb") * val.get("bc") * val.get("bd"), 0.25));
            vector.put("three", Math.pow(val.get("ca") * val.get("cb") * val.get("cc") * val.get("cd"), 0.25));
            vector.put("four", Math.pow(val.get("da") * val.get("db") * val.get("dc") * val.get("dd"), 0.25));
            vector.put("all", vector.get("one") + vector.get("two") + vector.get("three") + vector.get("four"));
            companyModelArrayList.get(i).setVector(vector);
        }
        setWeightCompany();
    }
    private void setWeightCompany(){
        for(int i = 0; i < companyModelArrayList.size(); i++) {
            Map<String, Double> weight = new HashMap<>();
            weight.put("one", companyModelArrayList.get(i).getVector().get("one") / companyModelArrayList.get(i).getVector().get("all"));
            weight.put("two", companyModelArrayList.get(i).getVector().get("two") / companyModelArrayList.get(i).getVector().get("all"));
            weight.put("three", companyModelArrayList.get(i).getVector().get("three") / companyModelArrayList.get(i).getVector().get("all"));
            weight.put("four", companyModelArrayList.get(i).getVector().get("four") / companyModelArrayList.get(i).getVector().get("all"));
            companyModelArrayList.get(i).setWeight(weight);
        }
        setVectorCriteria();
    }
    private void setVectorCriteria(){
        for(int i = 0; i < criteriaArrayList.size(); i++){
            Map<String, Double> vector = new HashMap<>();
            Map<String, Double> val = criteriaArrayList.get(i).getVal();
            vector.put("one", Math.pow(val.get("aa") * val.get("ab") * val.get("ac") * val.get("ad"), 0.25));
            vector.put("two", Math.pow(val.get("ba") * val.get("bb") * val.get("bc") * val.get("bd"), 0.25));
            vector.put("three", Math.pow(val.get("ca") * val.get("cb") * val.get("cc") * val.get("cd"), 0.25));
            vector.put("four", Math.pow(val.get("da") * val.get("db") * val.get("dc") * val.get("dd"), 0.25));
            vector.put("all", vector.get("one") + vector.get("two") + vector.get("three") + vector.get("four"));
            criteriaArrayList.get(i).setVector(vector);
        }
        setWeightCriteria();
    }
    private void setWeightCriteria(){
        for(int i = 0; i < criteriaArrayList.size(); i++){
            Map<String, Double> weight = new HashMap<>();
            weight.put("one", criteriaArrayList.get(i).getVector().get("one") / criteriaArrayList.get(i).getVector().get("all"));
            weight.put("two", criteriaArrayList.get(i).getVector().get("two") / criteriaArrayList.get(i).getVector().get("all"));
            weight.put("three", criteriaArrayList.get(i).getVector().get("three") / criteriaArrayList.get(i).getVector().get("all"));
            weight.put("four", criteriaArrayList.get(i).getVector().get("four") / criteriaArrayList.get(i).getVector().get("all"));
            criteriaArrayList.get(i).setWeight(weight);
        }
        calculate();
    }
    private void calculate(){
        Double A = companyModelArrayList.get(0).getWeight().get("one") * criteriaArrayList.get(0).getWeight().get("one")
                + companyModelArrayList.get(1).getWeight().get("one") * criteriaArrayList.get(0).getWeight().get("two")
                + companyModelArrayList.get(2).getWeight().get("one") * criteriaArrayList.get(0).getWeight().get("three")
                + companyModelArrayList.get(3).getWeight().get("one") * criteriaArrayList.get(0).getWeight().get("four");
        Double B = companyModelArrayList.get(0).getWeight().get("two") * criteriaArrayList.get(0).getWeight().get("one")
                + companyModelArrayList.get(1).getWeight().get("two") * criteriaArrayList.get(0).getWeight().get("two")
                + companyModelArrayList.get(2).getWeight().get("two") * criteriaArrayList.get(0).getWeight().get("three")
                + companyModelArrayList.get(3).getWeight().get("two") * criteriaArrayList.get(0).getWeight().get("four");
        Double C = companyModelArrayList.get(0).getWeight().get("three") * criteriaArrayList.get(0).getWeight().get("one")
                + companyModelArrayList.get(1).getWeight().get("three") * criteriaArrayList.get(0).getWeight().get("two")
                + companyModelArrayList.get(2).getWeight().get("three") * criteriaArrayList.get(0).getWeight().get("three")
                + companyModelArrayList.get(3).getWeight().get("three") * criteriaArrayList.get(0).getWeight().get("four");
        Double D = companyModelArrayList.get(0).getWeight().get("four") * criteriaArrayList.get(0).getWeight().get("one")
                + companyModelArrayList.get(1).getWeight().get("four") * criteriaArrayList.get(0).getWeight().get("two")
                + companyModelArrayList.get(2).getWeight().get("four") * criteriaArrayList.get(0).getWeight().get("three")
                + companyModelArrayList.get(3).getWeight().get("four") * criteriaArrayList.get(0).getWeight().get("four");

        BottomSheetDialog bottomSheetDialog =new BottomSheetDialog(this,R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.calculated);


        TextView a = bottomSheetDialog.findViewById(R.id.A);
        TextView b = bottomSheetDialog.findViewById(R.id.B);
        TextView c = bottomSheetDialog.findViewById(R.id.C);
        TextView d = bottomSheetDialog.findViewById(R.id.D);
        Button btn = bottomSheetDialog.findViewById(R.id.back);

        a.setText(A.toString());
        b.setText(B.toString());
        c.setText(C.toString());
        d.setText(D.toString());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();

    }
    private Boolean checkFields(){
        if (companyModelArrayList.size() > 2){
            haveTwoOrMoreCompany = true;
        }else{
            haveTwoOrMoreCompany = false;
        }
        return haveCriteria && haveTwoOrMoreCompany;
    }
    private void initAdapter(){
        CompanyAdapter companyAdapter = new CompanyAdapter(this, companyModelArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        companyAdapter.setOnItemClickListener(new CompanyAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                showBottomSheetDialog(position);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
        companyRV.setLayoutManager(linearLayoutManager);
        companyRV.setAdapter(companyAdapter);
    }
    private void showBottomSheetDialog(int pos) {

        BottomSheetDialog bottomSheetDialog =new BottomSheetDialog(this,R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        Button cancel = bottomSheetDialog.findViewById(R.id.cancel);
        Button save = bottomSheetDialog.findViewById(R.id.save);
        Button delete = bottomSheetDialog.findViewById(R.id.delete);


        EditText name = bottomSheetDialog.findViewById(R.id.name);

        EditText aa = bottomSheetDialog.findViewById(R.id.AA);
        EditText ab = bottomSheetDialog.findViewById(R.id.AB);
        EditText ac = bottomSheetDialog.findViewById(R.id.AC);
        EditText ad = bottomSheetDialog.findViewById(R.id.AD);
        EditText ba = bottomSheetDialog.findViewById(R.id.BA);
        EditText bb = bottomSheetDialog.findViewById(R.id.BB);
        EditText bc = bottomSheetDialog.findViewById(R.id.BC);
        EditText bd = bottomSheetDialog.findViewById(R.id.BD);
        EditText ca = bottomSheetDialog.findViewById(R.id.CA);
        EditText cb = bottomSheetDialog.findViewById(R.id.CB);
        EditText cc = bottomSheetDialog.findViewById(R.id.CC);
        EditText cd = bottomSheetDialog.findViewById(R.id.CD);
        EditText da = bottomSheetDialog.findViewById(R.id.DA);
        EditText db = bottomSheetDialog.findViewById(R.id.DB);
        EditText dc = bottomSheetDialog.findViewById(R.id.DC);
        EditText dd = bottomSheetDialog.findViewById(R.id.DD);

        if (pos == -1){
            delete.setVisibility(View.GONE);
        }else{
            delete.setVisibility(View.VISIBLE);
            Map<String, Double> val = new HashMap<>();
            name.setText(companyModelArrayList.get(pos).getCompany_name());
            val.putAll(companyModelArrayList.get(pos).getVal());
            aa.setText(String.valueOf(val.get("aa")));
            ab.setText(String.valueOf(val.get("ab")));
            ac.setText(String.valueOf(val.get("ac")));
            ad.setText(String.valueOf(val.get("ad")));
            ba.setText(String.valueOf(val.get("ba")));
            bb.setText(String.valueOf(val.get("bb")));
            bc.setText(String.valueOf(val.get("bc")));
            bd.setText(String.valueOf(val.get("bd")));
            ca.setText(String.valueOf(val.get("ca")));
            cb.setText(String.valueOf(val.get("cb")));
            cc.setText(String.valueOf(val.get("cc")));
            cd.setText(String.valueOf(val.get("cd")));
            da.setText(String.valueOf(val.get("da")));
            db.setText(String.valueOf(val.get("db")));
            dd.setText(String.valueOf(val.get("dd")));
            dc.setText(String.valueOf(val.get("dc")));
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                companyModelArrayList.remove(pos);
                bottomSheetDialog.dismiss();
                initAdapter();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Double> val = new HashMap<>();
                val.put("aa", convertToInt(aa));
                val.put("ab", convertToInt(ab));
                val.put("ac", convertToInt(ac));
                val.put("ad", convertToInt(ad));
                val.put("ba", convertToInt(ba));
                val.put("bb", convertToInt(bb));
                val.put("bc", convertToInt(bc));
                val.put("bd", convertToInt(bd));
                val.put("ca", convertToInt(ca));
                val.put("cb", convertToInt(cb));
                val.put("cc", convertToInt(cc));
                val.put("cd", convertToInt(cd));
                val.put("da", convertToInt(da));
                val.put("db", convertToInt(db));
                val.put("dc", convertToInt(dc));
                val.put("dd", convertToInt(dd));
                saveNewCompany(name.getText().toString(), val, pos);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }
    private void showBottomSheetDialogCriteria() {

        BottomSheetDialog bottomSheetDialog =new BottomSheetDialog(this,R.style.SheetDialog);
        bottomSheetDialog.setContentView(R.layout.criteria_dialog);

        Button cancel = bottomSheetDialog.findViewById(R.id.cancel);
        Button save = bottomSheetDialog.findViewById(R.id.save);

        EditText aa = bottomSheetDialog.findViewById(R.id.AA);
        EditText ab = bottomSheetDialog.findViewById(R.id.AB);
        EditText ac = bottomSheetDialog.findViewById(R.id.AC);
        EditText ad = bottomSheetDialog.findViewById(R.id.AD);
        EditText ba = bottomSheetDialog.findViewById(R.id.BA);
        EditText bb = bottomSheetDialog.findViewById(R.id.BB);
        EditText bc = bottomSheetDialog.findViewById(R.id.BC);
        EditText bd = bottomSheetDialog.findViewById(R.id.BD);
        EditText ca = bottomSheetDialog.findViewById(R.id.CA);
        EditText cb = bottomSheetDialog.findViewById(R.id.CB);
        EditText cc = bottomSheetDialog.findViewById(R.id.CC);
        EditText cd = bottomSheetDialog.findViewById(R.id.CD);
        EditText da = bottomSheetDialog.findViewById(R.id.DA);
        EditText db = bottomSheetDialog.findViewById(R.id.DB);
        EditText dc = bottomSheetDialog.findViewById(R.id.DC);
        EditText dd = bottomSheetDialog.findViewById(R.id.DD);

        if (!haveCriteria){
            haveCriteria = true;
            Map<String, Double> val = new HashMap<>();
            val.put("aa", convertToInt(aa));
            val.put("ab", convertToInt(ab));
            val.put("ac", convertToInt(ac));
            val.put("ad", convertToInt(ad));
            val.put("ba", convertToInt(ba));
            val.put("bb", convertToInt(bb));
            val.put("bc", convertToInt(bc));
            val.put("bd", convertToInt(bd));
            val.put("ca", convertToInt(ca));
            val.put("cb", convertToInt(cb));
            val.put("cc", convertToInt(cc));
            val.put("cd", convertToInt(cd));
            val.put("da", convertToInt(da));
            val.put("db", convertToInt(db));
            val.put("dc", convertToInt(dc));
            val.put("dd", convertToInt(dd));
            criteriaArrayList.add(0,new Criteria(val));
        }else{
            Map<String, Double> val = new HashMap<>();
            val.putAll(criteriaArrayList.get(0).getVal());
            aa.setText(String.valueOf(val.get("aa")));
            ab.setText(String.valueOf(val.get("ab")));
            ac.setText(String.valueOf(val.get("ac")));
            ad.setText(String.valueOf(val.get("ad")));
            ba.setText(String.valueOf(val.get("ba")));
            bb.setText(String.valueOf(val.get("bb")));
            bc.setText(String.valueOf(val.get("bc")));
            bd.setText(String.valueOf(val.get("bd")));
            ca.setText(String.valueOf(val.get("ca")));
            cb.setText(String.valueOf(val.get("cb")));
            cc.setText(String.valueOf(val.get("cc")));
            cd.setText(String.valueOf(val.get("cd")));
            da.setText(String.valueOf(val.get("da")));
            db.setText(String.valueOf(val.get("db")));
            dd.setText(String.valueOf(val.get("dd")));
            dc.setText(String.valueOf(val.get("dc")));
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Double> val = new HashMap<>();
                val.put("aa", convertToInt(aa));
                val.put("ab", convertToInt(ab));
                val.put("ac", convertToInt(ac));
                val.put("ad", convertToInt(ad));
                val.put("ba", convertToInt(ba));
                val.put("bb", convertToInt(bb));
                val.put("bc", convertToInt(bc));
                val.put("bd", convertToInt(bd));
                val.put("ca", convertToInt(ca));
                val.put("cb", convertToInt(cb));
                val.put("cc", convertToInt(cc));
                val.put("cd", convertToInt(cd));
                val.put("da", convertToInt(da));
                val.put("db", convertToInt(db));
                val.put("dc", convertToInt(dc));
                val.put("dd", convertToInt(dd));
                criteriaArrayList.add(0,new Criteria(val));
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }

    private void saveNewCompany(String name, Map val, int pos){
        if(pos == -1){
            companyModelArrayList.add(new CompanyModel(name, val));
        }else{
            companyModelArrayList.set(pos, new CompanyModel(name, val));
        }
        initAdapter();
    }
    private Double convertToInt(EditText R2){
        if (R2.getText().toString().isEmpty()){
            return Double.parseDouble(R2.getHint().toString());
        }else{
            return Double.parseDouble((R2.getText().toString()));
        }
    }
}
