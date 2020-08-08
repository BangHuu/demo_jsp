package com.laptrinhjavaweb.dao.impl;

import com.laptrinhjavaweb.dao.GenericDAO;
import com.laptrinhjavaweb.mapper.RowMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AbstractDAO<T> implements GenericDAO<T> {
  ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

  public Connection getConnection() {
    try {
//      Class.forName("com.mysql.jdbc.Driver");
//      String url = "jdbc:mysql://localhost:3306/laptrinhjavaweb";
//      String user = "root";
//      String pass = "Banghd98?";
      Class.forName(resourceBundle.getString("driverName"));
      String url = resourceBundle.getString("url");
      String user = resourceBundle.getString("user");
      String password = resourceBundle.getString("password");
      return DriverManager.getConnection(url, user, password);
    } catch (ClassNotFoundException | SQLException e) {
      return null;
    }
  }

  @Override
  public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... parameters) {
    List<T> results = new ArrayList<>();
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      connection = getConnection();
      statement = connection.prepareStatement(sql);
      // set parameter
      setParameter(statement, parameters);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        results.add(rowMapper.mapRow(resultSet));
      }
      return results;
    } catch (SQLException e) {
      return null;
    } finally {
      // sử dụng finally để khi code trên lỗi thì vẫn có thể đóng
      try {
        if (connection != null) {
          connection.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
        return null;
      }
    }
  }

  @Override
  public void update(String sql, Object... parameter) {
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = getConnection();
      connection.setAutoCommit(false);
      statement = connection.prepareStatement(sql);
      setParameter(statement, parameter);
      statement.executeUpdate();
      connection.commit();
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
        if (statement != null) {
          statement.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public Long insert(String sql, Object... parameter) {
    ResultSet resultSet = null;
    Long id = null;
    Connection connection = null;
    PreparedStatement statement = null;
    try {
      connection = getConnection();
      connection.setAutoCommit(false);
      statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      setParameter(statement, parameter);
      statement.executeUpdate();
      resultSet = statement.getGeneratedKeys(); // todo  id tự tăng
      if (resultSet.next()) {
        id = resultSet.getLong(1);
      }

      connection.commit();
      return id;
    } catch (SQLException e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
      }
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public int count(String sql, Object... parameters) {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    try {
      int count = 0;
      connection = getConnection();
      statement = connection.prepareStatement(sql);
      setParameter(statement, parameters);
      resultSet = statement.executeQuery();
      while (resultSet.next()) {
        count = resultSet.getInt(1);
      }
      return count;
    } catch (SQLException e) {
      return 0;
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
        if (statement != null) {
          statement.close();
        }
        if (resultSet != null) {
          resultSet.close();
        }
      } catch (SQLException e) {
        return 0;
      }
    }
  }

  private void setParameter(PreparedStatement statement, Object... parameters) {
    try {
      for (int i = 0; i < parameters.length; i++) {
        Object parameter = parameters[i];
        int index = i + 1;
        if (parameter instanceof Long) {
          statement.setLong(index, (Long) parameter);
        } else if (parameter instanceof String) {
          statement.setString(index, (String) parameter);
        } else if (parameter instanceof Integer) {
          statement.setInt(index, (Integer) parameter);
        } else if (parameter instanceof Timestamp) {
          statement.setTimestamp(index, (Timestamp) parameter);
        } else if (parameter == null) {
          statement.setNull(index, Types.NULL);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
