from utils import parse_intlist


class Day6:
    def __init__(self, inp: str) -> None:
        tstr, dstr = inp.split("\n")
        self.times = parse_intlist(tstr.split(":")[1])
        self.distances = parse_intlist(dstr.split(":")[1])

        pass

    def task1(self):
        res = 1
        for t, d in zip(self.times, self.distances):
            options = [st*(t-st) for st in range(t+1)]
            res *= len(list(filter(lambda e: e > d, options)))

        return res

    def task2(self):
        newt = int("".join([str(t) for t in self.times]))
        newd = int("".join([str(d) for d in self.distances]))

        i = 1
        while i < newt:
            if i*(newt-i) >= newd:
                break
            i += 1

        return newt-i - i + 1
