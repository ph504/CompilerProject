a = int(input())
b = int(input())
c = int(input())
d = int(input())

z = a + (b * 5)
a = z * d
z = 2 * a + int((a + b) / (c + d))
b = int(z / a)
c = b * a + z
d = a - b - c - d - z

print(a)
print(b)
print(c)
print(d)

print(z)
