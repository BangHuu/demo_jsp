package com.laptrinhjavaweb.controller.web;

import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.model.UserModel;
import com.laptrinhjavaweb.service.ICategroyService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.utils.FormUtils;
import com.laptrinhjavaweb.utils.SessionUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(urlPatterns = {"/trang-chu","/dang-nhap","/thoat"})
public class HomeController extends HttpServlet {

  @Inject private ICategroyService iCategroyService;
  @Inject private IUserService iUserService;
  ResourceBundle resourceBundle = ResourceBundle.getBundle("message");

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
//      String title = "Bài viết 5";
//      String content = "bai viet 5";
//      Long categoryId = 1L;
//      NewModel newModel = new NewModel();
//      newModel.setTitle(title);
//      newModel.setContent(content);
//      newModel.setCategoryId(categoryId);
//      iNewService.save(newModel);
    //    Long categoryId = 1L;
    //    request.setAttribute("news", iNewService.findByCategoryId(categoryId));
    String action = request.getParameter("action");
    if (action != null && action.equals("login")) {
      String alert = request.getParameter("alert");
      String message = request.getParameter("message");
      if (message != null && alert != null) {
        request.setAttribute("message", resourceBundle.getString(message));
        request.setAttribute("alert", alert);
      }
      RequestDispatcher rd = request.getRequestDispatcher("/views/login.jsp");
      rd.forward(request, response);
    } else if (action != null && action.equals("logout")) {
      SessionUtil.getInstance().removeValue(request, "USERMODEL");
      response.sendRedirect(request.getContextPath()+"/trang-chu");
    } else {
      request.setAttribute("categories", iCategroyService.findAll());
      RequestDispatcher rd = request.getRequestDispatcher("/views/web/home.jsp");
      rd.forward(request, response);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String action = request.getParameter("action");
    if (action != null && action.equals("login")) {
      UserModel model = FormUtils.toModel(UserModel.class, request);
      model = iUserService.findByUserNameAndPasswordAndStatus(model.getUserName(), model.getPassword(), 1);
      if (model != null) {
        SessionUtil.getInstance().putValue(request, "USERMODEL", model);
        if (model.getRole().getCode().equals("USER")) {
          response.sendRedirect(request.getContextPath()+"/trang-chu");
        } else if (model.getRole().getCode().equals("ADMIN")) {
          response.sendRedirect(request.getContextPath()+"/admin-home");
        }
      } else {
        response.sendRedirect(request.getContextPath()+"/dang-nhap?action=login&message=username_password_invalid&alert=danger");
      }
    }
  }
}
