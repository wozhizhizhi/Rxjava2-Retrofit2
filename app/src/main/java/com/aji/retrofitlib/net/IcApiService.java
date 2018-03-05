package com.aji.retrofitlib.net;

import com.aji.retrofitlib.Bean.BasicResponse;
import com.aji.retrofitlib.Bean.module.Gril;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Author:zhangmiss on 2018/03/05 0005.
 * mail:867596152@qq.com
 * Descripiton:
 */

public interface IcApiService
{
    @Headers("Cache-Control: public, max-age=100")
    @GET("福利/10/1")
    Observable<BasicResponse<List<Gril>>> getGril();
}
