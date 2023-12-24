package Figures;

public class Bishop extends Figure{
    public Bishop(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo) {
        if (Math.abs(rowFrom - rowTo) == Math.abs(colFrom - colTo)){
            return true;
        }
        return false;
    }

    @Override
    public boolean canAttack(int rowFrom, int colFrom, int rowTo, int colTo) {
        return this.canMove(rowFrom, colFrom, rowTo, colTo);
    }
}