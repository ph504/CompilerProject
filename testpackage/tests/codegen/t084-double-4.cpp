#include <iostream>
#include <iomanip>

using namespace std;

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    float d1;
    float d2;
    float d3;
    float d4;
    float d5;
    float d6;
    float d7;
    float d8;

    d1 = 69.82413714;
    d2 = 960.7071281;
    d3 = 0.5281794697;
    d4 = 0.5281794697;
    d5 = -5039.128903;
    d6 = 7585.800593;
    d7 = -11748.63533;
    d8 = -13446.89678;

    cout << d1 + d2 << endl;
    cout << d4 + d4 << endl;
    cout << d5 + d6 << endl;
    cout << d7 + d8 << endl;

    return 0;
}
