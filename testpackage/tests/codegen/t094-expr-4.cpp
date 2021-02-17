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

int btoi(bool b) {
    return b;
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    cout << btoi(true) << endl;
    cout << btoi(false) << endl;

    return 0;
}
