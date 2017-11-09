package cipher;

public class RailFence  {

   
    public String cifrado(String plain, int key) {
        int tam = plain.length();
        int div = tam / key;
        char matrix[][] = new char[key][div];
        int cont = 0;
        String cipherText = "";
        for (int i = 0; i < div; i++) {
            for (int j = 0; j < key; j++) {
                if (cont != tam) {
                    matrix[j][i] = plain.charAt(cont++);
                } else {
                    matrix[j][i] = 'x';
                }
            }
        }
      
        for (int i = 0; i < key; i++) {
            for (int j = 0; j < div; j++) {
                cipherText += matrix[i][j];
                if(i%2==0)
                    System.out.printf(""+matrix[i][j]+"   ");
                else
                    System.out.printf("   "+matrix[i][j]);
            }
            System.out.println("");
        }
        return cipherText;
    }

    public String descifrado(String cipher, int key) {
        int tam = cipher.length();
        int div = tam / key;
        char matrix[][] = new char[key][div];
        int k = 0;

        String plainText = "";

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < div; j++) {
                matrix[i][j] = cipher.charAt(k++);
            }
        }
        for (int i = 0; i < div; i++) {
            for (int j = 0; j < key; j++) {
                plainText += matrix[j][i];
            }
        }

        return plainText;
    }
}
