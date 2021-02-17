
def abs_mult(a, b):
    if a > b:
        c = a - b
    else:
        c = b - a
    return c * a * b


a = int(input())
b = int(input())

print(abs_mult(a, b))
