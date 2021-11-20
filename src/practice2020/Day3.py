def transform(lls):
    grid = []
    i = 0
    for l in lls:
        cg = [s == "#" for s in l]
        grid.append(cg)
        i += 1
    return grid

def prettyprint (grid):
    ng = []
    for g in grid:
        cl = []
        for s in g:
            if s: cl.append(1)
            else: cl.append(0)
        ng.append(cl) 

def count (lines, dx,dy):
    c = 0
    x = 0
    y = 0
   
    while y < len(lines):
        if y != 0 and lines[y][x % len(lines[0])] == "#":
            c += 1
        y += dy
        x += dx
     
    return c

def allOptions(lines):
    res = 1
    res *= count (lines, 1, 1)
    res *= count (lines, 3, 1)
    res *= count (lines, 5, 1)
    res *= count (lines, 7, 1)
    res *= count (lines, 1, 2)
    return res

if __name__ == "__main__":
    with open("src/input", "r+") as file1:
        lines = file1.read().split("\n")
        print(allOptions(lines))