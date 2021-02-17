#include <iostream>
#include <iomanip>

using namespace std;

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    float d;
    d = 3.1415;
    cout << d << endl;

    d = 455555555.0;
    cout << d << endl;

    d = -0.123456789;
    cout << d << endl;

    return 0;
}
