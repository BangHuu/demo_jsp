package com.laptrinhjavaweb.dao;

import com.laptrinhjavaweb.mapper.RowMapper;

import java.util.List;

public interface GenericDAO<T>  {
//    Object...parameters để truyền dc nhiểu tham số vào câu query
    <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters);
    void update(String sql, Object... parameter);
    Long insert(String sql,Object... parameter);
    int count(String sql, Object... parameters);
}
