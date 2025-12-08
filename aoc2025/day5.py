from intervaltree import Interval, IntervalTree

def part1(content):
    ranges, ids = content
    ranges = [(r[0], r[1]+1) for r in ranges]
    t = IntervalTree.from_tuples(ranges)
    res = 0
    for id in ids:
        if t[id]: 
            res += 1
    return res


def part2(content):
    ranges = content
    
    ranges = [(r[0], r[1]+1) for r in ranges[:-1]]
    t = IntervalTree.from_tuples(ranges)
    t[10:14] = (10,14)
    print(t)
    print(t.overlap(12,19))
    # raise NotImplementedError()


if __name__ == "__main__":
    test = 1
    day = 5
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    ranges, ids = content.split("\n\n")
    ranges = [[int(el) for el in r.split("-")] for r in ranges.split("\n")]
    ids = [int(el) for el in ids.split("\n")]
    # print(part1((ranges, ids)))
    print(part2(ranges))
