/*
 * Created on 26/12/2006
 */
package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.media.opengl.GL2;

/**
 * @author Demián Gutierrez
 */
public class Part extends PartBase {

	public void render(GL2 gl, int currColorIndex) throws Exception {
		List<Part> partTransList = new ArrayList<Part>();
		ColorIndex colorIndex = ColorIndex.getInstance();

		Iterator<Part> itt = partList.iterator();

		while (itt.hasNext()) {
			Part part = itt.next();

			float[] partColor;

			int partColorIndex = currColorIndex;

			if (part.colr == ColorIndex.COLOR_EDGE) {
				partColor = colorIndex.getEdgeColor(currColorIndex);
			} else if (part.colr != ColorIndex.COLOR_MAIN) {
				partColor = colorIndex.getPartColor(part.colr);
				partColorIndex = part.colr;
			} else {
				partColor = colorIndex.getPartColor(currColorIndex);
			}
			
			if (partColor[3] == 1.0) {
				switch (part.type) {
				case 1:
					gl.glPushMatrix();

					// Very important
					float[] mm = new float[] {//
					part.x[1], part.x[2], part.x[3], 0,//
							part.y[1], part.y[2], part.y[3], 0,//
							part.z[1], part.z[2], part.z[3], 0,//
							part.x[0], part.y[0], part.z[0], 1 };

					gl.glMultMatrixf(mm, 0);

					part.partDeps.render(gl, partColorIndex);

					gl.glPopMatrix();
					break;
				case 2:
					gl.glColor3f(partColor[0], partColor[1], partColor[2]);
					gl.glBegin(GL2.GL_LINES);
					gl.glVertex3d(part.x[0], part.y[0], part.z[0]);
					gl.glVertex3d(part.x[1], part.y[1], part.z[1]);
					gl.glEnd();
					break;
				case 3:
					gl.glColor4f(partColor[0], partColor[1], partColor[2], partColor[3]);
					gl.glBegin(GL2.GL_TRIANGLES);
					gl.glVertex3d(part.x[0], part.y[0], part.z[0]);
					gl.glVertex3d(part.x[1], part.y[1], part.z[1]);
					gl.glVertex3d(part.x[2], part.y[2], part.z[2]);
					gl.glEnd();
					break;
				case 4:
					gl.glColor4f(partColor[0], partColor[1], partColor[2], partColor[3]);
					gl.glBegin(GL2.GL_QUADS);
					gl.glVertex3d(part.x[0], part.y[0], part.z[0]);
					gl.glVertex3d(part.x[1], part.y[1], part.z[1]);
					gl.glVertex3d(part.x[2], part.y[2], part.z[2]);
					gl.glVertex3d(part.x[3], part.y[3], part.z[3]);
					gl.glEnd();
					break;
				}
			} else {
				partTransList.add(part);
			}
		}

		for(Part part : partTransList){
			float[] partColor;

			int partColorIndex = currColorIndex;

			if (part.colr == ColorIndex.COLOR_EDGE) {
				partColor = colorIndex.getEdgeColor(currColorIndex);
			} else if (part.colr != ColorIndex.COLOR_MAIN) {
				partColor = colorIndex.getPartColor(part.colr);
				partColorIndex = part.colr;
			} else {
				partColor = colorIndex.getPartColor(currColorIndex);
			}

			switch (part.type) {
			case 1:
				gl.glPushMatrix();

				// Very important
				float[] mm = new float[] {//
				part.x[1], part.x[2], part.x[3], 0,//
						part.y[1], part.y[2], part.y[3], 0,//
						part.z[1], part.z[2], part.z[3], 0,//
						part.x[0], part.y[0], part.z[0], 1 };

				gl.glMultMatrixf(mm, 0);

				part.partDeps.render(gl, partColorIndex);

				gl.glPopMatrix();
				break;
			case 2:
				gl.glColor3f(partColor[0], partColor[1], partColor[2]);
				gl.glBegin(GL2.GL_LINES);
				gl.glVertex3d(part.x[0], part.y[0], part.z[0]);
				gl.glVertex3d(part.x[1], part.y[1], part.z[1]);
				gl.glEnd();
				break;
			case 3:
				gl.glColor4f(partColor[0], partColor[1], partColor[2], partColor[3]);
				gl.glBegin(GL2.GL_TRIANGLES);
				gl.glVertex3d(part.x[0], part.y[0], part.z[0]);
				gl.glVertex3d(part.x[1], part.y[1], part.z[1]);
				gl.glVertex3d(part.x[2], part.y[2], part.z[2]);
				gl.glEnd();
				break;
			case 4:
				gl.glColor4f(partColor[0], partColor[1], partColor[2], partColor[3]);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(part.x[0], part.y[0], part.z[0]);
				gl.glVertex3d(part.x[1], part.y[1], part.z[1]);
				gl.glVertex3d(part.x[2], part.y[2], part.z[2]);
				gl.glVertex3d(part.x[3], part.y[3], part.z[3]);
				gl.glEnd();
				break;
			}
		}
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();

		ret.append(type);
		ret.append(" ");
		ret.append(colr);
		ret.append(" ");

		switch (type) {
		case 2:
			ret.append(coordToString(2));
			break;
		case 1:
			ret.append(coordToString(4));
			ret.append(" ");
			ret.append(partDeps.name);
			break;
		case 3:
			ret.append(coordToString(3));
			break;
		case 4:
			ret.append(coordToString(4));
			break;
		}

		return ret.toString();
	}
}
