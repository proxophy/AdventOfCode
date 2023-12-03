from day1 import Day1
from day2 import Day2
from day3 import Day3

def readinput():
    with open("test_inp.txt") as f:
        tcontent = f.read()

    with open("inp.txt") as f:
        content = f.read()

    return tcontent, content


if __name__ == "__main__":
    tcontent, content = readinput()
    day = Day3(content)

    print(day.task2())
