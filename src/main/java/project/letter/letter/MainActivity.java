package project.letter.letter;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import skin.support.SkinCompatManager;
import skin.support.app.SkinCompatActivity;
import skin.support.content.res.ColorState;
import skin.support.content.res.SkinCompatUserThemeManager;

import static java.net.URI.create;

public class MainActivity extends SkinCompatActivity {


    private final int REQUESTCODE = 101;
    private int ui = -1;
    private int mk = -1;
    private String fileName = "Letter";
    private String FileName = "diary.txt";
    private String initcc = "这是一个粘贴板示例";
    private String Convir = "这是一个简洁的粘贴板\n以下是自动获取的剪贴板内容\n\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        create(fileName);
        creatFile(FileName);
        Findsetting();
        FindsettingCopy();
        ReadSet();
        ReadSetcopy();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "保存成功！", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                EditText texr = findViewById(R.id.edit);
                String uui = texr.getText().toString().trim();
                String tys = uui;
                Wrute(view, tys);
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
            String tui = null;

            int b;

   /*             do
                {
                    if (br.read()==-1)break;
                    //uio += br;
                    uio += br.readLine();
                    uio += "\n";
                    //tui += br.read();


                }while ((tui+=br.read())!=null);*/

            while ((b = br.read()) != -1) {
                uio += (char) b;


            }

            ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData data = cm.getPrimaryClip();
            ClipData.Item item = data.getItemAt(0);
            String uited = item.getText().toString().trim();
            uio += uited;
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
                Toast.makeText(MainActivity.this,
                        "第一次打开请允许手机存储权限用于保存粘贴内容！",
                        Toast.LENGTH_LONG).show();
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
                File newfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);
                FileOutputStream fas;
                try {
                    fas = new FileOutputStream(newfile);
                    fas.write(Convir.getBytes());
                    fas.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("creatXMLFileException", e.getMessage());
            }
        }

    }


    public void Wrute(View view, String tys) {
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

    public void figur(MenuItem item) {

        /*final EditText editText = new EditText(MainActivity.this);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(MainActivity.this);
        inputDialog.setTitle("我是一个输入Dialog").setView(editText);*/
        // inputDialog.setPositiveButton()

        final EditText tetone = new EditText(this);
        tetone.setText(FileName);

        String uri = null;
        new AlertDialog.Builder(this)
                .setMessage("以下是原文件的带后缀名称，请修改时加上后缀。\n(注:记得点击保存按钮)\n")

                .setView(tetone)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if (uri==null)
                        FileName = tetone.getText().toString().trim();

                        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);

                        if (!mFile.exists()) {
                            try {
                                mFile.createNewFile();
                                File newfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + FileName);
                                FileOutputStream fas;
                                try {
                                    fas = new FileOutputStream(newfile);
                                    fas.write(Convir.getBytes());
                                    fas.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setNegativeButton("否", null)
                .show();


    }


    public void butrie(View view) {

        EditText ui = findViewById(R.id.edit);

        ui.setText("");

    }

    public void buju(MenuItem item) {

/*        new AlertDialog.Builder(this)
                .setMessage("是否添加清空按钮？")
                .setPositiveButton("是", new DialogInterface.OnClickListener(){
                    public void Onclick(DialogInterface dialog, int which){
                      Button But = (Button)findViewById(R.id.buttonr);
                      //View ty= new View(MainActivity.this);
                        But.setVisibility(View.VISIBLE);

            }


        })



    }*/


        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("布局");
        builder.setMessage("是否添加清空按键？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Button But = (Button) findViewById(R.id.buttonr);
                But.setVisibility(View.VISIBLE);
                WriteSetting("101");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Button But = (Button)findViewById(R.id.buttonr);
                But.setVisibility(View.INVISIBLE);
                WriteSetting("201");
            }
        });
        builder.create().show();


    }

    public void Findsetting()
    {
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + "Lettersetting.dba");

        if (!mFile.exists())
        {
            try {

                mFile.createNewFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else{

            FileOutputStream is;

            try{
                FileInputStream fis;

                fis = new FileInputStream(mFile);

                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                int a = br.read();

                ui = a;
            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }

    public void FindsettingCopy()
    {
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + "Lettersettingcopy.dba");

        if (!mFile.exists())
        {
            try {

                mFile.createNewFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else{

            FileOutputStream is;

            try{
                FileInputStream fis;

                fis = new FileInputStream(mFile);

                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                int a = br.read();

                mk = a;
            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }
    public void WriteSetting(String sys)
    {
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + "Lettersetting.dba");

        if (!mFile.exists())
        {
            try {

                mFile.createNewFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else{

            FileOutputStream is;

            try{
                FileOutputStream rad;

                rad = new FileOutputStream(mFile);


                rad.write(sys.getBytes());




            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }

    public void ReadSet() {
        String Readone = "101";
        String Readtwo = "201";
        String Get = "";
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + "Lettersetting.dba");


        FileInputStream fis;

        try {

            fis = new FileInputStream(mFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));


            int b;

   /*             do
                {
                    if (br.read()==-1)break;
                    //uio += br;
                    uio += br.readLine();
                    uio += "\n";
                    //tui += br.read();


                }while ((tui+=br.read())!=null);*/

            while ((b = br.read()) != -1) {
                Get += (char) b;


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Button thia = (Button)findViewById(R.id.buttonr);
        if (Get.equals(Readone)){

            thia.setVisibility(View.VISIBLE);


        }
        if (Get.equals(Readtwo)){

            thia.setVisibility(View.INVISIBLE);

        }


    }


    public void copy(MenuItem item) {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("布局");
        builder.setMessage("是否添加复制按键？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Button But = (Button) findViewById(R.id.copyr);
                But.setVisibility(View.VISIBLE);
                //WriteSetting("101");
                WriteSettingcopy("1201");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Button But = (Button)findViewById(R.id.copyr);
                But.setVisibility(View.INVISIBLE);
                //WriteSetting("201");
                WriteSettingcopy("2101");
            }
        });
        builder.create().show();


    }

    public void couip(View view) {
        Context con = this;
        ClipboardManager cmp = (ClipboardManager)con.getSystemService(Context.CLIPBOARD_SERVICE);

        EditText ir = findViewById(R.id.edit);
        cmp.setText(ir.getText());

        Toast.makeText(this, "复制成功", Toast.LENGTH_LONG).show();




    }


    public void WriteSettingcopy(String sys)
    {
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + "Lettersettingcopy.dba");

        if (!mFile.exists())
        {
            try {

                mFile.createNewFile();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }else{

            FileOutputStream is;

            try{
                FileOutputStream rad;

                rad = new FileOutputStream(mFile);


                rad.write(sys.getBytes());




            }catch (Exception e){
                e.printStackTrace();
            }



        }


    }



    public void ReadSetcopy() {
        String Readone = "1201";
        String Readtwo = "2101";
        String Get = "";
        File mFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Letter" + "/" + "Lettersettingcopy.dba");


        FileInputStream fis;

        try {

            fis = new FileInputStream(mFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));


            int b;

   /*             do
                {
                    if (br.read()==-1)break;
                    //uio += br;
                    uio += br.readLine();
                    uio += "\n";
                    //tui += br.read();


                }while ((tui+=br.read())!=null);*/

            while ((b = br.read()) != -1) {
                Get += (char) b;


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Button thia = (Button)findViewById(R.id.copyr);
        if (Get.equals(Readone)){

            thia.setVisibility(View.VISIBLE);


        }
        if (Get.equals(Readtwo)){

            thia.setVisibility(View.INVISIBLE);

        }


    }


    public void anyemode(MenuItem item) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("暗夜模式");
        builder.setMessage("是否开启暗夜模式？？");
        builder.setPositiveButton("开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SkinCompatManager.getInstance().loadSkin("night", SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);

                SkinCompatUserThemeManager.get().addColorState(R.color.background_material_light,"#ffffff");



            }
        });
        builder.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SkinCompatManager.getInstance().restoreDefaultTheme();

            }
        });
        builder.create().show();


    }
}
