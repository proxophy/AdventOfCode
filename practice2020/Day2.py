def checkLine (line):
    nums, c, word = line.split(" ")
    # extract min and max
    min, max = nums.split("-")
    min = int(min)
    max = int(max)
    # extract character
    c = c[0]
    # count character in word
    count = word.count(c)
    return count >= min and count <= max

def countValid (lls):
    i = 0
    for l in lls:
        if checkLine(l): i += 1
    return i

def checkLine2 (line):
    nums, c, word = line.split(" ")
    # extract min and max
    pos1, pos2 = nums.split("-")
    pos1 = int(pos1)
    pos2 = int(pos2)
    # extract character
    c = c[0]
    return (word[pos1-1] == c) ^ (word[pos2-1] == c) 

def countValid2 (lls):
    i = 0
    for l in lls:
        if checkLine2(l): i += 1
    return i

if __name__ == "__main__":
    with open("src/input", "r+") as file1:
        lines = file1.read().split("\n")
        print (countValid2(lines))


    