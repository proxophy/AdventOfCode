class Day4:
    def __init__(self, inp: str) -> None:
        self.cards = [self.parse_game(l) for l in inp.split("\n")]

    def parse_game(self, l):
        _, strnums = l.split(":")
        strwin, strmynums = strnums.split("|")
        win = [int(el) for el in strwin.split(" ") if len(el) > 0]
        mynums = [int(el) for el in strmynums.split(" ") if len(el) > 0]
        return win, mynums

    def card_score(self, card):
        win, mynums = card
        return len(set(win) & set(mynums))

    def task1(self):
        res = 0
        for card in self.cards:
            score = self.card_score(card)
            if score > 0:
                res += 2**(score-1)
        return res

    def task2(self):
        amounts = [1 for i in range(len(self.cards))]
        for i, copies in enumerate(amounts):
            score = self.card_score(self.cards[i])
            for j in range(i+1, min(len(self.cards), i+1+score)):
                amounts[j] += copies
        return sum(amounts)
