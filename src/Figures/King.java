package Figures;

public class King extends Figure
{
    public King(String name, char color)
    {
        super(name, color);
    }

    @Override
    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        if (
                (Math.abs(rowFrom - rowTo) == 1 && colFrom == colTo)
                        || (rowFrom == rowTo && Math.abs(colFrom - colTo) == 1)
                        || ((Math.abs(rowFrom - rowTo) == 1 && Math.abs(colFrom - colTo) == 1))
        )
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