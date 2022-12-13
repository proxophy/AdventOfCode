import functools

def parsecontent(content):
    pairs = []
    for p in content.split("\n\n"):
        if len(p.split("\n")) >= 2:
            el1, el2 = p.split("\n")[0], p.split("\n")[1]
            pairs.append((eval(el1), eval(el2)))
    return pairs

def compare(c1, c2) -> int:
    if isinstance(c1, int) and isinstance(c2, int):
        return c1 - c2
    elif isinstance(c1, list) and isinstance(c2, list):
        j = 0
        while j < len(c1) and j < len(c2):
            compel = compare(c1[j], c2[j])
            if compel < 0:
                return -1
            elif compel > 0:
                return 1
            j += 1
        if j >= len(c2) and j < len(c1):
            return 1
        if len(c1) == len(c2):
            return 0
        return -1
    elif isinstance(c1, int) and isinstance(c2, list):
        return compare([c1], c2)
    elif isinstance(c1, list) and isinstance(c2, int):
        return compare(c1, [c2])

    return 0

def part1(pairs):
    res = sum([j+1 if compare(*p) < 0 else 0 for j, p in enumerate(pairs)])
    return res

def part2(pairs):
    allpackets = [[[2]], [[6]]]
    for p in pairs:
        allpackets.extend(p)
    allpackets.sort(key=functools.cmp_to_key(compare))
    return (allpackets.index([[2]])+1)*(allpackets.index([[6]])+1)


if __name__ == "__main__":
    with open("inp.txt", "r") as f:
        content = f.read()

    pairs = parsecontent(content)
    res1 = part1(pairs)
    res2 = part2(pairs)

    print("Res 1:", res1)
    print("Res 2:", res2)
