#include <iostream>

using namespace std;

int main(int argc, char **argv) {
    double d1;
    double d2;
    double d3;
    double d4;
    double d5;
    double d6;
    double d7;
    double d8;

    bool r;

    d1 = 69.82413714;
    d2 = 960.7071281;
    d3 = 0.5281794697;
    d4 = 0.5281794697;
    d5 = -5039.128903;
    d6 = 7585.800593;
    d7 = -11748.63533;
    d8 = -13446.89678;

    r = d1 == d2;
    cout << (r ? "true" : "false") << endl;

    r = d3 == d4;
    cout << (r ? "true" : "false") << endl;

    r = d5 == d6;
    cout << (r ? "true" : "false") << endl;

    r = d7 == d8;
    cout << (r ? "true" : "false") << endl;

    return 0;
}
