package com.example.proccoli2.NewModels;

public interface ResultHandler<T> {
   void onSuccess(T data);
   void onFailure(Exception e);
}
