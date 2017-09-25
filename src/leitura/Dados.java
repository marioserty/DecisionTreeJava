/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leitura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import sun.net.www.content.audio.x_aiff;

/**
 *
 * @author Mário
 */
public class Dados {
    public static double[][] entrada = new double[275][256];
    public static double[][] saidaDesejada = new double[275][2];

    public void setEntrada(FileReader entrada) throws IOException {
        BufferedReader buffer = new BufferedReader(entrada);
        String head = buffer.readLine();
        
        for(int i = 0; buffer.ready(); i++){
            
            String line = buffer.readLine();
            String[] vetor = line.split(",");
            
            for (int j = 0; j < vetor.length-1; j++) {
                this.entrada[i][j] = Double.valueOf(vetor[j+1]);
            }
        }
    }
    
    public void setSaidaDesejada(FileReader entrada) throws IOException {
        BufferedReader buffer = new BufferedReader(entrada);
        String head = buffer.readLine();
        
        for(int i = 0; buffer.ready(); i++){
            
            String line = buffer.readLine();
            String[] vetor = line.split(",");
            
            if (vetor[1].equals("2")) {
                vetor[1] = "0";
            }else{
                vetor[1] = "1";
            }
            
            for (int j = 0; j < vetor.length; j++) {
                this.saidaDesejada[i][j] = Double.valueOf(vetor[j]);
            }
        }
        int x = 5;
    }
    
    public void printEntrada(){
        for (int i = 0; i < 275; i++) {
            for (int j = 0; j < 256; j++) {
                System.out.print( " " + this.entrada[i][j]);
            }
            System.out.println("\n");
        }
    }
}
