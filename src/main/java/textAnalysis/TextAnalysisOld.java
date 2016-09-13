//package textAnalysis;
//
//import preliminaryTextProcessing.PreliminaryTextProcessing;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class TextAnalysisOld {
//
//    // Метод разбивает текст на предложения и возвращает список этих предложений с характеристиками предложений.
//    // Предложением является список лексем, оканчивающийся '.','!','?','¡'(?!)
//    // Списки предложений состоят из списков слов и списков пунктуаций.
//    public static List<Paragraph> getSentences(String text) {
//        List<Paragraph> paragraphs = new ArrayList<>();
//
//        if (text != null && text.length() > 0) {
//            List<Word> words = new ArrayList<>();
//            List<Punctuation> punctuationMarks = new ArrayList<>();
//            StringBuilder lexeme = new StringBuilder();
//
//            int wordNumber = -1; // До первого слова
//            boolean isSentence = false;
//            boolean isWord = false;
//
//            for (int i = 0; i < text.length(); i++) {
//                char ch = text.charAt(i);
//                if (ch == '.' || ch == '!' || ch == '?' || ch == '…' || ch == '¡') {
//                    if(isWord) {
//                        words.add(new Word(lexeme.toString()));
//                        isWord = false;
//                    }
//                    punctuationMarks.add(new Punctuation(ch, wordNumber));
//                    if(isSentence) {
//                        paragraphs.add(new Paragraph(words, punctuationMarks));
//                        words = new ArrayList<>();
//                        punctuationMarks = new ArrayList<>();
//                        lexeme.setLength(0);
//                        wordNumber = -1;
//                        isSentence = false;
//                    }
//                } else {
//                    if (ch == ',' || ch == ';' || ch == ':' || ch == '(' || ch == '{' || ch == '[' || ch == ')' || ch == '}' || ch == ']' || ch == '—' || ch == '«' || ch == '»') {
//                        if(isWord) {
//                            words.add(new Word(lexeme.toString()));
//                            isWord = false;
//                        }
//                        punctuationMarks.add(new Punctuation(ch, wordNumber));
//                    } else {
//                        if (ch == ' ') {
//                            words.add(new Word(lexeme.toString()));
//                            isWord = false;
//                        } else {
//                            if (!isSentence) {
//                                wordNumber = 0;
//                                isWord = true;
//                                isSentence = true;
//                            } else {
//                                if (!isWord) {
//                                    lexeme.setLength(0);
//                                    wordNumber ++;
//                                    isWord = true;
//                                }
//                            }
//                            lexeme.append(ch);
//                        }
//                    }
//                }
//            }
//        }
//        return paragraphs;
//    }
//
//
//    private void findFullName() {
//        String text = "И.А.Черненко-Ива Обычно,И. А.Хру ,все хорошо но не так как. Хру-Черненко И. А.  ";
//        text = PreliminaryTextProcessing.getResult(text);
//        System.out.println(text);
//        Pattern p1 = Pattern.compile("[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)*? [А-ЯЁ]\\.[А-ЯЁ]\\.|[А-ЯЁ]\\.[А-ЯЁ]\\.[А-ЯЁ][а-яё]+(-[А-ЯЁ][а-яё]+)*");
//        Matcher m = p1.matcher (text);
//        while (m.find()) {
//            System.out.println(m.group(0));
//        }
//
//    }
//
//    // Метод возвращает строку, состоящую из отформатированных предложений
//    public static String getResult(List<Paragraph> paragraphs) {
//        StringBuilder sb = new StringBuilder();
//        paragraphs.forEach(m -> {
//            sb.append(m.getParagraphWithSpaces());
//            sb.append("<br/>");
//        });
//        return sb.toString();
//    }
//}
