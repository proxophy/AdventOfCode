import re
import numpy as np

if __name__ == "__main__":
    with open("test_inp.txt", "r") as f:
        content = f.read()

    amountpattern = "[\d]"
    amount = max([int(i) for i in re.findall(amountpattern, content)])
    print(amount)

    cratepattern = "(    |\[[A-Z]\] |   |\[[A-Z]\])"*amount
    cratelines = re.finditer(cratepattern, content)
    cratelines = [[el.replace("[", "").replace("]", "").replace(" ", "")
                   for el in l.groups()] for l in cratelines]

    cratecols = [[cratelines[j][i]
                  for j in range(len(cratelines))][::-1] for i in range(amount)]
    cratecols = list(map(lambda l: list(filter(lambda x:  x != "", l)), cratecols))

    movepattern = f"move ([\d]+) from ([\d]+) to ([\d]+)"
    moves = re.findall(movepattern, content)

    with open("test_inp2.txt","w") as f:
        f.write(f"{amount}\n")
        for col in cratecols:
            f.write(" ".join(col) + "\n")
        for move in moves:
            f.write(" ".join(move) + "\n")
