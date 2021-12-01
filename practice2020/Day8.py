def task1(lines):
    moves = [parseline(l) for l in lines]
    n = len(moves)
    visited = [False]*n
    acc = 0
    curr = 0
    while not visited[curr]:
        visited[curr] = True
        curr_m = moves[curr]
        # execute move
        if curr_m[0] == "nop":
            curr += 1
        elif curr_m[0] == "acc":
            acc += curr_m[1]
            curr += 1
        elif curr_m[0] == "jmp":
            curr += curr_m[1]

    return acc

# "op imm" -> [op, imm]
def parseline(line):
    move, imm_s = line.split()
    abs = int(imm_s[1:])
    if imm_s[0] == "+":
        imm = abs
    else:
        imm = -abs
    return [move, imm]

def task2(lines):
    moves = [parseline(l) for l in lines]
    # change nops to jmp
    for m in moves:
        if m[0] == "nop":
            m[0] = "jmp"
            loop, acc = simulate_moves(moves)
            if not loop:
                return acc
            m[0] = "nop"
    # change jmp to nop
    for m in moves:
        if m[0] == "jmp":
            m[0] = "nop"
            loop, acc = simulate_moves(moves)
            if not loop:
                return acc
            m[0] = "jmp"
    return 0

# see if it loops and return acc
def simulate_moves(moves):
    n = len(moves)
    visited = [False]*n
    acc = 0
    curr = 0
    loop = False
    while 0 <= curr < n:
        if visited[curr]:
            loop = True
            break

        visited[curr] = True
        curr_m = moves[curr]
        # execute move
        if curr_m[0] == "nop":
            curr += 1
        elif curr_m[0] == "acc":
            acc += curr_m[1]
            curr += 1
        elif curr_m[0] == "jmp":
            curr += curr_m[1]

    return loop, acc

if __name__ == "__main__":
     with open("src/input", "r+") as file1:
        lines = file1.read().split("\n")
        print(task2(lines))