package com.helperdemo.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.helperdemo.R;
import com.helperdemo.base.BaseActivity;

public class MainActivity extends BaseActivity {

    public RecyclerView recyclerView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.content);
        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ClearItemAdapter clearItemAdapter = new ClearItemAdapter();
        recyclerView.setAdapter(clearItemAdapter);
    }

    private void onClearItemClick(ClearItem clearItem) {
        switch (clearItem.name) {
            case "应用安装":
                intent = new Intent(this,InStallActivity.class);
                startActivity(intent);
                break;
            case "应用卸载":
                intent = new Intent(this,UnInStallActivity.class);
                startActivity(intent);
                break;
            case "手机清理":
                break;
        }
    }

    class ClearItemAdapter extends RecyclerView.Adapter<ClearItemViewHolder> {

        @Override
        public ClearItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ClearItemViewHolder(getLayoutInflater().inflate(R.layout.view_main_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ClearItemViewHolder holder, int position) {
            holder.textView.setText(clearItems[position].name);
        }

        @Override
        public int getItemCount() {
            return clearItems.length;
        }
    }

    class ClearItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ClearItemViewHolder(final View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.clear);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClearItemClick(clearItems[getLayoutPosition()]);
                }
            });
        }
    }

    ClearItem[] clearItems = new ClearItem[]{
            new ClearItem("应用安装"),
            new ClearItem("应用卸载"),
            new ClearItem("手机清理"),
    };

    class ClearItem {
        String name;

        public ClearItem(String name) {
            this.name = name;
        }
    }
}
