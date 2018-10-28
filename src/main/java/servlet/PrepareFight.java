package servlet;

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
import java.util.Date;

@WebServlet("/prepareFight")
public class PrepareFight extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(PrepareFight.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Date sqlTime = new Date();
        ServletUtils.setDateAndCountToZero();
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("name");
        Game game = ContextListener.duels.get().get(name);
        Player hero = ServletUtils.playerEnemy(name, game);
        Player enemy;
        if (game.getPlayer1().getName().equals(name)) enemy = game.getPlayer2();
        else enemy = game.getPlayer1();
        ServletUtils.setAttributesForPlayer(req, hero, true);
        ServletUtils.setAttributesForPlayer(req, enemy, false);
        log.info("doGet {} {} ", hero, enemy);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/prepareFight.jsp");
        req.setAttribute("sqlCount", ContextListener.sqlCount.get());
        req.setAttribute("sqlTime", (new Date().getTime()-sqlTime.getTime()));
        dispatcher.forward(req, resp);
    }
}
