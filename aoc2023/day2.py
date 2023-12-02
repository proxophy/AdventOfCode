from math import prod


class Day2:
    def __init__(self, inp: str) -> None:
        self.games = [self.parse_game(l) for l in inp.split("\n")]
        pass

    def parse_game(self, line):
        # could have done regex but whatever
        def parse_subgame(sg):
            draws = [d[1:].split(" ") for d in sg.split(",")]
            return {col: int(num) for num, col in draws}

        gs, cs = line.split(":")
        _, gi = gs.split(" ")
        gi = int(gi)
        subgames = [parse_subgame(sg) for sg in cs.split(";")]
        return gi, subgames

    def game_max(self, game):
        rm, gm, bm = 0, 0, 0
        for subgame in game:
            rm = max(rm, subgame.get("red", 0))
            gm = max(gm, subgame.get("green", 0))
            bm = max(bm, subgame.get("blue", 0))
        return rm, gm, bm

    def task1(self):
        res = sum([gi for gi, game in self.games
                   if all([col <= con for col, con in
                           zip(self.game_max(game),  [12, 13, 14])])])
        return res

    def task2(self):
        res = sum([prod(self.game_max(game)) for _, game in self.games])
        return res
