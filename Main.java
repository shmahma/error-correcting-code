

import java.io.*;

public class Main {
    
    public static Matrix loadMatrix(String file, int r, int c) {
        byte[] tmp =  new byte[r * c];
        byte[][] data = new byte[r][c];
        try {
            FileInputStream fos = new FileInputStream(file);
            fos.read(tmp);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < r; i++)
            for (int j = 0; j< c; j++)
                data[i][j] = tmp[i * c + j];
            return new Matrix(data);
    }
    
    public static void main(String[] arg){
    	/*TACHE 1---------------------*/
        //byte[][] tab = {{1,0,0},{0,1,0},{0,0,1}};
        //byte[][] tab2 = {{0,0,0},{1,0,0},{1,0,1}};
        //Matrix m1 = new Matrix(tab);
        //Matrix m2 = new Matrix(tab2);
        //m1.display();
        //m2.display();
        ///m1.multiply(m2).display();
        //m1.add(m2).display();
        //m2.transpose().display();
        //Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        //Matrix hbasesys = hbase.sysTransform();
        //hbasesys.display();
        //Matrix gen=hbasesys.genG();
        //byte[][] tabu = {{1,0,1,0,1}};
        //Matrix u = new Matrix(tabu);
        //u.display();
        //gen.display();
        //Matrix x = u.multiply(gen);
        //hbase.display();
        /*TACHE 2-------------------------*/
        //TGraph tGraph = new TGraph(hbase, 3, 4);
        //tGraph.display();
        //tGraph.decode(x, 100).display();
        //x.display();
        //byte[][] tab = {{0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        //byte[][] tab2 = {{0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        //byte[][] tab3 = {{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}};
        //byte[][] tab4 = {{0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}};
        //Matrix e1= new Matrix(tab);
        //Matrix e2= new Matrix(tab2);
        //Matrix e3= new Matrix(tab3);
        //Matrix e4= new Matrix(tab4);
        //Matrix y1= x.add(e1);
        //Matrix y2= x.add(e2);
        //Matrix y3= x.add(e3);
        //Matrix y4= x.add(e4);
        //y1,y2,y3 la correction marche parce qu'elle contient moins de 3 erreurs
        //y4 ne marche pas parce qu'elle contient 3 erreurs
        //y3.display();
        //tGraph.decode(y3, 100).display();
    	/*TACHE 3----------------------------*/
    	Matrix hbase = loadMatrix("data/Matrix-2048-6144-5-15", 2048, 6144);
    	Matrix hbasesys = hbase.sysTransform();
    	Matrix gen = hbasesys.genG();
    	byte[][] tabu = new byte[1][6144-2048];
    	for(int i=0;i<6144-2048;i++) {
    		if(i%2==0) {
    			tabu[0][i]=1;
    		}
    		else {
    			tabu[0][i]=0;
    		}
    	}
    	Matrix u = new Matrix(tabu);
    	Matrix x = u.multiply(gen);
    	//x.display();
        TGraph tGraph = new TGraph(hbase, 5, 15);
        //tGraph.display();
        int successCount=0;
    	int failureCount=0;
        for(int i=0;i<Math.pow(10,4);i++) {
        	
        	Matrix err = x.errGen(124);
        	//Matrix err = x.errGen(134);
        	//Matrix err = x.errGen(144);
        	//Matrix err = x.errGen(154);
        	Matrix y = x.add(err);
        	Matrix dec=tGraph.decode(y,200);
        	if(dec.isEqualTo(x)) {
        		successCount++;;
        	}
        	else failureCount++;
        	

        }
        System.out.println("Success percentage: " + (successCount / (double)Math.pow(10,4)) * 100 + "%");
    	System.out.println("Failure percentage: " + (failureCount / (double)Math.pow(10,4)) * 100+ "%");
       
    }
}