
import java.util.*;


public class Matrix {
    private byte[][] data = null;
    private int rows = 0, cols = 0;
    
    public Matrix(int r, int c) {
        data = new byte[r][c];
        rows = r;
        cols = c;
    }
    
    public Matrix(byte[][] tab) {
        rows = tab.length;
        cols = tab[0].length;
        data = new byte[rows][cols];
        for (int i = 0 ; i < rows ; i ++)
            for (int j = 0 ; j < cols ; j ++) 
                data[i][j] = tab[i][j];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public byte getElem(int i, int j) {
        return data[i][j];
    }
    
    public void setElem(int i, int j, byte b) {
        data[i][j] = b;
    }
    
    public boolean isEqualTo(Matrix m){
        if ((rows != m.rows) || (cols != m.cols))
            return false;
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                if (data[i][j] != m.data[i][j])
                    return false;
                return true;
    }
    
    public void shiftRow(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < cols; i++){
            tmp = data[a][i];
            data[a][i] = data[b][i];
            data[b][i] = tmp;
        }
    }
    
    public void shiftCol(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < rows; i++){
            tmp = data[i][a];
            data[i][a] = data[i][b];
            data[i][b] = tmp;
        }
    }
     
    public void display() {
        System.out.print("[");
        for (int i = 0; i < rows; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            
            System.out.print("[");
            
            for (int j = 0; j < cols; j++) {
                System.out.printf("%d", data[i][j]);
                
                if (j != cols - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.print("]");
            
            if (i == rows - 1) {
                System.out.print("]");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                result.data[j][i] = data[i][j];
    
        return result;
    }
    
    public Matrix add(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if ((m.rows != rows) || (m.cols != cols))
            System.out.printf("Erreur d'addition\n");
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                r.data[i][j] = (byte) ((data[i][j] + m.data[i][j]) % 2);
        return r;
    }
    
    public Matrix multiply(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if (m.rows != cols)
            System.out.printf("Erreur de multiplication\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                r.data[i][j] = 0;
                for (int k = 0; k < cols; k++){
                    r.data[i][j] =  (byte) ((r.data[i][j] + data[i][k] * m.data[k][j]) % 2);
                }
            }
        }
        
        return r;
    }
    public void addRow(int a, int b) {
        
  
        for (int j = 0; j < cols; j++) 
                this.data[a][j] = (byte) ((this.data[a][j] + this.data[b][j]) % 2);
    	
    }
    public void addCol(int a, int b) {
        
    	  
        for (int i = 0; i < rows; i++) 
                this.data[i][a] = (byte) ((this.data[i][a] + this.data[i][b]) % 2);
    	
    }
    public Matrix sysTransform() {
    	Matrix r = this.add(new Matrix(rows,cols));
    	for(int i=0;i<rows;i++) {
    		int j=cols-rows+i;
    		for(int k=i+1;k<rows;k++) {
    			if(r.data[k][j]==1){
    				r.shiftRow(i, k);
    				break;
    			}
    		}
    		for(int k=i+1;k<rows;k++) {
    			if(r.data[k][j]==1)
    				if(r.data[k][j]==(byte)1) {
    					r.addRow(k, i);
    				}
    		}
    	}
    	int l=1;
    	for(int i=rows-1;i>=0;i--) {
    		int j=cols-l;
    		for(int k=i-1;k>=0;k--) {
    			if(r.data[k][j]==(byte)1) {
    				r.addRow(k, i);
    			}
    		}
    		l++;
    	}
    	
    	
    	return r;
    	
    }
    public Matrix genG() {
    	Matrix r = new Matrix(cols-(rows),cols);
    	Matrix d = new Matrix(rows, cols-rows);
    	for(int i=0;i<rows;i++) {
    		for(int j=0;j<cols-rows;j++) {
    			d.data[i][j]=(byte)data[i][j];
    		}
    	}
    	Matrix l=d.transpose();
    	
    	for(int i=0;i<r.rows;i++) {
    		r.data[i][i]=(byte)1;
    	}
    	
    	for(int i=0;i<r.rows;i++) {
    		for(int j=r.rows;j<r.cols;j++) {
    			r.data[i][j]=(byte)l.data[i][j-r.rows];
    		}
    	}
    	
    	return r;
    }
    public Matrix errGen(int w) {
        Matrix vector = new Matrix(1, cols);
        Random random = new Random();
        
        for (int i = 0; i < w; i++) {
            int indexrand = random.nextInt(cols);
            
            if (vector.data[0][indexrand] == 0) {
                vector.data[0][indexrand] = 1;
            } 
            else {
                i--;
            }
        }
        
        return vector;
    }
    
}

