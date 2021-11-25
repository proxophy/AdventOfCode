from collections import Counter

def analysize_group_any(group):
    summarized = group.replace("\n", "")
    answers_set = set(summarized)
    return len(answers_set)

def sum_counts_any(groups):
    res = sum([analysize_group_any(group) for group in groups])
    return res

def analysize_group_every(group):
    members =  group.split("\n")
    if  len(members) == 1:
        return len(members[0])

    common = Counter(members[0])
    for i in range(1, len(members)):
        common = common & Counter(members[i])
        if len(common) == 0:
            return 0

    return len(common)

def sum_counts_every(groups):
    res = sum([analysize_group_every(group) for group in groups])
    return res

if __name__ == "__main__":
    with open("src/input", "r+") as file1:
        inputs = file1.read().split("\n\n")
        print(sum_counts_every(inputs))
  