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

class Base {
public:
    int counter;

    virtual Base* action_s() = 0;
    virtual Base* action_o() = 0;
    void print() {
        cout << counter << endl;
    }
    void init() {
        counter = 0;
    }
};

class B;

class A : public Base {
public:
    B* pair;
    Base* action_o();
    Base* action_s();
    void set_pair(B*);
};

Base* A::action_s() {
    cout << "A.action_s()" << endl;
    counter += 1;
    return this;
}

Base* A::action_o() {
    cout << "A.action_o()" << endl;
    counter += 1;
    return (Base *) pair;
}

void A::set_pair(B* p) {
    pair = p;
}

class B : public Base {
public:
    A* pair;
    Base* action_o();
    Base* action_s();
    void set_pair(A*);
};

Base* B::action_s() {
    cout << "B.action_s()" << endl;
    counter += 1;
    return this;
}

Base* B::action_o() {
    cout << "B.action_o()" << endl;
    counter += 1;
    return pair;
}

void B::set_pair(A* p) {
    pair = p;
}


int main(int argc, char **argv) {
    cout << fixed << setprecision(8);

    Base* q;
    A a;
    B b;
//    a = new A;
//    b = new B;
    a.init();
    b.init();
    a.set_pair(&b);
    b.set_pair(&a);

    q = &a;
    q->action_s()->action_s()->action_o()->action_o()->action_s()->action_o()->action_s()->action_s()->action_o();
    a.print();
    b.print();

    return 0;
}
