package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.service.ICategroyService;

import javax.inject.Inject;
import java.util.List;

public class CategoryService implements ICategroyService {
  //  private ICategoryDAO iCategoryDAO;
  //
  //  public CategoryService() {
  //    iCategoryDAO = new CategoryDAO();
  //  }
  @Inject // thay cho đoạn code trên
  private ICategoryDAO iCategoryDAO;

  @Override
  public List<CategoryModel> findAll() {
    return iCategoryDAO.findAll();
  }
}
