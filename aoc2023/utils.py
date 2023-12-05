def parse_intlist(line:str):
    return [int(n) for n in line.split(" ") if len(n) > 0]