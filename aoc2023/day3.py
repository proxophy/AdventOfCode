class Day3:
    def __init__(self, inp: str) -> None:
        self.field = inp.split("\n")
        self.m = len(self.field)
        self.n = len(self.field[0])
        self.symbols = [(i, j) for i in range(self.m) for j in range(self.n)
                        if self.field[i][j] != "." and not self.field[i][j].isdigit()]
        self.stars = [(i, j) for (i,j) in self.symbols if self.field[i][j] == "*"]
        self.numbers = [(i, x) for i in range(self.m) for x in self.get_numbers(i)]
        pass

    def get_numbers(self, ix: int):
        line = self.field[ix]
        lastnum: str = ""
        nums = []
        lastnumstart: int = 0
        for i, e in enumerate(line):
            if e.isdigit():
                lastnum += e
            else:
                if len(lastnum) > 0:
                    nums.append((lastnumstart, i-1, int(lastnum)))
                    lastnum = ""

                lastnumstart = i+1

        if len(lastnum) > 0:
            nums.append((lastnumstart, len(line)-1, int(lastnum)))

        return nums

    

    def task1(self):
        def number_adjacent_symbol(i, sj, ej):
            for k in range(max(0, i-1), min(self.m-1, i+1)+1):
                for l in range(max(0, sj-1), min(self.n-1, ej+1) + 1):
                    if (k, l) in self.symbols:
                        return True
            return False

        res = 0
        for i, (sj, ej, num) in self.numbers:
            if number_adjacent_symbol(i, sj, ej):
                res += num

        return res

    def task2(self):
        res = 0
        for stari, starj in self.stars:
            l = []
            for i, (sj, ej, num) in self.numbers:
                if i-1 <= stari <= i+1 and sj-1 <= starj <= ej+1:
                    l.append(num)
            if len(l) == 2:
                res += l[0]*l[1]

        return res
