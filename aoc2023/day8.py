import networkx as nx

class Day8:
    def __init__(self, inp: str) -> None:
        instr, nodes = inp.split("\n\n")
        self.instr = [(1 if d == "R" else 0) for d in instr]
        nodes = [l.split("=") for l in nodes.split("\n")]
        self.nodes = {l[0][:-1]:[l[1].split(",")[0][-3:], l[1].split(",")[1][1:4]] 
                 for l in nodes}
        pass

    def task1(self):
        curr :str= "AAA"
        i = 0
        res = 0
        while curr != "ZZZ":
            curr = self.nodes[curr][self.instr[i]]
            if i < len(self.instr) - 1 :
                i += 1
            else:
                i = 0
            res += 1
        return res
    
    def path(self, node):
        res = []
        curr = node
        for instr in self.instr:
            curr = self.nodes[curr][instr]
            res.append(curr)
        return res
    
    

    def task2(self):
        # no possible ends during sequence, only at end
        pathdict = {node:self.path(node)[-1] for node in self.nodes}
        print(pathdict)
        
        currnodes = [node for node in self.nodes if node[-1]=="A"]
        res = 0
        # while True:
        #     pathsz = [pathdictz[node] for node in currnodes]

        #     for j in range(len(self.instr)):
        #         # print([pathsz[i][j] for i in range(len(currnodes))])
        #         if all([pathsz[i][j] for i in range(len(currnodes))]):
        #             return res + j + 1
        #     res += len(self.instr)
        #     currnodes = [pathdict[node] for node in currnodes]
            # print(currnodes)
