#include <bits/stdc++.h>

struct File {
    std::string name;
    int size;

    friend std::istream &operator>>(std::istream &stream, File &data) {
        std::string line;
        if (std::getline(stream, line)) {
            std::stringstream iss(line);
            if (!(iss >> data.size && iss >> data.name)) {
                stream.setstate(std::ios::failbit);
            }
        }
        return stream;
    }
    friend std::ostream &operator<<(std::ostream &stream, File &data) {
        stream << data.name << ": " << data.size;
        return stream;
    }
};

struct Dir {
    std::string name;
    Dir *parentDir;
    std::vector<File> files;
    std::vector<Dir *> subdirs;

    Dir() {
        name = "/";
    }

    Dir(std::string _name, Dir *parentDir) {
        name = _name;
    }

    Dir(std::string _name, std::vector<File> _files, std::vector<Dir *> _subdirs) {
        name = _name;
        files = _files;
        subdirs = _subdirs;
    }

    friend std::istream &operator>>(std::istream &stream, Dir &data) {
        std::string line;
        bool reading = true;

        std::vector<File> files;
        std::vector<Dir *> subdirs;

        while (reading && std::getline(stream, line)) {
            File currfile;
           

            std::stringstream iss(line);
            if (iss >> currfile) {
                files.push_back(currfile);
            } else {
                iss = std::stringstream(line);
                std::string first;
                iss >> first;

                if (first == "dir") {
                    std::string name;
                    iss >> name;
                    Dir *currsubdir = new Dir();
                    *currsubdir = Dir(name, &data);
                    subdirs.push_back(currsubdir);
                } else {
                    reading = false;
                    break;
                }
            }
        }

        data.subdirs = subdirs;
        data.files = files;
        return stream;
    }

    friend std::ostream &operator<<(std::ostream &stream, Dir &data) {
        stream << "Dir name: " << data.name << "\n";

        for (File f : data.files) {
            stream << "File: " << f << "\n";
        }

        for (Dir *sd : data.subdirs) {
            stream << "Subdir:" << sd->name << "\n";
        }
        return stream;
    }
};

Dir readInput(const char *name) {
    Dir res = Dir();
    Dir *currdir = &res;
    
    Dir parent;

    std::ifstream myfile(name);
    std::string line;
    // first line is always
    std::getline(myfile, line);
    while (std::getline(myfile, line)) {
        std::stringstream iss(line);
        std::string op;
        iss >> op;  // skip $
        iss >> op;

        if (op == "cd") {
            std::cout << line << "\n";
            if (iss >> op) {
                if (op == "..") {
                    std::cout << "currdirr name: " << currdir->name << "\n";
                    std::cout << "parent name: " << parent.name << "\n";
                    if (currdir->parentDir) {
                        std::cout << "here currdirr parent name: " << (*(currdir->parentDir)).name << "\n";
                        currdir = (currdir->parentDir);
                    }
                    //
                } else {
                    for (Dir *subdir : currdir->subdirs) {
                        std::cout << subdir->name << "\n";
                        if (subdir->name == op) {
                            std::cout << "update to parent \n";
                            parent = *currdir;
                            subdir->parentDir = &parent;
                            currdir = subdir;
                            break;
                        }
                    }
                }

                // if (currdir.parentDir) {
                //      std::cout << "here 1 currdirr parent name: " << currdir.name << "\n";
                //         std::cout << "here 1 currdirr parent name: " << (*(currdir.parentDir)).name << "\n";
                //         std::cout << "why\n";
                //     }
            }
        } else if (op == "ls") {
            
            Dir newdir = Dir();
            myfile >> newdir;
            currdir = &newdir;
            std::cout << line << "\n";

        } else {
            std::cout << line << "\n";
            break;
        }

        std::cout << "res name: " << res.name << "\n";
        std::cout << "currdirr name: " << currdir->name << "\n";
        // std::cout << "parent name: " << parent.name << "\n";
        if (currdir->name != "/") {
            std::cout << "currdirr patent name: " << currdir->parentDir->name << "\n";
        }
        for (Dir *subdir : currdir->subdirs) {
            std::cout << subdir->name << "\n";
        }
        std::cout << "\n";
    }

    // while (currdir.name != "/") {
    //     currdir = *(currdir.parentDir);
    // }

    // delete (newdir);

    return res;
}

int main() {
    using std::chrono::duration;
    using std::chrono::duration_cast;
    using std::chrono::high_resolution_clock;
    using std::chrono::microseconds;

    Dir inputdir = readInput("test_inp.txt");

    std::cout << inputdir;

    auto t1 = high_resolution_clock::now();
    // int part1res = firstuniquesubset(inputline, 4);
    // int part2res = firstuniquesubset(inputline, 14);
    auto t2 = high_resolution_clock::now();

    auto ms_int = duration_cast<microseconds>(t2 - t1);

    // std::cout << "Part 1: " << part1res << "\n";
    // std::cout << "Part 2: " << part2res << "\n";
    std::cout << ms_int.count() << " microseconds\n";
}