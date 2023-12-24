package Figures;

public class Pawn extends Figure
{

    private boolean isFirstStep = true;

    public Pawn(String name, char color)
    {
        super(name, color);
    }

    @Override
    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        //   if (!super.canMove(rowFrom, col, rowTo, colTo)){
        //       return false;
        //   }

        if (this.isFirstStep)
        {
            if (((((rowFrom + 2 == rowTo) || (rowFrom + 1 == rowTo)) && this.getColor() == 'w') ||
                    (((rowFrom - 2 == rowTo) || (rowFrom - 1 == rowTo)) && this.getColor() == 'b')) && (colFrom == colTo))
            {
                this.isFirstStep = false;
                return true;
            }

        }
        else
        {
            if ((((rowFrom + 1 == rowTo) && (this.getColor() == 'w')) || ((rowFrom - 1 == rowTo) && (this.getColor() == 'b'))) && (colFrom == colTo))
            {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean canAttack(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return Math.abs(rowFrom - rowTo) == 1 && Math.abs(colFrom - colTo) == 1;
    }
}