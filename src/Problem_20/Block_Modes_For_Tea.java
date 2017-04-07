package Problem_20;

/**
 * Created by yasmine on 4/6/2017.
 */
public class Block_Modes_For_Tea {

    final static int BLOCKSIZE = 4;
    final static int DELTA = 0x9e3779b9;
    int IV;
    int []k;
     String plainText="";
    static String encryptionText="";


    public Block_Modes_For_Tea(String newPlainText, int[]key, int IV)
    {
        this.plainText = newPlainText;
        this.k=key;
        this.IV=IV;
        encryptionText="";

        /**
         * ECB encryption
         **/
        encryptionText=encryptionText.concat("-------------------- ECB encryption -------------------- \n" );

        encryptionText=encryptionText.concat("-------------------- Plain text blocks -------------------- \n");

        int[][] c = ECBencrypt(plainText, k);	//encrypt
        encryptionText=encryptionText.concat("-------------------- Cipher text blocks-------------------- \n");
        printCipherData(c);                    	//display cipher text
        encryptionText=encryptionText.concat("-------------------- Restored Plain text  -------------------- \n");
        encryptionText=encryptionText.concat(ECBdecrypt(c, k) + "\n"); //decrypt and display
        /**
         * CBC encryption demonstraion
         **/
        encryptionText=encryptionText.concat("-------------------- CBC encryption -------------------- \n");
        encryptionText=encryptionText.concat("-------------------- Plain text blocks -------------------- \n");
        c = CBCencrypt(plainText, k, IV); 	//encrypt
        encryptionText=encryptionText.concat("-------------------- Cipher text blocks-------------------- \n");
        printCipherData(c);                    	//display cipher text

        encryptionText=encryptionText.concat("-------------------- Restored Plain text  -------------------- \n");
        encryptionText=encryptionText.concat(CBCdecrypt(c, k, IV) + "\n"); //decrypt and display
        /**
         * CTR encryption
         **/
        encryptionText=encryptionText.concat("-------------------- CBC encryption -------------------- \n");
        encryptionText=encryptionText.concat("-------------------- Plain text blocks -------------------- \n");
        c = CTRencrypt(plainText,k, IV); 	//encrypt
        encryptionText=encryptionText.concat("-------------------- Cipher text blocks-------------------- \n");
        printCipherData(c);                    	//display cipher text

        encryptionText=encryptionText.concat("-------------------- Restored Plain text  -------------------- \n");
        encryptionText=encryptionText.concat(CTRdecrypt(c, k, IV) + "\n"); //decrypt and display

        System.out.println(encryptionText);
    }


    public static int[][] ECBencrypt(String plainText, int [] key)
    {
        int numOfBlocks = ((plainText.length() - (plainText.length() % 8)) / 8);
        if((plainText.length() % 8) == 0)
        {
            int i = 0, j = 0;
            String block = null;
            int[][] cipherData = new int[numOfBlocks][];
            for(i = 0, j = 0; i < numOfBlocks; i++, j = j + 8)
            {
                block = plainText.substring(j, j + 8);
                encryptionText= encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n" );
                cipherData[i] = encrypt(block, key);
            }
            return cipherData;
        }
        else //accounts for the case with a partial-block at the end
        {
            numOfBlocks++;
            int i = 0, j = 0;
            String block = null;
            int[][] cipherData = new int[numOfBlocks][];
            for(i = 0, j = 0; i < numOfBlocks - 1; i++, j = j + 8)
            {
                block = plainText.substring(j, j + 8);
                encryptionText=encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n" );
                cipherData[i] = encrypt(block, key);
            }
            block = plainText.substring(j, plainText.length());
            if(block.length() < 8 && block.length() > 0)
            {
                while(block.length() < 8)
                {
                    block = block + " ";
                }
                cipherData[i] = encrypt(block, key);
                encryptionText=encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n" );
            }
            return cipherData;
        }
    }

    public static String ECBdecrypt(int[][] cipherData, int[] key)
    {
        String plainText = "";

        for(int i = 0; i < cipherData.length; i++)
        {
            plainText = plainText + decrypt(cipherData[i], key);

        }
        return plainText;
    }

