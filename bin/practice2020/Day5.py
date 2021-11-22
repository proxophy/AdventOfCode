def transform(inp):
    row = inp[0:7]
    col = inp[7:]
    row = row.replace("F", "0")
    row = row.replace("B", "1")
    col = col.replace("L", "0")
    col = col.replace("R", "1")
    r = int(row, 2)
    c = int(col, 2)
    id = r*8 + c
    return id

def findMax(inps):
    tr_inps = [transform(inp) for inp in inps]
    return max(tr_inps)

def findSeat(inps):
    tr_inps = [transform(inp) for inp in inps]
    srt_inps = sorted(tr_inps)
    for i in range(len(srt_inps)-1):
        if srt_inps[i] + 2 == srt_inps[i+1]:
            return srt_inps[i] + 1
    return -1

if __name__ == "__main__":
    with open("src/input", "r+") as file1:
        inputs = file1.read().split("\n")
        print(findSeat(inputs))