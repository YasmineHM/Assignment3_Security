package Problem_9;

/**
 * Created by yasmine on 4/7/2017.
 */
public class RC4 {
    private static int [] key = {0x1A,0x2B,0x3C,0x4D,0x5E,0x6F,0x77};
    private static int [] s= new int[256];
    private static int [] k= new int[256];
    private static int j=0;
    private static int i=0;
    public static void main(String[] args) {

        Intialization();
        System.out.println("the permutation S and indices i and j after the initialization phase has completed.");
        Print();

        i=j=0;
        int t=0;
        int keystreamByte=0;
        for(int r=0;r<100;r++){
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            int  temp=s[i];
            s[i]=s[j];
            s[j]=temp;
            t = (s[i] + s[j]) % 256;
            keystreamByte = s[t];
        }
        System.out.println("the permutation S and indices i and j after the first 100 bytes of keystream have been generated");
        Print();
        i=0; j=0;
        for(int r=0;r<1000;r++){
            i = (i + 1) % 256;
            j = (j + s[i]) % 256;
            int  temp=s[i];
            s[i]=s[j];
            s[j]=temp;
            t = (s[i] + s[j]) % 256;
            keystreamByte = s[t];
        }
        System.out.println("after the first 1000 bytes of keystream have been generated: ");
        Print();
    }
    public static void Intialization()
    {
        for(int i=0;i<256;i++){
            s[i]=i;
            k[i]=key[i%7];
        }
        //int j=0;
        int temp=0;
        for(int i=0;i<256;i++){
            j = (j + s[i] + k[i]) % 256;
            temp=s[i];
            s[i]=s[j];
            s[j]=temp;
        }
    }
    public static void Print()
    {
        System.out.println();
        System.out.println("i: "+i);
        System.out.println("j: "+j);
        System.out.println();
        System.out.println("---------- S ----------");
        for(int m=0;m<256;m++){
            if(m%16==0)
                System.out.println("");
            System.out.print(" "+s[m]+" ");
        }
        System.out.println("\n");
    }
}
