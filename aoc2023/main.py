from day1 import Day1


def readinput():
    with open("test_inp.txt") as f:
        tcontent = f.read()

    with open("inp.txt") as f:
        content = f.read()

    return tcontent, content


if __name__ == "__main__":
    tcontent, content = readinput()
    day = Day1(tcontent)
    
    print(day.task1())