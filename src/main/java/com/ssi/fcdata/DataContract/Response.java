package com.ssi.fcdata.DataContract;

public class Response<T> {
    public String message;
    public String status;
    public T data;
    public  int totalRecord = 1;

}
