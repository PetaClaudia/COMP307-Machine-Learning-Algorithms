import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Cosine;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Pow;
import org.jgap.gp.function.Sine;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;


public class GP extends GPProblem{
  static ArrayList<Double> inputs;
  static ArrayList<Double> outputs;
  static Variable xVariable;
  
  public GP(String file) throws InvalidConfigurationException {
    super(new GPConfiguration());
    
    File f = new File(file);
    GPConfiguration config = getGPConfiguration();
    GP.inputs = new ArrayList<Double>();
    GP.outputs = new ArrayList<Double>();
    GP.xVariable = Variable.create(config, "X", CommandGene.DoubleClass);
    
    parser(f);

    config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
    config.setMaxInitDepth(4);
    config.setPopulationSize(1000);
    config.setMaxCrossoverDepth(8);
    config.setFitnessFunction(new Fitness(inputs, outputs, xVariable));
    config.setStrictProgramCreation(true);
    
    System.out.println("end constructor");
}

  @Override
  public GPGenotype create() throws InvalidConfigurationException {
    GPConfiguration config = getGPConfiguration();

    // The return type of the GP program.
    Class[] types = { CommandGene.DoubleClass };

    // Arguments of result-producing chromosome: none
    Class[][] argTypes = { {} };

    // Next, we define the set of available GP commands and terminals to
    // use.
    CommandGene[][] nodeSets = {
        {
            xVariable,
            new Add(config, CommandGene.DoubleClass),
            new Multiply(config, CommandGene.DoubleClass),
            new Divide(config, CommandGene.DoubleClass),
            new Subtract(config, CommandGene.DoubleClass),
            new Pow(config, CommandGene.DoubleClass),
            new Cosine(config, CommandGene.DoubleClass),
            new Sine(config, CommandGene.DoubleClass),
            new Terminal(config, CommandGene.DoubleClass, -1.0, 1.0, true)
        }
    };

    GPGenotype result = GPGenotype.randomInitialGenotype(config, types, argTypes,
            nodeSets, 20, true);

    return result;
  }
  
  public void parser(File file) {
    try {
      Scanner scan = new Scanner(file);
      scan.nextLine();
      while(scan.hasNext()) {
        String line = scan.nextLine();
        String[] tokens = line.split(" ");
        // process the tokens
        double x = Double.parseDouble(tokens[0]);
        double y = Double.parseDouble(tokens[1]);
        
        System.out.println("x: "+x+", y: "+y);
        
        inputs.add(x);
        outputs.add(y);
      }
      System.out.println("inputs size: "+inputs.size()+", outputs size: "+outputs.size());
      scan.close();
      
    } catch (Exception e) {
      System.out.println("Parse Error");
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    try {
      GPProblem gp = null;
      if (args.length == 1) {
        String file = args[0];
        gp = new GP(file);
        GPGenotype genotype = gp.create();
        genotype.setVerboseOutput(true);
        genotype.evolve(50);
        genotype.outputSolution(genotype.getAllTimeBest());
        System.out.println("End of main");
      }
    } catch (InvalidConfigurationException e) {
      System.out.println("Genetic Program Failed");
      e.printStackTrace();
    }
  }

}
