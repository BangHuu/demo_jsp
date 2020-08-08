package com.laptrinhjavaweb.controller.admin.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.utils.HttpUtils;
import com.laptrinhjavaweb.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/api-admin-new"})
public class NewAPI extends HttpServlet {
  @Inject private INewService iNewService;

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      NewModel newModel =  HttpUtils.of(request.getReader()).toModel(NewModel.class); // convert từu json chuyển sang NewModel
      newModel.setCreatedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
      newModel = iNewService.save(newModel);
      mapper.writeValue(response.getOutputStream(), newModel); // xuất dữ liệu ra
  }

  protected void doPut(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("application/json");
    NewModel updateNew = HttpUtils.of(request.getReader()).toModel(NewModel.class);
    updateNew.setModifiedBy(((UserModel) SessionUtil.getInstance().getValue(request, "USERMODEL")).getUserName());
    updateNew = iNewService.update(updateNew);
    mapper.writeValue(response.getOutputStream(), updateNew);
  }

  protected void doDelete(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      ObjectMapper mapper = new ObjectMapper();
      request.setCharacterEncoding("UTF-8");
      response.setContentType("application/json");
      NewModel newModel = HttpUtils.of(request.getReader()).toModel(NewModel.class);
      iNewService.delete(newModel.getIds());
      mapper.writeValue(response.getOutputStream(),"{}");
  }
}
