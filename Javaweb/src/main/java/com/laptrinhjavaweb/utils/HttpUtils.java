package com.laptrinhjavaweb.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;

// todo utils như 1 thư viện dùng chung cho các class , do ng dùng tự xây dựng
public class HttpUtils {
  private String value;

  public HttpUtils(String value) {
    this.value = value;
  }
  // map key với fiu trong model,T là model
  public <T> T toModel(Class<T> tClass) {
    try {
      // ObjectMapper chuyển json thành chuỗi và từ chuỗi thành json
      return new ObjectMapper().readValue(value, tClass);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }
  // HttpUtils là 1 kiểu dữ liệu
  // chuyển chuối json sang dạng string . (cộng chuỗi) tách từng chuỗi key->value của json chuyển
  // thành StringBuffer
  public static HttpUtils of(BufferedReader reader) {
    StringBuffer sb = new StringBuffer();
    String line;
    try {

      while ((line = reader.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      System.out.print(e.getMessage());
    }
    return new HttpUtils(sb.toString());
  }
}
