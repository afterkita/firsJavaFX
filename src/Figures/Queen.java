package Figures;

public class Queen extends Figure
{
    public Queen(String name, char color)
    {
        super(name, color);
    }

    @Override
    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        if ((rowFrom == rowTo && colFrom != colTo) || (rowFrom != rowTo && colFrom == colTo) || (Math.abs(rowFrom - rowTo) == Math.abs(colFrom - colTo)))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean canAttack(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return this.canMove(rowFrom, colFrom, rowTo, colTo);
    }
}