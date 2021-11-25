from igraph import Graph

def task1(lines):
    n = len(lines)
    tr_input = [read_line(line) for line in lines]

    myedges, myweights = get_weighted_edges(tr_input)

    g = Graph(n, edges = myedges, edge_attrs = {"weight": myweights}, directed=True)
    map = {read_line(lines[i])[0]:i  for i in range(len(lines))} 
    return len(g.neighborhood(map["shiny gold"], order = 10000000, mode = 'in'))-1

# prepare edges for graph
def get_weighted_edges(tr_input):
    #nodes are currently color of bags, transform to numbers
    map = {read_line(lines[i])[0]:i  for i in range(len(lines))} 
    edges = []
    weights = []
    for el in tr_input:
        if el[1] is None:
            continue

        ine = map[el[0]]

        for o in el[1]:
            edges.append([ine, map[o[0]]])
            weights.append(o[1])

    return edges, weights

# transform a line from the input into a more accesible format for the graph
def read_line(line):
    ib, out_bags = line.split(" bags contain ")

    if out_bags == "no other bags.":
        return (ib, None)

    out_bags = out_bags[:-1]
    ob_split = out_bags.split(", ")
    ob = [read_out_bag(expr) for expr in ob_split]
    return (ib, ob)

# make a tuple for a bags/bags contained in a bag
def read_out_bag(out_bags):
    words = out_bags.split()
    return (words[1] + " " + words[2], int(words[0]))


if __name__ == "__main__":
     with open("src/input_t", "r+") as file1:
        lines = file1.read().split("\n")
       
        print(task1(lines))