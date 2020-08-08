package com.laptrinhjavaweb.utils;

import javax.servlet.http.HttpServletRequest;

public class SessionUtil {
    private static SessionUtil sessionUtil = null;
    // giải quyết vấn đề tạo nhiều session
    // kiểm tra null thì tạo còn có r thì dùng lại
    public static SessionUtil getInstance() {
        if (sessionUtil == null) {
            sessionUtil = new SessionUtil();
        }
        return sessionUtil;
    }
    // todo duy trì thông tin ng dùng
    public void putValue(HttpServletRequest request, String key, Object value) {
        request.getSession().setAttribute(key, value);
    }
    // todo lấy thông tin người dùng
    public Object getValue(HttpServletRequest request, String key) {
        return request.getSession().getAttribute(key);
    }
    // todo xóa thông tin
    public void removeValue(HttpServletRequest request, String key) {
        request.getSession().removeAttribute(key);
    }
}
