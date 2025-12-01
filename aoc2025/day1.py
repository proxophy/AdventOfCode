def part1(content):
    pos = 50
    res = 0
    for left, amount in content:
        if left:
            pos -= amount
        else:
            pos += amount
        pos %= 100
        if pos == 0:
            res += 1
    return res


def part2(content):
    pos = 50
    res = 0
    for left, amount in content:
        for _ in range(amount):
            if left:
                pos -= 1
            else:
                pos += 1
            pos %= 100
            if pos == 0:
                res += 1
    return res


if __name__ == "__main__":
    test = 0
    day = 1
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    lines = [(l[0] == "L", int(l[1:])) for l in content.split("\n")]

    print(part1(lines))
    print(part2(lines))
