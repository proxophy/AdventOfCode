def task1(lines, k):
    numbers = [int(l) for l in lines] 
    return check_elms(numbers, k)

def check_elms(numbers, k):
    curr = numbers[:k]
    i = k
    while i < len(numbers):
        if not sum_pos(curr, numbers[i]): 
            return numbers[i]
        curr.append(numbers[i])
        curr.pop(0)    
        i += 1

    return -1

def sum_pos(arr, el):
    for i in range(len(arr)-1):
        for j in range(i+1, len(arr)):
            if arr[i]+arr[j] == el:
                return True
    return False

def task2(lines, k):
    numbers = [int(l) for l in lines] 
    res = find_fault(numbers, k)
    num_red = numbers[:res]
    solarr = find_cont_subset(num_red, numbers[res])
    return min(solarr) + max(solarr)
    

def find_fault(numbers, k):
    curr = numbers[:k]
    i = k
    while i < len(numbers):
        if not sum_pos(curr, numbers[i]): 
            return i
        curr.append(numbers[i])
        curr.pop(0)    
        i += 1

    return -1

def find_cont_subset(arr, e):
    for i in range(len(arr)-1):
        sum = arr[i]
        for j in range(i+1, len(arr)):
            sum += arr[j]
            if sum == e:
                return arr[i:(j+1)]
            elif sum > e:
                break

    return 

if __name__ == "__main__":
     with open("src/input", "r+") as file1:
        lines = file1.read().split("\n")
        print(task2(lines, 25))