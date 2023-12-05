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

    def map_num(self, rmap: Dict, num: int):
        for (ss, se) in rmap:
            if ss <= num <= se:
                return (num - ss) + rmap[(ss, se)]
        return num

    def task1(self):
        arr = []
        for seed in self.seeds:
            curr = seed
            for rmap in self.rmaps:
                curr = self.map_num(rmap, curr)
            arr.append(curr)
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
            arr.append((self.map_num(rmap, ss),
                       self.map_num(rmap, se)))

        return arr

    def task2(self):
        i = 0
        arr = []

        intervals = [(self.seeds[i], self.seeds[i] + self.seeds[i+1] - 1)
                     for i in range(0, len(self.seeds), 2)]

        for rmap in self.rmaps:
            newintervals = [newinterval for interval in intervals
                            for newinterval in self.split_range(rmap, *interval)]
            intervals = newintervals
        arr, _ = zip(*intervals)

        return min(arr)
