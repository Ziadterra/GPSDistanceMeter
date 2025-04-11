# Git Most Common Commands:

- Status
- Create new branch locally
- Switch to branch locally
- Pull
- Add
- Commit
- Push

- Status: List changed files:
```bash
git status    
```

- Create new branch locally:
```bash
git checkout -b branch_name
```

- Switch to branch locally:
```bash
git checkout branch_name
```

- Pull: Get remote changes from the same branch:
```bash
git pull    
```

- Pull: Get remote changes from the another branch:
```bash
git pull origin/branch_name 
```

- Add all changes to staging area:
```bash
git add .    
```

- Add specific changes to staging area:
```bash
git add app/src/main/java/com/example/gpsdistancemeter/Distance.java app/src/main/java/com/example/gpsdistancemeter/GpsTracker.java
```
or could be done like that:
```bash
git add app/src/main/java/com/example/gpsdistancemeter
```

- Commit changes:
```bash
git commit -m "comment better to be in preset simple"    
```

- Push commits to new remote branch:
```bash
git push –set-upstream origin new-branch  
```

- Push commits to the existed remote branch:
```bash
git push
```