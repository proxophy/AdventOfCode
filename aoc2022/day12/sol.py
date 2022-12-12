import time
import numpy as np

class Heightmap:
    def __init__(self, lines):
        self.grid = np.array([[ord(el)-97 for el in l.replace("\n", "")]
                    for l in lines])
        self.m, self.n = len(self.grid), len(self.grid[0])
        self.startpos = self.endpos = (0, 0)
        for i in range(self.m):
            for j in range(self.n):
                if self.grid[i][j] == -14:
                    self.startpos = (i, j)
                    self.grid[i, j] = 0
                    continue
                elif self.grid[i, j] == -28:
                    self.endpos = (i, j)
                    self.grid[i, j] = 25
                    continue
        # edges for when you can max 1 up
        self.edgesu = {(i, j): [] for i in range(self.m) for j in range(self.n)}
        # edges for when you can go max 1 down
        self.edgesd = {(i, j): [] for i in range(self.m) for j in range(self.n)}
        for i in range(self.m):
            for j in range(self.n):
                cv = self.grid[i, j]
                if i > 0:
                    if self.grid[i-1, j]-cv <= 1:
                        self.edgesu[(i, j)].append((i-1, j))
                    if (self.grid[i-1, j]-cv)*(-1) <= 1:
                        self.edgesd[(i, j)].append((i-1, j))
                if i < self.m-1 :
                    if self.grid[i+1, j]-cv <= 1:
                        self.edgesu[(i, j)].append((i+1, j))
                    if (self.grid[i+1, j]-cv)*(-1) <= 1:
                        self.edgesd[(i, j)].append((i+1, j))
                if j > 0:
                    if self.grid[i, j-1]-cv <= 1:
                        self.edgesu[(i, j)].append((i, j-1))
                    if (self.grid[i, j-1]-cv)*(-1) <= 1:
                        self.edgesd[(i, j)].append((i, j-1))
                if j < self.n-1:
                    if self.grid[i, j+1]-cv <= 1:
                        self.edgesu[(i, j)].append((i, j+1))
                    if (self.grid[i, j+1]-cv)*(-1) <= 1:
                        self.edgesd[(i, j)].append((i, j+1))


    def bfs(self, start, up=True):
        usededges = self.edgesu if up else self.edgesd

        explored = np.array([[False for j in range(self.n)]
                            for i in range(self.m)], dtype=bool)
        q = [start]
        explored[start] = True

        distances = np.array([[0 for j in range(self.n)]for i in range(self.m)])
        while len(q) > 0:
            v = q.pop(0)
            for w in usededges[v]:
                if not explored[w]:
                    explored[w] = True
                    distances[w] = distances[v] + 1
                    q.append(w)
        return distances


    def part1(self):
        distances = self.bfs(self.startpos)
        return distances[self.endpos]

    def part2(self):
        distances = self.bfs(self.endpos, False)
        adistances = []
        for i in range(self.m):
            for j in range(self.n):
                if self.grid[i,j] == 0:
                    adistances.append(distances[(i,j)])

        adistances = list(filter(lambda x: x != 0, adistances))
        return min(adistances)


if __name__ == "__main__":
    with open("inp.txt", "r") as f:
        lines = f.readlines()

    start = time.time()
    heightmap = Heightmap(lines)
    res1 = heightmap.part1()
    res2 = heightmap.part2()
    end = time.time()

    print("Time", (end-start)*1000)
    print("Res 1: ", res1)
    print("Res 2: ", res2)
