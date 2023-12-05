import functools
from typing import Dict

from utils import parse_intlist


class Day5:
    def __init__(self, inp: str) -> None:
        parts = inp.split("\n\n")
        self.seeds = parse_intlist(parts[0].split(":")[1])
        self.rmaps = [self.parse_map(part)for part in parts[1:]]
        return

    def parse_map(self, s: str):
        lines = s.split("\n")
        ranges = {}
        for l in lines[1:]:
            ds, ss, rl = parse_intlist(l)
            ranges[(ss, ss+rl-1)] = ds
        return ranges

    def map_num(self, num: int, rmap: Dict):
        for (ss, se) in rmap:
            if ss <= num <= se:
                return (num - ss) + rmap[(ss, se)]
        return num

    def task1(self):
        arr = [functools.reduce(self.map_num, self.rmaps, seed)
               for seed in self.seeds]
        return min(arr)

    def split_range(self, rmap: Dict, start: int, end: int):
        rmap_ranges = sorted(list(rmap.keys()))

        # range intersections
        splits = []
        cmin = start
        if start < rmap_ranges[0][0]:
            splits.append((start, rmap_ranges[0][0]-1))
            cmin = rmap_ranges[0][0]

        for (s, e) in rmap_ranges:
            if s <= cmin <= e:
                splits.append((cmin, min(e, end)))
                cmin = e+1

            if cmin > end:
                break

        if cmin < end:
            splits.append((cmin, end))

        # map ranges
        arr = []
        for ss, se in splits:
            arr.append((self.map_num(ss, rmap),
                       self.map_num(se, rmap)))

        return arr

    def task2(self):
        arr = []

        intervals = [(self.seeds[i], self.seeds[i] + self.seeds[i+1] - 1)
                     for i in range(0, len(self.seeds), 2)]

        for rmap in self.rmaps:
            newintervals = [newinterval for interval in intervals
                            for newinterval in self.split_range(rmap, *interval)]
            intervals = newintervals
        arr, _ = zip(*intervals)

        return min(arr)


if __name__ == "__main__":
    def foo(y: int, x: str) -> int:
        return int(x)+y
    l = "1", "2", "3", "4", "5", "6"
    res = functools.reduce(foo, l, 1)
    print(res)

    pass
