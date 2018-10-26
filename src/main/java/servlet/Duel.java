package servlet;

import db.DB;
import entity.Player;
import logic.ContextListener;
import logic.DuelMaker;
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

@WebServlet("/duel")
public class Duel extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Duel.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Player player = ContextListener.db.get().findByName((String) session.getAttribute("name"));
        req.setAttribute("name", player.getName());
        req.setAttribute("damage", player.getDamage());
        req.setAttribute("health", player.getHealth());
        req.setAttribute("rating", player.getRating());
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/duel.jsp");
        DuelMaker.add(ContextListener.db.get().findByName((String) req.getSession().getAttribute("name")));
        DuelMaker.makeGame();
        log.info("doGet");
        dispatcher.forward(req, resp);
    }
}
