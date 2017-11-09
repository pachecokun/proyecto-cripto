package cipher;

public class TwistedPathCipher{
    
        /*Crea llave para mensaje de cierta longitud*/
        public char[] createKey(int length){
                     
            int cols = 5;
            int rows = (int)Math.ceil((float)length/cols);
            
            char[] key = new char[cols*rows+2];
            
            key[0] = (char)(rows-1);
            key[1] = (char)(cols-1);
            
            int k = 2;
            
            for(int i = cols -1;i>=0;i--){
                for (int j = 0; j <rows-1; j++) {
                    key[k++] = i%2==0?'u':'d';
                }
                key[k++] = 'l';
            }
            return key;
        }
    
        /*Encripta mensaje*/
	public char[] encrypt(char[] message, char[] key){
		
		int cols = 5;
		int rows = (int)Math.ceil((float)message.length/cols);
		
		char[] res = new char[cols*rows];
		
		char[][] table = new char[rows][cols];
	
		int k = 0;
		for(int i = 0;i<rows;i++){
			for(int j = 0;j<cols;j++){
				table[i][j] = message[k++];
				if(k==message.length){
					break;
				}
			}
			if(k==message.length){
				break;
			}
		}
	
		int i = key[0];
		int j = key[1];
		
		for(k = 2;k<key.length;k++){
			res[k-2] = table[i][j];
			if(key[k]=='u'){
				i--;
			}else if(key[k]=='d'){
				i++;
			}else if(key[k]=='l'){
				j--;
			}else if(key[k]=='r'){
				j++;
			}
		}
		
		return res;
	}
        
        /*Encripta mensaje utilizando llave generada*/
        public char[] encrypt(char[] message){
            return encrypt(message, createKey(message.length));
        }
        
        /*Desencripta*/
	public char[] decrypt(char[] ciphertext, char[] key){
		char[] res = new char[ciphertext.length];
		
		int cols = 5;
		int rows = (int)Math.ceil(ciphertext.length/5.0);
		
		char[][] table = new char[rows][cols];
	
		int k;	
		int i = key[0];
		int j = key[1];
		
		for(k = 2;k<key.length;k++){
			table[i][j] = ciphertext[k-2];
			if(key[k]=='u'){
				i--;
			}else if(key[k]=='d'){
				i++;
			}else if(key[k]=='l'){
				j--;
			}else if(key[k]=='r'){
				j++;
			}
		}
		
		k = 0;
		
		for(i = 0;i<rows;i++){
			for(j = 0;j<cols;j++){
				res[k++] = table[i][j];
			}
		}
		
		return res;
	}
        
        /*Genera llave y desencripta*/
        public char[] decrypt(char[] message){
            return decrypt(message, createKey(message.length));
        }
        
}

