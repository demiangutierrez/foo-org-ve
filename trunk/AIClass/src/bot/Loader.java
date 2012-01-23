package bot;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

      if (data.length != 4) {
        throw new IllegalArgumentException("data.length != 4");
      }

      if (ray == null) {
        ray = new Ray();
        ray.p0.x = Integer.parseInt(data[0]);
        ray.p0.y = Integer.parseInt(data[1]);
        ray.pu.x = Integer.parseInt(data[2]);
        ray.pu.y = Integer.parseInt(data[3]);

        ray.color = Color.GREEN;
        ray.pu.color = Color.YELLOW;
      } else {
        Line l = new Line();

        l.p1.x = Integer.parseInt(data[0]);
        l.p1.y = Integer.parseInt(data[1]);
        l.p2.x = Integer.parseInt(data[2]);
        l.p2.y = Integer.parseInt(data[3]);

        l.color = Color.RED;

        lineList.add(l);
      }
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