    public static int[][] CBCencrypt(String plainText, int [] key, int iv)
    {
        int numOfBlocks = ((plainText.length() - (plainText.length() % 8)) / 8);
        if((plainText.length() % 8) == 0)
        {
            int i = 0, j = 0;
            String block = null;
            int[][] cipherData = new int[numOfBlocks][];
            for(i = 0, j = 0; i < numOfBlocks; i++, j = j + 8)
            {
                block = plainText.substring(j, j + 8);
                encryptionText=encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n" );
                String leftHalf = block.substring(0, BLOCKSIZE);
                String rightHalf = block.substring(BLOCKSIZE, block.length());
                if(i == 0)
                {
                    int[] p = {textToNum3(leftHalf) ^ iv, textToNum3(rightHalf) ^ iv};
                    cipherData[i] = encrypt_cbc(p, key);
                }
                else
                {
                    int[] p = {textToNum3(leftHalf) ^ cipherData[i - 1][0],
                            textToNum3(rightHalf) ^ cipherData[i - 1][1]};
                    cipherData[i] = encrypt_cbc(p, key);
                }
            }
            return cipherData;
        }
        else //accounts for the case with a partial-block at the end
        {
            numOfBlocks++;
            int i = 0, j = 0;
            String block = null;
            int[][] cipherData = new int[numOfBlocks][];
            for(i = 0, j = 0; i < numOfBlocks - 1; i++, j = j + 8)
            {
                block = plainText.substring(j, j + 8);
                encryptionText= encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n");
                String leftHalf = block.substring(0, BLOCKSIZE);
                String rightHalf = block.substring(BLOCKSIZE, block.length());
                if(i == 0)
                {
                    int[] p = {textToNum3(leftHalf) ^ iv, textToNum3(rightHalf) ^ iv};
                    cipherData[i] = encrypt_cbc(p, key);
                }
                else
                {
                    int[] p = {textToNum3(leftHalf) ^ cipherData[i - 1][0],
                            textToNum3(rightHalf) ^ cipherData[i - 1][1]};
                    cipherData[i] = encrypt_cbc(p, key);
                }
            }
            block = plainText.substring(j, plainText.length());
            if(block.length() < 8 && block.length() > 0)
            {
                while(block.length() < 8)
                {
                    block = block + " ";
                }
                String leftHalf = block.substring(0, BLOCKSIZE);
                String rightHalf = block.substring(BLOCKSIZE, block.length());
                if(i == 0)
                {
                    int[] p = {textToNum3(leftHalf) ^ iv, textToNum3(rightHalf) ^ iv};
                    cipherData[i] = encrypt_cbc(p, key);
                }
                else
                {
                    int[] p = {textToNum3(leftHalf) ^ cipherData[i - 1][0],
                            textToNum3(rightHalf) ^ cipherData[i - 1][1]};
                    cipherData[i] = encrypt_cbc(p, key);
                }
                encryptionText=encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n");
            }
            return cipherData;
        }
    }

    public static String CBCdecrypt(int[][] cipherData, int [] key, int iv)
    {
        String plainText = "";
        for(int i = 0; i < cipherData.length; i++)
        {
            int[] temp = decrypt_cbc(cipherData[i], key);
            if(i == 0)
            {
                temp[0] = iv ^ temp[0];
                temp[1] = iv ^ temp[1];
            }
            else
            {
                temp[0] = cipherData[i - 1][0] ^ temp[0];
                temp[1] = cipherData[i - 1][1] ^ temp[1];
            }
            plainText = plainText + numToText3(temp[0]) + numToText3(temp[1]);
        }
        return plainText;
    }

