def parseline(line):
    if line[0] == 'n':
        return (line.replace("\n", ""), 0)
    else:
        op, stram = line.split(" ")
        return (op, int(stram))


def simulate(commands):
    xval = 1
    cicle = 1
    xupd = []
    for c in commands:
        if c[0] == "noop":
            xupd.append((cicle, xval))
            cicle += 1
        else:
            xupd.append((cicle, xval))
            xupd.append((cicle+1, xval))
            cicle += 2
            xval += c[1]

    return xupd


def part1(commands):
    cx = simulate(commands)
    arr = [20, 60, 100, 140, 180, 220]
    res = 0
    for v in arr:
        res += v * cx[v-1][1]
    return res


def part2(commands):
    cx = simulate(commands)
    res = ["#" if x-1 <= (c-1) % 40 <= x+1 else "." for (c, x) in cx]
    splitres = ["".join(res[i*40:((i+1)*40)]) for i in range(6)]
    return "\n".join(splitres)


if __name__ == "__main__":
    with open("inp.txt", "r") as f:
        lines = f.readlines()

    commands = [parseline(l) for l in lines]

    res1 = part1(commands)
    res2 = part2(commands)
    print("Res 1:", res1)
    print("Res 2:")
    print(res2)
