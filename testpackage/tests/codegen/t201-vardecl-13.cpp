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

    int t;
    int c;
    c = 0;
    for ( t = 5; t > 0; t = t - 1 ) {
        // this one may get a lil tricky :D
        int t;
        t = 5;
        cout << t << endl;
        c = c + 1;
        if (c > 10) break;
    }
    cout << t << endl;
    return 0;
}
