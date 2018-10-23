import java.util.UUID;

public class Player {
    private String name, password;
    private int rating, damage, health;
    private boolean ready;
    private String  uuid;

    public Player(String name, String password, int rating, int damage, int health, boolean ready) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.rating = rating;
        this.damage = damage;
        this.health = health;
        this.ready = ready;
    }

    public Player( String uuid , String name, String password, int rating, int damage, int health, boolean ready) {
        this.name = name;
        this.password = password;
        this.rating = rating;
        this.damage = damage;
        this.health = health;
        this.ready = ready;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", rating=" + rating +
                ", damage=" + damage +
                ", health=" + health +
                ", ready=" + ready +
                ", uuid=" + uuid +
                '}';
    }

    public boolean isReady() {
        return ready;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
