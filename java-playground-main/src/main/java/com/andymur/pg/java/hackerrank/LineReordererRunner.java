package com.andymur.pg.java.hackerrank;

import java.util.*;
import java.util.stream.Collectors;

public class LineReordererRunner {

    public static void main(String[] args) {
        LineReordererRunner runner = new LineReordererRunner();
        List<String> logData = new ArrayList<>();

        logData.add("a1 9 2 3 1");
        logData.add("g1 act car");
        logData.add("zo4 4 7");
        logData.add("ab1 off key dog");
        logData.add("a8 act zoo");

        List<String> result = runner.reorderLines(5, logData);
        System.out.println(result);
    }

    public List<String> reorderLines(int logFileSize, List<String> logLines)
    {
        Solver solver = new Solver(logFileSize, logLines);
        return solver.getData().stream().map(TokenList::stringify).collect(Collectors.toList());
    }

    static class Solver {
        public static final String DELIMITER = " ";
        SortedSet<TokenList<?>> data = new TreeSet<>(comparator());

        public Solver(int logFileSize, List<String> logLines) {
            for (int i = 0; i < logFileSize; i++) {
                parseLogLine(i, logLines.get(i));
            }
        }

        public SortedSet<TokenList<?>> getData() {
            return data;
        }

        private static Comparator<TokenList<?>> comparator() {
            return (tokenList1, tokenList2) -> {
                if (tokenList1.type == TokenType.STRING && tokenList2.type == TokenType.STRING) {
                    List<String> firstTokenList = (List<String>) tokenList1.tokens;
                    List<String> secondTokenList = (List<String>) tokenList2.tokens;

                    String firstTokenValue = String.join("", firstTokenList);
                    String secondTokenValue = String.join("", secondTokenList);

                    if (firstTokenValue.equals(secondTokenValue)) {
                        return tokenList1.identifier.compareTo(tokenList2.identifier);
                    } else {
                        return firstTokenValue.compareTo(secondTokenValue);
                    }

                } else if (tokenList1.type == TokenType.INTEGER && tokenList2.type == TokenType.INTEGER) {
                    return tokenList1.lineNumber < tokenList2.lineNumber ? -1 : 1;
                } else {
                    return tokenList1.type == TokenType.STRING ? -1 : 1;
                }
            };
        }

        private void parseLogLine(int lineNumber, String logLine) {
            StringTokenizer tokenizer = new StringTokenizer(logLine, DELIMITER);
            String identifier = tokenizer.nextToken();
            String firstEntry = tokenizer.nextToken();

            if (isInteger(firstEntry, 10)) {
                List<Integer> numbers = new ArrayList<>();
                numbers.add(Integer.parseInt(firstEntry));
                while (tokenizer.hasMoreTokens()) {
                    numbers.add(Integer.parseInt(tokenizer.nextToken()));
                }
                data.add(new TokenList<>(identifier, lineNumber, TokenType.INTEGER, numbers));
            } else {
                List<String> words = new ArrayList<>();
                words.add(firstEntry);
                while (tokenizer.hasMoreTokens()) {
                    words.add(tokenizer.nextToken());
                }
                data.add(new TokenList<>(identifier, lineNumber, TokenType.STRING, words));
            }

        }

        public static boolean isInteger(String s, int radix) {
            if(s.isEmpty()) return false;
            for(int i = 0; i < s.length(); i++) {
                if(i == 0 && s.charAt(i) == '-') {
                    if(s.length() == 1) return false;
                    else continue;
                }
                if(Character.digit(s.charAt(i),radix) < 0) return false;
            }
            return true;
        }
    }

    static class TokenList<E> {
        private final String identifier;
        private final int lineNumber;
        private  final TokenType type;
        private final List<E> tokens;

        public TokenList(String identifier, int lineNumber, TokenType type, List<E> tokens) {
            this.identifier = identifier;
            this.lineNumber = lineNumber;
            this.type = type;
            this.tokens = tokens;
        }

        public String stringify() {
            StringBuilder builder = new StringBuilder(identifier);
            builder.append(" ").append(tokens.stream().map(Objects::toString).collect(Collectors.joining(" ")));
            return builder.toString();
        }

        @Override
        public String toString() {
            return "TokenList{" +
                    "lineNumber=" + lineNumber +
                    ", type=" + type +
                    ", tokens=" + tokens +
                    '}';
        }
    }

    enum TokenType {
        STRING, INTEGER
    }
}
