package com.aji.retrofitlib.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aji.retrofitlib.Bean.BasicResponse;
import com.aji.retrofitlib.Bean.module.Gril;
import com.aji.retrofitlib.R;
import com.aji.retrofitlib.net.IcApi;
import com.aji.retrofitlib.util.DefaultObserver;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity
{
    private Button btn;

    @Override
    protected int getLayoutId()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }


    public void getData()
    {
        IcApi.getIcApiService()
                .getGril()
                .compose(this.<BasicResponse<List<Gril>>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<Gril>>>(this)
                {
                    @Override
                    public void onSuccess(BasicResponse<List<Gril>> response)
                    {
                        List<Gril> results = response.getResults();
                        showToast("请求成功，我的女孩个数为"+results.size());
                    }

                });
    }

}
