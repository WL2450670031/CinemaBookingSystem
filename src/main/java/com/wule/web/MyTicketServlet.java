package com.wule.web;

import com.wule.pojo.Seat;
import com.wule.pojo.User;
import com.wule.service.SelectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//创建于2022/6/25 23:44
@WebServlet("/myTicketServlet")
public class MyTicketServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        String userPower = req.getParameter("userPower");
        SelectService service = new SelectService();
        List<Seat> list = null;

        if (userPower.equals("visitor"))
        {
            user.setUserPower(userPower);
        }
        else
        {
            String userNum = req.getParameter("userNum");
            try
            {//获取用户全部信息
                user = service.userDataService(userNum);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

            try
            {//获取用户票库
                list = service.getTicketService(userNum);
            } catch (SQLException e)
            {
                throw new RuntimeException(e);
            }

        }

        req.setAttribute("user",user);
        req.setAttribute("ticketList",list);
        req.getRequestDispatcher("/myTicket.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
