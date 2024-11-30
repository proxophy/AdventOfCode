class Day10:
    def __init__(self, inp: str) -> None:
        self.field = inp.split("\n")
        m, n = len(self.field), len(self.field[0])
        self.symbols = [(i, j) for i in range(m) for j in range(n)
                        if self.field[i][j] != "."]
        print(m,n)
        self.edges = {}
        self.si, self.sj = 0, 0
        for (i, j) in self.symbols:
            curr = []
            if self.field[i][j] == "|":
                curr = [(i+1, j), (i-1, j)]
            elif self.field[i][j] == "-":
                curr = [(i, j-1), (i, j+1)]
            elif self.field[i][j] == "L":
                curr = [(i-1, j), (i, j+1)]
            elif self.field[i][j] == "J":
                curr = [(i, j-1), (i-1, j)]
            elif self.field[i][j] == "7":
                curr = [(i, j-1), (i+1, j)]
            elif self.field[i][j] == "F":
                curr = [(i, j+1), (i+1, j)]
            elif self.field[i][j] == "S":
                self.si, self.sj = i, j
                curr = [(i-1,j),(i+1,j),(i,j-1),(i,j+1)]
            curr = [(ki, kj) for (ki, kj) in curr 
                    if 0 <= ki < m and 0 <= kj < n and self.field[ki][kj]!="."]
            self.edges[(i,j)] = curr
            # print((i, j), curr)

        for (i, j) in self.symbols:
            self.edges[(i,j)] = [(ki,kj)
                for (ki,kj) in self.edges[(i,j)]
                if (i,j) in self.edges[(ki,kj)]]
            # print(i,j, self.edges[(i,j)])

        # print(self.edges[(0,1)], self.edges[(0,2)])

        pass

    def bfs(self):
        distances = {(i,j):-1 for (i,j) in self.symbols}
        distances[(self.si,self.sj)] = 0
        queue = [(self.si,self.sj)]
        while len(queue):
            curr = queue.pop()
            currdist = distances[curr]
            for (i,j) in self.edges[curr]:
                if distances[(i,j)] >= 0:
                    continue
                else:
                    distances[(i,j)] = currdist + 1
                    queue.insert(0,(i,j))
        return distances


    def task1(self):
        distances = self.bfs()
        return max(distances.values())
