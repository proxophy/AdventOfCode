from itertools import product
import math
import numpy as np
from scipy.optimize import linprog


def pressbutton(s, button) -> str:
    res: list[str] = list(s)
    for b in button:
        if s[b] == "#":
            res[b] = "."
        else:
            res[b] = "#"
    sres: str = "".join(res)
    return sres



def pprint(tab):
    for l in tab:
        print(" ".join(map(lambda s: (str(s)).rjust(2), l)))


def rec(dp, trans, cnum, tnum) -> int:
    numcombs, numbuttons = len(trans), len(trans[0])
    # basecase
    if cnum == 0:
        dp[tnum][cnum] = 1
        return 1
    elif tnum == 0:
        dp[tnum][cnum] = 0
        return 0

    if dp[tnum][cnum] != -1:
        return dp[tnum][cnum]

    res = 0
    # diagram that we get by pressing button
    for newc in trans[cnum]:
        res = max(res, rec(dp, trans, newc, tnum-1))

    dp[tnum][cnum] = res
    return res


def solvemachine(machine):
    diagram, buttons, _ = machine
    combs = ["".join(s) for s in product(".#", repeat=len(diagram))]
    trans = {(combs.index(c), buttons.index(b)): combs.index(pressbutton(c, b))
             for c, b in product(combs, buttons)}
    trans = [[combs.index(pressbutton(combs[c], buttons[b])) for b in range(len(buttons))]
             for c in range(len(combs))]

    nb = len(buttons)
    dp = [[-1 for i in range(len(combs))] for t in range(nb+1)]
    ti = combs.index(diagram)  # index of target

    for t in range(nb+1):
        res = rec(dp, trans, ti, t)
        if res > 0:
            return t
    assert False
    return 0


def part1(machines):
    res = 0
    for m in machines:
        res += solvemachine(m)
    return res

def solvemachine2(machine):
    _ , buttons, joltage = machine
    nb = len(buttons)
    n = len(joltage)
    A = np.zeros(shape=(n,nb))
    for i, button in enumerate(buttons):
        for bi in button:
            A[bi, i]  = 1
    b = np.array(list(joltage))
    c = np.ones((nb,))
    res = linprog(c, A_eq = A, b_eq = b, integrality=1)
    
    return int(res.x.sum())

def part2(content):
    res = 0
    for m in machines:
        res += solvemachine2(m)
        # break
    return res



def readline(line):
    line = line.split(" ")
    diagram = line[0][1:-1]
    buttons = [eval(el) for el in line[1:-1]]
    buttons = [((b,) if isinstance(b, int) else b) for b in buttons]
    joltage = eval("(" + line[-1][1:-1] + ")")

    return diagram, buttons, joltage


if __name__ == "__main__":
    test = 0
    day = 10
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    machines = [readline(l) for l in content.split("\n")]

    # print(part1(machines))
    print(part2(content))
