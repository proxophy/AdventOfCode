from intervaltree import Interval, IntervalTree
from numpy import searchsorted
from itertools import permutations


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

    fixpoints = []
    fvals = {}
    res = 0

    for s, e in ranges:

        fresh = 0
        if not fixpoints or s > max(fixpoints) or e < min(fixpoints):
            if s < e:
                fixpoints += [s, e]
                fvals[s] = 1
                fvals[e] = -1
            else:
                fixpoints += [s]
                fvals[s] = 0

            fresh = e - s + 1

        else:
            fresh = 0
            si = searchsorted(fixpoints, s, side="left")
            ei = searchsorted(fixpoints, e, side="right")
            presum = sum(list(fvals.values())[:si])
            active_ivals = presum
            olds = s
            olde = e

            if s == max(fixpoints):
                fresh = e - s
            elif si == ei:
                # no intersect with other intervals besides start point
                if presum == 0:
                    fresh = e - s + 1
                if fixpoints[si] == s:
                    fresh -= 1
                if ei < len(fixpoints) and fixpoints[ei] == e:
                    fresh -= 1
            else:
                if presum == 0:
                    fresh += fixpoints[si] - s
                    s = fixpoints[si]

                for i in range(si, min(ei, len(fixpoints)) - 1):
                    active_ivals += fvals[fixpoints[i]]
                    # no intervals until next fixpoint
                    if active_ivals == 0:
                        assert fixpoints[i+1] - fixpoints[i] - 1 >= 0
                        fresh += max(0, fixpoints[i+1] - fixpoints[i] - 1)

                active_ivals += fvals[fixpoints[ei - 1]]
                if ei >= len(fixpoints):
                    assert e - fixpoints[-1] >= 0
                    fresh += e - fixpoints[-1]
                elif active_ivals == 0:
                    assert e - fixpoints[ei - 1] >= 0
                    fresh += e - fixpoints[ei - 1]
                    if e == fixpoints[ei]:
                        fresh -= 1
                    assert fresh >= 0

            if olds not in fixpoints:
                fixpoints.append(olds)
                fvals[olds] = 1
            else:
                fvals[olds] += 1
            if olde not in fixpoints:
                fixpoints.append(olde)
                fvals[olde] = -1
            else:
                fvals[olde] -= 1

        res += fresh
        fixpoints.sort()
        fvals = dict(sorted(fvals.items()))
    return res


def part2try2(ranges):
    fixpoints = []
    fvals = {}
    for s, e in ranges:
        if s in fixpoints:
            fvals[s] += 1
        else:
            fixpoints.append(s)
            fvals[s] = 1
        if e in fixpoints:
            fvals[e] -= 1
        else:
            fixpoints.append(e)
            fvals[e] = -1
    fixpoints.sort()
    fvals = dict(sorted(fvals.items()))
    active = 0
    fresh = 0
    for i in range(len(fixpoints)):
        active += fvals[fixpoints[i]]
        if i < len(fixpoints) - 1 and active > 0:
            fresh += fixpoints[i+1] - fixpoints[i]
        elif active == 0 or fvals[fixpoints[i]] == 0 :
            fresh += 1
        # print(fixpoints[i], active, fresh)
        
    return fresh


if __name__ == "__main__":
    test = 0
    day = 5
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    ranges, ids = content.split("\n\n")
    ranges = [[int(el) for el in r.split("-")] for r in ranges.split("\n")]
    ids = [int(el) for el in ids.split("\n")]

    # ranges = [(5, 7), (8, 8)]
    res1 = part2(ranges)
    res2 = part2try2(ranges)
    print(f"res2/res1: {res2}/{res1} {res1==res2}")

