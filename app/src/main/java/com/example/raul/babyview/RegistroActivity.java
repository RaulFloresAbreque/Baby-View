package com.example.raul.babyview;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistroActivity extends AppCompatActivity {

    EditText edtNombreUsuario, edtApellidosUsuario, edtEdadUsuario, edtNumeroUsuario, edtPassUsuario, edtCorreoUsuario;
    Button btnRegistroUsuario, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtNombreUsuario=(EditText)findViewById(R.id.edtNombreUsuario);
        edtApellidosUsuario=(EditText)findViewById(R.id.edtApellidosUsuario);
        edtEdadUsuario=(EditText)findViewById(R.id.edtEdadUsuario);
        edtNumeroUsuario=(EditText)findViewById(R.id.edtNumeroUsuario);
        edtCorreoUsuario=(EditText)findViewById(R.id.edtCorreoUsuario);
        edtPassUsuario=(EditText)findViewById(R.id.edtPassUsuario);
        btnRegistroUsuario=(Button)findViewById(R.id.btnRegistroUsuario);
        btnVolver=(Button)findViewById(R.id.btnVolverRegistro);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente=new Intent(RegistroActivity.this,LoginActivity.class);
                startActivity(siguiente);
            }
        });

        btnRegistroUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarregistro();

            }
        });

    }

    public Connection conexionBD(){
        Connection conexion=null;
        try{
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion=DriverManager.getConnection("jdbc:jtds:sqlserver://localhost;databaseName=babyview;user:sa;password=1234;");

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

        }return conexion;
    }

    public void consultarregistro(){
        try
        {
            String resultado="";
            String usuarioconsult = edtCorreoUsuario.getText().toString();

            PreparedStatement pst = conexionBD().prepareStatement("Select correo_usuario from usuario where correo_usuario = '" + usuarioconsult + "'");
            ResultSet rs= pst.executeQuery();
            if(rs.next()){

                resultado=rs.getString("correo_usuario");
            }
            if(resultado == usuarioconsult){

                Toast.makeText(getApplicationContext(),"Usuario Existente",Toast.LENGTH_SHORT).show();
            }else{
                agregarregistro();
            }
        }
        catch(SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void agregarregistro(){
        try
        {
            PreparedStatement pst = conexionBD().prepareStatement("insert into usuario values(?,?,?,?,?,?)");
            pst.setString(1,edtNombreUsuario.getText().toString());
            pst.setString(2,edtApellidosUsuario.getText().toString());
            pst.setString(3,edtEdadUsuario.getText().toString());
            pst.setString(4,edtCorreoUsuario.getText().toString());
            pst.setString(5,edtNumeroUsuario.getText().toString());
            pst.setString(6,edtPassUsuario.getText().toString());
            pst.executeQuery();
            Toast.makeText(getApplicationContext(),"Usuario Registrado",Toast.LENGTH_SHORT).show();
            Intent siguiente=new Intent(RegistroActivity.this,RegBebeActivity.class);
            startActivity(siguiente);
        }
        catch(SQLException e)
        {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

}
