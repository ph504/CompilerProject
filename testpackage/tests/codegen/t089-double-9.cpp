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

    float r;

    d1 = 69.82413714;
    d2 = 960.7071281;
    d3 = 0.5281794697;
    d4 = 0.5281794697;
    d5 = -5039.128903;
    d6 = 7585.800593;
    d7 = -11748.63533;
    d8 = -13446.89678;

    r = d1 + d2 * d3 - 4 / d4 + d5 / d6 + d1 * 10 + 2 * d7 - d8;
    cout << r << endl;

    r = d8 * d2 + -d3 + d2 * d2 - d3 * d7 + d8 - d5 / d1;
    cout << r << endl;

    r = d5 * d3 / d2 - d1 * d6 / d4;
    cout << r << endl;

    return 0;
}
