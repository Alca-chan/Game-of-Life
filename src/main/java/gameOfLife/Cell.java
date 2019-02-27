package gameOfLife;

public class Cell {
    private boolean alive = false;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public String toString() {
        if (alive) {
            return "O";
        } else {
            return " ";
        }
    }
}
