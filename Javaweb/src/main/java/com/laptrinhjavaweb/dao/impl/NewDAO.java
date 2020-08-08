package com.laptrinhjavaweb.dao.impl;

import com.laptrinhjavaweb.dao.INewDAO;
import com.laptrinhjavaweb.mapper.NewMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;

import java.util.List;

public class NewDAO extends AbstractDAO<NewModel> implements INewDAO {

    @Override
    public List<NewModel> findByCategoryId(Long categoryId) {
        String sql = "select * from news where categoryid = ?";
        return query(sql, new NewMapper(), categoryId);
        //    List<NewModel> results = new ArrayList<>();
        //    // mở connection
        //    Connection connection = getConnection();
        //    String sql = "select * from news whrer categoryid=?";
        //    //            dùng statment ko sử dụng dc tham số
        //    //            dùng prepestatement dùng dc tham số ?? , nên dùng prepestatement
        //    PreparedStatement statement = null;
        //    ResultSet resultSet = null;
        //    if (connection != null) {
        //      try {
        //        statement = connection.prepareStatement(sql);
        //        statement.setLong(1,categoryId);
        //        // innit parameter
        //        // resultSet như 1 bảng liên kết với db
        //        resultSet = statement.executeQuery();
        //        //        Kiểm tra resultSet.next() để xem có giá trị trong bảng ko thì mới vào
        // resultSet để
        //        // getData lên
        //        while (resultSet.next()) {
        //          NewModel newModel = new NewModel();
        //          newModel.setId(resultSet.getLong("id"));
        //          newModel.setTitle(resultSet.getString("title"));
        //          results.add(newModel);
        //        }
        //        return results;
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
        //      return results;
    }

    @Override
    public Long save(NewModel newModel) {
        //      commit nếu tất cả transaction success thì sẽ thêm dữ liệu vào db
        //      rollback nếu 1 transaction fail thì rollback( sẽ ko cho các transaction khác thay đổi
        // db)
        //    String sql = "insert into news(title,content,categoryid) values(?,?,?)";
        //    Connection connection = null;
        //    PreparedStatement statement = null;
        //    ResultSet resultSet = null;
        //    Long id = null;
        //    try {
        //      connection = getConnection();
        //      connection.setAutoCommit(false);
        //      statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);// todo để
        // sử dụng getGeneratedKeys() cần thêm Statement.RETURN_GENERATED_KEYS
        //      statement.setString(1, newModel.getTitle());
        //      statement.setString(2, newModel.getContent());
        //      statement.setLong(3, newModel.getCategoryId());
        //      statement.executeUpdate();
        //      resultSet = statement.getGeneratedKeys(); // todo  id tự tăng
        //      if (resultSet.next()) {
        //        id = resultSet.getLong(1);
        //      }
        //      connection
        //          .commit(); // todo Khi dùng executeUpdate() mà ko commit sẽ ko lưu data xuống
        // database
        //      return id;
        //    } catch (SQLException e) {
        //      if (connection != null) {
        //        try {
        //          connection.rollback();
        //        } catch (SQLException ex) {
        //          ex.printStackTrace();
        //        }
        //      }
        //      return null;
        //    } finally {
        //      try {
        //        if (connection != null) {
        //          connection.close();
        //        }
        //        if (statement != null) {
        //          statement.close();
        //        }
        //        if (resultSet != null) {
        //          resultSet.close();
        //        }
        //      } catch (SQLException e) {
        //        return null;
        //      }
        //    }
        //    String sql = "insert into news(title,content,categoryid) values(?,?,?)";
        StringBuilder sql = new StringBuilder("insert into news(title,content,");
        sql.append("thumbnail,shortdescription,categoryid,createddate,createdby)");
        sql.append("values(?,?,?,?,?,?,?)");
        return insert(
                sql.toString(),
                newModel.getTitle(),
                newModel.getContent(),
                newModel.getThumbnail(),
                newModel.getShortDescription(),
                newModel.getCategoryId(),
                newModel.getCreatedDate(),
                newModel.getCreatedBy());
    }

    @Override
    public NewModel findOne(Long id) {
        String sql = "select * from news where id = ? ";
        List<NewModel> news = query(sql, new NewMapper(), id);
        return news.isEmpty()
                ? null
                : news.get(0); // nếu như rỗng trả về null còn có giá trị sẽ trả về newModel đầu tiên
    }

    @Override
    public void update(NewModel updateNew) {
        StringBuilder sql = new StringBuilder("update news set title = ? , thumbnail=?,");
        sql.append("shortdescription = ?,content=?,categoryid=?,");
        sql.append("createddate=?,createdby=?,modifieddate=?,modifiedby=? where id = ?");
        update(
                sql.toString(),
                updateNew.getTitle(),
                updateNew.getThumbnail(),
                updateNew.getShortDescription(),
                updateNew.getContent(),
                updateNew.getCategoryId(),
                updateNew.getCreatedDate(),
                updateNew.getCreatedBy(),
                updateNew.getModifiedDate(),
                updateNew.getModifiedBy(),
                updateNew.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "delete from news where id=?";
        update(sql, id);
    }

    @Override
    public List<NewModel> findAll(Pageble pageble) {
////        String sql = "select * from news limit ?,?";
//        return query(sql, new NewMapper(),offset,limit);
//        StringBuilder sql = new StringBuilder("SELECT * FROM news");
//        if (sortName != null && sortBy != null) {
//            sql.append(" order by "+sortName+" "+sortBy+" ");
//        }
//        if (offset != null && limit != null) {
//            sql.append(" limit"+offset+", "+limit);
//            return query(sql.toString(), new NewMapper(), offset, limit);
//        } else {
//            return query(sql.toString(), new NewMapper());
//        }
        StringBuilder sql = new StringBuilder("SELECT * FROM news");
        if (pageble.getSorter() != null) {
            sql.append(" ORDER BY "+pageble.getSorter().getSortName()+" "+pageble.getSorter().getSortBy()+"");
        }
        if (pageble.getOffset() != null && pageble.getLimit() != null) {
            sql.append(" LIMIT "+pageble.getOffset()+", "+pageble.getLimit()+"");
        }
        return query(sql.toString(), new NewMapper());
    }

    @Override
    public int getTotalItem() {
        String sql = "SELECT count(*) FROM news";
        return count(sql);
    }
}
