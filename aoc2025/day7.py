def rec(t, i, j, grid, dp):
    m = len(grid)
    n = len(grid[0])
    if t == 0:
        dp[t][i][j] = 1 if grid[i][j] == "S" else 0
        return dp[t][i][j]
    elif i == 0:
        # print("line 7")
        dp[t][i][j] = 1 if grid[i][j] == "S" else 0
        return dp[t][i][j]

    if dp[t][i][j] != -1:
        # print("line 15", t, i, j, ": ", dp[t][i][j])
        return dp[t][i][j]
    if i > 0:
        # beam going downwards
        if rec(t-1, i-1, j, grid, dp) == 1 and grid[i-1][j] != "^":
            # print("line 15")
            dp[t][i][j] = 1
            return 1
    if j > 0:
        # split
        if rec(t-1, i, j-1, grid, dp) == 1 and grid[i][j-1] == "^":
            # print("line 21")
            dp[t][i][j] = 1
            return 1
    # print(f"j: {j} {n-1}")
    if j < n - 1:
        if rec(t-1, i, j+1, grid, dp) == 1 and grid[i][j+1] == "^":
            # print("line 26")
            dp[t][i][j] = 1
            return 1
    if t > 1 and rec(t-1, i, j, grid, dp) == 1:
        # print("line 30")
        dp[t][i][j] = 1
        return 1
    # print(f"({t},{i},{j})")
    dp[t][i][j] = 0
    return 0


def printdp(t, dp):
    tab = dp[t]
    for l in tab:
        print(" ".join(map(lambda s: (str(s)).rjust(2), l)))


def part1(content):
    grid = content
    m = len(grid)
    n = len(grid[1])

    startpos = [i for i, el in enumerate(grid[0]) if el == "S"][0]
    dp = [[[(-1 if i > 0 else 0) for j in range(n)]
           for i in range(m)] for t in range(2*m)]
    for t in range(2*m):
        dp[t][0][startpos] = 1
    ts = 2*m - 1
    row = 2
    res = 0
    for i in range(m):
        for j in range(n):
            dpres = rec(ts, i, j, grid, dp)
            if grid[i][j] == "^" and dpres == 1:
                res += 1

    return res


def pprint(tab):
    for l in tab:
        print(" ".join(map(lambda s: (str(s)).rjust(2), l)))


def rec2(i, j, dp, grid):
    m = len(grid)
    n = len(grid[1])
    # base case
    if i == m-1:
        return 1
    if dp[i][j] != 0:
        return dp[i][j]
    if grid[i][j] == "." or grid[i][j] == "S":
        recres = rec2(i+1, j, dp, grid)
        dp[i][j] = recres
        return recres
    if grid[i][j] == "^":
        recres = rec2(i+1, j-1, dp, grid) + rec2(i+1, j+1, dp, grid)
        dp[i][j] = recres
        return recres
    dp[i][j] = -1
    return -1


def part2(content):
    grid = content
    m = len(grid)
    n = len(grid[1])
    dp = [[(1 if i == m-1 else 0) for j in range(n)] for i in range(m)]
    # pprint(grid)
    startpos = [i for i, el in enumerate(grid[0]) if el == "S"][0]
    res = rec2(0, startpos, dp, grid)
    # pprint(dp)
    return res


if __name__ == "__main__":
    test = 0
    day = 7
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    grid = content.split("\n")
    # print(part1(grid))
    print(part2(grid))
