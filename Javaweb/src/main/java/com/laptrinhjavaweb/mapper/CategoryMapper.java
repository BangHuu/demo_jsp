package com.laptrinhjavaweb.mapper;

import com.laptrinhjavaweb.model.CategoryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements RowMapper<CategoryModel> {
    @Override
    public CategoryModel mapRow(ResultSet rs) {
        CategoryModel categoryModel = new CategoryModel();

        try {
            categoryModel.setId(rs.getLong("id"));
            categoryModel.setName(rs.getString("name"));
            categoryModel.setCode(rs.getString("code"));
            return categoryModel;
        } catch (SQLException e) {
            return null;
        }

    }
}
