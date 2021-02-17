
from abc import ABC, abstractmethod


class Nameable(ABC):

    @abstractmethod
    def setName(self, name):
        pass

    @abstractmethod
    def getName(self):
        pass


class Person(Nameable):
    name = None
    age = None

    def setAge(self, age: int):
        self.age = age

    def getAge(self):
        return self.age

    def setName(self, name: str):
        self.name = name

    def getName(self):
        return self.name

    def print(self):
        print("Name:", self.name, "Age:", self.age)


class Student(Person):
    grade = None

    def setGrade(self, grade: int):
        self.grade = grade

    def print(self):
        print("Name:", self.name, "Age:", self.age, "Grade:", self.grade)


p1 = Person()
p1.setName(input())
p1.setAge(int(input()))

p2 = Person()
n = p2
n.setName(input())
p2.setAge(int(input()))

s1 = Student()
s1.setName(input())
s1.setAge(int(input()))
s1.setGrade(int(input()))

s2 = Student()
n = s2
n.setName(input())
p = s2
p.setAge(int(input()))
s2.setGrade(int(input()))

p1.print()
p2.print()
s1.print()
s2.print()

n = p1
print(n.getName())
n = s2
print(n.getName())

p = p2
print(p.getName())
print(p.getAge())
p = s1
print(p.getAge())
p.print()

n = s2
print(n.getName())
