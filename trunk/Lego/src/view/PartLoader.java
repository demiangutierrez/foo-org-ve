/*
 * Created on 26/12/2006
 */
package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Demián Gutierrez
 */
public class PartLoader {

	// ----------------------------------------
	// Singleton
	// ----------------------------------------

	private static PartLoader instance;

	public static PartLoader getInstance() {
		if (instance == null) {
			instance = new PartLoader();
		}

		return instance;
	}

	// ----------------------------------------
	// Props
	// ----------------------------------------

	private Map<String, Part> parts;
	private List<String> path = new ArrayList<String>();

	// ----------------------------------------

	private PartLoader() {
		// Empty
	}

	// ----------------------------------------

	protected File findFile(String name) throws Exception {
		String fs = System.getProperty("file.separator");

		File ret = null;

		Iterator<String> itt = path.iterator();

		while (itt.hasNext()) {
			String curPath = itt.next();

			if (fs.equals("/")) {
				name = name.replace('\\', '/');
				name = name.toLowerCase();
			}

			ret = new File(curPath + fs + name);

			if (ret.exists()) {
				break;
			}

			ret = null;
		}

		if (ret == null) {
			throw new FileNotFoundException(name);
		}

		return ret;
	}

	public Part loadPart(String name, boolean reset) throws Exception {
		if (reset) {
			parts = new HashMap<String, Part>();
		}
		// ----------------------------------------
		// For mpd support
		// ----------------------------------------

		List<UnresolvedBean> unresolvedBeanList = new ArrayList<UnresolvedBean>();
		Part ret = null;
		Part cur = null;

		// ----------------------------------------
		// Read the file
		// ----------------------------------------

		BufferedReader rd = new BufferedReader(new FileReader(findFile(name)));

		Pattern pattern = Pattern.compile(" +");

		String line;

		while ((line = rd.readLine()) != null) {
			line = line.trim();

			if (line.equals("")) {
				continue;
			}

			String[] data = pattern.split(line);

			if (Integer.parseInt(data[0]) != 0) {
				Part itm = new Part();

				try {
					itm.parse(line);
				} catch (FileNotFoundException e) {
					UnresolvedBean unresolvedBean = new UnresolvedBean();

					unresolvedBean.name = e.getMessage();
					unresolvedBean.itm = itm;
					unresolvedBeanList.add(unresolvedBean);
				}

				if (cur == null) {
					cur = new Part();
					cur.name = data[2];
					cur.type = 1;
					cur.partList = new ArrayList<Part>();

					ret = cur;
				}

				cur.partList.add(itm);
			} else {
				if (data.length >= 3 && data[1].equalsIgnoreCase("FILE")) {
					cur = new Part();
					cur.name = data[2];
					cur.type = 1;
					cur.partList = new ArrayList<Part>();

					parts.put(cur.name, cur);

					if (ret == null) {
						ret = cur;
					}
				}
			}
		}

		// ----------------------------------------
		// Check unresolved
		// ----------------------------------------

		Iterator<UnresolvedBean> itt = unresolvedBeanList.iterator();

		while (itt.hasNext()) {
			UnresolvedBean unresolvedBean = (UnresolvedBean) itt.next();
			unresolvedBean.itm.partDeps = getPart((String) unresolvedBean.name);
		}
		
		rd.close();
		
		return ret;
	}

	// ----------------------------------------

	public Part getPart(String name) throws Exception {
		Part ret = (Part) parts.get(name);

		if (ret == null) {
			try {
				ret = loadPart(name, false);
			} catch (FileNotFoundException e) {
				// name is used for mpd
				throw new FileNotFoundException(name);
			}

			parts.put(name, ret);
		}

		return ret;
	}

	// ----------------------------------------

	public List<String> getPath() {
		return path;
	}

	// ----------------------------------------

	private static class UnresolvedBean {
		public String name;
		public Part itm;
	}
}
