// IO Stuff
#include <fstream>
#include <iostream>
#include <sstream>
// Strings
#include <string>
// Data structures
#include <map>
#include <tuple>
#include <vector>

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

int part1(std::vector<std::string> moves) {
    std::map<std::string, int> scores{
        {"A Z", 0 + 3},{"B X", 0 + 1},{"C Y", 0 + 2},
        {"A X", 3 + 1},{"B Y", 3 + 2},{"C Z", 3 + 3},
        {"A Y", 6 + 2},{"B Z", 6 + 3},{"C X", 6 + 1},
    };

    int score = 0;

    for (std::string move : moves) {
        if (scores.contains(move)) {
            score += scores[move];
        } else {
            std::cout << move << " not in moves \n";
        }
    }

    return score;
}

int part2(std::vector<std::string> moves) {
    // x:loose, y:draw, z:win
    std::map<std::string, int> scores{
        {"A Z", 6 + 2},{"B X", 0 + 1},{"C Y", 3 + 3},
        {"A X", 0 + 3},{"B Y", 3 + 2},{"C Z", 6 + 1},
        {"A Y", 3 + 1},{"B Z", 6 + 3},{"C X", 0 + 2},
    };

    int score = 0;

    for (std::string move : moves) {
        if (scores.contains(move)) {
            score += scores[move];
        } else {
            std::cout << move << " not in moves \n";
        }
    }

    return score;
}


int main() {
    std::vector<std::string> moves = readinput("inp.txt");
    int part1res = part1(moves);
    std::cout << "part1: " << part1res << "\n";
    int part2res = part2(moves);
    std::cout << "part2: " << part2res << "\n";
}