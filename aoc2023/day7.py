from collections import Counter
from functools import cmp_to_key


class Day7:
    def __init__(self, inp: str) -> None:
        self.cards = [(line.split(" ")[0], int(line.split(" ")[1]))
                      for line in inp.split("\n")]
        pass

    def get_type(self, hand, joker):
        counter = Counter(hand)
        lenc = len(counter)
        amounts = set(counter.values())

        joker = joker and "J" in hand

        if lenc == 1:
            return 7
        elif lenc == 2 and amounts == {1, 4}:
            if joker:
                return 7
            return 6
        elif lenc == 2 and amounts == {2, 3}:
            if joker:
                return 7
            return 5
        elif lenc == 3 and amounts == {1, 3}:
            if joker and counter["J"] == 1:
                return 6
            return 4
        elif lenc == 3 and amounts == {1, 2}:
            if joker and counter["J"] == 2:
                return 6
            elif joker and counter["J"] == 1:
                return 5
            return 3
        elif lenc == 4:
            if joker:
                return 4
            return 2
        else:
            if joker:
                return 2
            return 1


    def compare_cards(self, hand1, hand2, joker:bool) -> int:
        t1, t2 = self.get_type(hand1, joker), self.get_type(hand2, joker)
        if t1 < t2:
            return -1
        elif t1 > t2:
            return 1

        if joker:
            labels = ["A", "K", "Q", "T", "9", "8",
                      "7", "6", "5", "4", "3", "2", "J"]
        else:
            labels = ["A", "K", "Q", "J", "T", "9",
                      "8", "7", "6", "5", "4", "3", "2"]
            
        for c1, c2 in zip(hand1, hand2):
            v1, v2 = labels.index(c1), labels.index(c2)
            if v1 < v2:
                return 1
            elif v1 > v2:
                return -1

        return 0

    def getwinnings(self, joker: bool):
        self.cards.sort(
            key=cmp_to_key(lambda c1, c2: self.compare_cards(c1[0], c2[0], joker))
        )
        res = 0
        for i, (_, val) in enumerate(self.cards):
            res += (i+1)*val
        return res

    def task1(self):
        return self.getwinnings(False)

    def task2(self):
        return self.getwinnings(True)
