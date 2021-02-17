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
    int ints[5];
    for (int i = 0; i < 5; i++) {
        cin >> ints[i];
    }

    cout << ints[ints[1] % ints[2] % ints[4] % ints[0] % ints[3] % 5] << endl;

    return 0;
}
