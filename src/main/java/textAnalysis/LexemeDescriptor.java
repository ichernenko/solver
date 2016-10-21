package textAnalysis;

import java.io.Serializable;

public class LexemeDescriptor implements Serializable {
    private boolean
            isFirst = false,
            hasDigit = false,
            hasLetter = false,
            hasFirstUpperCase = false,
            hasLowerCase = false,
            hasUpperCase = false,
            hasRussian = false,
            hasLatin = false,
            hasOther = false;

    char analyze(char ch) {
        char newCh;
        if (Character.isDigit(ch)) {
            newCh = ch;
            if (!hasDigit) {
                hasDigit = true;
            }
        } else {
            newCh = Character.toLowerCase(ch);
            if (Character.isLetter(ch)) {
                if (!hasLetter) {
                    hasLetter = true;
                }
                if (ch != newCh) {
                    if (!isFirst) {
                        if (!hasFirstUpperCase) {
                            hasFirstUpperCase = true;
                        }
                    } else {
                        if (!hasUpperCase) {
                            hasUpperCase = true;
                        }
                    }
                } else {
                    if (!hasLowerCase) {
                        hasLowerCase = true;
                    }
                }

                if ((ch >= 'А' && ch <= 'я') || ch == 'Ё' || ch == 'ё') {
                    if (!hasRussian) {
                        hasRussian = true;
                    }
                } else {
                    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                        if (!hasLatin) {
                            hasLatin = true;
                        }
                    } else {
                        if (!hasOther) {
                            hasOther = true;
                        }
                    }
                }
            } else {
                if (!hasOther) {
                    hasOther = true;
                }
            }
        }
        if (!isFirst) {
            isFirst = true;
        }
        return newCh;
    }

    public String getAllProperties() {
        return (hasDigit ? "цифры" : "") +
                (hasLetter ? (hasDigit ? ", " : "") + "буквы:" : "") +
                (hasLowerCase ? " строчные" : "") +
                (hasUpperCase ? " заглавные" : "") +
                (hasRussian ? " русские" : "") +
                (hasLatin ? " латинские" : "") +
                (hasOther ? (hasDigit || hasLetter ? ", " : "") + "другие символы" : "") +
                (hasFirstUpperCase ? ", 1-я заглавная" : "");
    }

    public boolean isHasDigit() {
        return hasDigit;
    }
    public boolean isHasLetter() {
        return hasLetter;
    }
    public boolean isHasFirstUpperCase() {
        return hasFirstUpperCase;
    }
    public boolean isHasLowerCase() {
        return hasLowerCase;
    }
    public boolean isHasUpperCase() {
        return hasUpperCase;
    }
    public boolean isHasRussian() {
        return hasRussian;
    }
    public boolean isHasLatin() {
        return hasLatin;
    }
    public boolean isHasOther() {
        return hasOther;
    }
}
