#include <iostream>

using namespace std;

int main(int argc, char **argv) {
    int x;
    int y;
    int z;
    
    int r;

    cin >> x;
    cin >> y;
    cin >> z;
 
    r = x * (y + 5) * (z - x * y) * 3 / ((z + 0) - x) % y;
    cout << r << endl;
    
    r = (x - (y + z) % (y % z));
    cout << r << endl;
    
    r = (x + (y * z)) * (z - y) / (z);
    cout << r << endl;
    
    return 0;
}

