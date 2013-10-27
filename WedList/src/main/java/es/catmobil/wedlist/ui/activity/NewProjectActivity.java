package es.catmobil.wedlist.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import es.catmobil.wedlist.R;

/**
 * Created by adria on 26/10/13.
 */
public class NewProjectActivity extends ActionBarActivity implements View.OnClickListener{
    private EditText nameEdt;
    private EditText descriptionEdt;
    private EditText thanksEdt;
    private ImageView imageView;
    private EditText paypalAccountEdt;
    private Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        findViews();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Project");
    }



    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().removeAllTabs();

    }
    private void findViews() {
        nameEdt = (EditText)findViewById( R.id.nameEdt );
        descriptionEdt = (EditText)findViewById( R.id.descriptionEdt );
        thanksEdt = (EditText)findViewById( R.id.thanksEdt );
        imageView = (ImageView)findViewById( R.id.imageView );
        paypalAccountEdt = (EditText)findViewById( R.id.paypalAccountEdt );
        btn = (Button)findViewById( R.id.btn );

        btn.setOnClickListener( this );
    }
    @Override
    public void onClick(View v) {
        if ( v == btn ) {
           Intent intent=new Intent(this,SelectGuestsActivity.class);
            startActivity(intent);
        }
    }
}
