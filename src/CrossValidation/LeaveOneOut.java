/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CrossValidation;

import Reader.Data;
import Main.ArithmeticExpressions;

/**
 *
 * @author Mário
 */
public class LeaveOneOut {
    
    private double acerto;
    
    public void validate(ArithmeticExpressions exp){
        for (int i = -1; i < Data.train.length; i++) {
            
        }
    }

    public double getAcerto() {
        return acerto;
    }

    public void setAcerto(double acerto) {
        this.acerto = acerto;
    }
}
