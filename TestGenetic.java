//To find the maximium of the function:
//       f(x)=sin(x)*sin(0.4x)*sin(3x)
import java.util.List;
public class TestGenetic {

    static MyGenetic genetic_experiment;

    static public void main(String args[]) {
        // we will use chromosomes with 10 1 bit genes per
        // chromosomes, and a population of 12 chromosomes:
        genetic_experiment = new MyGenetic(10, 20, 0.85f, 0.3f);
        int geneIndex = 0;
        for (Chromosome ll  : genetic_experiment.chromosomes) {
          System.out.println(ll.chromosome + " : " + genetic_experiment.geneToFloat(geneIndex++));
        }
        int NUM_CYCLES = 500;
        for (int i=0; i<NUM_CYCLES; i++) {
            genetic_experiment.evolve();
            if ((i%(NUM_CYCLES/5))==0 || i==(NUM_CYCLES-1)) {
                System.out.println("Generation " + i);
                genetic_experiment.print();
            }
        }
    }
}

class MyGenetic extends Genetic {
    MyGenetic(int num_g, int num_c, float crossover_fraction,
              float mutation_fraction) {
        super(num_g, num_c, crossover_fraction, mutation_fraction);
    }
    private float fitness(float x) {
        return (float)(Math.sin(x) * Math.sin(0.4f * x) * Math.sin(3.0f * x));
    }
    float geneToFloat(int chromosomeIndex) {
        int base = 1;
        float x = 0;
        for (int j=0; j<numGenesPerChromosome; j++)  {
            if (getGene(chromosomeIndex, j)) {
                x += base;
            }
            base *= 2;
        }
        x /= 102.4f;
        return x;
    }
    public void calcFitness() {
        for (int i=0; i<numChromosomes; i++) {
            float x = geneToFloat(i);
            chromosomes.get(i).setFitness(fitness(x));
        }
    }

    public void print() {
        float sum = 0.0f;
        for (int i=0; i<numChromosomes; i++) {
            float x = geneToFloat(i);
            sum += chromosomes.get(i).getFitness();
            if (true) { 
                System.out.print("Fitness for chromosome ");
                System.out.print(i);
                System.out.print(" is ");
                System.out.println(chromosomes.get(i).getFitness() + ", occurs at x=" + x);
            }
        }
        sum /= (float)numChromosomes;
        System.out.println("Average fitness=" + sum +
        		           " and best fitness for this generation:" +
        		           chromosomes.get(0).getFitness());
    }
}
