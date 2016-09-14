package preliminaryTextProcessing;

import java.util.regex.Pattern;

public class PreliminaryTextProcessing {

    // Примечание: Необходимо все последовательности знаков пунктуации заменить на какой-то неиспользуемый символ
    // (например, ?!!! на ¡, ...... на …);
    // Между Фамилиями и инициалами может стоять "неразрывный пробел";
    // При наличии знака пунктуации, пробел после него удаляется;

    private static Pattern
    // удаление начальных и конечных непечатаемых символов
    p1 = Pattern.compile("^\\s+|\\s+$"),
    // замена всех непечатаемых сиволов на пробел, кроме знака перехода на новую строку ('\n') - признака нового параграфа
    p2 = Pattern.compile("[ \\t\\r\\f]+"),
    // замена кавычек на закрывающие
    p3 = Pattern.compile("(\\S[^-–— ])\"|“"),
    // замена кавычек на закрывающие
    p4 = Pattern.compile("”|’"),
    // замена кавычек на открывающие
    p5 = Pattern.compile(" *([\"„“‘])"),
    // замена дефисов, с ведущим пробелом, и средних тире на длинные тире, без обрамляющих пробелов
    p6 = Pattern.compile(" - *| *– *"),
    // удаление начальных и конечных пробелов перед указанными символами
    p7 = Pattern.compile(" *([\n\\.,;!\\?:\\(\\{\\[\\)\\}\\]«»/+*<>=@&\\\\]) *"),
    // замена трех и более точек на символ "многоточие"
    p8 = Pattern.compile("\\.{3,}"),
    // замена повторяющихся знаков на один такой знак
    p9 = Pattern.compile("([\n\\.,;!\\?:])\\1+"),
    // замена вместе стоящих восклисательного и вопросительного знака на знак "¡"
    p10 = Pattern.compile("([!\\?]){2,}"),
    // замена амперсанда на символ-близнец "＆" (для корректной обработки ответа отсервера на клиенте)
    p11 = Pattern.compile("&"),
    // удаление пометок в тексте вида (!) или (?)
    p12 = Pattern.compile("\\(([\\?\\!])\\)"),
    // удаление пробелов в числах
    p13 = Pattern.compile("([0-9]) ([0-9])*?"),
    // удаление пробела между ведущим номером или знаком доллара и цифрой
    p14 = Pattern.compile("([№\\$#])([0-9])"),
    // добавление пробела между ведущей цифрой и последующим символом (не знаком пунктуации)
    p15 = Pattern.compile("([0-9]|°|%)(\\pL|№|#|\\$)"),
    // замена дефиса, среднего и малого тире, идущих после закрывающих кавычек, закрывающих скобок, запятых, и пробела на тоже, но без пробела
    p16 = Pattern.compile("([»\\)\\]\\}])([-–—]) ");

        public static String getResult(String text) {
        if (text != null && text.length() > 0) {
            text = p1.matcher(text).replaceAll("");
            text = p2.matcher(text).replaceAll(" ");
            text = p3.matcher(text).replaceAll("$1»");
            text = p4.matcher(text).replaceAll("»");
            text = p5.matcher(text).replaceAll("«");
            text = p6.matcher(text).replaceAll("—");
            text = p7.matcher(text).replaceAll("$1");
            text = p8.matcher(text).replaceAll("…");
            text = p9.matcher(text).replaceAll("$1");
            text = p10.matcher(text).replaceAll("¡");
            text = p11.matcher(text).replaceAll("＆");
            text = p12.matcher(text).replaceAll("");
            text = p13.matcher(text).replaceAll("$1$2");
            text = p14.matcher(text).replaceAll("$1 $2");
            text = p15.matcher(text).replaceAll("$1 $2");
            text = p16.matcher(text).replaceAll("$1—");

            // если у текста нет завершающего знака пунктуации, то добавляется точка в конец текста
            char ch = text.charAt(text.length() - 1);
            if (ch != '.' && ch != '!' && ch != '?' && ch != '…' && ch != '¡') {
                return text + '.' + '\n';
            }
        }

        return text + '\n';
    }
}