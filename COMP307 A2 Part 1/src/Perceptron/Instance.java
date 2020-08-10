package Perceptron;

import java.util.Arrays;

public class Instance {
  public int[] features;
  public int cl;
  
  public Instance(int[] features, int cl) {
    this.features = features;
    this.cl = cl;
  }

  @Override
  public String toString() {
    return "Instance [features=" + Arrays.toString(features) + ", cl=" + cl + "]";
  }
}
