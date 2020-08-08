package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.dao.ICategoryDAO;
import com.laptrinhjavaweb.dao.INewDAO;
import com.laptrinhjavaweb.model.CategoryModel;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.INewService;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

public class NewService implements INewService {
  @Inject private INewDAO iNewDAO;
  @Inject private ICategoryDAO iCategoryDAO;
  @Override
  public List<NewModel> findByCategoryId(Long categoryId) {
    return iNewDAO.findByCategoryId(categoryId);
  }

  @Override
  public NewModel save(NewModel newModel) {
    newModel.setCreatedDate(new Timestamp(System.currentTimeMillis()));
    CategoryModel category = iCategoryDAO.findOneByCode(newModel.getCategoryCode());
    newModel.setCategoryId(category.getId());
    Long newId = iNewDAO.save(newModel);
    return iNewDAO.findOne(newId);
  }

  @Override
  public NewModel update(NewModel updateNew) {
    NewModel oldNew = iNewDAO.findOne(updateNew.getId());
    updateNew.setCreatedDate(oldNew.getCreatedDate());
    updateNew.setCreatedBy(oldNew.getCreatedBy());
    updateNew.setModifiedDate(new Timestamp(System.currentTimeMillis()));// lấy ngày giờ hiện tại
    CategoryModel category = iCategoryDAO.findOneByCode(updateNew.getCategoryCode());
    updateNew.setCategoryId(category.getId());
    iNewDAO.update(updateNew);
    return iNewDAO.findOne(updateNew.getId());
  }

  @Override
  public void delete(long[] ids) {
    for (long id : ids) {
      // xóa comment trc khi xóa news
      iNewDAO.delete(id);
    }
  }

  @Override
  public List<NewModel> findAll(Pageble pageble) {
    return iNewDAO.findAll(pageble);
  }

  @Override
  public int getTotalItem() {
    return iNewDAO.getTotalItem();
  }

  @Override
  public NewModel findOne(long id) {
    NewModel newModel = iNewDAO.findOne(id);
    CategoryModel categoryModel = iCategoryDAO.findOne(newModel.getCategoryId());
    newModel.setCategoryCode(categoryModel.getCode());
    return newModel;
  }
}
