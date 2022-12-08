import numpy as np

if __name__ == "__main__":
    with open("inp.txt", "r") as f:
        lines = f.readlines()

    grid = np.array([list(map(int, l.replace("\n", ""))) for l in lines])
    n = len(grid)
    visible = np.array([[i*j == 0 or i == n-1 or j == n-1 for j in range(n)]
               for i in range(n)], dtype = bool)

    isvisible = lambda arr, val : all(x < val for x in arr)

    def visiblecount(arr, height):
        n = len(arr)
        if n == 0:
            return 0
        else:
            i = 0
            count = 0
            while ( i <n):
                if arr[i] >= height:
                    count += 1
                    break
                count += 1
                i += 1
            return count

    maxscenic = 0

    for i in range(1, n-1):
        for j in range(1, n-1):
            height = grid[i][j]
            left = grid[i,:j]
            right = grid[i,j+1:]
            top = grid[:i,j]
            down = grid[i+1:,j]

            visible[i][j] = (isvisible(left, height) or
                       isvisible(right, height) or
                       isvisible(top, height) or
                       isvisible(down, height))

            visiblecounts = [visiblecount(left[::-1],height), visiblecount(right,height),
                         visiblecount(top[::-1],height), visiblecount(down,height)]

            currscenic =  np.product(visiblecounts)
            maxscenic = max(maxscenic, currscenic)


    res1 = np.count_nonzero(visible)
    res2 = maxscenic

    print("Res 1:", res1)
    print("Res 2:", res2)
