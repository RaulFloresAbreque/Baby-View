package com.example.raul.babyview;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    EditText edtNombreLogin, edtPassLogin;
    Button btnIngresoLogin, btnRegistroLogin;

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        edtNombreLogin = (EditText)findViewById(R.id.edtNombreLogin);
        edtPassLogin=(EditText)findViewById(R.id.edtPassLogin);
        btnIngresoLogin=(Button)findViewById(R.id.btnIngresoLogin);
        btnRegistroLogin=(Button)findViewById(R.id.btnRegistroLogin);


        btnRegistroLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent siguiente = new Intent(LoginActivity.this,RegistroActivity.class);
                startActivity(siguiente);
            }
        });


        btnIngresoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                consultaInicio();

            }
        });




    }

    public Connection conexionBD(){
        Connection conexion=null;
        try
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://localhost;databaseName=babyview;user=sa;password=1234;");
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        return conexion;

    }

    public void consultaInicio(){
        try
        {
            String resultado="";
            String usuarioconsult = edtNombreLogin.getText().toString();

            PreparedStatement pst = conexionBD().prepareStatement("Select nombre_usuario from usuario where nombre_usuario = '" + usuarioconsult + "'");
            ResultSet rs= pst.executeQuery();
            if(rs.next()){

                resultado=rs.getString("nombre_usuario");

            }
            if(resultado == usuarioconsult){

                Intent siguiente = new Intent(LoginActivity.this,PrincipalActivity.class);
                startActivity(siguiente);

            }else{

                Toast.makeText(getApplicationContext(),"Datos Incorrectos",Toast.LENGTH_SHORT).show();

            }

        }
        catch(SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }


    public native String stringFromJNI();
}
