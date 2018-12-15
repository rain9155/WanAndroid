package com.example.hy.wanandroid.model.network.gson;

import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Version;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 自定义ResponseBody解析器
 * Created by 陈健宇 at 2018/10/27
 */
public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, Object>{

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CustomResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override public Object convert(ResponseBody value) throws IOException {
        try {
            BaseResponse baseResponse = (BaseResponse) adapter.fromJson(value.charStream());
            if(baseResponse.getErrorCode() == 0){
                return baseResponse;
            }else {
                throw new ApiException(baseResponse.getErrorCode(), baseResponse.getErrorMsg());
            }
            } finally {
            value.close();
        }
    }
}
