package bwie.com.mouth08;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    List<Data.DataBean> list;
    Myadapter adapter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        loadData();
    }

    private void loadData() {
        String url = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=5&gender=2&ts=1871746850&page="+page;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("username", "hyman")
                .addParams("password", "123")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Request request, Exception e)
                    {

                    }

                    @Override
                    public void onResponse(String response)
                    {
                        Data data = new Gson().fromJson(response, Data.class);
                        list.addAll(data.getData());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this,"数据请求成功",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new Myadapter(this, list);
        recyclerview.setAdapter(adapter);
        adapter.onClickListener(new RecyclerViewListener.onClickListener() {
            @Override
            public void onClickListener(View v, int position) {
                Toast.makeText(MainActivity.this, list.get(position).getIntroduction(), Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this,Activity_Map.class));
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                page = 1;
                list.clear();
                loadData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                page++;
                loadData();
            }
        });//设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new MaterialHeader(this).setShowBezierWave(true));
//设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

    }
}
