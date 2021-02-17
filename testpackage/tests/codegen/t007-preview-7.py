
class Person:
    name = None
    age = None

    def setAge(self, age: int):
        self.age = age

    def setName(self, name: str):
        self.name = name

    def print(self):
        print("Name:", self.name, "Age:", self.age)


name = input()
age = int(input())

p = Person()
p.setName(name)
p.setAge(age)

p.print()
