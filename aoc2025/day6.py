import numpy as np
import math

def part1(content):
    
    nums = [[int(e) for e in l.split()] for l in content[:-1]]
    ops = [e == "*" for e in content[-1].split()]
    nums = np.array(nums)
    res = 0
    for i, op in enumerate(ops):
        if op:
           res += nums[:, i].prod()
        else:
            res += nums[:, i].sum()
    return res


def part2(content):
    rows = content[:-1]
    opsl = content[-1]
    ops = [e == "*" for e in content[-1].split()]
    # find column
    colsidc = [i for i, el in enumerate(opsl) if el == "*" or el == "+"]
    colsidc.append(len(rows[0])+1)
    nums = []
    for i, col in enumerate(colsidc[:-1]):
        start = col
        end = colsidc[i+1] - 1 # exclusive
        colnums = []
        for c in range(start, end):
            snum = ""
            for row in rows:
                snum += row[c]
            colnums.append(int(snum))
        nums.append(colnums)
        pass
   
    res = 0
    for i,op in  enumerate(ops):
        if op:
            res += math.prod(nums[i])
        else:
            res += sum(nums[i])
    
    # raise NotImplementedError()
    return res

if __name__ == "__main__":
    test = 0
    day = 6
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    
    content = content.split("\n")
    print(part1(content))
    print(part2(content))
