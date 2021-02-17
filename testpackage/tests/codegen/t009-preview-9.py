from typing import List


def sort(items: List[int]):
    n = len(items)

    for i in range(0, n - 1):
        for j in range(0, n - i - 1):

            if items[j] > items[j + 1]:
                items[j], items[j + 1] = items[j + 1], items[j]


print("Please enter the numbers (max count: 100, enter -1 to end sooner): ");

rawitems = [0] * 100
# for (i = 0; i < 100; i += 1) {
i = 0
while i < 100:
    x = int(input())
    if x == -1:
        break

    rawitems[i] = x
    i += 1


items = [0] * i
for j in range(0, i):
    items[j] = rawitems[j]

sort(items)

print("After sort: ")

for x in items:
    print(x)
