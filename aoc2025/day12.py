def presentarea(present):
    present = "".join(present)
    res = [(p == "#") for p in present]
    return sum(res)

def shortcheck(presents, wl):
    (w, l), nums = wl
    areaneeded = 0
    pareas = list(map(presentarea, presents))
    for p, n in zip(pareas, nums):
        areaneeded += n*p
    return areaneeded <= w*l

def part1(presents, wishlist):
    res = 0
    for wl in wishlist:
        if  shortcheck(presents, wl):
            res += 1
    return res


def part2(content):
    raise NotImplementedError()

def parseWishlist(wl):
    area, nums = wl.split(":") 
    area = tuple(map(int, area.split("x")))
    nums = list(map(int, nums.split()))
    return area, nums

if __name__ == "__main__":
    test = 0
    day = 12
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
        
    content = content.split("\n\n")
    presents = content[:-1]
    presents = [p[3:].split("\n") for p in presents]
    wishlist = content[-1]
    wishlist = [parseWishlist(wl) for wl in wishlist.split("\n")]
    
    
    print(part1(presents, wishlist))
    # print(part2(content))
