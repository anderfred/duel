package servlet;

import logic.ContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/win")
public class Win extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Win.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", "Вы победили!");
        ContextListener.duels.get().remove((String) req.getSession().getAttribute("name"));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/summary.jsp");
        log.info("doGet");
        dispatcher.forward(req, resp);
    }


}
