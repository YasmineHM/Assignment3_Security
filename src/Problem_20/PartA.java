package Problem_20;

/**
 * Created by yasmine on 4/5/2017.
 */
public class PartA {


        private static int L=0x01234567;
        private static int R=0x89ABCDEF;
        private static int [] K=new int[4];
        private static int delta = 0x9e3779b9;
        public static void main(String args[])
        {
            System.out.println();
            System.out.println("Plain Text is "+Integer.toHexString(L)+Integer.toHexString(R));
            System.out.println();
            System.out.println( "The Resulted Cipher Text : "+Encryption());
            System.out.println();
            System.out.println("The Restored plain Text : "+Decryption());
        }
        public static String Encryption()
        {
            long plaintext=0x0123456789ABCDEFL;
            K[0]= 0xA56BABCD;
            K[1]=0x00000000;
            K[2]=0xFFFFFFFF;
            K[3] =0xABCDEF01;
            System.out.println("------------- Encryption Function -------------");
            int sum = 0;
            for (int i=0;i<32;i++){
                sum += delta;
                L += ((R<<4)+K[0])^(R+sum)^((R>>5)+K[1]);

                R += ((L<<4)+K[2])^(L+sum)^((L>>5)+K[3]);
                System.out.println("Sum after Round : "+(i+1));
                System.out.println(Integer.toHexString(sum));
                System.out.println();
                System.out.println("Left block after Round :"+(i+1));
                System.out.println(Integer.toHexString(L));
                System.out.println();
                System.out.println("Right block after Round :"+(i+1));
                System.out.println(Integer.toHexString(R));
                System.out.println();
            }

            String ciphertext = Integer.toHexString(L)+Integer.toHexString(R);
            return  ciphertext;
        }
        public static String Decryption()
        {
            System.out.println("------------- Decryption Function -------------");
            int  sum = delta << 5;
            for(int i=0;i<32;i++)
            {

                R -=((L<<4)+K[2])^(L+sum)^((L>>5)+K[3]);
                L -= ((R<<4)+K[0])^(R+sum)^((R>>5)+K[1]);
                sum -= delta;

                System.out.println("Sum after Round : "+(i+1));
                System.out.println(Integer.toHexString(sum));
                System.out.println();
                System.out.println("Left block after Round :"+(i+1));
                System.out.println(Integer.toHexString(L));
                System.out.println();
                System.out.println("Right block after Round :"+(i+1));
                System.out.println(Integer.toHexString(R));
                System.out.println();

            }
            String plain = Integer.toHexString(L)+Integer.toHexString(R);
            return plain;

        }



}
