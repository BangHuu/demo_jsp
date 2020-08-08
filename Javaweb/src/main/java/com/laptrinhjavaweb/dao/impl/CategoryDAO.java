package com.laptrinhjavaweb.dao.impl;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.mapper.CategoryMapper;
import com.laptrinhjavaweb.model.CategoryModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends AbstractDAO<CategoryModel> implements ICategoryDAO {
  @Override
  public List<CategoryModel> findAll() {
      String sql = "select * from category";
      return query(sql,new CategoryMapper());
//    List<CategoryModel> results = new ArrayList<>();
//    // mở connection
//    Connection connection = getConnection();
//    String sql = "select * from category";
//    //            dùng statment ko sử dụng dc tham số
//    //            dùng prepestatement dùng dc tham số ?? , nên dùng prepestatement
//    PreparedStatement statement = null;
//    ResultSet resultSet = null;
//    if (connection != null) {
//      try {
//        statement = connection.prepareStatement(sql);
//        // innit parameter
//        // resultSet như 1 bảng liên kết với db
//        resultSet = statement.executeQuery();
//        //        Kiểm tra resultSet.next() để xem có giá trị trong bảng ko thì mới vào resultSet để
//        // getData lên
//        while (resultSet.next()) {
//          CategoryModel categoryModel = new CategoryModel();
//          categoryModel.setId(resultSet.getLong("id"));
//          categoryModel.setName(resultSet.getString("name"));
//          categoryModel.setCode(resultSet.getString("code"));
//          results.add(categoryModel);
//        }
//      } catch (SQLException e) {
//        e.printStackTrace();
//      } finally {
//        // sử dụng finally để khi code trên lỗi thì vẫn có thể đóng
//        try {
//          if (connection != null) {
//            connection.close();
//          }
//          if (statement != null) {
//            statement.close();
//          }
//          if (resultSet != null) {
//            resultSet.close();
//          }
//        } catch (SQLException e) {
//          return null;
//        }
//      }
//    }
//    return results;
  }

    @Override
    public CategoryModel findOne(long id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        List<CategoryModel> category = query(sql, new CategoryMapper(), id);
        return category.isEmpty() ? null : category.get(0);
    }

    @Override
    public CategoryModel findOneByCode(String code) {
        String sql = "SELECT * FROM category WHERE code = ?";
        List<CategoryModel> category = query(sql, new CategoryMapper(), code);
        return category.isEmpty() ? null : category.get(0);
    }
}
