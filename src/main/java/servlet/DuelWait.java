package servlet;

import db.DB;
import entity.Player;
import logic.ContextListener;
import logic.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/duelWait")
public class DuelWait extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(DuelWait.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setDateAndCountToZero();
        HttpSession session = req.getSession();
        if (ContextListener.duels.get().containsKey(session.getAttribute("name"))) {
            log.info("doGet ");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/prepareFight");
            req.setAttribute("sqlTime", 0);
            req.setAttribute("sqlCount", ContextListener.sqlCount.get());
            dispatcher.forward(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/duelWait.jsp");
            log.info("doGet");
            req.setAttribute("sqlCount", ContextListener.sqlCount.get());
            req.setAttribute("sqlTime", 0);
            dispatcher.forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setDateAndCountToZero();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/duelWait.jsp");
        log.info("doPost");
        req.setAttribute("sqlCount", ContextListener.sqlCount.get());
        req.setAttribute("sqlTime", 0);
        dispatcher.forward(req, resp);
    }
}
