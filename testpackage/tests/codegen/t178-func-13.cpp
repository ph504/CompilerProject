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

float func1(float x) {
    return x * 2.0;
}

float func2(float x) {
    return x + 1.0;
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    cout << func1(func2(func1(func1(func2(2.5))))) << endl;
    cout << func2(func2(func1(func1(func2(1.2))))) << endl;

    return 0;
}
