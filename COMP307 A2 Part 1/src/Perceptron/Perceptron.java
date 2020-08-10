package Perceptron;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * @author petadouglas
 *
 */
public class Perceptron {
  /**
   * Weights.
   */
  public double[] weights = new double[3];
  /**
   * List of instances.
   */
  public List<Instance> instList;
  
  public double bias;
  public double adjustment = 0.5;
  
  /**
   * Constructor
   */
  public Perceptron(String file) {
    this.bias = 1;
    File dataFile = new File(file);
    this.instList = new ArrayList<Instance>();
    for(int i = 0; i< this.weights.length; i++) {
      //this.weights[i] = Math.random();
      this.weights[i] = 0;
      System.out.println("Weight "+ i+ " :"+this.weights[i]);
    }
    //parse
    parser(dataFile);
    System.out.println("Parsed");
    //200 epochs
    for(int i = 0; i<200; i++) {
      //train each instance
      for(Instance inst : instList) {
        System.out.println("Trained");
        train(inst.features, inst.cl);
      }
    }
    test();
    System.out.println("Tested");
  }
  
//  private void test() {
//    int correct = 0;
//    boolean allCorrect = false;
//    int epoch = 0;
////    //until the perceptron is always right
////    while (allCorrect == false) {
////      System.out.println("Start while loop");
//    while (epoch<200) {
//      bias = 1;
//      for(Instance inst : instList) {
//        int guess = guess(inst.features);
//        if(guess == inst.cl) {
//          System.out.println("correct");
//          correct++;
//          continue;
//        }
//        if(guess == 0 && inst.cl == 1) {
//          System.out.println("wrong 1");
//          for(int i = 0; i<inst.features.length; i++) {
//          //increase 𝑏=𝑤0, increase 𝑤i for each positive 𝑥i, 
//            //decrease 𝑤i for each negative 𝑥i
//            this.bias+=adjustment;
//            System.out.println("Bias "+ bias);
//            if (inst.features[i] == 1) {
//              weights[i]+=adjustment;
//              System.out.println("Weight "+ i+ " :"+this.weights[i]);
//            }
//            if (inst.features[i] == 0) {
//              weights[i]-=adjustment;
//              System.out.println("Weight "+ i+ " :"+this.weights[i]);
//            }
//          
//          }
//        }
//        if(guess == 1 && inst.cl == 0) {
//          System.out.println("wrong 2");
//          for(int i = 0; i<inst.features.length; i++) {
//          //decrease 𝑏=𝑤0, decrease 𝑤i for each positive 𝑥i, 
//          //increase 𝑤i for each negative 𝑥i
//           this.bias-=adjustment;
//           System.out.println("Bias "+ bias);
//          if (inst.features[i] == 1) {
//            weights[i]-=adjustment;
//            System.out.println("Weight "+ i+ " :"+this.weights[i]);
//          }
//          if (inst.features[i] == 0) {
//            weights[i]+=adjustment;
//            System.out.println("Weight "+ i+ " :"+this.weights[i]);
//          }
//        }
//        }
//      }
//      if (correct == instList.size()) {
//        allCorrect = true;
//      }
//      epoch++;
//    }
//    System.out.println("Total correct = "+correct+" / "+instList.size()*epoch);
//    double p = ((double)correct/((double)instList.size()*epoch))*100;
//    System.out.println("Percentage correct = "+p);
//  }
  private void test() {
    int correct = 0;
    for(Instance inst : instList) {
      int guess = predict(inst.features);
      if(guess == inst.cl) {
        correct++;
      }
      System.out.println("guess :"+guess+"   actual :"+inst.cl);
    }
    System.out.println("Total correct = "+correct+" / "+instList.size());
    double p = ((double)correct/((double)instList.size()))*100;
    System.out.println("Percentage correct = "+p);
  }
  /**
   * Parser.
   * @param file The data file.
   */
  public void parser(File file) {
    try {
      Scanner scan = new Scanner(file);
      scan.nextLine();
      while(scan.hasNext()) {
        String line = scan.nextLine();
        String[] tokens = line.split(" ");
        // process the tokens
        int[] feat = new int[3];
        int cl = 0;
        
        for(int i = 0; i<feat.length; i++) {
          feat[i] = Integer.parseInt(tokens[i]);
        }
        cl = Integer.parseInt(tokens[3]);
        
        Instance instance = new Instance(feat, cl);
        
        this.instList.add(instance);
        System.out.println(instance.toString());
      }
      
      scan.close();
    } catch (Exception e) {
      System.out.println("Parse Error");
      e.printStackTrace();
    }
  }
  
  /**
   * @return guess
   */
  public int predict(int[] inputs) {
    double sum = 0;
    for (int i = 0; i<this.weights.length; i++) {
      sum += inputs[i]*this.weights[i];
    }
    sum+=this.bias;
    int output = sign(sum);
    return output;
  }
  
  /**
   * returns 1 if +ve or -1 if -ve.
   * @param n number.
   * @return pos or neg.
   */
  public int sign(double n) {
    if(n>0) {
      return 1;
    }
    else {
      return 0;
    }
  }
  
  public void train(int[] inputs, int target) {
    int prediction = predict(inputs);
    int error = target - prediction;
    double learningRate = 0.1;
    for (int i = 0; i<this.weights.length; i++) {
      weights[i] += error * inputs[i] * learningRate;
      //System.out.println("train Weight "+ i+ " :"+this.weights[i]);
    }
  }
  
  public static void main(String[] args) {
    if (args.length == 1) {
      String file = args[0];
      new Perceptron(file);
    }
  }
  
}

