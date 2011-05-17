#include <iostream>
#include <cstdlib>
#include <cmath>
#include <GL/glut.h>
#include <GL/gl.h>

using namespace std;

int wXYView;
int wXZView;
int wYZView;
int wMMView;

GLdouble angTOP = 0;
GLdouble angRGH = 0;
GLdouble angFRN = 0;

GLdouble dist = 4;

int prevX;
int prevY;

// --------------------------------------------------------------------------------

#define _X 0
#define _Y 1
#define _Z 2

GLdouble vY[3] = { 0, 1, 0 };
GLdouble vX[3] = { 1, 0, 0 };
GLdouble vZ[3] = { 0, 0, -1 };

void calcCrossProduct(GLdouble v1[3], GLdouble v2[3], GLdouble vr[3]) {
	vr[_X] = /* */(v1[_Y] * v2[_Z] - v1[_Z] * v2[_Y]);
	vr[_Y] = /**/-(v1[_X] * v2[_Z] - v1[_Z] * v2[_X]);
	vr[_Z] = /* */(v1[_X] * v2[_Y] - v1[_Y] * v2[_X]);

	double mod = sqrt( //
			/*    */vr[_X] * vr[_X] + //
					vr[_Y] * vr[_Y] + //
					vr[_Z] * vr[_Z]);

	vr[_X] /= mod;
	vr[_Y] /= mod;
	vr[_Z] /= mod;
}

// --------------------------------------------------------------------------------

void updateRef() {
	glPushMatrix();

	glLoadIdentity();
	glRotated(angTOP, vY[_X], vY[_Y], vY[_Z]);

	GLdouble d1[16] = //
			/*  */{ vX[_X], vX[_Y], vX[_Z], 0, //
					0, 0, 0, 0, //
					0, 0, 0, 0, //
					0, 0, 0, 1 };

	glMultMatrixd(d1);
	glGetDoublev(GL_MODELVIEW_MATRIX, d1);

	vX[_X] = d1[0];
	vX[_Y] = d1[1];
	vX[_Z] = d1[2];

	glLoadIdentity();
	glRotated(angRGH, vX[_X], vX[_Y], vX[_Z]);

	GLdouble d2[16] = //
			/*  */{ vY[_X], vY[_Y], vY[_Z], 0, //
					0, 0, 0, 0, //
					0, 0, 0, 0, //
					0, 0, 0, 1 };

	glMultMatrixd(d2);
	glGetDoublev(GL_MODELVIEW_MATRIX, d2);

	vY[_X] = d2[0];
	vY[_Y] = d2[1];
	vY[_Z] = d2[2];

	GLdouble v1[3] = { vY[_X], vY[_Y], vY[_Z] };
	GLdouble v2[3] = { vX[_X], vX[_Y], vX[_Z] };
	GLdouble vr[3] = { 0, 0, 0 };

	calcCrossProduct(v1, v2, vr);

	vZ[_X] = vr[0];
	vZ[_Y] = vr[1];
	vZ[_Z] = vr[2];

	// ----------------------------------------


	glLoadIdentity();
	glRotated(angFRN, vZ[_X], vZ[_Y], vZ[_Z]);

	GLdouble d3[16] = //
			/*  */{ vX[_X], vX[_Y], vX[_Z], 0, //
					0, 0, 0, 0, //
					0, 0, 0, 0, //
					0, 0, 0, 1 };

	glMultMatrixd(d3);
	glGetDoublev(GL_MODELVIEW_MATRIX, d3);

	vX[_X] = d3[0];
	vX[_Y] = d3[1];
	vX[_Z] = d3[2];

	glLoadIdentity();
	glRotated(angFRN, vZ[_X], vZ[_Y], vZ[_Z]);

	GLdouble d4[16] = //
			/*  */{ vY[_X], vY[_Y], vY[_Z], 0, //
					0, 0, 0, 0, //
					0, 0, 0, 0, //
					0, 0, 0, 1 };

	glMultMatrixd(d4);
	glGetDoublev(GL_MODELVIEW_MATRIX, d4);

	vY[_X] = d4[0];
	vY[_Y] = d4[1];
	vY[_Z] = d4[2];

	glPopMatrix();
}

