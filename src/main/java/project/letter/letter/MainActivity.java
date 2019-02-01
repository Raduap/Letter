package project.letter.letter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.net.URI.create;

public class MainActivity extends AppCompatActivity {


    private final int REQUESTCODE = 101;
    private String fileName = "Letter";
    private String FileName = "diary.txt";
    private String Convir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        create(fileName);
        creatFile(FileName);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "保存成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                EditText texr = findViewById(R.id.edit);
                String uui = texr.getText().toString().trim();
                String tys = uui;
                Wrute(view,tys);
            }
        });
/*        File file = new File("mnt/sdcard/1/Log");
       if ( !file.exists()){
           file.mkdirs();
           Toast.makeText(MainActivity.this,"创建成功！",Toast.LENGTH_SHORT).show();
       }*/

        EditText set = findViewById(R.id.edit);
        set.setText("这是一个便签示例");

        //File mFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);

        //String the = mFiles.toString().trim();

        
        //BufferedReader br = new BufferedReader(new InputStreamReader());

        String uio = "";
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);
            FileInputStream fis;
            try {
                fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String tui="";

                uio = br.readLine();
                while (br.read()!=-1)
                {
                    uio += br.readLine();
                }

/*                Toast.makeText(MainActivity.this,
                        "读取之前保存的内容",
                        Toast.LENGTH_LONG).show();*/
            } catch (Exception e) {
                // TODO Auto-generated catch block  
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "第一次打开请允许手机存储权限！",
                        Toast.LENGTH_LONG).show();
            }  
            
            set.setText(uio);


       /* set.setText(the);
        try {
           // FileReader reader = new FileReader(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);

            FileInputStream fin = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);

            int length = fin.available();

            byte[] buffer = new byte[length];

            fin.read(buffer);


        }catch(IOException e) {

            e.printStackTrace();

        }*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            //询问用户权限
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                //用户同意
            } else {
                //用户不同意
            }
        }
    }

    public void create(String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkSelfPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTCODE);
            }
        }
        //Environment.getExternalStorageDirectory().getAbsolutePath():SD卡根目录
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName);
        if (!file.exists()) {
            boolean isSuccess = file.mkdirs();
            //Toast.makeText(MainActivity.this,"文件夹创建成功",Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(MainActivity.this,"文件夹已存在",Toast.LENGTH_LONG).show();
        }
    }


    public void creatFile(String Filename) {

        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + Filename);

        if (!mFile.exists()) {
            try {
                mFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("creatXMLFileException", e.getMessage());
            }
        }

    }


    public void Wrute(View view,String tys){
        File filed = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filed);
            fos.write(tys.getBytes());
            fos.close();
            /*Toast.makeText(MainActivity.this,
                    "写入文件成功",
                    Toast.LENGTH_LONG).show();*/
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
      /*      Toast.makeText(MainActivity.this,
                    "o",
                    Toast.LENGTH_LONG).show();*/
        }
    }

    public void abou(MenuItem item) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("关于");
        builder.setMessage("便签文件在SD卡根目录Letter文件夹\n\n                                     BY Radua");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();


    }
}
