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

@WebServlet("/")
public class Login extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Login.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/login.jsp");
        log.info("doGet");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (ContextListener.db.get().userIsExist(login, password)) {
            session.setAttribute("name", login);
            log.info("doPost {} {}", login, password);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/welcome.jsp");
            dispatcher.forward(req, resp);
        } else if (ContextListener.db.get().findByName(login) == null) {
            session.setAttribute("name", login);
            ContextListener.db.get().createPlayer(new Player(login, password, 0, 10, 100, true));
            log.info("doPost new player {} {}", login, password);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/welcome.jsp");
            dispatcher.forward(req, resp);
        } else {
            session.invalidate();
            log.info("doPost invalidate {} {}", login, password);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/login.jsp");
            dispatcher.forward(req, resp);
        }

    }
}
