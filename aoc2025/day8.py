from collections import Counter

class uf_ds:
    parent_node = {}
    sizes = {}

    def make_set(self, u):
        for i in u:
            self.parent_node[i] = i
            self.sizes[i] = 1

    def find(self, k):
        root = k
        while self.parent_node[root] != root:
            root = self.parent_node[root]
        return root

    def union(self, a, b):
        x = self.find(a)
        y = self.find(b)
        if x == y:
            return

        if self.sizes[x] < self.sizes[y]:
            x, y = y, x

        self.parent_node[y] = x
        self.sizes[x] = self.sizes[x] + self.sizes[y]


def squareddist(boxa, boxb):
    return (boxa[0]-boxb[0])**2 + (boxa[1]-boxb[1])**2 + (boxa[2]-boxb[2])**2


def part1(boxes):
    nodes = list(range(len(boxes)))
    edges = [(i, j, squareddist(boxes[i], boxes[j]))
             for i in range(len(boxes)) for j in range(i+1, len(boxes))]
    edges.sort(key= lambda x: x[2])
    uf = uf_ds()
    uf.make_set(nodes)
    for i, j, _ in edges[:1000]:
        uf.union(i,j)
    
    comp = []
    for node in nodes:
        # print(node, uf.find(node))
        comp.append(uf.find(node))
    compsizes = list(Counter(comp).values())
    compsizes.sort(reverse=True)
    return compsizes[0] * compsizes[1] * compsizes[2]
    # raise NotImplementedError()


def part2(boxes):
    nodes = list(range(len(boxes)))
    edges = [(i, j, squareddist(boxes[i], boxes[j]))
             for i in range(len(boxes)) for j in range(i+1, len(boxes))]
    edges.sort(key= lambda x: x[2])
    uf = uf_ds()
    uf.make_set(nodes)
    edge_num = 0
    for i, j, _ in edges:
        if uf.find(i) != uf.find(j):
            uf.union(i,j)
            edge_num += 1
            # we have MST :)
            if edge_num == len(nodes) - 1:
                print(i, j)
                print(boxes[i], boxes[j])
                return boxes[i][0] * boxes[j][0]


if __name__ == "__main__":
    test = 0
    day = 8
    fn = f"inputs/{"example" if test else "day"}{day}.txt"
    with open(fn) as f:
        content = f.read()
    boxes = [list(map(int, l.split(","))) for l in content.split("\n")]
    # print(part1(boxes))
    print(part2(boxes))
