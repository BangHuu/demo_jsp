package com.laptrinhjavaweb.mapper;

import com.laptrinhjavaweb.model.NewModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewMapper implements RowMapper<NewModel> {
    @Override
    public NewModel mapRow(ResultSet rs) {
        NewModel newModel = new NewModel();
        try {
            newModel.setId(rs.getLong("id"));
            newModel.setTitle(rs.getString("title"));
            newModel.setContent(rs.getString("content"));
            newModel.setCategoryId(rs.getLong("categoryid"));
            newModel.setThumbnail(rs.getString("thumbnail"));
            newModel.setShortDescription(rs.getString("shortdescription"));
            newModel.setCreatedDate(rs.getTimestamp("createddate"));
            newModel.setCreatedBy(rs.getString("createdby"));
            if(rs.getTimestamp("modifieddate")!=null){
                newModel.setModifiedDate(rs.getTimestamp("modifieddate"));// chỉ thêm khi thực hiện doPut
            }
            if(rs.getString("modifiedby")!=null){
                newModel.setModifiedBy(rs.getString("modifiedby"));
            }
            return newModel;
        } catch (SQLException e) {
            return null;
        }
    }
}
