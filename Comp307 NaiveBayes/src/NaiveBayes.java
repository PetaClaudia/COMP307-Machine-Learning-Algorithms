import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class NaiveBayes {

  List<Instance> labelledList;
  List<Instance> unlabelledList;
  
  public NaiveBayes(String file1, String file2) {
    File labelledFile = new File(file1);
    File unlabelledFile = new File(file2);
    labelledList = new ArrayList<Instance>();
    unlabelledList = new ArrayList<Instance>();
    parser(labelledFile, unlabelledFile);
    classify();
    for(Instance inst : unlabelledList) {
      System.out.println(inst.toString());
    }
}

  private void parser(File labelledFile, File unlabelledFile) {
    try {
      Scanner scan = new Scanner(labelledFile);
      while(scan.hasNext()) {
        ArrayList<Integer> feats = new ArrayList<>();
        for( int i = 0; i< 12; i++) {
          feats.add(scan.nextInt());
        }
        int label = scan.nextInt();
        labelledList.add(new Instance(feats, label));
      }
      System.out.println("labelled size: "+labelledList.size());
//      for(Instance inst : labelledList) {
//        System.out.println(inst.toString());
//      }
      scan.close();
      
      Scanner sc = new Scanner(unlabelledFile);
      while(sc.hasNext()) {
        ArrayList<Integer> feats = new ArrayList<>();
        for( int i = 0; i< 12; i++) {
          feats.add(sc.nextInt());
        }
        int label = 1;
        unlabelledList.add(new Instance(feats, label));
      }
      System.out.println("unlabelled size: "+unlabelledList.size());
//      for(Instance inst : unlabelledList) {
//        System.out.println(inst.toString());
//      }
      sc.close();
    }catch (Exception e) {
      System.out.println("Parse Error");
      e.printStackTrace();
    }
  }
  
  private List<Double> getProbabilities(List<Instance> instances){
    List<Integer> feats = new ArrayList<>();
    
    for(int i = 0 ;i < 12; i++ ){
      feats.add(0);
    }

    for(Instance inst : instances){
      for(int i = 0 ;i < 12; i++ ){
        feats.set(i, feats.get(i)+inst.features.get(i));
      }
    }
    List<Double> probabilities = new ArrayList<>();
    for(int i = 0; i < feats.size(); i++){
      System.out.println("feats "+feats.get(i)+" inst size "+instances.size());
        double prob = (double)feats.get(i) / (double) instances.size();
        System.out.println("prob "+prob);
        probabilities.add(prob);
    }
    System.out.println("prob retrun "+probabilities.toString());
    return probabilities;
}
  
  public void classify() {
    for(Instance unlabelledInst : unlabelledList) {
      List<Instance> spamList = new ArrayList<>();
      List<Instance> nonSpamList = new ArrayList<>();
      double denominator = 1.0, spam = 1.0, nonSpam = 1.0;

      for(Instance inst : labelledList) {
        if(inst.label == 0) {
          nonSpamList.add(inst);
        } else if(inst.label == 1){
          spamList.add(inst);
        }
      }
      List<Double> allProbs = getProbabilities(labelledList);
      List<Double> spamProbs = getProbabilities(spamList);
      List<Double> nonSpamProbs = getProbabilities(nonSpamList);
      spam = (double) spamList.size()/ (double)labelledList.size();
      nonSpam = (double) nonSpamList.size()/(double)labelledList.size();

      for (int i = 0; i < unlabelledInst.features.size(); i++){
        if(unlabelledInst.features.get(i) == 0) {
          denominator *= 1.0 - allProbs.get(i);
          nonSpam *= 1.0 - nonSpamProbs.get(i);
          spam *= 1.0 - spamProbs.get(i);
          
        }else if(unlabelledInst.features.get(i) == 1){
          denominator *= allProbs.get(i);
          nonSpam *= nonSpamProbs.get(i);
          spam *= spamProbs.get(i);
        }
        
      }
      System.out.println("SPAM "+spam+" NON "+nonSpam);
      spam /= denominator;
      nonSpam /= denominator;
      
      System.out.println("SPAM div "+spam+" NON div "+nonSpam);

      if(spam > nonSpam) {
        unlabelledInst.label = 1;
      } else {
        unlabelledInst.label = 0;
      }
    }
  }
  
  public static void main(String[] args) {
    if (args.length == 2) {
      String labelledf = args[0];
      String unlabelledf = args[1];
        new NaiveBayes(labelledf, unlabelledf);
    }
  }
}
