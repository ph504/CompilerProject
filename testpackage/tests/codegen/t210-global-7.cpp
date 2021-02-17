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

int g;

void test1() {
    g = 16;
}

void test2() {
    cout << g << endl;
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    int g;
    g = 1;
    cout << g << endl;
    test1();
    cout << g << endl;
    test2();
    cout << g << endl;

    return 0;
}
