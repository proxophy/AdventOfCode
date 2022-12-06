#include <bits/stdc++.h>

std::vector<char> readInput(const char* name) {
    std::ifstream myfile(name);
    std::vector<char> res;
    char curr;
    while (myfile >> curr) {
        res.push_back(curr);
    }
    return res;
}

int firstuniquesubloop(std::vector<char> line, int k) {
    int n = line.size();
    if (n < k) {
        return -1;
    }

    for (int i = 0; i < n - (k - 1); i++) {
        bool unique = true;
        for (int p = 0; p < k - 1; p++) {
            for (int q = p + 1; q < k; q++) {
                unique = unique && (line[i + p] != line[i + q]);
            }
            if (!unique) {
                break;
            }
        }
        if (unique) {
            return i + k;
        }
    }
    return 0;
}

int firstuniquesubset(std::vector<char> line, int k) {
    int n = line.size();
    if (n < k) {
        return -1;
    }

    for (int i = 0; i < n - (k - 1); i++) {
        std::set<char> curr;
        for (int j = 0; j < k; j++){
            curr.insert(line[i+j]);
        }
        if (curr.size() == k){
            return i+k;
        }
    }
    return 0;
}

int main() {
    using std::chrono::duration;
    using std::chrono::duration_cast;
    using std::chrono::high_resolution_clock;
    using std::chrono::microseconds;

    std::vector<char> inputline = readInput("inp.txt");

    std::cout << "\n";

    auto t1 = high_resolution_clock::now();
    int part1res = firstuniquesubset(inputline, 4);
    int part2res = firstuniquesubset(inputline, 14);
    auto t2 = high_resolution_clock::now();

    auto ms_int = duration_cast<microseconds>(t2 - t1);

    std::cout << "Part 1: " << part1res << "\n";
    std::cout << "Part 2: " << part2res << "\n";
    std::cout << ms_int.count() << " microseconds\n";
}