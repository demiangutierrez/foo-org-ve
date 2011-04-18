#include <iostream>
#include <stdlib.h>
#include <fstream>
#include <math.h>

using namespace std;

char SECT_CHARS[] = {//
  '.', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'j', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
  'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'J', 'M', 'N', 'O', 'P', 'Q',
  'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

int **data;
int **sect;

int datW;
int datH;

int currSectChar = 1;

// --------------------------------------------------------------------------------

int **allocArray(int w, int h) {
  int **ret = new int*[h];

  for (int i = 0; i < h; i++) {
    ret[i] = new int[w];
  }

  return ret;
}

// --------------------------------------------------------------------------------

void load(const char *filename) {
  ifstream file(filename);
  string line;

  if (!file.is_open()) {
    return;
  }

  getline(file, line);

  int spaceidx = line.find(" ", 0, 1);

  datW = atoi(line.substr(0, spaceidx    ).c_str());
  datH = atoi(line.substr(   spaceidx + 1).c_str());

  data = allocArray(datW, datH);

  for (int j = 0; j < datH; j++) {
    getline(file, line);

    for (int i = 0; i < datW; i++) {
      data[i][j] = atoi(line.substr(i, 1).c_str());
    }
  }

  file.close();
}

// --------------------------------------------------------------------------------

void handleCoord(int x, int y, int minX, int minY, int maxX, int maxY) {

  // ----------------------------------------
  // check if we can process this node
  // ----------------------------------------

  if (sect[x][y] != 0 || data[x][y] == 1) {
    return;
  }

  // ----------------------------------------
  // stack
  // ----------------------------------------

  int stackX[(maxX - minX) * (maxY - minY)];
  int stackY[(maxX - minX) * (maxY - minY)];
  int currStackIndex = 0;

  // ----------------------------------------
  // add the first element to the stack
  // ----------------------------------------

  stackX[currStackIndex] = x;
  stackY[currStackIndex] = y;
  currStackIndex++;

  while (currStackIndex > 0) {

    // ----------------------------------------
    // take the first element from the stack
    // ----------------------------------------

    currStackIndex--;
    int currX = stackX[currStackIndex];
    int currY = stackY[currStackIndex];

    // ----------------------------------------
    // handle and check neighbor nodes
    // ----------------------------------------

    sect[currX][currY] = currSectChar;

    for (int j = currY - 1; j < currY + 2; j++) {
      for (int i = currX - 1; i < currX + 2; i++) {

	// ----------------------------------------
	// check if we have to add this to stack
	// ----------------------------------------

	if (i == currX && j == currY) {
	  continue;
	}

	if (i < 0/**/|| i >= datW || j < 0/**/|| j >= datH) {
	  continue;
	}

	if (i < minX || i >= maxX || j < minY || j >= maxY) {
	  continue;
	}

	if (sect[i][j] == -1) {
	  continue;
	}

	if (sect[i][j] != +0) {
	  continue;
	}

	if (data[i][j] == +1) {
	  continue;
	}

	// ----------------------------------------
	// add to stack / mark as added (-1)
	// ----------------------------------------

	sect[i][j] = -1;

	stackX[currStackIndex] = i;
	stackY[currStackIndex] = j;
	currStackIndex++;
      }
    }
  }
}

// --------------------------------------------------------------------------------

void defineSectors(int minX, int minY, int maxX, int maxY) {
  for (int j = minY; j < maxY; j++) {
    for (int i = minX; i < maxX; i++) {
      if (sect[i][j] == 0 && data[i][j] != 1) {
	handleCoord(i, j, minX, minY, maxX, maxY);
	currSectChar++;
      }
    }
  }
}

// --------------------------------------------------------------------------------

void defineSectors(int w, int h) {
  sect = allocArray(datW, datH);

  int *buffer = new int[datW*datH];

  for (int j = 0; j < datH; j++) {
    for (int i = 0; i < datW; i++) {
      sect[i][j] = 0;
    }
  }

  int totSecW = (int) ceil((double) datW / w);
  int totSecH = (int) ceil((double) datH / h);

  for (int j = 0; j < totSecH; j++) {
    for (int i = 0; i < totSecW; i++) {
      int maxX = (i * w + w) > datW ? datW : i * w + w;
      int maxY = (j * h + h) > datH ? datH : j * h + h;

      defineSectors(i * w, j * h, maxX, maxY);
    }
  }
}

// --------------------------------------------------------------------------------

void printData() {
  for (int j = 0; j < datH; j++) {
    for (int i = 0; i < datW; i++) {
      cout << data[i][j];
    }
    cout << endl;
  }
}

// --------------------------------------------------------------------------------

void printSect() {
  for (int j = 0; j < datH; j++) {
    for (int i = 0; i < datW; i++) {
      cout << SECT_CHARS[sect[i][j]];
    }
    cout << endl;
  }
}

// --------------------------------------------------------------------------------

int main() {
  load("map1.txt");

  cout << "--------------------------------------------------------------->" << endl;
  printData();
  cout << "--------------------------------------------------------------->" << endl;
  defineSectors(6, 6);
  cout << "--------------------------------------------------------------->" << endl;
  printSect();
  cout << "--------------------------------------------------------------->" << endl;
}

