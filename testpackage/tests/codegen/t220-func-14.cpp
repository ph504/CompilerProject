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

int factorial_helper(int n, int acc) {
    if (n == 0) return acc;
    else return factorial_helper(n - 1, acc * n);
}

int factorial(int n) {
    return factorial_helper(n, 1);
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    cout << factorial(5) << endl;

    return 0;
}
