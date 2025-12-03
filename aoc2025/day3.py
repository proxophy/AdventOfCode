from itertools import combinations

def getjoltage(line):
    res = 0
    for i in range(len(line)-1):
        for j in range(i+1, len(line)):
            res = max(line[i]*10 + line[j], res)
    return res

def part1(content):
    
    res = 0
    for l in content:
        res += getjoltage(l)
    
    return res

def getjoltage12(l):
    rl = []
    last = -11
    for i in range(12):
        if last < 0:
            c = max(l[:last])
            ix = l[:last].index(c)
            l = l[(ix+1):]
        else:
            c = max(l)
        
        rl.append(c)
        last += 1
        
    res = int("".join(map(str,rl)))
    print(res)
    return res

def part2(content):
    res = 0
    for l in content:
        res += getjoltage12(l)
    return res

if __name__ == "__main__":
    test = 0
    day = 3
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()  
    content = content.split("\n")
    content = [[int(el) for el in l] for l in content]

    # print(part1(content))
    print(part2(content))
