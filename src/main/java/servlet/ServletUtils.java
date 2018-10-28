package servlet;

import entity.Player;
import logic.ContextListener;
import logic.Game;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class ServletUtils {
    public static Player playerEnemy(String name, Game game) {
        if (game.getPlayer1().getName().equals(name)) {
            return game.getPlayer1();
        } else return game.getPlayer2();
    }

    public static void setAttributesForPlayer(HttpServletRequest request, Player player, boolean isHero) {
        if (isHero) request.setAttribute("hero_name", player.getName());
        else request.setAttribute("enemy_name", player.getName());
        if (isHero) request.setAttribute("hero_damage", player.getDamage());
        else request.setAttribute("enemy_damage", player.getDamage());
        if (isHero) request.setAttribute("hero_rating", player.getRating());
        else request.setAttribute("enemy_rating", player.getRating());
        if (isHero) request.setAttribute("hero_health", player.getHealth());
        else request.setAttribute("enemy_health", player.getHealth());
    }
    public static void setDateAndCountToZero(){
        ContextListener.date.get().setTime(new Date().getTime());
        ContextListener.sqlCount.set(0);
    }
}
