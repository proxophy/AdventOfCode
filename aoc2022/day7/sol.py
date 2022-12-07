import re
if __name__ == "__main__":
    with open("inp.txt", "r") as f:
        content = f.read()

    commands = re.findall("\$ ([^\$]+)\n", content)

    def split(s): return ("cd", s[3:]) if s[:2] == "cd" else (
        "ls", s.split("\n")[1:])
    commands = list(map(split, commands))

    parent = {"_": ""}
    pwd = "_"
    ftree = {"_": ([], [])}
    for c in commands:
        if c[0] == "cd":
            if c[1] == "/":
                pwd = "_"
            elif c[1] == "..":
                pwd = parent[pwd]
            else:
                pwd = f"{pwd}/{c[1]}"
        elif c[0] == "ls":
            for el in c[1]:
                p1, p2 = el.split(" ")
                newpath = f"{pwd}/{p2}"
                if p1 == "dir":
                    parent[newpath] = pwd
                    ftree[pwd][0].append(newpath)
                    ftree[newpath] = ([], [])

                else:
                    ftree[pwd][1].append((p2, int(p1)))

    def dirval(name): return sum(
        map(dirval, ftree[name][0])) + sum(map(lambda x: x[1], ftree[name][1]))

    allsizes = list(map(dirval, ftree.keys()))
    res1 = sum(list(filter(lambda x: x <= 100000, allsizes)))

    outerspace = dirval("_")
    spacenneeded = 30000000 - (70000000 - outerspace)
    res2 = min(filter(lambda x: x >= spacenneeded, allsizes))

    print("Part 1:", res1)
    print("Part 2:", res2)