void updateCameraPos() {
	updateRef();

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	gluPerspective(60, 1, dist - 3, dist + 10);

	gluLookAt( //
			-dist * vZ[_X], -dist * vZ[_Y], -dist * vZ[_Z], //  eye
			0, 0, 0, //  cnt
			vY[_X], vY[_Y], vY[_Z]); // upv
	glMatrixMode(GL_MODELVIEW);
}

// --------------------------------------------------------------------------------


void reshape(int width, int height) {
	glViewport(0, 0, width, height);
	updateCameraPos();
}

// --------------------------------------------------------------------------------


void drawBox4d(GLdouble cx, GLdouble cy, GLdouble cz, GLdouble l) {
	glBegin(GL_QUADS);

	GLdouble xM = cx - l / 2;
	GLdouble xP = cx + l / 2;

	GLdouble yM = cy - l / 2;
	GLdouble yP = cy + l / 2;

	GLdouble zM = cz - l / 2;
	GLdouble zP = cz + l / 2;

	// ----------------------------------------
	// yM (BOT)
	// ----------------------------------------
	glColor3f(0, 1, 0);
	glVertex3f(xM, yM, zM);
	glVertex3f(xP, yM, zM);
	glVertex3f(xP, yM, zP);
	glVertex3f(xM, yM, zP);

	// ----------------------------------------
	// zP (FRN)
	// ----------------------------------------
	glColor3f(0, 0, 1);
	glVertex3f(xP, yM, zP);
	glVertex3f(xM, yM, zP);
	glVertex3f(xM, yP, zP);
	glVertex3f(xP, yP, zP);

	// ----------------------------------------
	// yP (TOP)
	// ----------------------------------------
	glColor3f(1, 1, 0);
	glVertex3f(xP, yP, zP);
	glVertex3f(xM, yP, zP);
	glVertex3f(xM, yP, zM);
	glVertex3f(xP, yP, zM);

	// ----------------------------------------
	// zM (BCK)
	// ----------------------------------------
	glColor3f(1, 1, 1);
	glVertex3f(xP, yP, zM);
	glVertex3f(xM, yP, zM);
	glVertex3f(xM, yM, zM);
	glVertex3f(xP, yM, zM);

	// ----------------------------------------
	// xM (LFT)
	// ----------------------------------------
	glColor3f(1, 0, 0);
	glVertex3f(xM, yP, zP);
	glVertex3f(xM, yM, zP);
	glVertex3f(xM, yM, zM);
	glVertex3f(xM, yP, zM);

	// ----------------------------------------
	// xP (RGH)
	// ----------------------------------------
	glColor3f(1, 0.5, 0);
	glVertex3f(xP, yP, zP);
	glVertex3f(xP, yM, zP);
	glVertex3f(xP, yM, zM);
	glVertex3f(xP, yP, zM);

	glEnd();
}

void drawAxes4(GLdouble cx, GLdouble cy, GLdouble cz, GLdouble l) {

	glColor3f(1, 1, 1);

	glBegin(GL_LINES);

	glVertex3d(0, 0, 0);
	glVertex3d(3 * vY[_X], 3 * vY[_Y], 3 * vY[_Z]);

	glVertex3d(0, 0, 0);
	glVertex3d(-3 * vZ[_X], -3 * vZ[_Y], -3 * vZ[_Z]);

	glColor3f(1, 0.5, 0); // X->R
	glVertex3d(0, 0, 0);
	glVertex3d(3, 0, 0);

	glColor3f(1, 1, 0); // Y->G
	glVertex3d(0, 0, 0);
	glVertex3d(0, 3, 0);

	glColor3f(0, 0, 1); // Z->B
	glVertex3d(0, 0, 0);
	glVertex3d(0, 0, 3);

	glEnd();
}

