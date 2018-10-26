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

@WebServlet("/fight")
public class Fight extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Fight.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/fight.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String name = (String) session.getAttribute("name");
        Game game = ContextListener.duels.get().get(name);
        game.hit(ContextListener.db.get().findByName(name));
        if (game.isPlayer1Dead() || game.isPlayer2Dead()) {
            if (game.getPlayer1().getName().equals(name)) {
                req.setAttribute("message", "Вы победили!");
                ServletUtils.setAttributesForPlayer(req, ContextListener.db.get().findByName(name), true);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/summary.jsp");
                dispatcher.forward(req, resp);
                return;
            } else {
                req.setAttribute("message", "Вы проиграли!");
                ServletUtils.setAttributesForPlayer(req, ContextListener.db.get().findByName(name), true);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/summary.jsp");
                dispatcher.forward(req, resp);
                return;
            }
        }
        log.info("doPost");
        Player hero = ServletUtils.playerEnemy(name, game);
        Player enemy;
        if (game.getPlayer1().getName().equals(name)) enemy = game.getPlayer2();
        else enemy = game.getPlayer1();
        ServletUtils.setAttributesForPlayer(req, hero, true);
        ServletUtils.setAttributesForPlayer(req, enemy, false);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/templates/fight.jsp");
        dispatcher.forward(req, resp);
    }


}
