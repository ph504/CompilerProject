#include <iostream>
#include <iomanip>

using namespace std;

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    cout << (double)(0) << endl;
    cout << (double)(1) << endl;
    cout << (double)(10000) << endl;
    cout << (double)(-0) << endl;
    cout << (double)(-1) << endl;
    cout << (double)(-10000) << endl;
    cout << (double)(32768) << endl;
    cout << (double)(-32768) << endl;

    return 0;
}
