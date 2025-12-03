import re

def invalidid(num):
    snum = str(num)
    lennum = len(snum)
    hl = int(lennum/2)
    if lennum % 2 == 1:
        return False
    else:
        return snum[:hl] == snum[hl:]

def part1(content):
    res = 0
    for a, b in content:
        for i in range(a, b+1):
            if invalidid(i):
                res += i
    return res

def invalidid2(num):
    snum = str(num)
    pattern = re.compile(r'(\d+)\1+')
    matches = pattern.fullmatch(snum)
    return bool(matches)
    

def part2(content):
    # examples = [12, 112, 12341234, 123123123, 1212121212, 1111111]
    # print(list(map(invalidid2, examples)))
    res = 0
    for a, b in content:
        for i in range(a, b+1):
            if invalidid2(i):
                res += i
    return res

if __name__ == "__main__":
    test = 0
    day = 2
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()

    content = [[int(el2) for el2 in el.split("-")] for el in content.split(",")]
    # print(content)
    print(part1(content))
    print(part2(content))
