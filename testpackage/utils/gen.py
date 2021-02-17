
# levels
levels = 100

f = open("code", "w")
res = ""
alphabet = [""]
b = 0
d = 1

for x in range(ord('A'), ord('Z') + 1):
    l = chr(x)
    a = "" + l
    alphabet.append(a)

for i in range(levels):
  dstr = f"{alphabet[d//26]}{alphabet[d % 26 + 1]}"
  bstr = f"{alphabet[b//26]}{alphabet[b % 26 + 1]}"
  res += f"""class {dstr} extends {bstr} {{\n\tvoid test() {{\n\t\tPrint("{dstr}.test()");\n\t}}\n}}\n\n"""
  b = d
  d = d + 1

print(res, file=f)

main_start = ""
main_start += f"""int main() {{\n"""
main_selves = ""
main_handles = ""

for i in range(levels + 1):
  cstr = f"{alphabet[i//26]}{alphabet[i % 26 + 1]}"
  vstr = cstr.lower()
  main_start += f"\t{cstr} {vstr};\n"
  main_selves += f"\t{vstr} = new {cstr};\n\t{vstr}.test();\n"
  main_handles += f"\ta = {vstr};\n\ta.test();\n"

main_end = "}"

print(main_start + "\n" + main_selves + "\n" + main_handles + main_end, file=f)

f.close()

## OUTPUT

f = open("out", "w")
for i in range(levels + 1):
  cstr = f"{alphabet[i//26]}{alphabet[i % 26 + 1]}"
  print(f"{cstr}.test()", file=f)

f.close()
