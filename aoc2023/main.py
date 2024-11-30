from day10 import Day10

def readinput():
    with open("test_inp.txt") as f:
        tcontent = f.read()

    with open("inp.txt") as f:
        content = f.read()

    return tcontent, content


if __name__ == "__main__":
    tcontent, content = readinput()
    day = Day10(content)

    print(day.task1())
