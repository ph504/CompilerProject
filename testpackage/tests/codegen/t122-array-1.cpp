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

class Z {
public:

    int counter;

    void init() {
        counter = 0;
    }

    Z* c(int x) {
        cout << "inside method c of Z" << endl;
        counter = counter + x;
        return this;
    }

    void print() {
        cout << "Value of Z counter is: " << counter << endl;
    }
};

class A {
public:
    Z* a(Z* z, int c) {
        cout << "entering method a of A" << endl;
        return z->c(c + 1);
    }

    Z* b(Z* z, int c) {
        cout << "entering method b of A" << endl;
        return z->c(c * 2);
    }
};

int main(int argc, char **argv) {
    A a;
    Z z;
//    a = new A;
//    z = new Z;
    z.init();

    a.b(a.a(a.b(&z, 0), 1), 2);
    z.print();

    return 0;
}
