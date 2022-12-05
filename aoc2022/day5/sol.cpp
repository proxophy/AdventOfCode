#include <bits/stdc++.h>

struct Throuple {
    int n;
    std::vector<std::vector<char>> c;
    std::vector<std::tuple<int, int, int>> moves;

    friend std::istream &operator>>(std::istream &stream, Throuple &data) {
        std::string line;

        if (std::getline(stream, line)) {
            std::stringstream iss(line);
            iss >> data.n;
        }
        for (int i = 0; i < data.n; i++) {
            if (std::getline(stream, line)) {
                std::stringstream iss(line);
                std::vector<char> currvec;
                char curr;
                while (iss >> curr) {
                    currvec.push_back(curr);
                }
                data.c.push_back(currvec);
            }
        }

        while (std::getline(stream, line)) {
            std::stringstream iss(line);
            std::tuple<int, int, int> currmove;
            if (!(iss >> std::get<0>(currmove) >> std::get<1>(currmove) >> std::get<2>(currmove))) {
                stream.setstate(std::ios::failbit);
            }
            data.moves.push_back(currmove);
        }

        return stream;
    }

    friend std::ostream &operator<<(std::ostream &stream, Throuple &data) {
        // stream << data.c[0].size() << " " << data.c[1].size() << " " << data.c[2].size();
        // for (std::tuple<int, int, int> m : data.moves) {
        //     stream << std::get<0>(m) << std::get<1>(m) << std::get<2>(m) << "\n";
        // }
        stream << data.n << "\n";
        for (std::vector<char> col : data.c) {
            stream << "col: ";
            for (char cha : col) {
                stream << cha << "-";
            }
            stream << "\n";
        }

        // for (std::tuple<int, int, int> m : data.moves) {
        //     int am = std::get<0>(m);
        //     int from = std::get<1>(m);
        //     int to = std::get<2>(m);
        //     stream << "move:" << am << " " << from << " " << to << "\n";
        // }

        return stream;
    }

    std::string part1() {
        int count = 1;
        for (std::tuple<int, int, int> m : moves) {
            int am = std::get<0>(m);
            int from = std::get<1>(m) - 1;
            int to = std::get<2>(m) - 1;

            if (from < 0 || from > n || to < 0 || to > n) {
                return "a";
            } else if (am > std::size(c[from])) {
                
                return "b";
            }
            for (int i = 0; i < am; i++) {
                char el = c[from].back();
                c[from].pop_back();
                c[to].push_back(el);
            }
            count++;
        }

        std::string res = "";
        for (std::vector<char> col : c) {
            res += col.back();
        }
        return res;
    }

    std::string part2() {
        int count = 1;
        for (std::tuple<int, int, int> m : moves) {
            int am = std::get<0>(m);
            int from = std::get<1>(m) - 1;
            int to = std::get<2>(m) - 1;
            if (from < 0 || from > n || to < 0 || to > n) {
                return "a";
            } else if (am > std::size(c[from])) {
                
                return "b";
            }

            std::vector<char> helper;
            for (int i = 0; i < am; i++) {
                char el = c[from].back();
               
                c[from].pop_back();
                helper.push_back(el);
            }

            for (int i = 0; i < am; i++) {
                char el = helper.back();
                helper.pop_back();
                c[to].push_back(el);
            }
            count++;
           
        }

        std::string res = "";
        for (std::vector<char> col : c) {
            res += col.back();
        }
        return res;
    }
};

int main() {
    using std::chrono::duration;
    using std::chrono::duration_cast;
    using std::chrono::high_resolution_clock;
    using std::chrono::microseconds;

    std::ifstream myfile("inp2.txt");
    Throuple data;
    myfile >> data;
    std::cout << data << "\n";
    auto t1 = high_resolution_clock::now();
    // std::string part1res = data.part1();
    std::string part2res = data.part2();
    auto t2 = high_resolution_clock::now();

    auto ms_int = duration_cast<microseconds>(t2 - t1);

    // std::cout << "Part 1: " << part1res << "\n";
    std::cout << "Part 2: " << part2res << "\n";
    std::cout << ms_int.count() << " microseconds\n";
}