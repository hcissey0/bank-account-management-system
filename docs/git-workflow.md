#  Git Workflow & Contribution Guide

This project follows a standard feature-branch workflow to ensure code quality and stability.

## Branching Strategy

- **`main`**: The production-ready branch. Do not commit directly to main.
- **`feature/<name>`**: For new features (e.g., `feature/bank-statement`).
- **`bugfix/<name>`**: For bug fixes (e.g., `bugfix/transaction-error`).
- **`refactor/<name>`**: For code cleanup and refactoring.

## Common Commands

### 1. Starting a New Task

Always start by syncing with the latest `main` branch:

```bash
# Switch to main
git checkout main

# Pull latest changes
git pull origin main

# Create a new branch
git checkout -b feature/my-new-feature
```

### 2. During Development

Check the status of your files:
```bash
git status
```

Stage your changes:
```bash
# Stage specific files
git add src/main/java/MyClass.java

# OR stage all changes (use carefully)
git add .
```

Commit your changes with a descriptive message:
```bash
git commit -m "feat: implement savings account interest calculation"
```

### 3. Syncing with Main (Rebase)

If `main` has moved forward while you were working, rebase your branch to keep history clean:

```bash
# Fetch latest
git fetch origin

# Rebase your feature branch onto main
git rebase origin/main
```
*Note: If conflicts occur, resolve them, then run `git rebase --continue`.*

### 4. Sharing Your Work

Push your branch to the remote repository:

```bash
git push -u origin feature/my-new-feature
```

### 5. Merging

Once your feature is complete and tested:

```bash
git checkout main
git merge feature/my-new-feature
git push origin main
```

## ��� Commit Message Convention

We follow the Conventional Commits specification:

- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation only changes
- `style:` Changes that do not affect the meaning of the code (white-space, formatting, etc)
- `refactor:` A code change that neither fixes a bug nor adds a feature
- `test:` Adding missing tests or correcting existing tests

Example:
> `feat: add transaction history view`
