package com.laptrinhjavaweb.controller.admin;

import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.model.NewModel;
import com.laptrinhjavaweb.paging.PageRequest;
import com.laptrinhjavaweb.paging.Pageble;
import com.laptrinhjavaweb.service.ICategroyService;
import com.laptrinhjavaweb.service.INewService;
import com.laptrinhjavaweb.sort.Sorter;
import com.laptrinhjavaweb.utils.FormUtils;
import com.laptrinhjavaweb.utils.MessageUtil;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin-new"})
public class NewController extends HttpServlet {
    @Inject
    private INewService iNewService;
    @Inject
    private ICategroyService iCategroyService;
    //    totalPage = totalItem/maxpageItem
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        request.getParameter()

//        String pageStr = request.getParameter("page");
//        String maxPageItemStr = request.getParameter("maxPageItem");
//        if (pageStr != null) {
//            newModel.setPage(Integer.parseInt(pageStr));
//        } else {
//            newModel.setPage(1);
//        }
//        if (maxPageItemStr != null) {
//            newModel.setMaxPageItem(Integer.parseInt(maxPageItemStr));
//        }
//        Integer offset = (newModel.getPage() - 1 * newModel.getMaxPageItem());
//        newModel.setListResult(iNewService.findAll(offset, newModel.getMaxPageItem(),
//                newModel.getSortName(), newModel.getSortBy()));
        NewModel model = FormUtils.toModel(NewModel.class, request);
        String view = "";
        if (model.getType().equals(SystemConstant.LIST)) {
            Pageble pageble = new PageRequest(model.getPage(), model.getMaxPageItem(),
                    new Sorter(model.getSortName(), model.getSortBy()));
            model.setListResult(iNewService.findAll(pageble));
            model.setTotalItem(iNewService.getTotalItem());
            model.setTotalPage((int) Math.ceil((double) model.getTotalItem() / model.getMaxPageItem()));
            view = "/views/admin/new/list.jsp";
        } else if (model.getType().equals(SystemConstant.EDIT)) {
            if (model.getId() != null) {
                model = iNewService.findOne(model.getId());
            }
            request.setAttribute("categories", iCategroyService.findAll());
            view = "/views/admin/new/edit.jsp";
        }
        MessageUtil.showMessage(request);
        request.setAttribute(SystemConstant.MODEL, model);
        RequestDispatcher rd = request.getRequestDispatcher(view);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
