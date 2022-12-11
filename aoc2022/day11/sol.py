import re
import copy
import time

def parsecontent(content):
    pattern = "Monkey (\d):\n[ ]+Starting items:([\d, ]+)\n[ ]+Operation: new = ([^\n]+)"
    pattern += "\n[ ]+Test: divisible by ([\d]+)\n[ ]+If true: throw to monkey (\d)\n[ ]+If false: throw to monkey (\d)"
    iter = re.finditer(pattern, content)
    monkeys = []
    for m in iter:
        groups = m.groups()
        items = [int(el) for el in groups[1].split(", ")]
        op = groups[2]
        test = (int(groups[3]), int(groups[4]), int(groups[5]))
        monkeys.append({"items": items, "op": op,
                       "test": test, "inspected": 0})

    return monkeys


def round(monkeys):
    n = len(monkeys)
    for monk in monkeys:
        monk["inspected"] += len(monk["items"])
        while len(monk["items"]) > 0:
            initval = monk["items"].pop(0) 
            mod = monk["test"][0]
            # operation
            factor = initval if monk["op"][1] == "old" else int(monk["op"][1])
            initval = (initval*factor if monk["op"][0] == "*"
                    else initval+factor)
            # monkey gets bored
            initval = initval // 3
            
            # where to move
            if initval % mod == 0:
                monkeys[monk["test"][1]]["items"].append(initval)
            else:
                monkeys[monk["test"][2]]["items"].append(initval)
    return


def roundfaster(monkeys):
    n = len(monkeys)
    for j,monk in enumerate(monkeys):
        if j == 2: print(len(monk["items"]))
        monk["inspected"] += len(monk["items"])
        while len(monk["items"]) > 0:
            old = monk["items"].pop(0) 
            dividend = monk["test"][0]
            # operation
            # factor = val if monk["op"][1] == "old" else int(monk["op"][1])
            # val = (val*factor if monk["op"][0] == "*" else val+factor)
            new = eval(monk["op"])
            if new % dividend == 0:
                monkeys[monk["test"][1]]["items"].append(new%dividend)
            else:
                monkeys[monk["test"][2]]["items"].append(new%dividend)
    # print()
    return

def part1(monkeys):
    for i in range(20):
        inspcount = sorted([m["inspected"] for m in monkeys])
    
    return inspcount[-1]*inspcount[-2]


def part2(monkeys):
    for i in range(20):
        # print([m["items"] for m in monkeys])
        roundfaster(monkeys)
    inspcount =[m["inspected"] for m in monkeys]
        # print([m["items"] for m in monkeys])
    print(inspcount)
        # print()


    inspcount = sorted(inspcount)
    
    return inspcount[-1]*inspcount[-2]


if __name__ == "__main__":
    with open("test_inp.txt", "r") as f:
        content = f.read()

    monkeys = parsecontent(content)
    monkeys2 = copy.deepcopy(monkeys)
    # print(monkeys)
    # res1 = part1(monkeys)
    start = time.time()
    res2 = part2(monkeys2)
    end = time.time()
    # print("Time", (end-start)*1000)
    # print("Res 1: ", res1)
    print("Res 2: ", res2)
