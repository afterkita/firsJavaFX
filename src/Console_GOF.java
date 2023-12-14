import sample.Conway_game_of_life;

public class Console_GOF {
    public static void main(String[] args) {
        Conway_game_of_life CGL = new Conway_game_of_life(100,100);
        CGL.randomSeed();
        CGL.iterate();
        byte[] dat = CGL.getData();
        System.out.println(dat);
        CGL.iterate();
        dat = CGL.getData();
        System.out.println(dat);
    }


}
