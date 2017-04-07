package Problem_5;

/**
 * Created by yasmine on 4/5/2017.
 */

public class A5_1 {


        private static int X8,Y10,Z10;
        private static int flag=0;
        private static String s="";
        private static String tt;
        public static void main( String args[] ){

            String x = "1010101010101010101";

            String y = "1100110011001100110011";

            String z = "11100001111000011110000";

            for(int ii=0;ii<32;ii++){
                System.out.println("-------------------------------------------------");
                System.out.println(" Bit NO "+ii+1+" Of Key Stream");
                System.out.println("-------------------------------------------------");
                System.out.println("Register X :"+x);
                System.out.println("Register Y :"+y);
                System.out.println("Register Z :"+z);
                X8 =GetRegistersBit(x,8);
                System.out.println("X8 ---> "+X8);
                Y10 =GetRegistersBit(y,10);
                System.out.println("Y10 ---> "+Y10);
                Z10 =GetRegistersBit(z,10);
                System.out.println("Z10 ---> "+Z10);


                int m=GetMajority(X8,Y10,Z10);
                System.out.println("Majority ----> "+GetMajority(X8,Y10,Z10));

                if(X8==m)
                {
                    //bits 13,16,17,18
                    System.out.println("X Step ");
                    int t = GetRegistersBit(x,13)^GetRegistersBit(x,16) ^GetRegistersBit(x,17) ^GetRegistersBit(x,18);

                    x=Integer.toString(t).concat(x.substring(0 , x.length()-1));

                    System.out.println("t   --->  "+t);

                    System.out.println("x new   --->  "+ x);
                    System.out.println(x.length());
                }
                if(Y10==m)
                {
                    //bits 20,21
                    System.out.println("Y Step ");
                    int t = GetRegistersBit(y,20) ^GetRegistersBit(y,21);
                    y=Integer.toString(t).concat(y.substring(0 , y.length()-1));

                    System.out.println("t   --->  "+t);
                    System.out.println("y new   --->  "+y);
                }
                if(Z10==m)
                {
                    //bits 7,20,21,22
                    System.out.println("Z Step ");

                    int t = GetRegistersBit(z,7) ^GetRegistersBit(z,20 )^GetRegistersBit(z,21) ^GetRegistersBit(z,22 );

                    z=Integer.toString(t).concat(z.substring(0 , z.length()-1));

                    System.out.println("t   --->  "+t);
                    System.out.println("z new   --->  "+z);
                }

                s+=GetRegistersBit(x,18) ^GetRegistersBit(y,21) ^GetRegistersBit(z,22) ;
                System.out.println("stream bits --> "+s);}
        }
        public static int GetMajority(int x, int y, int z)
        {
            int major;
            if(x==1 && y==1 &&z==1)
            {
                major=1;

            }
            else if(x==0 && y==0 &&z==0)
            {
                major=0;

            }
            else
            {
                major=(x^y^z)^1;

            }
            return major;
        }
        public static int  GetRegistersBit(String register,int i)
        {


            return   Integer.parseInt(""+register.charAt(i));
        }





}
