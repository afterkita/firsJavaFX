package Application;

import Figures.*;

import static Application.Application.running;

import java.util.ArrayList;

public class Board
{
    //TODO: Список фигур и начальное положение всех фигур
    private Figure fields[][] = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);

    private static final boolean check = false;

    public char getColorGaming()
    {
        return colorGaming;
    }

    public void setColorGaming(char colorGaming)
    {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init()
    {
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'), new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'),
                new Pawn("P", 'w'), new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'), new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'),
                new Pawn("P", 'b'), new Pawn("P", 'b'),
        };
    }

    public String getCell(int row, int col)
    {
        Figure figure = this.fields[row][col];

        if (figure == null)
        {
            return "    ";
        }

        return " " + figure.getColor() + figure.getName() + " ";
    }

    public ArrayList<String> getTakeWhite()
    {
        return takeWhite;
    }

    public ArrayList<String> getTakeBlack()
    {
        return takeBlack;
    }

    public boolean moveFigure(int rowFrom, int colFrom, int rowTo, int colTo)
    {

        Figure figure = this.fields[rowFrom][colFrom];

        if (figure == null)
        {
            return false;
        }

        if (
                this.fields[rowFrom][colFrom].getColor() != this.colorGaming
                        || this.fields[rowTo][colTo] instanceof King
        )
        {
            return false;
        }


        if (canMove(rowFrom, colFrom, rowTo, colTo))
        {
            this.fields[rowTo][colTo] = figure;
            this.fields[rowFrom][colFrom] = null;

            int[] kingLocation = getKingLocation();

            if (
                    this.fields[rowFrom][colFrom] instanceof King
                            && isCheck(rowTo, colTo)
                            || isCheck(kingLocation[0], kingLocation[1])
            )
            {
                System.out.println("check");

                this.fields[rowTo][colTo] = null;
                this.fields[rowFrom][colFrom] = figure;

                return false;
            }

            System.out.println("move");

            return true;
        } else if (canAttack(rowFrom, colFrom, rowTo, colTo))
        {
            Figure tempFigure = this.fields[rowTo][colTo];

            this.fields[rowTo][colTo] = figure;
            this.fields[rowFrom][colFrom] = null;

            int[] kingLocation = getKingLocation();

            if (
                    this.fields[rowFrom][colFrom] instanceof King
                            && isCheck(rowTo, colTo)
                            || isCheck(kingLocation[0], kingLocation[1])
            )
            {
                System.out.println("check");

                this.fields[rowTo][colTo] = tempFigure;
                this.fields[rowFrom][colFrom] = figure;

                return false;
            }


            System.out.println("attack");
            switch (this.fields[rowTo][colTo].getColor())
            {
                case 'w':
                {
                    this.takeWhite.add(this.fields[rowTo][colTo].getColor() + this.fields[rowTo][colTo].getName());
                    break;
                }
                case 'b':
                {
                    this.takeBlack.add(this.fields[rowTo][colTo].getColor() + this.fields[rowTo][colTo].getName());
                    break;
                }
            }

            return true;
        }

        return false;
    }

    public boolean canMove(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return (
                this.fields[rowTo][colTo] == null
                        && this.fields[rowFrom][colFrom].canMove(rowFrom, colFrom, rowTo, colTo)
                        && !hasObstacles(rowFrom, colFrom, rowTo, colTo)
        );
    }

    public boolean canAttack(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        return (
                this.fields[rowTo][colTo] != null
                        && this.fields[rowFrom][colFrom].canAttack(rowFrom, colFrom, rowTo, colTo)
                        && !hasObstacles(rowFrom, colFrom, rowTo, colTo)
                        && this.fields[rowTo][colTo].getColor() != this.fields[rowFrom][colFrom].getColor()
        );
    }

    public boolean isCheck(int row, int col)
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (this.fields[i][j] != null)
                {
                    Figure figure = this.fields[i][j];

                    if (
                            figure.getColor() != this.getColorGaming()
                                    && figure.canAttack(i, j, row, col)
                                    && !hasObstacles(i, j, row, col)
                    )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isCheckMate()
    {
        int[] kingLocation = getKingLocation();

        int row = kingLocation[0];
        int col = kingLocation[1];

        ArrayList<int[]> posibleMoves = new ArrayList<>();

        if (!isCheck(row, col))
        {
            return false;
        }

        for (int i = -1; i < 2; i++)
        {
            for (int j = -1; j < 2; j++)
            {
                if (
                        row + i < 0
                                || row + i > 7
                                || col + j < 0
                                || col + j > 7
                )
                {
                    continue;
                }


                if (this.fields[row + i][col + j] == null)
                {
                    posibleMoves.add(new int[]{row + i, col + j});
                }

                if (
                        !isCheck(row + i, col + j)
                                && (
                                canMove(row, col, row + i, col + i)
                                        || canAttack(row, col, row + i, col + i)
                        )
                )
                {
                    return false;
                }
            }
        }

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (this.fields[i][j] != null)
                {

                    Figure figure = this.fields[i][j];

                    if (figure instanceof King)
                    {
                        continue;
                    }

                    for (int[] possibleMove : posibleMoves)
                    {
                        int tempRow = possibleMove[0];
                        int tempCol = possibleMove[1];

                        if (canMove(i, j, tempRow, tempCol))
                        {
                            this.fields[tempRow][tempCol] = figure;
                            this.fields[i][j] = null;

                            if (!isCheck(row, col))
                            {
                                this.fields[tempRow][tempCol] = null;
                                this.fields[i][j] = figure;

                                return false;
                            }

                            this.fields[tempRow][tempCol] = null;
                            this.fields[i][j] = figure;
                        }
                        else if (canAttack(i, j, tempRow, tempCol))
                        {
                            Figure tempFigure = this.fields[tempRow][tempCol];

                            this.fields[tempRow][tempCol] = figure;
                            this.fields[i][j] = null;

                            if (!isCheck(row, col))
                            {
                                this.fields[tempRow][tempCol] = tempFigure;
                                this.fields[i][j] = figure;

                                return false;
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private int[] getKingLocation()
    {
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                if (
                        this.fields[i][j] instanceof King
                                && this.fields[i][j].getColor() == this.getColorGaming()
                )
                {
                    return new int[]{i, j};
                }
            }
        }

        return new int[0];
    }

    public boolean hasObstacles(int rowFrom, int colFrom, int rowTo, int colTo)
    {
        if (this.fields[rowFrom][colFrom] instanceof Knight)
        {
            return false;
        }

        int dx, dy;

        if (rowFrom == rowTo)
        {
            dx = 0;
        } else
        {
            dx = (rowTo - rowFrom) / Math.abs(rowTo - rowFrom);
        }

        if (colFrom == colTo)
        {
            dy = 0;
        } else
        {
            dy = (colTo - colFrom) / Math.abs(colTo - colFrom);
        }

        while (colFrom != colTo || rowFrom != rowTo)
        {
            colFrom += dy;
            rowFrom += dx;

            if (
                    (colFrom != colTo || rowFrom != rowTo)
                            && this.fields[rowFrom][colFrom] != null
            )
            {
                return true;
            }
        }

        return false;
    }

    public void printBoard()
    {
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1; row--)
        {
            System.out.print(row);
            for (int col = 0; col < 8; col++)
            {
                System.out.print("|" + getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for (int col = 0; col < 8; col++)
        {
            System.out.print("    " + col);
        }
    }
}