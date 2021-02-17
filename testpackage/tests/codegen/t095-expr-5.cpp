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
    b1 = i1 > i2;
    b2 = itod(i2) >= d1;
    rd = itod(btoi(b1)) * d1 + itod(btoi(b2)) * d2;
    ri = dtoi(rd);
    ri = ri * i1 - i2 * i2 * ri;
    rd = itod(ri % 2) + rd * 0.5;
    cout << btoa(b1 || b2) << endl;
    cout << ri << endl;
    cout << rd << endl;

    cin >> i1;
    cin >> i2;
    b1 = i1 > i2;
    b2 = itod(i2) >= d1;
    rd = itod(btoi(b1)) * d1 + itod(btoi(b2)) * d2;
    ri = dtoi(rd);
    ri = ri * i1 - i2 * i2 * ri;
    rd = itod(ri % 2) + rd * 0.5;
    cout << btoa(b1 || b2) << endl;
    cout << ri << endl;
    cout << rd << endl;

    cin >> i1;
    cin >> i2;
    b1 = i1 > i2;
    b2 = itod(i2) >= d1;
    rd = itod(btoi(b1)) * d1 + itod(btoi(b2)) * d2;
    ri = dtoi(rd);
    ri = ri * i1 - i2 * i2 * ri;
    rd = itod(ri % 2) + rd * 0.5;
    cout << btoa(b1 || b2) << endl;
    cout << ri << endl;
    cout << rd << endl;

    cin >> i1;
    cin >> i2;
    b1 = i1 > i2;
    b2 = itod(i2) >= d1;
    rd = itod(btoi(b1)) * d1 + itod(btoi(b2)) * d2;
    ri = dtoi(rd);
    ri = ri * i1 - i2 * i2 * ri;
    rd = itod(ri % 2) + rd * 0.5;
    cout << btoa(b1 || b2) << endl;
    cout << ri << endl;
    cout << rd << endl;

    return 0;
}
