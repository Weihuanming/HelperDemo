package com.helperdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.helperdemo.R;
import com.helperdemo.base.BaseActivity;
import com.helperdemo.util.BaseUtil;

import java.util.List;
import java.util.Map;

public class UnInStallActivity extends BaseActivity {

    public ImageView imageView;
    public RecyclerView recyclerView;
    public List<Map<String, Object>> appList;
    public AppListAdapter appListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uninstall);
        imageView = (ImageView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.app_list);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        init();
    }

    public void init() {
        appList = BaseUtil.getAppList(this);
//        Map<String, Object> map = list.get(0);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        appListAdapter = new AppListAdapter();
        recyclerView.setAdapter(appListAdapter);
    }

    class AppListAdapter extends RecyclerView.Adapter<AppListViewHolder> {

        @Override
        public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AppListViewHolder(getLayoutInflater().inflate(R.layout.view_unstall_item, parent, false));
        }

        @Override
        public void onBindViewHolder(AppListViewHolder holder, int position) {
            holder.appIcon.setImageDrawable((Drawable) appList.get(position).get("app_logo"));
            holder.appName.setText((String) appList.get(position).get("app_name"));
            holder.appVersion.setText((String) appList.get(position).get("app_version_name"));
        }

        @Override
        public int getItemCount() {
            return appList.size();
        }
    }

    class AppListViewHolder extends RecyclerView.ViewHolder {

        public ImageView appIcon;
        public TextView appName;
        public TextView appVersion;
        public Button button;

        public AppListViewHolder(final View itemView) {
            super(itemView);
            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            appName = (TextView) itemView.findViewById(R.id.app_name);
            appVersion = (TextView) itemView.findViewById(R.id.app_version);
            button = (Button) itemView.findViewById(R.id.uninstall);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri packageURI = Uri.parse("package:" + appList.get(getLayoutPosition()).get("package_name"));
                    Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
                    startActivity(intent);
                }
            });
        }
    }

    public class MonitorSysReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent){
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                appListAdapter.notifyDataSetChanged();
            }
        }
    }
}
