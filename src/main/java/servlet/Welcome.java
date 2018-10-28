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

@WebServlet("/welcome")
public class Welcome extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(Welcome.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtils.setDateAndCountToZero();
        log.info("doGet");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/welcome.jsp");
        req.setAttribute("sqlCount", ContextListener.sqlCount.get());
        req.setAttribute("sqlTime", 0);
        dispatcher.forward(req, resp);
    }
}
