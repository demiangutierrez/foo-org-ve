package ray2D.proy;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.cyrano.util.misc.ColorParser;

public class Loader {

  private List<Line> lineList = new ArrayList<Line>();

  private Ray ray;

  // --------------------------------------------------------------------------------

  public Loader() {
    // Empty
  }

  // --------------------------------------------------------------------------------

  public void load(InputStream is) throws Exception {
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

    Pattern p = Pattern.compile(" +");

    String line;

    while ((line = rd.readLine()) != null) {
      line = line.trim();

      if (line.equals("") || line.startsWith("#")) {
        continue;
      }

      String[] data = p.split(line);

      if (data.length != 5) {
        throw new IllegalArgumentException("data.length != 5");
      }

      //      if (ray == null) {
      //        ray = new Ray();
      //        ray.p0.setX(Integer.parseInt(data[0]));
      //        ray.p0.setY(Integer.parseInt(data[1]));
      //        ray.pu.setX(Integer.parseInt(data[2]));
      //        ray.pu.setY(Integer.parseInt(data[3]));
      //
      //        ray.color = Color.GREEN;
      //        ray.pu.color = Color.YELLOW;
      //      } else {
      Line l = new Line();

      l.p1.setX(Double.parseDouble(data[0]));
      l.p1.setY(Double.parseDouble(data[1]));
      l.p2.setX(Double.parseDouble(data[2]));
      l.p2.setY(Double.parseDouble(data[3]));

      l.color = ColorParser.parse(data[4]);

      lineList.add(l);
      //      }
    }
  }

  // --------------------------------------------------------------------------------

  public List<Line> getLineList() {
    return lineList;
  }

  public Ray getRay() {
    return ray;
  }
}
