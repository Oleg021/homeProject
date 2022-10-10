package com.nix.vyrvykhvost;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestRepository repo = RequestRepository.getInstance();
        String ipAddress = req.getRemoteAddr();
        String agent = req.getHeader("user-agent");
        Request thisRequest = new Request(ipAddress, agent);
        repo.add(thisRequest);
        req.setAttribute("repository", repo.getAll());
        resp.setContentType("text/html");
        RequestDispatcher rd = req.getRequestDispatcher("indexx.jsp");
        rd.forward(req, resp);
    }
}
