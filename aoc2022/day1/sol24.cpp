#include <fstream>
#include <iostream>
#include <vector>
#include <algorithm>

using std::cout, std::cin;
using std::string;
using std::vector;

typedef vector<int> elve;

// g++ -std=c++14 -Wall -O3
void day1(string fn) {
  std::ifstream infile(fn);

  string line;
  vector<elve> elves;
  elve currelve;
  vector<int> calsum;
  int cursum = 0;
  while (std::getline(infile, line)) {
    if (line == "") {
      elves.push_back(currelve);
      currelve.clear();
      calsum.push_back(cursum);
      cursum = 0;
      continue;
    }
    int val = std::stoi(line);
    currelve.push_back(val);
    cursum += val;
  }
  elves.push_back(currelve);
  calsum.push_back(cursum);

  int part1 = *std::max_element(calsum.begin(), calsum.end());
  cout << "Part 1: " << part1 << "\n";
  std::sort(calsum.begin(), calsum.end(), std::greater<int>());
  int part2 = calsum[0] + calsum[1] + calsum[2];
  cout <<  "Part 2: " << part2 << "\n";
}

int main(int argc, char** argv) {
  string fn = "day.in";
  if (argc > 1) fn = argv[1];

  day1(fn);
}