/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inequality;

import Arithmetic.ArithmeticExpression;
import Arithmetic.Binary;
import Data.Data;

/**
 *
 * @author Mário
 */
public class LessThan extends Binary {

    private int variable;
    private double label;

    public LessThan(int variable, double label, ArithmeticExpression right, ArithmeticExpression left) {
        this.variable = variable;
        this.label = label;
        super.right = right;
        super.left = left;
    }

    @Override
    public double processOnTrain(int instance) {
        if (Data.train[instance][getVariable()] < getLabel()) {
            return super.left.processOnTrain(instance);
        } else {
            return super.right.processOnTrain(instance);
        }
    }

    @Override
    public double processOnTest(int instance) {
        if (Data.test[instance][getVariable()] < getLabel()) {
            return super.left.processOnTest(instance);
        } else {
            return super.right.processOnTest(instance);
        }
    }

    @Override
    public int height() {
        return Math.max(super.getLeft().height(), super.getRight().height()) + 1;
    }

    @Override
    public String toString() {
        return "if( data[" + Data.columns[variable] + "] < " + label + " ){" + "\n\t" + (super.getLeft().toString()) + "\n\t" + "\n}else{" + "\n\t" + (super.getRight().toString()) + "\n\t" + "}\n";
    }

    public int getVariable() {
        return variable;
    }

    public void setVariavel(int variable) {
        this.variable = variable;
    }

    public double getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }
}
