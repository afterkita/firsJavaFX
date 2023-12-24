package Figures;

// 1(w) - White 2(b) - Black;

public abstract class Figure
{
    public char getColor()
    {
        return color;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setColor(char color)
    {
        this.color = color;
    }

    private String name;

    public String getName()
    {
        return name;
    }

    private char color;

    //private int[] position = new int[2];  // 0- row ; 1 - col


    public Figure(String name, char color)
    {
        this.name = name;
        this.color = color;
    }

    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return (
                (rowFrom >= 0 && rowFrom < 8)
                        && (colFrom >= 0 && colFrom < 8)
                        && (rowTo >= 0 && rowTo < 8)
                        && (colTo >= 0 && colTo < 8)
                        && (rowFrom != rowTo)
                        && (colFrom != colTo)
        );
    }

    public boolean canAttack(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return this.canMove(rowFrom, colFrom, rowTo, colTo);
    }
}