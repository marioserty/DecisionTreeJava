/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DecisionTrees;

import Arithmetic.ArithmeticExpression;
import Arithmetic.Constant;
import Arithmetic.GreaterThan;
import Arithmetic.LessThan;
import Metrics.AUC;
import Data.Data;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author mario
 */
public class LeafWiseTree implements Runnable {

    private final int iterations;
    private final int seed;
    private final int verboseEval;
    private final int verbosity;
    private final Random r;
    private int bestIteration;
    private ArithmeticExpression bestExpression;

    public LeafWiseTree(int iterations, int verboseEval, int verbosity, int seed) {
        this.iterations = iterations;
        this.seed = seed;
        this.verboseEval = verboseEval;
        this.verbosity = verbosity;
        this.r = new Random(seed);
    }

    @Override
    public void run() {
        bestExpression = generateDepthThree();
        ArithmeticExpression e2 = (ArithmeticExpression) bestExpression.clone();
        for (int i = 0; i < iterations; i++) {
            e2 = mutation(e2);
            if (AUROC(e2) > AUROC(bestExpression)) {
                bestExpression = e2;
                bestIteration = i;
            } else {
                e2 = bestExpression;
            }
            if (i % verboseEval == 0 && verbosity == 1) {
                System.out.println("Iteration " + i + "/" + iterations + " AUC: " + AUROC(bestExpression));
            }
        }
        System.out.println("Best iteration: " + bestIteration + " AUC: " + AUROC(bestExpression));
    }

    public void saveTreeCode(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(bestExpression.toString());
        writer.close();
    }

    public double AUROC(ArithmeticExpression exp) {
        double[] probability = new double[Data.target.length];
        for (int i = 0; i < Data.target.length; i++) {
            probability[i] = exp.process(i);
        }
        return AUC.measure(Data.target, probability);
    }

//    public ArithmeticExpression generateInequality() {
//        int variable = r.nextInt(Data.trainNumCols - 1);
//        double label = Data.train[variable][r.nextInt(Data.trainNumRows - 1)];
//
//        if (r.nextDouble() < 0.5) {
//            return new GreaterThan(variable, label, generateClassifier(), generateClassifier());
//        } else {
//            return new LessThan(variable, label, generateClassifier(), generateClassifier());
//        }
//    }
//
//    public ArithmeticExpression generateClassifier() {
//        return new Constant(r.nextDouble());
//    }
    public ArithmeticExpression generateDepthOne() {
        return new Constant(r.nextDouble());
    }

    public ArithmeticExpression generateDepthTwo() {
        ArithmeticExpression left = generateDepthOne();
        ArithmeticExpression right = generateDepthOne();

        int variable = r.nextInt(Data.trainNumCols - 1);
        double label = Data.train[r.nextInt(Data.trainNumRows - 1)][variable];

        if (r.nextDouble() < 0.5) {
            return new GreaterThan(variable, label, generateDepthOne(), generateDepthOne());
        } else {
            return new LessThan(variable, label, generateDepthOne(), generateDepthOne());
        }
    }

    public ArithmeticExpression generateDepthThree() {
        ArithmeticExpression left;
        ArithmeticExpression right;

        if (r.nextDouble() < 1.0 / 3.0) {
            left = generateDepthTwo();
            right = generateDepthOne();
        } else if (r.nextDouble() < 2.0 / 3.0) {
            left = generateDepthOne();
            right = generateDepthTwo();
        } else {
            left = generateDepthTwo();
            right = generateDepthTwo();
        }

        int variable = r.nextInt(Data.trainNumCols - 1);
        double label = Data.train[r.nextInt(Data.trainNumRows - 1)][variable];

        if (r.nextDouble() < 0.5) {
            return new GreaterThan(variable, label, generateDepthOne(), generateDepthOne());
        } else {
            return new LessThan(variable, label, generateDepthOne(), generateDepthOne());
        }
    }

    public ArithmeticExpression mutation(ArithmeticExpression exp) {
        /**
         * apagar esquerda e gerar alt3, 2, apagar direita e gerar alt3, 2
         * trocar meio;
         */

        int variable = r.nextInt(Data.trainNumCols - 1);
        double label = Data.train[r.nextInt(Data.trainNumRows - 1)][variable];

        double d = r.nextDouble();

        if (d < 1.0 / 4.0) {
            double d2 = r.nextDouble();
            if (r.nextDouble() < 0.5) {
                return new GreaterThan(variable, label, mutation(generateDepthOne()), generateDepthOne());
            } else {
                return new LessThan(variable, label, mutation(generateDepthOne()), generateDepthOne());
            }
        } else if (d < 2.0 / 4.0) {
            double d2 = r.nextDouble();
            if (r.nextDouble() < 0.5) {
                return new GreaterThan(variable, label, generateDepthOne(), mutation(generateDepthOne()));
            } else {
                return new LessThan(variable, label, generateDepthOne(), mutation(generateDepthOne()));
            }
        } else if (d < 3.0 / 4.0) {
            double d2 = r.nextDouble();
            if (r.nextDouble() < 0.5) {
                return new GreaterThan(variable, label, exp.getLeft(), (generateDepthTwo()));
            } else {
                return new LessThan(variable, label, exp.getLeft(), (generateDepthTwo()));
            }
        } else {
            double d2 = r.nextDouble();
            if (r.nextDouble() < 0.5) {
                return new GreaterThan(variable, label, exp.getLeft(), (generateDepthTwo()));
            } else {
                return new LessThan(variable, label, exp.getLeft(), (generateDepthTwo()));
            }
        }
    }

}