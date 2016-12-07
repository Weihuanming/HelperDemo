package com.helperdemo.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.helperdemo.R;
import com.helperdemo.base.BaseActivity;
import com.helperdemo.util.BaseUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InStallActivity extends BaseActivity {

    public ImageView back;
    public TextView textView;
    public RecyclerView recyclerView;
    public List<Map<String, Object>> apkList = new ArrayList<>();
    public ApkListAdapter apkListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install);
        back = (ImageView) findViewById(R.id.back);
        textView = (TextView) findViewById(R.id.none_apk);
        recyclerView = (RecyclerView) findViewById(R.id.apk_list);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    private void init() {
        BaseUtil.FindAllAPKFile(Environment.getExternalStorageDirectory(), getBaseContext(), apkList);
        if (apkList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apkListAdapter = new ApkListAdapter();
        recyclerView.setAdapter(apkListAdapter);
    }

    class ApkListAdapter extends RecyclerView.Adapter<ApkListViewHolder> {

        @Override
        public ApkListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ApkListViewHolder(getLayoutInflater().inflate(R.layout.view_install_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ApkListViewHolder holder, int position) {
            holder.apkIcon.setImageDrawable((Drawable) apkList.get(position).get("app_logo"));
            holder.apkName.setText((String) apkList.get(position).get("app_name"));
            holder.apkVersion.setText((String) apkList.get(position).get("app_version_name"));
        }

        @Override
        public int getItemCount() {
            return apkList.size();
        }
    }

    class ApkListViewHolder extends RecyclerView.ViewHolder {

        public ImageView apkIcon;
        public TextView apkName;
        public TextView apkVersion;
        public Button button;
        public RelativeLayout apkItem;

        public ApkListViewHolder(View itemView) {
            super(itemView);
            apkIcon = (ImageView) itemView.findViewById(R.id.apk_icon);
            apkName = (TextView) itemView.findViewById(R.id.apk_name);
            apkVersion = (TextView) itemView.findViewById(R.id.apk_version);
            button = (Button) itemView.findViewById(R.id.install);
            apkItem = (RelativeLayout) itemView.findViewById(R.id.apk_item);

            initListener();
        }

        public void initListener() {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.fromFile(new File(apkList.get(getLayoutPosition()).get("apk_path").toString()));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(uri,"application/vnd.android.package-archive");
                    startActivity(intent);
                }
            });

//            apkItem.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    initDialog(getLayoutPosition());
//                    return true;
//                }
//            });
        }
    }

    public void initDialog(final int position) {
        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("是否要删除Apk安装包")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String path = (String) apkList.get(position).get("apk_path");
                        File file = new File(path);
                        if (file.delete()) {
                            Toast.makeText(getBaseContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "删除失败", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
