package com.gdmec.jacky.datastorage;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class DataStorage extends AppCompatActivity {
    private EditText editText1,editText2;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_storage);
        editText1= (EditText) findViewById(R.id.edittext1);
        editText2= (EditText) findViewById(R.id.edittext2);
        tv= (TextView) findViewById(R.id.textview);
    }
    public void spWrite(View view){
        SharedPreferences user=getSharedPreferences("user",32768);
        SharedPreferences.Editor editor=user.edit();
        editor.putString("account",editText1.getText().toString());
        editor.putString("password",editText2.getText().toString());
        editor.commit();
        Toast.makeText(this,"SharedPreferences写入成功！",Toast.LENGTH_LONG).show();
    }


    public void spRead(View view){
        SharedPreferences user=getSharedPreferences("user",0);
        String account=user.getString("account","没有这个值！");
        String password=user.getString("password","没有这个值！");
        tv.setText("帐号："+account+"\n"+"密码："+password);
        Toast.makeText(this,"SharedPreferences读取成功！",Toast.LENGTH_LONG).show();
    }
    public void ROMWrite(View view){
        String account=editText1.getText().toString();
        String password=editText2.getText().toString();
        try {
            FileOutputStream fos=openFileOutput("user.txt",32768);
            OutputStreamWriter osw=new OutputStreamWriter(fos);
            BufferedWriter bufferedWriter=new BufferedWriter(osw);
            bufferedWriter.write("account:"+account+"\npassword:"+password+"\n");
            bufferedWriter.flush();
            fos.close();
            Toast.makeText(this,"ROM写入成功！",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ROMRead(View view){
        String account=editText1.getText().toString();
        String password=editText2.getText().toString();
        try {
            FileInputStream fis=openFileInput("user.txt");
            InputStreamReader inReader=new InputStreamReader(fis);
            BufferedReader bufferedReader=new BufferedReader(inReader);
            StringBuffer sb=new StringBuffer();
            String s;
            while ((s=bufferedReader.readLine())!=null){
                sb.append(s+"\n");
            }
            fis.close();
            tv.setText(sb);
            Toast.makeText(this,"ROM读取成功！",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void SDWrite(View view){
        String str=editText1.getText().toString()+"\n"+editText2.getText().toString()+"\n";
        String sdCardRoot= Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename=sdCardRoot+"/test.txt";
        File file=new File(filename);
        try {
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(str.getBytes());
            fos.flush();
            fos.close();
            Toast.makeText(this,"SDCard写入成功！",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }public void SDRead(View view){
        String sdCardRoot= Environment.getExternalStorageDirectory().getAbsolutePath();
        String filename=sdCardRoot+"/test.txt";
        File file=new File(filename);
        int length= (int) file.length();
        byte[] b=new byte[length];
        try {
            FileInputStream fis=new FileInputStream(file);
            fis.read(b,0,length);
            fis.close();
            tv.setText(new String(b));
            Toast.makeText(this,"SDCard读取成功！",Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
