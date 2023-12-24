package Figures;

public class Rook extends Figure
{
    public Rook(String name, char color)
    {
        super(name, color);
    }

    @Override
    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return rowFrom == rowTo || colFrom == colTo;
    }

    @Override
    public boolean canAttack(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return this.canMove(rowFrom, colFrom, rowTo, colTo);
    }
}