    public static int[][] CTRencrypt(String plainText, int []key, int iv)
    {
        int numOfBlocks = ((plainText.length() - (plainText.length() % 8)) / 8);
        if((plainText.length() % 8) == 0)
        {
            int i = 0, j = 0;
            String block = null;
            int[][] cipherData = new int[numOfBlocks][];
            for(i = 0, j = 0; i < numOfBlocks; i++, j = j + 8)
            {
                block = plainText.substring(j, j + 8);
                encryptionText= encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n");
                String leftHalf = block.substring(0, BLOCKSIZE);
                String rightHalf = block.substring(BLOCKSIZE, block.length());


                int[] p = {textToNum3(leftHalf), textToNum3(rightHalf)};
                int[] ivPlain = {iv + i, iv + i};
                int[] ivCipher = encrypt_cbc(ivPlain, key);
                p[0] = p[0] ^ ivCipher[0];
                p[1] = p[1] ^ ivCipher[1];
                cipherData[i] = p;
            }
            return cipherData;
        }
        else //accounts for the case with a partial-block at the end
        {
            numOfBlocks++;
            int i = 0, j = 0;
            String block = null;
            int[][] cipherData = new int[numOfBlocks][];
            for(i = 0, j = 0; i < numOfBlocks - 1; i++, j = j + 8)
            {
                block = plainText.substring(j, j + 8);
                encryptionText= encryptionText.concat("plain-block " + i + " = " + "[" + block + "] \n" );
                String leftHalf = block.substring(0, BLOCKSIZE);
                String rightHalf = block.substring(BLOCKSIZE, block.length());
                int[] p = {textToNum3(leftHalf), textToNum3(rightHalf)};
                int[] ivPlain = {iv + i, iv + i};
                int[] ivCipher = encrypt_cbc(ivPlain, key);
                p[0] = p[0] ^ ivCipher[0];
                p[1] = p[1] ^ ivCipher[1];
                cipherData[i] = p;
            }
            block = plainText.substring(j, plainText.length());
            if(block.length() < 8 && block.length() > 0)
            {
                while(block.length() < 8)
                {
                    block = block + " ";
                }
                String leftHalf = block.substring(0, BLOCKSIZE);
                String rightHalf = block.substring(BLOCKSIZE, block.length());
                int[] p = {textToNum3(leftHalf), textToNum3(rightHalf)};
                int[] ivPlain = {iv + i, iv + i};
                int[] ivCipher = encrypt_cbc(ivPlain, key);
                p[0] = p[0] ^ ivCipher[0];
                p[1] = p[1] ^ ivCipher[1];
                cipherData[i] = p;
                encryptionText=encryptionText.concat("plain block  no ----->" + i + " = " + "[" + block + "] \n" );
            }
            return cipherData;
        }
    }

    public static String CTRdecrypt(int[][] cipherData, int []key, int iv)
    {
        String plainText = "";
        for(int i = 0; i < cipherData.length; i++)
        {
            int[] ivPlain = {iv + i, iv + i};
            int[] ivCipher = encrypt_cbc(ivPlain, key);
            int[] p = cipherData[i];
            p[0] = p[0] ^ ivCipher[0];
            p[1] = p[1] ^ ivCipher[1];
            plainText = plainText + numToText3(p[0]) + numToText3(p[1]);
        }
        return plainText;
    }

    public static int textToNum3(String text)
    {
        StringBuffer lBinary = new StringBuffer();

        byte[] lBytes = text.getBytes();

        for(int z = 0; z < lBytes.length; z++)
        {
            if(lBytes[z] < 32)
            {
                lBinary.append("000").append(Integer.toBinaryString(lBytes[z]));
            }
            else if(lBytes[z] >= 32 && lBytes[z] < 64)
            {
                lBinary.append("00").append(Integer.toBinaryString(lBytes[z]));
            }
            else if(64 <= lBytes[z] && lBytes[z] < 128)
            {
                lBinary.append("0").append(Integer.toBinaryString(lBytes[z]));
            }
            else
            {
                lBinary.append(Integer.toBinaryString(lBytes[z]));
            }
        }
        return Integer.parseInt(lBinary.toString(), 2);
    }


