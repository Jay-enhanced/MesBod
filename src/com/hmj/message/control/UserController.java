package com.hmj.message.control;

import com.hmj.message.dao.UserDAO;
import com.hmj.message.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by woshi on 2017/12/24.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    public UserDAO userDAO;
    
    @RequestMapping(value = "/register")
    public void register(UserEntity user, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (userDAO.getUserByUsername(user.getUsername()) == null)
        {
            user.setType(0);
            userDAO.addUser(user);
            response.getWriter().print("注册成功");
        }
        else {
            response.getWriter().print("注册失败");
        }
    }

    @RequestMapping(value = "/login")
    public void login(UserEntity user, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserEntity user0 = userDAO.getUserByUsername(user.getUsername());
        if (user0 != null && user0.getPassword().equals(user.getPassword()))
        {
            httpSession.setAttribute("current_user", user0);
            if(user0.getType() == 1){
                response.getWriter().print("管理员");
            } else {
                response.getWriter().print("登录成功");
            }
        }
        else {
            response.getWriter().print("登录失败");
        }
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        httpSession.removeAttribute("current_user");
        response.getWriter().print("注销成功");
    }

    @RequestMapping(value = "/currentuser")
    public void getcurrentuser(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }

        UserEntity user = (UserEntity)user_ob;
        response.getWriter().print(user.getUsername());
    }
}
