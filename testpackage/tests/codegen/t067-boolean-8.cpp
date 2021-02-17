#include <iostream>

using namespace std;

int main(int argc, char **argv) {
    bool b1;
    bool b2;
    bool b3;
    bool b4;

    bool br;

    b1 = true;
    b2 = false;
    b3 = true;
    b4 = false;

    br = b1 || b2 && b3 && !b4;
    cout << br << endl;

    br = b2 && b4 || b3 && !b4 || b4;
    cout << br << endl;

    br = b4 == b3 || b3 == b2 && b2 == !b1;
    cout << br << endl;

    return 0;
}
