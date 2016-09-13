package textAnalysis;

import java.io.Serializable;

class WordDescriptor implements Serializable {
    private boolean
            isFirst = false,
            hasDigit = false,
            hasFirstUpperCase = false,
            hasLowerCase = false,
            hasUpperCase = false,
            hasRussian = false,
            hasLatin = false,
            hasOther = false;

    WordDescriptor() {
    }

    char analyze(char ch) {
        char newCh;
        if (Character.isDigit(ch)) {
            newCh = ch;
            hasDigit = true;
        } else {
            newCh = Character.toLowerCase(ch);
            if (Character.isLetter(ch)) {
                if (ch != newCh) {
                    if (!isFirst) {
                        hasFirstUpperCase = true;
                    } else {
                        hasUpperCase = true;
                    }
                } else {
                    hasLowerCase = true;
                }

                if ((ch >= 'А' && ch <= 'я') || ch == 'Ё' || ch == 'ё') {
                    hasRussian = true;
                } else {
                    if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                        hasLatin = true;
                    } else {
                        hasOther = true;
                    }
                }
            } else {
                hasOther = true;
            }
        }
        isFirst = true;
        return newCh;
    }

    public String getAllProperties() {
        return  (hasDigit ? "цифры" : "") +
                ((hasLowerCase || hasUpperCase || hasFirstUpperCase) ? (hasDigit ? ", " : "") + "буквы:" : "") +
                (hasLowerCase ? " строчные" : "") +
                (hasUpperCase ? " заглавные" : "") +
                (hasRussian ? " русские" : "") +
                (hasLatin ? " латинские" : "") +
                (hasOther ? (hasDigit || hasLowerCase || hasUpperCase || hasFirstUpperCase ? ", " : "") + "другие символы" : "") +
                (hasFirstUpperCase ? ", 1-я заглавная" : "");
    }

    public boolean isHasDigit() {
        return hasDigit;
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
