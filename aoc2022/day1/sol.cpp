#include <algorithm>
#include <fstream>
#include <iostream>
#include <sstream>
#include <string>
#include <vector>

struct Elve {
    std::vector<int> items;
    Elve(std::vector<std::string> stritems) {
        for (std::string stritem : stritems) {
            try {
                items.push_back(std::stoi(stritem));
            } catch (...) {
                std::cout << "Invalid input\n";
            }
        }
    }
    int calories() {
        int sum = 0;
        for (int item : items) {
            sum += item;
        }
        return sum;
    }
};

std::vector<std::vector<std::string>> readinput(const char* name){
    std::ifstream myfile(name);
    std::vector<std::vector<std::string>> strelems;
    if (myfile.is_open()) {
        std::vector<std::string> currelem;
        std::string myline;
        while (std::getline(myfile, myline)) {
            if (myline != "\0") {
                currelem.push_back(myline);
            } else {
                strelems.push_back(currelem);
                currelem.clear();
            }
        }
    }
    return strelems;
}

int main() {
    
    std::vector<std::vector<std::string>> strelves = readinput("inp.txt");

    // Task 
    std::vector<int> calories;
    for (std::vector<std::string> strelve : strelves) {
        Elve currelve = Elve(strelve);
        calories.push_back(currelve.calories());
    }
    std::sort(calories.begin(), calories.end(), std::greater<int>());
    // Results
    int task1res = calories[0];
    int task2res = calories[0] + calories[1] + calories[2];

    std::cout << "Task 1: " << task1res << "\nTask 2: "<< task2res <<"\n";
}