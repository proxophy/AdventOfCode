# Day 4
def transform(input):
    res = input.replace("\n", " ")
    res = res.split(" ")
    res = {r[0:3]: r[4:] for r in res} #make dictionnary
    
    return res

def checkPresent(i_dict):
    required = ['byr', 'iyr', 'eyr', 'hgt', 'hcl', 'ecl', 'pid']
    for r in required:
        if r not in i_dict:
            return False
    return True

def checkValid(i_dict):
    if not checkPresent(i_dict):
        return False

    res = vbyr(i_dict) and viyr(i_dict) and veyr(i_dict) and vhgt(i_dict) and vhcl(i_dict) and vecl(i_dict) and vpid(i_dict)  
    return res

def vbyr(i_dict):
    inp = i_dict["byr"]
    try: #is int?
        inp = int(inp)
    except ValueError:
        return False
    return 1920 <= inp <= 2002

def viyr(i_dict):
    inp = i_dict["iyr"]
    try: #is int?
        inp = int(inp)
    except ValueError:
        return False

    return 2010 <= inp <= 2020

def veyr(i_dict):
    inp = i_dict["eyr"]
    try: #is int?
        inp = int(inp)
    except ValueError:
        return False
    return 2020 <= inp <= 2030

def vhgt(i_dict):
    inp = i_dict["hgt"]
    if "cm" in inp:
        inp = inp[:-2]
        try: #is int?
            inp = int(inp)
        except ValueError:
            return False
        return 150 <= inp <= 193 
    else:
        inp = inp[:-2]
        try: #is int?
            inp = int(inp)
        except ValueError:
            return False
        return 59 <= inp <= 76

def vhcl(i_dict):
    inp = i_dict["hcl"]
    if len(inp) != 7 or inp[0] != "#":
        return False

    try: #ishex?
        int(inp[1:], 16)
        return True
    except ValueError:
        return False

def vecl(i_dict):
    inp = i_dict["ecl"]
    allowed =  ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]
    return inp in allowed

def vpid(i_dict):
    inp = i_dict["pid"]
    if len(inp) != 9:
        return False

    try: #is int?
        int(inp)
        return True
    except ValueError:
        return False

def countPresent (inputs):
    tr_inputs = [transform(inp) for  inp in inputs]
    i = 0
    for l in tr_inputs:
        if checkPresent(l): i += 1
    return i

def countValid (inputs):
    tr_inputs = [transform(inp) for  inp in inputs]
    i = 0
    for l in tr_inputs:
        if checkValid(l): i += 1
    return i

if __name__ == "__main__":
    with open("src/input", "r+") as file1:
        inputs = file1.read().split("\n\n")
        print(countValid(inputs))