#include <iostream>
#include <iomanip>

using namespace std;

const char *itob(int i) {
    return i != 0 ? "true" : "false";
}

int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    cout << itob(0) << endl;
    cout << itob(1) << endl;
    cout << itob(-1) << endl;
    cout << itob(300000) << endl;
    cout << itob(-300000) << endl;

    return 0;
}
