package sample;

public class Conway_game_of_life {

    private final int width;
    private final int height;
    private final int size;
    private int seedCount = 9500;
    byte[] data;
    public Conway_game_of_life(){

        this(100, 100);

    }
    public Conway_game_of_life(int width, int height){
        this.width = width;
        this.height = height;
        this.size = width * height;
        data = new byte[size];
    }

    public void iterate(){
        byte[] prev = new byte[size];
        System.arraycopy(data, 0, prev, 0, size);
        byte[] next = new byte[size];
        for ( int i = 0; i < width; i++ ){
            for ( int j = 0; j < height; j++ ){
                int type = isAlive(i, j, prev);
                if ( type > 0 ){
                    next[j * width + i] = 1;
                }else{
                    next[j * width + i] = 0;
                }
            }
        }
        System.arraycopy(next, 0, data, 0, size);
    }

    protected int isAlive(int x, int y, byte[] d){
        int count = 0;
        int pos1 = y * width + x;
        for ( int i = x-1; i <= x + 1; i++ ){
            for ( int j = y - 1; j <= y + 1; j++ ){
                int pos = j * width + i;
                if ( pos >= 0 && pos < size - 1 && pos != pos1){
                    if ( d[pos] == 1 ){
                        count++;
                    }
                }
            }
        }

        //dead

        if ( d[pos1] == 0 ){

            if ( count == 3 ){//becomes alive.

                return 1;
            }
            return 0;//still dead
        }else{//live
            if ( count < 2 || count > 3 ){//Dies
                return 0;
            }
            return 1;//lives

        }

    }
    public void randomSeed(){
        for ( int i = 0; i < seedCount; i++ ){
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            data[y * width + x] = 1;
        }
    }
    public byte[] getData(){
        return data;
    }
}
