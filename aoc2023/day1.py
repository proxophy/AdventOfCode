import re

class Day1:
    def __init__(self, inp: str) -> None:
        self.lines = inp.split("\n")
        pass

    def get_cal_value(self, l):
        l = re.sub("[a-z]", "", l)
        return int(l[0] + l[-1])

    def task1(self):
        return sum(self.get_cal_value(l) for l in self.lines)

    def task2(self):
        def replace_spelledout(line):
            dmap = {"one": 1, "two": 2, "three": 3, "four": 4, "five": 5, "six": 6,
                    "seven": 7, "eight": 8, "nine": 9, "zero":0}
            lds = list(dmap.keys())
            nums = []
            i = 0
            while i < len(line):
                d = [line[i:].startswith(ld) for ld in lds]
                if any(d):
                    numi = d.index(True)
                    dstr = lds[numi]
                    nums.append(str(dmap[dstr]))
                else:
                    if line[i].isdigit():
                        nums.append(line[i])
                i += 1
            return "".join(nums)
        
        res = 0
        for l in self.lines:
            res += self.get_cal_value(replace_spelledout(l))

        return res