    public static String numToText3(int num)
    {
        String outBinary = Integer.toBinaryString(num);

        while(outBinary.length() < 32)
        {
            outBinary = "0" + outBinary;
        }

        byte[] outBytes = new byte[BLOCKSIZE];

        for(int i = 0, j = 0; i < outBinary.length(); i = i + 8, j++)
        {
            String bytePiece = outBinary.substring(i, i + 8);
            outBytes[j] = (byte)Integer.parseInt(bytePiece, 2);
        }

        String outString = "";
        for(int i = 0; i < outBytes.length; i++)
        {
            outString = outString + (char)outBytes[i];
        }
        return outString;
    }

    public static int[] encrypt(String plain, int [] key)
    {
        String leftHalf = plain.substring(0, BLOCKSIZE);
        String rightHalf = plain.substring(BLOCKSIZE, plain.length());

        int mult = 255 / 32;
        int l = textToNum3(leftHalf);
        int r = textToNum3(rightHalf);
        int sum = 0;
        for(int i = 0; i < 32; i++)
        {
            sum += DELTA;
            l += ((r << 4) + key[0]) ^ (r + sum) ^ ((r >> 5) + key[1]);
            r += ((l << 4) + key[2]) ^ (l + sum) ^ ((l >> 5) + key[3]);

            encryptionText=encryptionText.concat("Round no "+i+"\n"+" left :" +l + "    Right : "+r +"\n");

        }
        int[] cipher = {l, r};
        return cipher;
    }

    public static String decrypt(int[] cipher, int [] key)
    {
        int mult = 255 / 32;
        int l = cipher[0];
        int r = cipher[1];
        int sum = DELTA << 5;
        for(int i = 0; i < 32; i++)
        {
            r -= ((l << 4) + key[2]) ^ (l + sum) ^ ((l >> 5) + key[3]);
            l -= ((r << 4) + key[0]) ^ (r + sum) ^ ((r >> 5) + key[1]);
            encryptionText=encryptionText.concat("Round no "+i+"\n"+" left :" +l + "    Right : "+r +"\n");
            sum -= DELTA;
        }
        String plain = numToText3(l) + numToText3(r);
        return plain;
    }

    public static int[] encrypt_cbc(int[] plain, int []key)
    {
        int mult = 255 /32;
        int l = plain[0];
        int r = plain[1];
        int sum = 0;
        for(int i = 0; i < 32; i++)
        {
            sum += DELTA;
            l += ((r << 4) + key[0]) ^ (r + sum) ^ ((r >> 5) + key[1]);
            r += ((l << 4) + key[2]) ^ (l + sum) ^ ((l >> 5) + key[3]);
            encryptionText=encryptionText.concat("Round no "+i+"\n"+" left :" +l + "    Right : "+r +"\n");
        }
        int[] cipher = {l, r};
        return cipher;
    }


    public static int[] decrypt_cbc(int[] cipher, int [] key)
    {
        int mult = 255 / 32;
        int l = cipher[0];
        int r = cipher[1];
        int sum = DELTA << 5;
        for(int i = 0; i < 32; i++)
        {
            r -= ((l << 4) + key[2]) ^ (l + sum) ^ ((l >> 5) + key[3]);
            l -= ((r << 4) + key[0]) ^ (r + sum) ^ ((r >> 5) + key[1]);
            encryptionText=encryptionText.concat("Round no "+i+"\n"+" left :" +l + "    Right : "+r +"\n");
            sum -= DELTA;
        }
        int[] p = {l, r};
        return p;
    }


    public static void printCipherData(int[][] cipherData)
    {
        for(int q = 0; q < cipherData.length; q++)
        {
            if(cipherData[q] != null)
            {
                encryptionText= encryptionText.concat("cipher block  no -----> "+ q + " = " + "[");
                printCipherBlock(cipherData[q]);
                encryptionText= encryptionText.concat("]" + "\n");
            }
        }
    }


    public static void printCipherBlock(int[] cipher)
    {
        if(cipher != null)
        {
            encryptionText= encryptionText.concat(numToText3(cipher[0]) + numToText3(cipher[1]));
        }
        else
        {
            encryptionText= encryptionText.concat("NullArry");
        }
    }
}
