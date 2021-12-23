def task1(lines):
    nums = [int(l) for l in lines]
    nums.append(0)
    nums.sort()
    onediff = 0
    threediff = 1
    for i in range(len(nums)-1):
        if nums[i+1] - nums[i] == 1:
            onediff += 1
        elif nums[i+1] - nums[i] == 3:
            threediff += 1

    return onediff * threediff

def task2(lines):
    nums = [int(l) for l in lines]
    nums.append(0)
    nums.sort()
    return dp_version(nums)

# dp
def dp_version(nums):
    n = len(nums)
    dp = [0]*n
    dp[0] = 1
    for i in range(1,n):
        if i >= 1 and nums[i] - nums[i-1] <= 3:
            dp[i] += dp[i-1]
        if i >= 2 and nums[i] - nums[i-2] <= 3:
            dp[i] += dp[i-2]
        if i >= 3 and nums[i] - nums[i-3] <= 3:
            dp[i] += dp[i-3]
    return dp[n-1]

# recursion
# way to slow, should have known that as a CS student
def arrways(nums, i):
    res = 0
    if i >= len(nums)-1:
        return 1
    if i < len(nums) - 1 and nums[i+1] - nums[i] <= 3:
        res += arrways(nums, i+1)
    if i < len(nums) - 2 and nums[i+2] - nums[i] <= 3:
        res += arrways(nums, i+2)
    if i < len(nums) - 3 and nums[i+3] - nums[i] <= 3:
        res += arrways(nums, i+3)
    return res

if __name__ == "__main__":
     with open("input", "r+") as file1:
        lines = file1.read().split("\n")
        print(task2(lines))
        # print(task2(lines, 25))