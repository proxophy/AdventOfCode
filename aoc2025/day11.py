def rec(dp, in_edges, start, u) -> int:
    if u == start:
        dp[u] = 1
        return 1
    elif len(in_edges[u]) == 0:
        dp[u] = 0
        return 0
    if dp[u] != -1:
        return dp[u]

    res = 0
    for v in in_edges[u]:
        res += rec(dp, in_edges, start, v)
    dp[u] = res
    return res


def part1(content, start="you", target="out"):
    nodes = list(map(lambda x: x[0], content))
    assert "out" not in nodes
    nodes += ["out"]

    in_edges = {u: [] for u in nodes}
    for x, N in content:
        for y in N:
            in_edges[y].append(x)
    dp = {u: -1 for u in nodes}
    res = rec(dp, in_edges, start, target)
    return res


def part2(content):
    svr_fft = part1(content, "svr", "fft")
    fft_dac = part1(content, "fft", "dac")
    dac_out = part1(content, "dac", "out")

    svr_dac = part1(content, "svr", "dac")
    dac_fft = part1(content, "dac", "fft")
    fft_out = part1(content, "fft", "out")
    return svr_fft*fft_dac*dac_out + svr_dac*dac_fft*fft_out


if __name__ == "__main__":
    test = 0
    day = 11
    fn = f"./inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()

    content = map(lambda x: x.split(":"), content.split("\n"))
    content = [(k, v.split()) for k, v in content]

    # print(part1(content, start="svr", target="out"))
    print(part2(content))
