package servlet;

import entity.Player;
import logic.ContextListener;
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
import java.util.Date;

//@WebServlet("/")
public class Login extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Login.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setDateAndCountToZero();
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/login.jsp");
        log.info("doGet");
        req.setAttribute("sqlCount", ContextListener.sqlCount.get());
        req.setAttribute("sqlTime", 0);
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setDateAndCountToZero();
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Date sqlTime = new Date();
        if (ContextListener.db.get().userIsExist(login, password)) {
            if (ContextListener.playersOnline.get().containsKey(login)) {
                session.invalidate();
                log.info("doPost error {} {}", login, password);
                req.setAttribute("error", "Ошибка игрок уже онлайн");
                RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/login.jsp");
                req.setAttribute("sqlCount", ContextListener.sqlCount.get());
                req.setAttribute("sqlTime", (new Date().getTime()-sqlTime.getTime()));
                dispatcher.forward(req, resp);
                return;
            }
            ContextListener.playersOnline.get().put(login, ContextListener.db.get().findByName(login));
            session.setAttribute("name", login);
            session.setAttribute("damage", ContextListener.playersOnline.get().get(login).getDamage());
            session.setAttribute("health", ContextListener.playersOnline.get().get(login).getHealth());
            session.setAttribute("rating", ContextListener.playersOnline.get().get(login).getRating());
            log.info("doPost {} {}", login, password);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/welcome.jsp");
            req.setAttribute("sqlCount", ContextListener.sqlCount.get());
            req.setAttribute("sqlTime", (new Date().getTime()-sqlTime.getTime()));
            dispatcher.forward(req, resp);
        } else if (ContextListener.db.get().findByName(login) == null) {
            session.setAttribute("name", login);
            ContextListener.db.get().createPlayer(new Player(login, password, 0, 10, 100, true));
            log.info("doPost new player {} {}", login, password);
            session.setAttribute("damage", 10);
            session.setAttribute("health", 100);
            session.setAttribute("rating", 0);
            ContextListener.playersOnline.get().put(login, ContextListener.db.get().findByName(login));
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/welcome.jsp");
            req.setAttribute("sqlCount", ContextListener.sqlCount.get());
            req.setAttribute("sqlTime", (new Date().getTime()-sqlTime.getTime()));
            dispatcher.forward(req, resp);
        } else if (password != null) {
            session.invalidate();
            req.setAttribute("error", "Неправильный пароль!");
            log.info("doPost invalidate {} {}", login, password);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/login.jsp");
            req.setAttribute("sqlCount", ContextListener.sqlCount.get());
            req.setAttribute("sqlTime", (new Date().getTime()-sqlTime.getTime()));
            dispatcher.forward(req, resp);
        } else {
            session.invalidate();
            log.info("doPost invalidate exit {} {}", login);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/login.jsp");
            req.setAttribute("sqlCount", ContextListener.sqlCount.get());
            req.setAttribute("sqlTime", (new Date().getTime()-sqlTime.getTime()));
            dispatcher.forward(req, resp);
        }
    }
}
