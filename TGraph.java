
public class TGraph {
    private int n_r;
    private int w_r;
    private int n_c;
    private int w_c;
    private int[][] left;
    private int[][] right;

    public TGraph(Matrix H, int wc, int wr) {
        this.n_r = H.getRows();
        this.w_r = wr;
        this.n_c = H.getCols();
        this.w_c = wc;

        left = new int[n_r][w_r + 1];
        right = new int[n_c][w_c + 1];

        for (int i = 0; i < n_r; i++) {
            for (int j = 0; j < w_r + 1; j++) {
                left[i][j] = 0;
            }
        }

        for (int i = 0; i < n_c; i++) {
            for (int j = 0; j < w_c + 1; j++) {
                right[i][j] = 0;
            }
        }

        for (int i = 0; i < n_r; i++) {
            int count = 1;
            for (int j = 0; j < n_c; j++) {
                if (H.getElem(i,j) == 1) {
                    left[i][count] = j;
                    count++;
                }
            }
        }

        for (int i = 0; i < n_c; i++) {
            int count = 1;
            for (int j = 0; j < n_r; j++) {
                if (H.getElem(j, i) == 1) {
                    right[i][count] = j;
                    count++;
                }
            }
        }
    }

    public void display() {
        System.out.println("left:");
        for (int i = 0; i < n_r; i++) {
            for (int j = 0; j <= w_r; j++) {
                System.out.print(left[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("right:");
        for (int i = 0; i < n_c; i++) {
            for (int j = 0; j <= w_c; j++) {
                System.out.print(right[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    public Matrix decode(Matrix code, int rounds) {
        Matrix correctedCode = new Matrix(code.getRows(),code.getCols());
        for(int i=0;i<code.getRows();i++) {
        	for(int j=0;j<code.getCols();j++) {
        		correctedCode.setElem(i, j, code.getElem(i, j));
        	}
        }
        for(int i=0;i<n_c;i++) {
        	right[i][0]=correctedCode.getElem(0, i);
        }
        int lim=0;
        while(lim!=rounds) {
            for (int i = 0; i < n_r; i++) {
                left[i][0] = 0;
                for(int j=1;j<w_r+1;j++) {
                	left[i][0] = (left[i][0] + right[left[i][j]][0]) % 2;
                }
            }
            int count =0;
            for (int i = 0; i < n_r; i++) {
                if(left[i][0] == 0) {
                	count++;
                }
            }
            if(count==n_r) {
            	for(int j=0;j<n_c;j++) {
                	correctedCode.setElem(0, j,(byte)right[j][0]);
                }
            	return correctedCode;
            }
            int max = 0;
            int[] dict = new int[n_c];
            for(int j=0;j<n_c;j++) {
            	dict[j]=0;
            	for(int i=1;i<w_c+1;i++) {
            		dict[j] = dict[j] + left[right[j][i]][0];
            	}
            	if(dict[j] > max) {
            		max=dict[j];
            	}
            }

            
            for (int j = 0; j < n_c; j++) {
                if (dict[j] == max) {
                    right[j][0] = 1 - right[j][0];
                }
            }
            lim++;
        }

        
        Matrix errorVector = new Matrix(1, code.getCols());
        for(int j=0;j<code.getCols();j++) {
        	errorVector.setElem(0, j,(byte) -1);
        }
        return errorVector;
    
    }

}

