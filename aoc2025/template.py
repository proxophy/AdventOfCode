def part1(content):
    raise NotImplementedError()


def part2(content):
    raise NotImplementedError()


if __name__ == "__main__":
    test = 0
    day = 1
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()

    print(part1(content))
    print(part2(content))
