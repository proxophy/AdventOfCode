import re
if __name__ == "__main__":
    with open("test_inp.txt", "r") as f:
        content = f.read()

    pattern = "\$ ([^\$]+)\n"
    pattern = "\$ (?P<cmd>cd|ls)(?P<trg> [a-z\/\.]+|)\n(?P<els>[^$]*)"
    commands = list(map (lambda x: x.groupdict(), re.finditer(pattern, content)))
    for c in commands:
        c["trg"] = c["trg"].replace(" ", "")
        elpattern = "(?P<size>\d+) (?P<fname>[a-z\.])|dir (?P<dname>[\.a-z])"
        c["els"] = list(map (lambda x: x.groupdict(), re.finditer(elpattern, c["els"])))

    parent = {"_": ""}
    pwd = "_"
    ftree = {"_": ([], [])}
    for c in commands:
        if c["cmd"] == "cd":
            trg = c["trg"]
            if trg == "/":
                pwd = "_"
            elif trg == "..":
                pwd = parent[pwd]
            else:
                pwd = f"{pwd}/{trg}"
        elif c["cmd"] == "ls":
            for el in c["els"]:
                if el["dname"]:
                    dname = el["dname"]
                    newpath = f"{pwd}/{dname}"
                    parent[newpath] = pwd
                    ftree[pwd][0].append(newpath)
                    ftree[newpath] = ([], [])

                else:
                    ftree[pwd][1].append((el["fname"], int(el["size"])))

    print(ftree)
    def dirval(name): return sum(
        map(dirval, ftree[name][0])) + sum(map(lambda x: x[1], ftree[name][1]))

    allsizes = list(map(dirval, ftree.keys()))
    res1 = sum(list(filter(lambda x: x <= 100000, allsizes)))

    outerspace = dirval("_")
    spacenneeded = 30000000 - (70000000 - outerspace)
    res2 = min(filter(lambda x: x >= spacenneeded, allsizes))

    print("Part 1:", res1)
    print("Part 2:", res2)
