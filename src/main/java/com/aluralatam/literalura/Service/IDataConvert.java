package com.aluralatam.literalura.Service;

public interface IDataConvert {
    <T> T getData(String json, Class<T> clase);
}
