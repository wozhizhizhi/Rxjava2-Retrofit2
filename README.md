# Rxjava2-Retrofit2
##Rxjava2+Retrofit2的封装
1. 如何使用
- 继承BaseActivity,按照如下方法使用
```
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
```
2. 项目目前还有一些功能没有添加，还在持续更新中

