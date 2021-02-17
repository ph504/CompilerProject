#include <iostream>
#include <iomanip>

using namespace std;

float itod(int i) {
    return (float) i;
}

const char *itob(int i) {
    return i != 0 ? "true" : "false";
}

int dtoi(float f) {
    int i = f;
    float t = f - i;

    int offset = 0;
    if (t >= 0.5) offset = 1;
    else if (t < -0.5) offset = -1;

    return i + offset;
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    cout << dtoi(0.0) << endl;
    cout << dtoi(-0.0) << endl;
    cout << dtoi(1.0) << endl;
    cout << dtoi(-1.0) << endl;

    cout << dtoi(0.3) << endl;
    cout << dtoi(0.5) << endl;
    cout << dtoi(-0.1) << endl;
    cout << dtoi(-0.5) << endl;
    cout << dtoi(-0.7) << endl;

    return 0;
}
