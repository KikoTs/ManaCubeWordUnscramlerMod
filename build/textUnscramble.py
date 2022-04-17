import glob, os
for file in glob.glob("*.log"):
    lines = []
    with open(file) as f:
        lines = f.readlines()
    
    for line in lines:
        substring = "The first person to unscramble "
        substring2 = "unscrambled the word "
        if substring in line:
            split1 = line.split(substring)[1]
            word = split1.split(" wins ")[0]
            print(word)
            f = open("scrambled.txt", "a")
            f.write(word + "\n")
            f.close()
        elif substring2 in line:
            split1 = line.split(substring2)[1]
            word = split1.split(" in ")[0]
            print(word)
            f = open("unscrambled.txt", "a")
            f.write(word + "\n")
            f.close()
