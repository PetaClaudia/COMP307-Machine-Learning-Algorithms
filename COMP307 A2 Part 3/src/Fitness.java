import java.util.ArrayList;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class Fitness extends GPFitnessFunction {
  private static Object[] NO_ARGS = new Object[0];

  public Fitness(ArrayList<Double> inputs, ArrayList<Double> outputs, Variable xVariable) {
    // TODO Auto-generated constructor stub
    
  }

  @Override
  protected double evaluate(IGPProgram arg0) {
    // TODO Auto-generated method stub
    double result = 0.0;

    for (int i = 0; i < GP.inputs.size(); i++) {
        // Set variable x with the current input number
        GP.xVariable.set(GP.inputs.get(i));
        // Execute the program as the function to be evolved
        double value = arg0.execute_double(0, NO_ARGS);

        // Sum up the absolute value of the difference of the actual and expected results. 
        //The closer this is to 0, the better the algorithm
        result += Math.abs(value - GP.outputs.get(i));
    }
    return result;
  }

}
