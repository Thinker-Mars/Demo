import sys
import os
# 通过此方法可以避免报错'__main__' is not a package
sys.path.insert(0, os.path.abspath(os.path.join(os.getcwd(), "..")))
from sol.getPos import entrance

if __name__ == "__main__":
    entrance(sys.argv[1], sys.argv[2])



