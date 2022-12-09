def movedir(pos, dir):
        x, y = pos
        nx = x+1 if dir == "R" else (x-1 if dir == "L" else x)
        ny = y+1 if dir == "U" else (y-1 if dir == "D" else y)
        return (nx,ny)

def adjust(spos, tpos):
    sx, sy = spos
    tx, ty = tpos
    # credits to vpyxl on discord for short if statement
    if abs(sx-tx) > 1 or abs(sy-ty)>1:
        if sx > tx:
            tx += 1
        elif sx < tx:
            tx -= 1
        if sy > ty:
            ty +=1
        elif sy < ty:
            ty -= 1

    return (tx, ty)

def part1(moves):
    poshead = (0, 0)
    postail = (0, 0)
    tailposset = {postail}

    for m in moves:
        dir, amount = m
        for _ in range(amount):
            poshead = movedir(poshead, dir)
            postail = adjust(poshead, postail)
            tailposset.add(postail)
    return len(tailposset)

def part2(moves):
    ropepos = [(0, 0)]*10
    tailposset = {(0, 0)}
    for m in moves:
        dir, amount = m
        for _ in range(amount):
            ropepos[0] = movedir(ropepos[0], dir)
            for i in range(1, 10):
                ropepos[i] = adjust(ropepos[i-1], ropepos[i])
            tailposset.add(ropepos[9])
    return len(tailposset)

def parsemove(x): return (x.split(" ")[0],  int(x.split(" ")[1]))

if __name__ == "__main__":
    with open("inp.txt", "r") as f:
        lines = f.readlines()

    moves = [parsemove(l) for l in lines]

    res1 = part1(moves)
    res2 = part2(moves)
    print("Res 1:", res1)
    print("Res 2:", res2)
