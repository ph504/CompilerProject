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

    float d1;
    float d2;
    int i1;
    int i2;
    bool b1;
    bool b2;

    float rd;
    int ri;

    d1 = 1.5741691;
    d2 = -0.1245714;

    cin >> i1;
    cin >> i2;
    ri = ri + (i1 % i2) * (i2 % i1);
    rd = rd + ((i1 * i2) % (btoi(i1 == i2) * 2 + 2)) * itod(ri) * d1;

    cin >> i1;
    cin >> i2;
    ri = ri + (i1 % i2) * (i2 % i1);
    rd = rd + ((i1 * i2) % (btoi(i1 == i2) * 2 + 2)) * itod(ri) * d2;

    cin >> i1;
    cin >> i2;
    ri = ri + (i1 % i2) * (i2 % i1);
    rd = rd + ((i1 * i2) % (btoi(i1 == i2) * 2 + 2)) * itod(ri) * d1;

    cin >> i1;
    cin >> i2;
    ri = ri + (i1 % i2) * (i2 % i1);
    rd = rd + ((i1 * i2) % (btoi(i1 == i2) * 2 + 2)) * itod(ri) * d2;

    cin >> i1;
    cin >> i2;
    ri = ri + (i1 % i2) * (i2 % i1);
    rd = rd + ((i1 * i2) % (btoi(i1 == i2) * 2 + 2)) * itod(ri) * d1;

    cin >> i1;
    cin >> i2;
    ri = ri + (i1 % i2) * (i2 % i1);
    rd = rd + ((i1 * i2) % (btoi(i1 == i2) * 2 + 2)) * itod(ri) * d2;

    cout << rd << endl;

    return 0;
}
