package com.u21.a0903_onlinemusic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.lihang.ShadowLayout;
import com.u21.a0903_onlinemusic.translate.Translate;
import com.u21.a0903_onlinemusic.translate.pic.Const;
import com.u21.a0903_onlinemusic.translate.pic.data.Config;
import com.u21.a0903_onlinemusic.translate.pic.data.Language;
import com.u21.a0903_onlinemusic.translate.pic.http.HttpStringCallback;
import com.u21.a0903_onlinemusic.translate.pic.pic.PicTranslate;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;
import net.steamcrafted.materialiconlib.MaterialIconView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private Spinner spinner;                    // 下拉框
    private List<String> data_list;             // 下拉框数据
    private ArrayAdapter<String> arr_adapter;   // 下拉框Adapter
    private EditText from;
    private TextView to;
    private TextView wordmain;
    private TextView wordexp;
    private LinearLayout layout;
    private MaterialIconView trans_pic;                // 图片翻译按钮
    private ImageView view;                     // 图片预览view
    private ImageView clear;                    // 清空按钮
    private String pic_result;                  // 图片绝对路径
    private ShadowLayout toC;


    public String sentence = "美好的事即将发生";


    //设置要转换的语言
    String lang_from = "zh";
    String lang_to = "en";
    String result = "";

    //初始化APIconfig
    Config config = new Config(Const.APPID, Const.SECRET_KEY);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //注册控件
        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        spinner = findViewById(R.id.language);
        layout = findViewById(R.id.linear);
        trans_pic = findViewById(R.id.transpic);
        view = findViewById(R.id.view);
        clear = findViewById(R.id.clear);
        wordmain = findViewById(R.id.toleft_up);
        wordexp = findViewById(R.id.toleft_down);
        toC = findViewById(R.id.toC);

        //初始化数据
        data_list = new ArrayList<>();
        data_list.add("             中 → 英");
        data_list.add("             自动检测 → 中");
        data_list.add("             中 → 日");
        data_list.add("             中 → 法");
        config.lang(Language.ZH, Language.EN);

