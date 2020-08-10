import java.util.ArrayList;
import java.util.List;

public class Instance {

  public List<Integer> features = new ArrayList<>();
  public int label;
  
  public Instance(List<Integer> features, int label) {
    this.features = features;
    this.label = label;
  }

  @Override
  public String toString() {
    return "Instance [features=" + features + ", label=" + label + "]";
  }
}