// --------------------------------------------------------------------------------

void display() {
	glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	glLoadIdentity();
	drawBox4d(0, 0, 0, 2);
	drawAxes4(0, 0, 0, 10);
	glFlush();
}

// --------------------------------------------------------------------------------

void init() {
	glClearColor(0, 0, 0, 0);
	glDisable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);
	glDepthFunc(GL_LEQUAL);
	updateRef();
}

// --------------------------------------------------------------------------------

int mouse_a;

void mouseFunc(int a, int b, int x, int y) {
	//	cout << "a: " << a << "; b: " << b //
	//			<< "; x: " << x << "; y: " << y //
	//			<< endl;

	angTOP = 0;
	angRGH = 0;
	angFRN = 0;
	mouse_a = a;
	prevX = x;
	prevY = y;
}

// --------------------------------------------------------------------------------

void motionFunc(int x, int y) {
	double dx = x - prevX;
	double dy = y - prevY;

	if (mouse_a == 0) {
		angTOP = dx;
		angRGH = dy;
	}

	if (mouse_a == 2) {
		if (dx < 0) {
			angFRN = /* */sqrt(dx * dx + dy * dy);
		} else {
			angFRN = /**/-sqrt(dx * dx + dy * dy);
		}
	}

	if (mouse_a == 1) {
		dist += dy / 10;
	}

	//	cout << "x: " << x << "; y: " << y //
	//			<< "; dx: " << dx << "; dy: " << dy //
	//			<< endl;

	updateCameraPos();

	//	glutSetWindow(wXYView);
	//	glutPostRedisplay();

	//	glutSetWindow(wXZView);
	//	glutPostRedisplay();

	//	glutSetWindow(wYZView);
	//	glutPostRedisplay();

	glutSetWindow(wMMView);
	glutPostRedisplay();

	prevX = x;
	prevY = y;
}

// --------------------------------------------------------------------------------

void initXYView() {
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowPosition(640, 0);
	glutInitWindowSize(640, 350);
	wXYView = glutCreateWindow("XYView");
	glutDisplayFunc(display);

	glClearColor(0, 0, 0, 0);
	glDisable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, 1, 1, 10);

	gluLookAt( //
			0, 0, 4, //  eye
			0, 0, 0, //  cnt
			0, 1, 0); // upv
	glMatrixMode(GL_MODELVIEW);
}

void initXZView() {
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowPosition(0, 400);
	glutInitWindowSize(640, 350);
	wXZView = glutCreateWindow("XZView");
	glutDisplayFunc(display);

	glClearColor(0, 0, 0, 0);
	glDisable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, 1, 1, 10);

	gluLookAt( //
			0, 4, 0, //  eye
			0, 0, 0, //  cnt
			0, 0, -1); // upv
	glMatrixMode(GL_MODELVIEW);
}

void initYZView() {
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowPosition(640, 400);
	glutInitWindowSize(640, 350);
	wYZView = glutCreateWindow("YZView");
	glutDisplayFunc(display);

	glClearColor(0, 0, 0, 0);
	glDisable(GL_CULL_FACE);
	glEnable(GL_DEPTH_TEST);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	gluPerspective(60, 1, 1, 10);

	gluLookAt( //
			4, 0, 0, //  eye
			0, 0, 0, //  cnt
			0, 1, 0); // upv
	glMatrixMode(GL_MODELVIEW);
}

int main(int argc, char **argv) {
	glutInit(&argc, argv);

	//	initXYView();
	//	initXZView();
	//	initYZView();

	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);
	glutInitWindowPosition(0, 0);
	glutInitWindowSize(640, 350);

	wMMView = glutCreateWindow(argv[0]);

	init();

	glutMouseFunc/* */(mouseFunc);
	glutMotionFunc/**/(motionFunc);

	glutDisplayFunc(display);
	glutReshapeFunc(reshape);

	glutMainLoop();
}
