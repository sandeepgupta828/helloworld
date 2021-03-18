import com.programs.Ranking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ParseForMaxRating {

  // return list of names of top 10 rated movies
  public List<Pair> processCsvDataAndPickTop10(String filePath) throws IOException {
    // setup priority queue
    // ordered by max rating at the top
    PriorityQueue<Pair> queue = new PriorityQueue<>((pair1, pair2) -> (int)((pair2.getValue()-pair1.getValue())*1000));

    // read data from file
    FileReader fileReader = new FileReader(filePath);
    BufferedReader bufferedReader = new BufferedReader(fileReader);

    String line = bufferedReader.readLine();
    while (line != null) {
      // process line
      Pair pair = processLine(line);
      if (pair != null) {
        queue.add(pair);
      }
      line = bufferedReader.readLine();
    }

    // pick top 10 movies

    List<Pair> top10Movies = new ArrayList<>();
    for (int i=0;i<10;i++) {
      if (queue.size() > 0) {
        top10Movies.add(queue.poll());
      } else {
        break;
      }
    }

    return top10Movies;
  }

  private Pair processLine(String line) {
    // parse line as per CSV rules

    // if line contains any double quote -- process with double quote expectation
    if (line.contains("\"")) {
      // double quote parsing
      // parse until

    } else {
      String[] lineParts = line.split(",");
      if (lineParts.length == 3) {
        return new Pair(lineParts[0], Double.parseDouble(lineParts[2]));
      }
    }
    return null;
  }

  public class Pair {
    String key;
    double value;

    public Pair(String key, double val) {
      this.key = key;
      this.value = val;
    }

    public String getKey() {
      return key;
    }

    public double getValue() {
      return value;
    }

    @Override
    public String toString() {
      return "Pair{" +
          "key='" + key + '\'' +
          ", value=" + value +
          '}';
    }
  }

  public static void main(String[] args) throws IOException {
    ParseForMaxRating parseForMaxRating = new ParseForMaxRating();
    System.out.println(parseForMaxRating.processCsvDataAndPickTop10("/Users/sgupta/data.csv"));
  }
}
