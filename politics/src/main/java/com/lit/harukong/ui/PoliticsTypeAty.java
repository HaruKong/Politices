package com.lit.harukong.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.adapter.MyPoliticsTypeAdapter;

/**
 * Created by haru on 2016/3/21.
 */
public class PoliticsTypeAty extends Activity {
    private Intent intent = new Intent();

    //问政类型数据
    private String[] types = {"咨询", "投诉", "求助", "举报", "建议", "表扬", "其它"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_politics_type);
        ListView listView = (ListView) findViewById(R.id.lv_politics_type);
        MyPoliticsTypeAdapter typeAdapter = new MyPoliticsTypeAdapter(types, this);
        listView.setAdapter(typeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent.putExtra("return", types[position]);
                setResult(AppContext.POLITICS_TYPE, intent);
                PoliticsTypeAty.this.finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        intent.putExtra("return", "");
        setResult(AppContext.POLITICS_TYPE, intent);
        return super.onKeyDown(keyCode, event);
    }
}
