def part1(content):
    tiles = content
    res = 0
    for i in range(len(tiles)-1):
        for j in range(i+1, len(tiles)):
            ta, tb = tiles[i], tiles[j]
            area = (abs(ta[0]-tb[0])+1) * (abs(ta[1]-tb[1])+1)
            res = max(area, res)
    return res


def part2(content):
    raise NotImplementedError()


if __name__ == "__main__":
    test = 0
    day = 9
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    tiles = [tuple(int(el) for el in l.split(","))
             for l in content.split("\n")]

    print(part1(tiles))
    # print(part2(content))
