#include <iostream>
#include <iomanip>

using namespace std;

float itod(int i) {
    return (float) i;
}

const char *btoa(bool b) {
    return b ? "true" : "false";
}

bool itob(int i) {
    return i != 0;
}

int dtoi(float f) {
    int i = f;
    float t = f - i;

    int offset = 0;
    if (t >= 0.5) offset = 1;
    else if (t < -0.5) offset = -1;

    return i + offset;
}

int btoi(bool b) {
    return b;
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    float doubles[5];

    doubles[0] = 6.3;
    doubles[1] = 4.7;
    doubles[2] = 9.5;
    doubles[3] = 12.5;
    doubles[4] = -3;

    cout << doubles[4] << endl;
    cout << doubles[3] << endl;
    cout << doubles[2] << endl;
    cout << doubles[1] << endl;
    cout << doubles[0] << endl;

    return 0;
}
