#include <bits/stdc++.h>

std::vector<std::string> readinput(const char* name) {
    std::ifstream myfile(name);
    std::vector<std::string> strelems;
    if (myfile.is_open()) {
        std::string myline;
        while (std::getline(myfile, myline)) {
            strelems.push_back(myline);
        }
    }
    return strelems;
}

int getval(char c) {
    int ascval = int(c);
    if (65 <= ascval && ascval <= 90) {
        return ascval - 64 + 26;
    } else if (97 <= ascval && ascval <= 122) {
        return ascval - 96;
    } else {
        return -1;
    }
}

int inline_overlapping(std::string line) {
    int n = line.length() / 2;
    std::vector<char> v1;
    std::vector<char> v2;

    // v1.insert(v1.begin(), std::begin(line1), std::end(line1));

    v1.insert(v1.begin(),std::begin(line), std::begin(line)+n);
    v2.insert(v2.begin(),std::begin(line)+n, std::begin(line)+2*n);

    std::sort(v1.begin(), v1.end());
    std::sort(v2.begin(), v2.end());

    std::vector<char> v_intersection;
    std::set_intersection(v1.begin(), v1.end(),
                          v2.begin(), v2.end(),
                          std::back_inserter(v_intersection));

    return getval(v_intersection[0]);
}

int part1(std::vector<std::string> lines) {
    int sum = 0;
    for (std::string line : lines) {
        sum += inline_overlapping(line);
    }
    return sum;
}

int three_overlapping(std::string line1, std::string line2, std::string line3) {
    std::vector<char> v1, v2, v3;
    v1.insert(v1.begin(), std::begin(line1), std::end(line1));
    v2.insert(v2.begin(), std::begin(line2), std::end(line2));
    v3.insert(v3.begin(), std::begin(line3), std::end(line3));

    std::sort(v1.begin(), v1.end());
    std::sort(v2.begin(), v2.end());
    std::sort(v3.begin(), v3.end());

    std::vector<char> v1v2intersect;
    std::set_intersection(v1.begin(), v1.end(),
                          v2.begin(), v2.end(),
                          std::back_inserter(v1v2intersect));

    std::vector<char> allintersect;
    std::set_intersection(v1v2intersect.begin(), v1v2intersect.end(),
                          v3.begin(), v3.end(),
                          std::back_inserter(allintersect));

    return getval(allintersect[0]);
}

int part2(std::vector<std::string> lines) {
    int sum = 0;

    for (int i = 0; i < lines.size(); i += 3) {
        sum += three_overlapping(lines[i], lines[i + 1], lines[i + 2]);
    }

    return sum;
}

int main() {
    using std::chrono::duration;
    using std::chrono::duration_cast;
    using std::chrono::high_resolution_clock;
    using std::chrono::microseconds;

    
    std::vector<std::string> lines = readinput("inp.txt");
    
    int part1res = part1(lines);
    auto t1 = high_resolution_clock::now();
    int part2res = part2(lines);
    auto t2 = high_resolution_clock::now();

    auto ms_int = duration_cast<microseconds>(t2 - t1);

    std::cout << "Part 1: " << part1res << "\n";
    std::cout << "Part 2: " << part2res << "\n";
    std::cout << ms_int.count() << " microseconds\n";
}