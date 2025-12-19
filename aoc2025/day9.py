def part1(content):
    tiles = content
    res = 0
    for i in range(len(tiles)-1):
        for j in range(i+1, len(tiles)):
            ta, tb = tiles[i], tiles[j]
            area = (abs(ta[0]-tb[0])+1) * (abs(ta[1]-tb[1])+1)
            res = max(area, res)
    return res

def minmax(a, b):
    return min(a, b), max(a, b)

def line_intersect_triangle(corner1, corner2, linep1, linep2,) -> bool:
    c1x, c1y = corner1
    c2x, c2y = corner2
    minx, maxx = minmax(c1x, c2x)
    miny, maxy = minmax(c1y, c2y)
    l1x, l1y = linep1
    l2x, l2y = linep2
    
    # one line point is in rectangle
    if minx < l1x < maxx and miny < l1y < maxy:
        return True 
    elif minx < l2x < maxx and miny < l2y < maxy:
        return True 
   
    assert l1x == l2x or l1y == l2y
    horizontal = l1y == l2y
    if horizontal:
        l1x, l2x = minmax(l1x, l2x)
    else:
        l1y, l2y = minmax(l1y, l2y)
    
    if horizontal:
        if l1x <= minx and l2x <= minx:
            return False # line left of rectangle 
        elif l1x >= maxx and l2x >= maxx:
            return False # line right of rectangle 
        elif l1y <= miny or l1y >= maxy:
            return False # line upwards or downward of rectangle
        assert l1x <= minx and l2x >= maxx
        return  miny < l1y < maxy
    else:
        if l1y <= miny and l2y <= miny:
            return False # line upwards of rectangle 
        elif l1y >= maxy and l2y >= maxy:
            return False # line downwards of rectangle 
        elif l1x <= minx or l1x >= maxx:
            return False # line left or right of traingle
        assert l1y <= miny and l2y >= maxy
        return minx < l1x < maxx

def part2(tiles):
    nt = len(tiles)
    maxarea = 0
    for i in range(nt):
        for j in range(i+1, nt):
            area = 0
            (t1x, t1y), (t2x, t2y) = tiles[i], tiles[j]
            if t1x == t2x:
                area = abs(t1y - t2y) + 1
            elif t1y == t2y:
                area = abs(t2x - t1x) + 1
            else:
                possible = 1
                for u in range(nt):
                    v = (u+1) % nt
                    if u == i or u == j or v == i or v == j:
                        continue
                    if line_intersect_triangle(tiles[i], tiles[j], tiles[u], tiles[v]):
                        possible = 0
                        break
                area = possible * (abs(t2x - t1x) + 1) * (abs(t1y - t2y) + 1) 
            maxarea = max(maxarea, area)
    return maxarea


if __name__ == "__main__":
    test = 0
    day = 9
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    tiles = [tuple(int(el) for el in l.split(","))
             for l in content.split("\n")]

    # print(part1(tiles))
    print("part 2:", part2(tiles))
