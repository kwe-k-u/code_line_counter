# Code Line Reader
### What it does?
Code line reader is a java program that iterates through a project (passed via a root directory), counting the number of lines of code in the sub files and folders

## Description
Using multi-threading, Code Line Reader iterates through each file and folder within a directory (unelss added to its .readignore file), creating threads for each file and similarly iterating through each line to count the number of lines and words


## Authors
The program was written by [@Kwe_k_u](https://www.github.com/kwe-k-u) as part of a weekly 2 day hackaton organised with friends


#### Feature Improvement list
- Add UI
- Ignore comments in code 
- Analyse line difference between gtit commits
- Line count for repositories saved on Github
- Add wildcard for .readignore (Eg: %name%.ext) 
