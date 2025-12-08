def countrolls(k, l, grid):
    m, n = len(grid), len(grid[0])
    c = 0
    for i in range(max(k-1, 0), min(m-1, k+1)+1):
        for j in range(max(l-1, 0), min(n-1, l+1)+1):
            if i == k and j == l:
                continue
            elif grid[i][j]:
                c += 1
    # print(c)
    return c < 4


def part1(content, flag=False):
    grid = content
    m, n = len(grid), len(grid[0])
    res = 0
    # print(countrolls(1,1,grid))
    indices = []
    for k in range(m):
        for l in range(n):
            if grid[k][l] and countrolls(k, l, grid):
                res += 1
                indices.append((k, l))
    if flag:
        return res, indices
    else:
        return res


def part2(content):
    grid = content
    res = 0
    while True:
        res1, indices = part1(grid, True)
        if res1 == 0:
            break
        res += res1
        for k, l in indices:
            grid[k][l] = False
        print(res1)
    return res


if __name__ == "__main__":
    test = 0
    day = 4
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    content = [[el == "@" for el in l] for l in content.split("\n")]
    # print(content)
    # print(part1(content))
    print(part2(content))
