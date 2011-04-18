#include <iostream>


using namespace std;


int main() {

  int *foo = new int[5*5];

  for (int i = 0; i < 25; i++) {
    foo[i] = i;
  }

  //  int *xxx = foo; // ???

  for (int i = 0; i < 5; i++) {
    for (int j = 0; j < 5; j++) {
      cout << foo[i][j] << endl;
    }
  }

  cout << "-------------------->" << endl;


  for (int i = 0; i < 25; i++) {
    cout << foo[i] << endl;
  }


}
