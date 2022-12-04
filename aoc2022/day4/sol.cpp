#include <bits/stdc++.h>

struct Pair {
    int s1, e1, s2, e2;

    friend std::istream &operator>>(std::istream &stream, Pair &data) {
        std::string line;

        if (std::getline(stream, line)) {
            // making reading input simpler, credits to ahm3t on d-infk discord
            std::replace(line.begin(), line.end(), ',', ' ');
            std::replace(line.begin(), line.end(), '-', ' ');
            std::stringstream iss(line);
            std::string s1, e1, s2, e2;
            if (!(iss >> data.s1 >> data.e1 >> data.s2 >> data.e2)) {
                stream.setstate(std::ios::failbit);
            }
        }
        return stream;
    }

    friend std::ostream &operator<<(std::ostream &stream, Pair &data) {
        stream << data.s1 << " " << data.e1 << " " << data.s2 << " " << data.e2;
        return stream;
    }

    bool fullycontaining() {
        return (s1 <= s2 && s2 <= e2 && e2 <= e1) || (s2 <= s1 && s1 <= e1 && e1 <= e2);
    }

    bool overlapping() {
        return (s1 <= s2 && s2 <= e1 && e1 <= e2) || (s2 <= s1 && s1 <= e2 && e2 <= e1) || fullycontaining();
    }
};

std::vector<Pair> readpairs(const char *name) {
    std::ifstream myfile(name);
    std::vector<Pair> pairelems;
    if (myfile.is_open()) {
        Pair curr;
        while (myfile >> curr) {
            pairelems.push_back(curr);
        }
    }
    return pairelems;
}

int part1(std::vector<Pair> pairs) {
    int count = 0;
    for (Pair p : pairs) {
        if (p.fullycontaining()) {
            count += 1;
        }
    }
    return count;

    // takes longer for some reason
    // return std::count_if(pairs.begin(), pairs.end(),
    //                      [](Pair p) { return p.fullycontaining(); });
}

int part2(std::vector<Pair> pairs) {
    int count = 0;
    for (Pair p : pairs) {
        if (p.overlapping()) {
            count += 1;
        }
    }
    return count;
}

int main() {
    using std::chrono::duration;
    using std::chrono::duration_cast;
    using std::chrono::high_resolution_clock;
    using std::chrono::microseconds;

    std::vector<Pair> pairs = readpairs("inp.txt");

    auto t1 = high_resolution_clock::now();
    int part1res = part1(pairs);
    int part2res = part2(pairs);
    auto t2 = high_resolution_clock::now();

    auto ms_int = duration_cast<microseconds>(t2 - t1);

    std::cout << "Part 1: " << part1res << "\n";
    std::cout << "Part 2: " << part2res << "\n";
    std::cout << ms_int.count() << " microseconds\n";
}