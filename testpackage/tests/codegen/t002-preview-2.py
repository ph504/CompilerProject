
i = 1
b = 0
while True:
    print("Please enter the #" + str(i) + " number:")
    a = int(input())
    if a < 0:
        break
    b += a
    i += 1

print("Sum of " + str(i) + " items is: " + str(b))
