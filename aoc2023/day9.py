from utils import parse_intlist

class Day9:
    def __init__(self, inp: str) -> None:
        self.histories = [parse_intlist(line) for line in inp.split("\n")]
        pass

    def compute_sequences(self, history):
        curr = history
        res = []
        while not all([x == 0 for x in curr]):
            res.append(curr)
            curr = [curr[i+1]-curr[i] for i in range(len(curr)-1)]
            
        return res

    def task1(self):
        res = 0
        for history in self.histories:
            sequences = self.compute_sequences(history)
            nextval = 0
            for seq in sequences:
                nextval += seq[-1]
            res += nextval
        return res

    def task2(self):
        res = 0
        for history in self.histories:
            sequences = self.compute_sequences(history)
            nextval = 0
            for seq in sequences[::-1]:
                nextval =  seq[0] - nextval
            res += nextval
        return res