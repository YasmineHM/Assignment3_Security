package Problem_20;

/**
 * Created by yasmine on 4/7/2017.
 */
public class Main {
    static String PlainText1="0123456789ABCDEF";
    static String PlainText2="Four score and seven years ago our fathers brought forth on this continent, a new nation, conceived in Liberty, and dedicated to the proposition that all men are created equal.";
    private static int IV = 0x32ac78ef;
    static int []k=new int[4];
    public static void main(String[] args) {
        k[0]= 0xA56BABCD;
        k[1]=0x00000000;
        k[2]=0xFFFFFFFF;
        k[3] =0xABCDEF01;
       // Block_Modes_For_Tea blockModesForTea =new Block_Modes_For_Tea(PlainText1,k,IV);
        Block_Modes_For_Tea blockModesForTea2 =new Block_Modes_For_Tea(PlainText2,k,IV);
    }
}