//        //设置背景图片
//        Resources resources = getApplicationContext().getResources();
//        Drawable backgroundDrawable = resources.getDrawable(R.drawable.maoboli);
//        layout.setBackground(backgroundDrawable);
        //layout.setBackgroundColor(Color.WHITE);

        //适配器
        arr_adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        //下拉控件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString().equals("             中 → 英")) {
                    lang_from = "zh";
                    lang_to = "en";
                    config.lang(Language.ZH, Language.EN);
                } else if (spinner.getSelectedItem().toString().equals("             自动检测 → 中")) {
                    lang_from = "auto";
                    lang_to = "zh";
                    config.lang(Language.AUTO, Language.ZH);
                } else if (spinner.getSelectedItem().toString().equals("             中 → 日")) {
                    lang_from = "zh";
                    lang_to = "jp";
                    config.lang(Language.ZH, Language.JP);
                } else if (spinner.getSelectedItem().toString().equals("             中 → 法")) {
                    lang_from = "zh";
                    lang_to = "fra";
                    config.lang(Language.ZH, Language.FRA);
                }

                afterTextChanged(from.getEditableText());       // 重选语言后同步内容

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String word = getRandWord();
        int index = word.indexOf("|");
        String name = word.substring(0, index);
        String explain = word.substring(index + 1);
        StringBuilder stringBuilder = new StringBuilder(explain);

        String[] sign = {"a.", "v.", "n.", "vi.", "vt.", "ad.", "adj.", "adv.", "pron."};
        for (String s : sign) {
            if (explain.indexOf(s) > 0) {
                stringBuilder.insert(explain.indexOf(s), "\n");
            }
        }


        wordmain.setText(name);
        wordexp.setText(stringBuilder.toString());

        //设置输入框监听
        from.addTextChangedListener(this);

        //设置清空按钮监听
        clear.setOnClickListener(this);

        //设置拍照按钮监听
        trans_pic.setOnClickListener(this);

        to.setOnClickListener(this);

        toC.setOnClickListener(this);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {
        //内容改变后

        if (from.getText().length() > 0) {
            //发送文字翻译请求
            if(from.getText().length()>=1){
                if (!isChinese(from.getText().toString())){
                    spinner.setSelection(1);
                }
            }
            result = Translate.trans_str(from.getText().toString(), lang_from, lang_to);
            Log.i("result", result);
            to.setGravity(Gravity.CENTER_VERTICAL);
            to.setText(result);


            clear.setImageResource(R.drawable.shibai);
        } else {
            to.setGravity(Gravity.CENTER);

            to.setText(sentence);
            clear.setImageResource(0);
        }


    }

    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap myBitmap = null;
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                Log.i("SDFAIL",
                        "SD卡不可用");
                return;
            }

                    Bundle extras = data.getExtras();
                    if (null != extras) {
                        Log.i("bb", "isNull:" + false);
                        myBitmap = (Bitmap) extras.get("data");
                    } else {
                        Uri uri = data.getData();
                        if (uri != null) {
                            myBitmap = BitmapFactory.decodeFile(uri.getPath());
                        }
                    }

                    pic_result = saveImageToGallery(this, myBitmap);

                    try {


                        config.pic(pic_result);
                        config.erase(Config.ERASE_NONE);
                        config.paste(Config.PASTE_FULL);


                        PicTranslate picTranslate = new PicTranslate();
                        picTranslate.setConfig(config);

                        picTranslate.trans(new HttpStringCallback() {

                                               @Override
                                               public void onSuccess(String response) throws JSONException {

                                                   super.onSuccess(response);
                                                   JSONObject jsonObject = new JSONObject(response);
                                                   String result_str = jsonObject.getString("data");
                                                   JSONObject jsonObject1 = new JSONObject(result_str);
                                                   result_str = jsonObject1.getString("content");
                                                   JSONArray jsonArray = new JSONArray(result_str);
                                                   JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
                                                   Log.e("respii", jsonObject2.toString());
                                                   runOnUiThread(() -> {
                                                       try {
                                                           from.setText(jsonObject2.getString("src"));
                                                           to.setText(jsonObject2.getString("dst"));
                                                       } catch (JSONException e) {
                                                           e.printStackTrace();
                                                       }

                                                   });
                                               }

                                               @Override
                                               protected void onFailure(Throwable e) {
                                                   super.onFailure(e);
                                               }
                                           }
                        );
                        view.setImageBitmap(myBitmap);// 将图片显示在ImageView里
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
        }
    }



    @Override
    public void onClick(View v) {
        if (v == trans_pic){
            Drawable camdrawable = MaterialDrawableBuilder.with(v.getContext()) // provide a context
                    .setIcon(MaterialDrawableBuilder.IconValue.CAMERA_IMAGE) // provide an icon
                    .setColor(Color.BLACK) // set the icon color
                    .build();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1);
            trans_pic.setImageDrawable(camdrawable);
        }
        if(v==clear){
            view.setImageResource(0);
            from.setText("");
            to.setText(sentence);
            trans_pic.setImageDrawable(MaterialDrawableBuilder.with(v.getContext()).setIcon(MaterialDrawableBuilder.IconValue.CAMERA).setColor(Color.BLACK).build());
        }
        if(v==to){
            if (to.getText() != null && to.getText() != sentence) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", to.getText());
                // 将ClipData内容放到系统剪贴板里。
                CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
                Snackbar.make(coordinatorLayout, "内容已复制到剪贴板！", Snackbar.LENGTH_SHORT).setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE).show();
                cm.setPrimaryClip(mClipData);
            }
        }
        if (v == toC) {
            String word = getRandWord();
            //Toast.makeText(this, word, Toast.LENGTH_SHORT).show();
            int index = word.indexOf("|");
            String name = word.substring(0, index);
            String explain = word.substring(index + 1);
            StringBuilder stringBuilder = new StringBuilder(explain);

            String[] sign = {"a.", "v.", "n.", "vi.", "vt.", "ad.", "adj.", "adv.", "pron.", "; "};
            for (String s : sign) {
                if (explain.indexOf(s) > 0) {
                    stringBuilder.insert(explain.indexOf(s), "\n");
                }
            }


            wordmain.setText(name);
            wordexp.setText(stringBuilder.toString());

        }
    }
    public static boolean isChinese(String c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c.charAt(0));


        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }


    public String saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片 创建文件夹
        File appDir = new File(Environment.getExternalStorageDirectory(), "Download");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        //图片文件名称
        String fileName = "oa_" + System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        String path = file.getAbsolutePath();
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), path, fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);

        return path;
    }

    public String getRandWord() {
        String word = null;
        List<String> list = new ArrayList<>();

        try {
            InputStream inputStream = getResources().getAssets().open("Allwords.txt");
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);
            Random random = new Random();
            int ran = random.nextInt(5120);
            String line;
            for (int i = 0; (line = reader.readLine()) != null; i++) {
                if (i == ran) {
                    word = line;
                }
                list.add(line);
            }
            reader.close();
            inputStream.close();
            streamReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return word;
    }

}