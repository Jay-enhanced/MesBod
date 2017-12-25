package com.hmj.message.control;

import com.hmj.message.dao.MessageDAO;
import com.hmj.message.model.MessageEntity;
import com.hmj.message.model.UserEntity;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by woshi on 2017/12/24.
 */
@Controller
@RequestMapping(value = "/message")
public class MessageController {

    @Autowired
    public MessageDAO messageDAO;

    @RequestMapping(value = "/all")
    public void getAllMessage(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }
        UserEntity user = (UserEntity)user_ob;
        if(user.getType() != 1){
            response.getWriter().print("您不是管理员，请登录！");
            return;
        }
        List<MessageEntity> messagelist = messageDAO.getAllMessage();

        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = mapperObj.writeValueAsString(messagelist);
        //System.out.println(jsonStr);
        response.getWriter().print(jsonStr);
    }

    @RequestMapping(value = "/{id}")
    public void getOneMessage(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response,
                        @PathVariable("id") int id) throws IOException {
        //response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }
        UserEntity user = (UserEntity)user_ob;
        if(user.getType() != 1){
            response.getWriter().print("您不是管理员，请登录！");
            return;
        }
        MessageEntity message = messageDAO.getMessageById(id);

        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = mapperObj.writeValueAsString(message);
        //System.out.println(jsonStr);
        response.getWriter().print(jsonStr);
    }

    @RequestMapping(value = "/replyMess")
    public void getReplyMessage(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }
        List<MessageEntity> messagelist = messageDAO.getReplyMessage();

        ObjectMapper mapperObj = new ObjectMapper();
        String jsonStr = mapperObj.writeValueAsString(messagelist);
        //System.out.println(jsonStr);
        response.getWriter().print(jsonStr);
    }

    @RequestMapping(value = "/add")
    public void add_message(MessageEntity messageEntity, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }

        UserEntity user = (UserEntity)user_ob;
        Object real_ob = request.getParameter("real");
        if (real_ob == null) {
            messageEntity.setUsername(user.getUsername());
        }
        if(user.getType() == 1){
            messageEntity.setStatus(1);
        } else {
            messageEntity.setStatus(0);
        }
        Date now = new Date();
        messageEntity.setTime(now);

        messageDAO.addMessage(messageEntity);
        response.getWriter().print("留言成功");
    }

    @RequestMapping(value = "/reply")
    public void reply_message(HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }
        UserEntity user = (UserEntity)user_ob;
        if(user.getType() != 1){
            response.getWriter().print("您不是管理员，请登录！");
            return;
        }

        //request.setCharacterEncoding("utf-8");
        //response.setCharacterEncoding( "utf-8" );
        int mess_id = Integer.parseInt(request.getParameter("mess_id"));
        MessageEntity mess = messageDAO.getMessageById(mess_id);
        String content = request.getParameter("content");
        mess.setReply(content);
        mess.setStatus(1);
        messageDAO.updateMessage(mess);
        response.getWriter().print("回复成功");
    }

    @RequestMapping(value = "/delete")
    public void deleteMessage(MessageEntity messageEntity, HttpSession httpSession, HttpServletRequest request,
                              HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }
        UserEntity user = (UserEntity)user_ob;
        if(user.getType() != 1){
            response.getWriter().print("您不是管理员，请登录！");
            return;
        }
        int message_id = Integer.parseInt(request.getParameter("message_id"));
        MessageEntity message = messageDAO.getMessageById(message_id);
        if(message == null){
            response.getWriter().print("此id不存在");
            return;
        }
        messageDAO.deleteMessage(message);
        response.getWriter().print("删除成功");
    }

    @RequestMapping(value = "/delete/{id}")
    public void deleteMessage(MessageEntity messageEntity, HttpSession httpSession, HttpServletRequest request,
                              @PathVariable("id") int id, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        Object user_ob = httpSession.getAttribute("current_user");
        if(user_ob == null) {
            response.getWriter().print("请登录");
            return;
        }
        UserEntity user = (UserEntity)user_ob;
        if(user.getType() != 1){
            response.getWriter().print("您不是管理员，请登录！");
            return;
        }
        //int message_id = Integer.parseInt(request.getParameter("message_id"));
        MessageEntity message = messageDAO.getMessageById(id);
        if(message == null){
            response.getWriter().print("此id不存在");
            return;
        }
        messageDAO.deleteMessage(message);
        response.getWriter().print("删除成功");
    }

}
